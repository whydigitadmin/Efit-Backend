package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			purchaseReturnItemVO.setTaxValue(purchaseReturnItemDTO.getTaxValue());
			purchaseReturnItemVO.setLandedValue(purchaseReturnItemDTO.getLandedValue());

			purchaseReturnItemVO.setPurchaseReturnVO(purchaseReturnVO);
			purchaseReturnItemVOs.add(purchaseReturnItemVO);
		}
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
		purchaseInvoiceVO.setOrgId(purchaseInvoiceDTO.getOrgId());
		purchaseInvoiceVO.setActive(purchaseInvoiceDTO.isActive());
		purchaseInvoiceVO.setCreatedBy(purchaseInvoiceDTO.getCreatedBy());

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
			purchaseInvoiceItemVO.setSgst(purchaseInvoiceItemDTO.getSgst());
			purchaseInvoiceItemVO.setCgst(purchaseInvoiceItemDTO.getCgst());
			purchaseInvoiceItemVO.setIgst(purchaseInvoiceItemDTO.getIgst());
			purchaseInvoiceItemVO.setTaxValue(purchaseInvoiceItemDTO.getTaxValue());
			purchaseInvoiceItemVO.setLandedValue(purchaseInvoiceItemDTO.getLandedValue());

			purchaseInvoiceItemVO.setPurchaseInvoiceVO(purchaseInvoiceVO);
			purchaseInvoiceItemVOs.add(purchaseInvoiceItemVO);
		}
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

}
