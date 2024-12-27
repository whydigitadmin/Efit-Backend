package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PurchaseInvoiceDTO;
import com.efitops.basesetup.dto.PurchaseInvoiceItemDTO;
import com.efitops.basesetup.dto.PurchaseReturnDTO;
import com.efitops.basesetup.dto.PurchaseReturnItemDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.PurchaseInvoiceItemVO;
import com.efitops.basesetup.entity.PurchaseInvoiceVO;
import com.efitops.basesetup.entity.PurchaseReturnItemVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.PurchaseInvoiceItemRepo;
import com.efitops.basesetup.repo.PurchaseInvoiceRepo;
import com.efitops.basesetup.repo.PurchaseReturnItemRepo;
import com.efitops.basesetup.repo.PurchaseReturnRepo;

@Service
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseReturnServiceImpl.class);

	@Autowired
	PurchaseReturnRepo purchaseReturnRepo;

	@Autowired
	PurchaseReturnItemRepo purchaseReturnItemRepo;

	@Autowired
	PurchaseInvoiceRepo purchaseInvoiceRepo;

	@Autowired
	PurchaseInvoiceItemRepo purchaseInvoiceItemRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	// purchaseReturn

	@Override
	public Map<String, Object> createUpdatePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO)
			throws ApplicationException {
		PurchaseReturnVO purchaseReturnVO = new PurchaseReturnVO();
		String message;
		String screenCode = "PCR";
		if (ObjectUtils.isNotEmpty(purchaseReturnDTO.getId())) {
			purchaseReturnVO = purchaseReturnRepo.findById(purchaseReturnDTO.getId())
					.orElseThrow(() -> new ApplicationException("PurchaseReturn Enquiry details"));
			message = "PurchaseReturn Updated Successfully";
			purchaseReturnVO.setUpdatedBy(purchaseReturnDTO.getCreatedBy());

		} else {

			String docId = purchaseReturnRepo.getPurchaseReturnDocId(purchaseReturnDTO.getOrgId(), screenCode);
			purchaseReturnVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(purchaseReturnDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			purchaseReturnVO.setCreatedBy(purchaseReturnDTO.getCreatedBy());
			purchaseReturnVO.setUpdatedBy(purchaseReturnDTO.getCreatedBy());

			message = "PurchaseReturn Created Successfully";
		}
		createUpdatedPurchaseReturnVOFromPurchaseReturnDTO(purchaseReturnDTO, purchaseReturnVO);
		purchaseReturnRepo.save(purchaseReturnVO);
		Map<String, Object> response = new HashMap<>();
		response.put("purchaseReturnVO", purchaseReturnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedPurchaseReturnVOFromPurchaseReturnDTO(PurchaseReturnDTO purchaseReturnDTO,
			PurchaseReturnVO purchaseReturnVO) {
		purchaseReturnVO.setSupplierName(purchaseReturnDTO.getSupplierName());
		purchaseReturnVO.setSupplierCode(purchaseReturnDTO.getSupplierCode());
		purchaseReturnVO.setCustomerName(purchaseReturnDTO.getCustomerName());
		purchaseReturnVO.setPurchaseInvoiceNo(purchaseReturnDTO.getPurchaseInvoiceNo());
		purchaseReturnVO.setPurchaseInvoiceDate(purchaseReturnDTO.getPurchaseInvoiceDate());
		purchaseReturnVO.setPoNo(purchaseReturnDTO.getPoNo());
		purchaseReturnVO.setGstNo(purchaseReturnDTO.getGstNo());
		purchaseReturnVO.setGstState(purchaseReturnDTO.getGstState());
		purchaseReturnVO.setAddress(purchaseReturnDTO.getAddress());
		purchaseReturnVO.setGatePassNo(purchaseReturnDTO.getGatePassNo());
		purchaseReturnVO.setIsReverseChrg(purchaseReturnDTO.getIsReverseChrg());
		purchaseReturnVO.setCurrency(purchaseReturnDTO.getCurrency());
		purchaseReturnVO.setExchangeRate(purchaseReturnDTO.getExchangeRate());
		purchaseReturnVO.setInvDcNo(purchaseReturnDTO.getInvDcNo());
		purchaseReturnVO.setInvDcDate(purchaseReturnDTO.getInvDcDate());
		purchaseReturnVO.setGstType(purchaseReturnDTO.getGstType());
		purchaseReturnVO.setToLocation(purchaseReturnDTO.getToLocation());
		purchaseReturnVO.setRemarks(purchaseReturnDTO.getRemarks());
		purchaseReturnVO.setOrgId(purchaseReturnDTO.getOrgId());
		purchaseReturnVO.setActive(purchaseReturnDTO.isActive());
		purchaseReturnVO.setCreatedBy(purchaseReturnDTO.getCreatedBy());

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(purchaseReturnDTO.getId())) {
			List<PurchaseReturnItemVO> purchaseReturnItemVO1 = purchaseReturnItemRepo
					.findByPurchaseReturnVO(purchaseReturnVO);
			purchaseReturnItemRepo.deleteAll(purchaseReturnItemVO1);

		}

		List<PurchaseReturnItemVO> purchaseReturnItemVOs = new ArrayList<>();
		for (PurchaseReturnItemDTO purchaseReturnItemDTO : purchaseReturnDTO.getPurchaseReturnItemDTO()) {
			PurchaseReturnItemVO purchaseReturnItemVO = new PurchaseReturnItemVO();
			purchaseReturnItemVO.setItemCode(purchaseReturnItemDTO.getItemCode());
			purchaseReturnItemVO.setItemName(purchaseReturnItemDTO.getItemName());
			purchaseReturnItemVO.setHsnSacCode(purchaseReturnItemDTO.getHsnSacCode());
			purchaseReturnItemVO.setTaxCode(purchaseReturnItemDTO.getTaxCode());
			purchaseReturnItemVO.setPrimaryUnit(purchaseReturnItemDTO.getPrimaryUnit());
			purchaseReturnItemVO.setPoRate(purchaseReturnItemDTO.getPoRate());
			purchaseReturnItemVO.setRejectQty(purchaseReturnItemDTO.getRejectQty());
			purchaseReturnItemVO.setUnitPrice(purchaseReturnItemDTO.getUnitPrice());
			purchaseReturnItemVO.setSgst(purchaseReturnItemDTO.getSgst());
			purchaseReturnItemVO.setCgst(purchaseReturnItemDTO.getCgst());
			purchaseReturnItemVO.setIgst(purchaseReturnItemDTO.getIgst());

			BigDecimal taxAmount = BigDecimal.ZERO;
			BigDecimal landedValues = BigDecimal.ZERO;

			BigDecimal amountSet = purchaseReturnItemDTO.getUnitPrice().multiply(purchaseReturnItemDTO.getRejectQty());
			purchaseReturnItemVO.setAmount(amountSet);

			if (purchaseReturnVO.getGstType() == null || purchaseReturnVO.getGstType().isEmpty()
					|| !purchaseReturnVO.getGstType().equalsIgnoreCase("INTRA")
							&& !purchaseReturnVO.getGstType().equalsIgnoreCase("INTER")) {
				purchaseReturnItemVO.setIgst(BigDecimal.ZERO);
				purchaseReturnItemVO.setCgst(BigDecimal.ZERO);
				purchaseReturnItemVO.setSgst(BigDecimal.ZERO);
				purchaseReturnItemVO.setTaxValue(BigDecimal.ZERO);
			} else {
				if (purchaseReturnVO.getGstType().equalsIgnoreCase("INTER")) {
					purchaseReturnItemVO.setIgst(purchaseReturnItemDTO.getIgst());
					BigDecimal igstAmount = purchaseReturnItemDTO.getIgst().multiply(purchaseReturnItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					purchaseReturnItemVO.setCgst(BigDecimal.ZERO);
					purchaseReturnItemVO.setSgst(BigDecimal.ZERO);
					taxAmount = igstAmount;
					purchaseReturnItemVO.setTaxValue(taxAmount);
				} else if (purchaseReturnVO.getGstType().equalsIgnoreCase("INTRA")) {
					purchaseReturnItemVO.setCgst(purchaseReturnItemDTO.getCgst());
					purchaseReturnItemVO.setSgst(purchaseReturnItemDTO.getSgst());
					BigDecimal sgstAmount = purchaseReturnItemDTO.getSgst().multiply(purchaseReturnItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					BigDecimal cgstAmount = purchaseReturnItemDTO.getCgst().multiply(purchaseReturnItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					purchaseReturnItemVO.setIgst(BigDecimal.ZERO);
					taxAmount = cgstAmount.add(sgstAmount);
					purchaseReturnItemVO.setTaxValue(taxAmount);
				}
			}
			totalTaxAmount = totalTaxAmount.add(purchaseReturnItemVO.getTaxValue());

			landedValues = purchaseReturnItemVO.getAmount().add(purchaseReturnItemVO.getTaxValue());
			purchaseReturnItemVO.setLandedValue(landedValues);
			netAmount = netAmount.add(purchaseReturnItemVO.getLandedValue());
			totalAmount = purchaseReturnItemVO.getTaxValue().add(purchaseReturnItemVO.getLandedValue());

			purchaseReturnItemVO.setPurchaseReturnVO(purchaseReturnVO);
			purchaseReturnItemVOs.add(purchaseReturnItemVO);
		}

		purchaseReturnVO.setTotalAmount(totalAmount);
		purchaseReturnVO.setNetAmount(netAmount);
		purchaseReturnVO.setTotalAmountTax(totalTaxAmount);
		purchaseReturnVO
				.setAmountInWords(amountInWordsConverterService.convert(purchaseReturnVO.getTotalAmount().longValue()));
		purchaseReturnVO.setPurchaseReturnItemVO(purchaseReturnItemVOs);

	}

	@Override
	public List<PurchaseReturnVO> getAllPurchaseReturnByOrgId(Long orgId) {

		return purchaseReturnRepo.getAllPurchaseReturnByOrgId(orgId);
	}

	@Override
	public PurchaseReturnVO getPurchaseReturnById(Long id) {

		return purchaseReturnRepo.getPurchaseReturnById(id);
	}

	@Override
	public String getPurchaseReturnDocId(Long orgId) {
		String ScreenCode = "PCR";
		String result = purchaseReturnRepo.getPurchaseReturnDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPurchaseInvoiceNumberFromPurchaseInvoice(Long orgId,String supplierCode) {
		Set<Object[]> chType = purchaseReturnRepo.getPurchaseInvoiceNumberFromPurchaseInvoice(orgId,supplierCode);
		return getPurchaseInvoiceNumber(chType);
	}

	private List<Map<String, Object>> getPurchaseInvoiceNumber(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("purchaseInvoiceNo", ch[0] != null ? ch[0].toString() : "");
			map.put("purchaseInvoiceDate", ch[1] != null ? ch[1].toString() : "");
			map.put("grnTime", ch[2] != null ? ch[2].toString() : "");
			map.put("poNo", ch[3] != null ? ch[3].toString() : "");
			map.put("gstNo", ch[4] != null ? ch[4].toString() : "");
			map.put("address", ch[5] != null ? ch[5].toString() : "");
			map.put("gatePassNo", ch[6] != null ? ch[6].toString() : "");
			map.put("currency", ch[7] != null ? ch[7].toString() : "");
			map.put("exchangeRate", ch[8] != null ? ch[8].toString() : "");
			map.put("invdcNo", ch[9] != null ? ch[9].toString() : "");
			map.put("invdcDate", ch[10] != null ? ch[10].toString() : "");
			map.put("gstType", ch[11] != null ? ch[11].toString() : "");
			map.put("customerName", ch[12] != null ? ch[12].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getLocationFromStockLocation(Long orgId) {
		Set<Object[]> chType = purchaseReturnRepo.getLocationFromStockLocation(orgId);
		return getLocation(chType);
	}

	private List<Map<String, Object>> getLocation(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("location", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemCodeAndItemDescFromPurchsaeInvoice(Long orgId, String purchaseInvoiceNo) {
		Set<Object[]> chType = purchaseReturnRepo.getItemCodeAndItemDescFromPurchsaeInvoice(orgId, purchaseInvoiceNo);
		return getItemCodeAndItemDesc(chType);
	}

	private List<Map<String, Object>> getItemCodeAndItemDesc(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemCode", ch[0] != null ? ch[0].toString() : "");
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			map.put("hsnScaCode", ch[2] != null ? ch[2].toString() : "");
			map.put("taxType", ch[3] != null ? ch[3].toString() : "");
			map.put("primaryUnit", ch[4] != null ? ch[4].toString() : "");
			map.put("poRate", ch[5] != null ? ch[5].toString() : "");
			map.put("unitPrice", ch[6] != null ? ch[6].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// purchaseInvoice

	@Override
	public Map<String, Object> createUpdatePurchaseInvoice(PurchaseInvoiceDTO purchaseInvoiceDTO)
			throws ApplicationException {
		PurchaseInvoiceVO purchaseInvoiceVO = new PurchaseInvoiceVO();
		String message;
		String screenCode = "PCI";
		if (ObjectUtils.isNotEmpty(purchaseInvoiceDTO.getId())) {
			purchaseInvoiceVO = purchaseInvoiceRepo.findById(purchaseInvoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("PurchaseInvoice Enquiry details"));
			message = "PurchaseInvoice Updated Successfully";
			purchaseInvoiceVO.setUpdatedBy(purchaseInvoiceDTO.getCreatedBy());

		} else {

			String docId = purchaseInvoiceRepo.getPurchaseInvoiceDocId(purchaseInvoiceDTO.getOrgId(), screenCode);
			purchaseInvoiceVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(purchaseInvoiceDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			purchaseInvoiceVO.setCreatedBy(purchaseInvoiceDTO.getCreatedBy());
			purchaseInvoiceVO.setUpdatedBy(purchaseInvoiceDTO.getCreatedBy());

			message = "PurchaseInvoice Created Successfully";
		}
		createUpdatedPurchaseInvoiceVOFromPurchaseInvoiceDTO(purchaseInvoiceDTO, purchaseInvoiceVO);
		purchaseInvoiceRepo.save(purchaseInvoiceVO);
		Map<String, Object> response = new HashMap<>();
		response.put("purchaseInvoiceVO", purchaseInvoiceVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedPurchaseInvoiceVOFromPurchaseInvoiceDTO(PurchaseInvoiceDTO purchaseInvoiceDTO,
			PurchaseInvoiceVO purchaseInvoiceVO) {
		purchaseInvoiceVO.setSupplierName(purchaseInvoiceDTO.getSupplierName());
		purchaseInvoiceVO.setPoNo(purchaseInvoiceDTO.getPoNo());
		purchaseInvoiceVO.setGrnNo(purchaseInvoiceDTO.getGrnNo());
		purchaseInvoiceVO.setGrnDate(purchaseInvoiceDTO.getGrnDate());
		purchaseInvoiceVO.setLocation(purchaseInvoiceDTO.getLocation());
		purchaseInvoiceVO.setInWardNo(purchaseInvoiceDTO.getInWardNo());
		purchaseInvoiceVO.setSupplierCode(purchaseInvoiceDTO.getSupplierCode());
		purchaseInvoiceVO.setGstState(purchaseInvoiceDTO.getGstState());
		purchaseInvoiceVO.setGstNo(purchaseInvoiceDTO.getGstNo());
		purchaseInvoiceVO.setIsReverseChrg(purchaseInvoiceDTO.getIsReverseChrg());
		purchaseInvoiceVO.setAddress(purchaseInvoiceDTO.getAddress());
		purchaseInvoiceVO.setCurrency(purchaseInvoiceDTO.getCurrency());
		purchaseInvoiceVO.setExchangeRate(purchaseInvoiceDTO.getExchangeRate());
		purchaseInvoiceVO.setInvDcNo(purchaseInvoiceDTO.getInvDcNo());
		purchaseInvoiceVO.setInvDcDate(purchaseInvoiceDTO.getInvDcDate());
		purchaseInvoiceVO.setGstType(purchaseInvoiceDTO.getGstType());
		purchaseInvoiceVO.setCustomerName(purchaseInvoiceDTO.getCustomerName());
		purchaseInvoiceVO.setRemarks(purchaseInvoiceDTO.getRemarks());
		purchaseInvoiceVO.setCnt(purchaseInvoiceDTO.getCnt());
		purchaseInvoiceVO.setOrgId(purchaseInvoiceDTO.getOrgId());
		purchaseInvoiceVO.setActive(purchaseInvoiceDTO.isActive());
		purchaseInvoiceVO.setCreatedBy(purchaseInvoiceDTO.getCreatedBy());

		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(purchaseInvoiceDTO.getId())) {
			List<PurchaseInvoiceItemVO> purchaseInvoiceItemVO1 = purchaseInvoiceItemRepo
					.findByPurchaseInvoiceVO(purchaseInvoiceVO);
			purchaseInvoiceItemRepo.deleteAll(purchaseInvoiceItemVO1);

		}

		List<PurchaseInvoiceItemVO> purchaseInvoiceItemVOs = new ArrayList<>();
		for (PurchaseInvoiceItemDTO purchaseInvoiceItemDTO : purchaseInvoiceDTO.getPurchaseInvoiceItemDTO()) {
			PurchaseInvoiceItemVO purchaseInvoiceItemVO = new PurchaseInvoiceItemVO();
			purchaseInvoiceItemVO.setItemCode(purchaseInvoiceItemDTO.getItemCode());
			purchaseInvoiceItemVO.setItemName(purchaseInvoiceItemDTO.getItemName());
			purchaseInvoiceItemVO.setHsnSacCode(purchaseInvoiceItemDTO.getHsnSacCode());
			purchaseInvoiceItemVO.setTaxtype(purchaseInvoiceItemDTO.getTaxtype());
			purchaseInvoiceItemVO.setPrimaryUnit(purchaseInvoiceItemDTO.getPrimaryUnit());
			purchaseInvoiceItemVO.setPoRate(purchaseInvoiceItemDTO.getPoRate());
			purchaseInvoiceItemVO.setRejectQty(purchaseInvoiceItemDTO.getRejectQty());
			purchaseInvoiceItemVO.setAcceptQty(purchaseInvoiceItemDTO.getAcceptQty());
			purchaseInvoiceItemVO.setUnitPrice(purchaseInvoiceItemDTO.getUnitPrice());
			purchaseInvoiceItemVO.setPoDetailsId(purchaseInvoiceItemDTO.getPoDetailsId());

			BigDecimal taxAmount = BigDecimal.ZERO;
			BigDecimal landedValues = BigDecimal.ZERO;

			BigDecimal amountSet = purchaseInvoiceItemDTO.getPoRate().multiply(purchaseInvoiceItemDTO.getAcceptQty());
			purchaseInvoiceItemVO.setAmount(amountSet);
			grossAmount = grossAmount.add(purchaseInvoiceItemVO.getAmount());

			if (purchaseInvoiceVO.getGstType() == null || purchaseInvoiceVO.getGstType().isEmpty()
					|| !purchaseInvoiceVO.getGstType().equalsIgnoreCase("INTRA")
							&& !purchaseInvoiceVO.getGstType().equalsIgnoreCase("INTER")) {
				purchaseInvoiceItemVO.setIgst(BigDecimal.ZERO);
				purchaseInvoiceItemVO.setCgst(BigDecimal.ZERO);
				purchaseInvoiceItemVO.setSgst(BigDecimal.ZERO);
				purchaseInvoiceItemVO.setTaxValue(BigDecimal.ZERO);
			} else {
				if (purchaseInvoiceVO.getGstType().equalsIgnoreCase("INTER")) {
					purchaseInvoiceItemVO.setIgst(purchaseInvoiceItemDTO.getIgst());
					BigDecimal igstAmount = purchaseInvoiceItemDTO.getIgst().multiply(purchaseInvoiceItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					purchaseInvoiceItemVO.setCgst(BigDecimal.ZERO);
					purchaseInvoiceItemVO.setSgst(BigDecimal.ZERO);
					taxAmount = igstAmount;
					purchaseInvoiceItemVO.setTaxValue(taxAmount);
				} else if (purchaseInvoiceVO.getGstType().equalsIgnoreCase("INTRA")) {
					purchaseInvoiceItemVO.setCgst(purchaseInvoiceItemDTO.getCgst());
					purchaseInvoiceItemVO.setSgst(purchaseInvoiceItemDTO.getSgst());
					BigDecimal sgstAmount = purchaseInvoiceItemDTO.getSgst().multiply(purchaseInvoiceItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					BigDecimal cgstAmount = purchaseInvoiceItemDTO.getCgst().multiply(purchaseInvoiceItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					purchaseInvoiceItemVO.setIgst(BigDecimal.ZERO);
					taxAmount = cgstAmount.add(sgstAmount);
					purchaseInvoiceItemVO.setTaxValue(taxAmount);
				}
			}
			totalTaxAmount = totalTaxAmount.add(purchaseInvoiceItemVO.getTaxValue());

			landedValues = purchaseInvoiceItemVO.getAmount().add(purchaseInvoiceItemVO.getTaxValue());
			purchaseInvoiceItemVO.setLandedValue(landedValues);
			netAmount = netAmount.add(purchaseInvoiceItemVO.getLandedValue());

			purchaseInvoiceItemVO.setPurchaseInvoiceVO(purchaseInvoiceVO);
			purchaseInvoiceItemVOs.add(purchaseInvoiceItemVO);
		}

		purchaseInvoiceVO.setGrossAmount(grossAmount);
		purchaseInvoiceVO.setNetAmount(netAmount);
		purchaseInvoiceVO.setTotalAmountTax(totalTaxAmount);
		purchaseInvoiceVO.setPurchaseInvoiceItemVO(purchaseInvoiceItemVOs);

	}

	@Override
	public List<PurchaseInvoiceVO> getAllPurchaseInvoiceByOrgId(Long orgId) {

		return purchaseInvoiceRepo.getAllPurchaseInvoiceByOrgId(orgId);
	}

	@Override
	public PurchaseInvoiceVO getPurchaseInvoiceById(Long id) {

		return purchaseInvoiceRepo.getPurchaseInvoiceById(id);
	}

	@Override
	public String getPurchaseInvoiceDocId(Long orgId) {
		String ScreenCode = "PCI";
		String result = purchaseInvoiceRepo.getPurchaseInvoiceDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderPoNumber(Long orgId, String supplierCode) {
		Set<Object[]> chType = purchaseInvoiceRepo.getPurchaseOrderPoNumber(orgId, supplierCode);
		return getPoNo(chType);
	}

	private List<Map<String, Object>> getPoNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("poNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>>  getGrnNoAndGrnDateFromGrnDetails(Long orgId, String poNo) {
		Set<Object[]> chType = purchaseInvoiceRepo.getGrnNoAndGrnDateFromGrnDetails(orgId, poNo);
		return getGrnNoAndGrnDate(chType);
	}

	private List<Map<String, Object>> getGrnNoAndGrnDate(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnNo", ch[0] != null ? ch[0].toString() : "");
			map.put("grnDate", ch[1] != null ? ch[1].toString() : "");
			map.put("location", ch[2] != null ? ch[2].toString() : "");
			map.put("inwordNo", ch[3] != null ? ch[3].toString() : "");
			map.put("supplierCode", ch[4] != null ? ch[4].toString() : "");
			map.put("gstNo", ch[5] != null ? ch[5].toString() : "");
			map.put("address", ch[6] != null ? ch[6].toString() : "");
			map.put("currency", ch[7] != null ? ch[7].toString() : "");
			map.put("exchangeRate", ch[8] != null ? ch[8].toString() : "");
			map.put("grnClearTime", ch[9] != null ? ch[9].toString() : "");
			map.put("invdcNo", ch[10] != null ? ch[10].toString() : "");
			map.put("invdcDate", ch[11] != null ? ch[11].toString() : "");
			map.put("gstType", ch[12] != null ? ch[12].toString() : "");
			map.put("customerName", ch[13] != null ? ch[13].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getItemCodeAndItemDescFromGrn(Long orgId, String grnNo) {
		Set<Object[]> chType = purchaseInvoiceRepo.getItemCodeAndItemDescFromGrn(orgId, grnNo);
		return getItemCode(chType);
	}

	private List<Map<String, Object>> getItemCode(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemCode", ch[0] != null ? ch[0].toString() : "");
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			map.put("hsnScaCode", ch[2] != null ? ch[2].toString() : "");
			map.put("taxType", ch[3] != null ? ch[3].toString() : "");
			map.put("primaryUnit", ch[4] != null ? ch[4].toString() : "");
			map.put("poRate", ch[5] != null ? ch[5].toString() : "");
			map.put("receivedQty", ch[6] != null ? ch[6].toString() : "");
			map.put("acceptQty", ch[7] != null ? ch[7].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPoDetailsId(Long docId, String itemDesc, Long orgId) {
		Set<Object[]> chType = purchaseInvoiceRepo.getPoDetailsId(docId, itemDesc, orgId);
		return getPoDetails(chType);
	}

	private List<Map<String, Object>> getPoDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("poDetailsId", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}
}
