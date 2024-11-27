package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ArBillBalanceDTO;
import com.efitops.basesetup.dto.ReceiptDTO;
import com.efitops.basesetup.dto.ReceiptInvDetailsDTO;
import com.efitops.basesetup.entity.ArBillBalanceVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ReceiptInvDetailsVO;
import com.efitops.basesetup.entity.ReceiptVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.ArBillBalanceRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ReceiptInvDetailsRepo;
import com.efitops.basesetup.repo.ReceiptRepo;

@Service
public class ARServiceImpl implements ARService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ARServiceImpl.class);

	@Autowired
	ReceiptRepo receiptRepo;

	@Autowired
	ReceiptInvDetailsRepo receiptInvDetailsRepo;

	@Autowired
	ArBillBalanceRepo arBillBalanceRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	// Receipt
	@Override
	public List<ReceiptVO> getAllReceiptReceivableByOrgId(Long orgId) {
		List<ReceiptVO> receiptReceivableVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received ReceiptReceivable BY OrgId : {}", orgId);
			receiptReceivableVO = receiptRepo.getAllReceiptReceivableByOrgId(orgId);
		}
		return receiptReceivableVO;
	}

	@Override
	public List<ReceiptVO> getAllReceiptReceivableById(Long id) {
		List<ReceiptVO> receiptReceivableVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ReceiptReceivable BY Id : {}", id);
			receiptReceivableVO = receiptRepo.getAllReceiptReceivableById(id);
		}
		return receiptReceivableVO;
	}

	@Override
	public Map<String, Object> updateCreateReceiptReceivable(@Valid ReceiptDTO receiptDTO) throws ApplicationException {
		String screenCode = "RT";
		ReceiptVO receiptVO = new ReceiptVO();
		String message;
		if (ObjectUtils.isNotEmpty(receiptDTO.getId())) {
			receiptVO = receiptRepo.findById(receiptDTO.getId())
					.orElseThrow(() -> new ApplicationException("Receipt Not Found!"));
			receiptVO.setUpdatedBy(receiptDTO.getCreatedBy());
			createUpdateReceiptVOByReceiptDTO(receiptDTO, receiptVO);
			message = "Receipt Updated Successfully";
		} else {
			// GETDOCID API
			String docId = receiptRepo.getReceiptDocId(receiptDTO.getOrgId(), receiptDTO.getFinYear(),
					receiptDTO.getBranchCode(), screenCode);
			receiptVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(receiptDTO.getOrgId(), receiptDTO.getFinYear(),
							receiptDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			receiptVO.setUpdatedBy(receiptDTO.getCreatedBy());
			receiptVO.setCreatedBy(receiptDTO.getCreatedBy());
			createUpdateReceiptVOByReceiptDTO(receiptDTO, receiptVO);
			message = "Receipt Created Successfully";
		}

		receiptRepo.save(receiptVO);
		Map<String, Object> response = new HashMap<>();
		response.put("receiptVO", receiptVO);
		response.put("message", message);
		return response;
	}

	private ReceiptVO createUpdateReceiptVOByReceiptDTO(@Valid ReceiptDTO receiptDTO, ReceiptVO receiptVO) {
		receiptVO.setBranch(receiptDTO.getBranch());
		receiptVO.setBranchCode(receiptDTO.getBranchCode());
		receiptVO.setCustomer(receiptDTO.getCustomer());
		receiptVO.setClient(receiptDTO.getClient());
		receiptVO.setCreatedBy(receiptDTO.getCreatedBy());
		receiptVO.setActive(receiptDTO.isActive());
		receiptVO.setCancel(receiptDTO.isCancel());
		receiptVO.setCancelRemarks(receiptDTO.getCancelRemarks());
		receiptVO.setFinYear(receiptDTO.getFinYear());
		receiptVO.setType(receiptDTO.getType());
		receiptVO.setCustomerName(receiptDTO.getCustomerName());
		receiptVO.setCustomerCode(receiptDTO.getCustomerCode());
		receiptVO.setBankCashAcc(receiptDTO.getBankCashAcc());
		receiptVO.setReceiptAmt(receiptDTO.getReceiptAmt());
		receiptVO.setBankChargeAcc(receiptDTO.getBankChargeAcc());
		receiptVO.setBankCharges(receiptDTO.getBankCharges());
		receiptVO.setInCurrencyBnkChargs(receiptDTO.getInCurrencyBnkChargs());
		receiptVO.setTdsAmt(receiptDTO.getTdsAmt());
		receiptVO.setInCurrencyTdsAmt(receiptDTO.getInCurrencyTdsAmt());
		receiptVO.setChequeBank(receiptDTO.getChequeBank());
		receiptVO.setReceiptType(receiptDTO.getReceiptType());
		receiptVO.setChequeUtiNo(receiptDTO.getChequeUtiNo());
		receiptVO.setChequeUtiDt(receiptDTO.getChequeUtiDt());
		receiptVO.setReceivedFrom(receiptDTO.getReceivedFrom());
		receiptVO.setReceiptType1(receiptDTO.getReceiptType1());
		receiptVO.setCurrency(receiptDTO.getCurrency());
		receiptVO.setCurrencyAmount(receiptDTO.getCurrencyAmount());
		receiptVO.setTaxAmt(receiptDTO.getTaxAmt());
		receiptVO.setBranchCode(receiptDTO.getBranchCode());
		receiptVO.setOrgId(receiptDTO.getOrgId());
		receiptVO.setNetAmount(receiptDTO.getNetAmount());
		receiptVO.setRemarks(receiptDTO.getRemarks());

		if (ObjectUtils.isNotEmpty(receiptVO.getId())) {
			List<ReceiptInvDetailsVO> receiptInvDetailsVO1 = receiptInvDetailsRepo.findByReceiptVO(receiptVO);
			receiptInvDetailsRepo.deleteAll(receiptInvDetailsVO1);
		}

		List<ReceiptInvDetailsVO> receiptInvDetailsVOs = new ArrayList<>();
		for (ReceiptInvDetailsDTO receiptInvDetailsDTO : receiptDTO.getReceiptInvDetailaDTO()) {
			ReceiptInvDetailsVO receiptInvDetailsVO = new ReceiptInvDetailsVO();

			receiptInvDetailsVO.setInvNo(receiptInvDetailsDTO.getInvNo());
			receiptInvDetailsVO.setInvDate(receiptInvDetailsDTO.getInvDate());
			receiptInvDetailsVO.setRefNo(receiptInvDetailsDTO.getRefNo());
			receiptInvDetailsVO.setRefDate(receiptInvDetailsDTO.getRefDate());
			receiptInvDetailsVO.setMasterRef(receiptInvDetailsDTO.getMasterRef());
			receiptInvDetailsVO.setHouseRef(receiptInvDetailsDTO.getHouseRef());
			receiptInvDetailsVO.setCurrency(receiptInvDetailsDTO.getCurrency());
			receiptInvDetailsVO.setExRate(receiptInvDetailsDTO.getExRate());
			receiptInvDetailsVO.setAmount(receiptInvDetailsDTO.getAmount());
			receiptInvDetailsVO.setChargeAmt(receiptInvDetailsDTO.getChargeAmt());
			receiptInvDetailsVO.setOutstanding(receiptInvDetailsDTO.getOutstanding());
			receiptInvDetailsVO.setSettled(receiptInvDetailsDTO.getSettled());
			receiptInvDetailsVO.setRecExRate(receiptInvDetailsDTO.getRecExRate());
			receiptInvDetailsVO.setTxnSettled(receiptInvDetailsDTO.getTxnSettled());
			receiptInvDetailsVO.setGainAmt(receiptInvDetailsDTO.getGainAmt());
			receiptInvDetailsVO.setReceiptVO(receiptVO);
			receiptInvDetailsVOs.add(receiptInvDetailsVO);
		}
		receiptVO.setReceiptInvDetailsVO(receiptInvDetailsVOs);
		return receiptVO;
	}

	@Override
	public List<ReceiptVO> getReceiptReceivableByActive() {
		return receiptRepo.findReceiptReceivablesByActive();
	}

	@Override
	public List<Map<String, Object>> getCustomerNameAndCodeForReceipt(Long orgId) {
		Set<Object[]> customerName = receiptRepo.getCustomerNameAndCodeForReceipt(orgId);
		return getCustomerName(customerName);
	}

	private List<Map<String, Object>> getCustomerName(Set<Object[]> customer) {
		List<Map<String, Object>> doctypeMappingDetails = new ArrayList<>();
		for (Object[] sup : customer) {
			Map<String, Object> doctype = new HashMap<>();
			doctype.put("customerName", sup[0] != null ? sup[0].toString() : "");
			doctype.put("customerCode", sup[1] != null ? sup[1].toString() : "");
			doctypeMappingDetails.add(doctype);
		}

		return doctypeMappingDetails;
	}

	@Override
	public String getReceiptDocId(Long orgId, String finYear, String branch, String branchCode) {
		String ScreenCode = "RT";
		String result = receiptRepo.getReceiptDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	// ArBillBalance
	@Override
	public List<ArBillBalanceVO> getAllArBillBalanceByOrgId(Long orgId) {
		List<ArBillBalanceVO> arBillBalanceVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received ArApBillBalance BY OrgId : {}", orgId);
			arBillBalanceVO = arBillBalanceRepo.getAllArBillBalanceByOrgId(orgId);
		}
		return arBillBalanceVO;
	}

	@Override
	public List<ArBillBalanceVO> getAllArBillBalanceById(Long id) {
		List<ArBillBalanceVO> arBillBalanceVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ArApBillBalance BY Id : {}", id);
			arBillBalanceVO = arBillBalanceRepo.getAllArBillBalanceById(id);
		}
		return arBillBalanceVO;
	}

	@Override
	public Map<String, Object> updateCreateArBillBalance(@Valid ArBillBalanceDTO arBillBalanceDTO)
			throws ApplicationException {
		String screenCode = "ARB";
		ArBillBalanceVO arBillBalanceVO = new ArBillBalanceVO();
		String message;
		if (ObjectUtils.isNotEmpty(arBillBalanceDTO.getId())) {
			arBillBalanceVO = arBillBalanceRepo.findById(arBillBalanceDTO.getId())
					.orElseThrow(() -> new ApplicationException("AR Bill Balance Not Found!"));
			createUpdateArBillBalanceVOByArBillBalanceDTO(arBillBalanceDTO, arBillBalanceVO);
			message = "AR Bill Balance Updated Successfully";
			arBillBalanceVO.setUpdatedBy(arBillBalanceDTO.getCreatedBy());
		} else {
			// GETDOCID API
			String docId = arBillBalanceRepo.getArBillBalanceDocId(arBillBalanceDTO.getOrgId(),
					arBillBalanceDTO.getFinYear(), arBillBalanceDTO.getBranchCode(), screenCode);
			arBillBalanceVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(arBillBalanceDTO.getOrgId(),
							arBillBalanceDTO.getFinYear(), arBillBalanceDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			arBillBalanceVO.setUpdatedBy(arBillBalanceDTO.getCreatedBy());
			arBillBalanceVO.setCreatedBy(arBillBalanceDTO.getCreatedBy());
			createUpdateArBillBalanceVOByArBillBalanceDTO(arBillBalanceDTO, arBillBalanceVO);
			message = "AR Bill Balance Created Successfully";
		}

		arBillBalanceRepo.save(arBillBalanceVO);
		Map<String, Object> response = new HashMap<>();
		response.put("arBillBalanceVO", arBillBalanceVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateArBillBalanceVOByArBillBalanceDTO(@Valid ArBillBalanceDTO arBillBalanceDTO,
			ArBillBalanceVO arBillBalanceVO) {
		arBillBalanceVO.setAccName(arBillBalanceDTO.getAccName());
		arBillBalanceVO.setPartyName(arBillBalanceDTO.getPartyName());
		arBillBalanceVO.setPartyCode(arBillBalanceDTO.getPartyCode());
		arBillBalanceVO.setCreditDays(arBillBalanceDTO.getCreditDays());
		arBillBalanceVO.setDocType(arBillBalanceDTO.getDocType());
		arBillBalanceVO.setCurrency(arBillBalanceDTO.getCurrency());
		arBillBalanceVO.setYearEndExRate(arBillBalanceDTO.getYearEndExRate());
		arBillBalanceVO.setBillExRate(arBillBalanceDTO.getBillExRate());
		arBillBalanceVO.setPostBillExRate(arBillBalanceDTO.isPostBillExRate());
		arBillBalanceVO.setBillNo(arBillBalanceDTO.getBillNo());
		arBillBalanceVO.setBillDate(arBillBalanceDTO.getBillDate());
		arBillBalanceVO.setSuppRefNo(arBillBalanceDTO.getSuppRefNo());
		arBillBalanceVO.setSuppRefDate(arBillBalanceDTO.getSuppRefDate());
		arBillBalanceVO.setDueDate(arBillBalanceDTO.getDueDate());
		arBillBalanceVO.setDebitAmt(arBillBalanceDTO.getDebitAmt());
		arBillBalanceVO.setCreditAmt(arBillBalanceDTO.getCreditAmt());
		arBillBalanceVO.setVoucherNo(arBillBalanceDTO.getVoucherNo());
		arBillBalanceVO.setAdjustmentDone(arBillBalanceDTO.isAdjustmentDone());
		arBillBalanceVO.setActive(arBillBalanceDTO.isActive());
		arBillBalanceVO.setBranch(arBillBalanceDTO.getBranch());
		arBillBalanceVO.setBranchCode(arBillBalanceDTO.getBranchCode());
		arBillBalanceVO.setCreatedBy(arBillBalanceDTO.getCreatedBy());
		arBillBalanceVO.setCancel(arBillBalanceDTO.isCancel());
		arBillBalanceVO.setCancelRemarks(arBillBalanceDTO.getCancelRemarks());
		arBillBalanceVO.setFinYear(arBillBalanceDTO.getFinYear());
		arBillBalanceVO.setOrgId(arBillBalanceDTO.getOrgId());
	}

	@Override
	public List<ArBillBalanceVO> getArBillBalanceByActive() {
		return arBillBalanceRepo.findArBillBalanceByActive();
	}

	@Override
	public List<Map<String, Object>> getPartyNameAndCodeForArBillBalance(Long orgId) {
		Set<Object[]> partyName = arBillBalanceRepo.getPartyNameAndCodeForArBillBalance(orgId);
		return getPartyName(partyName);
	}

	private List<Map<String, Object>> getPartyName(Set<Object[]> customer) {
		List<Map<String, Object>> doctypeMappingDetails = new ArrayList<>();
		for (Object[] sup : customer) {
			Map<String, Object> doctype = new HashMap<>();
			doctype.put("partyName", sup[0] != null ? sup[0].toString() : "");
			doctype.put("partyCode", sup[1] != null ? sup[1].toString() : "");
			doctypeMappingDetails.add(doctype);
		}

		return doctypeMappingDetails;
	}

	// Receipt Register
	@Override
	public List<Map<String, Object>> getAllReceiptRegister(Long orgId, String fromDate, String toDate,
			String subLedgerName) {
		Set<Object[]> register = receiptRepo.findAllReceiptRegister(orgId, fromDate, toDate, subLedgerName);
		return getRegister(register);
	}

	private List<Map<String, Object>> getRegister(Set<Object[]> getRegister) {
		List<Map<String, Object>> doctypeMappingDetails = new ArrayList<>();
		for (Object[] sup : getRegister) {
			Map<String, Object> doctype = new HashMap<>();
			doctype.put("docId", sup[0] != null ? sup[0].toString() : "");
			doctype.put("docDate", sup[1] != null ? sup[1].toString() : "");
			doctype.put("subLedgerName", sup[2] != null ? sup[2].toString() : "");
			doctype.put("bankCash", sup[3] != null ? sup[3].toString() : "");
			doctype.put("receiptAmount", sup[4] != null ? sup[4].toString() : "");
			doctype.put("bankCharges", sup[5] != null ? sup[5].toString() : "");
			doctype.put("taxAmount", sup[6] != null ? sup[6].toString() : "");
			doctype.put("tdsAmount", sup[7] != null ? sup[7].toString() : "");
			doctype.put("invoiceNo", sup[8] != null ? sup[8].toString() : "");
			doctype.put("invoiceDate", sup[9] != null ? sup[9].toString() : "");
			doctype.put("refNo", sup[10] != null ? sup[10].toString() : "");
			doctype.put("refDate", sup[11] != null ? sup[11].toString() : "");
			doctype.put("chequeBank", sup[12] != null ? sup[12].toString() : "");
			doctype.put("chequeNo", sup[13] != null ? sup[13].toString() : "");
			doctype.put("amount", sup[14] != null ? sup[14].toString() : "");
			doctype.put("outstanding", sup[15] != null ? sup[15].toString() : "");
			doctype.put("setteled", sup[16] != null ? sup[16].toString() : "");
			doctype.put("createdOn", sup[17] != null ? sup[17].toString() : "");
			doctype.put("createdBy", sup[18] != null ? sup[18].toString() : "");

			doctypeMappingDetails.add(doctype);
		}

		return doctypeMappingDetails;
	}

	@Override
	public String getArBillBalanceDocId(Long orgId, String finYear, String branch, String branchCode) {
		String ScreenCode = "ARB";
		String result = receiptRepo.getArBillBalanceDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

}
