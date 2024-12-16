package com.efitops.basesetup.service;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.GrnDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseOrderDetailsDTO;
import com.efitops.basesetup.dto.ThirdPartyAttachmentDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.GrnDetailsVO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.PurchaseEnquiryDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;
import com.efitops.basesetup.entity.ThirdPartyAttachmentVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionDetailsVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.GrnDetailsRepo;
import com.efitops.basesetup.repo.GrnRepo;
import com.efitops.basesetup.repo.PurchaseEnquiryDetailsRepo;
import com.efitops.basesetup.repo.PurchaseOrderDetailsRepo;
import com.efitops.basesetup.repo.PurchaseOrderRepo;
import com.efitops.basesetup.repo.ThirdPartyAttachmentRepo;
import com.efitops.basesetup.repo.ThirdPartyInspectionDetailsRepo;
import com.efitops.basesetup.repo.ThirdPartyInspectionRepo;

@Service
public class GrnServiceImpl implements GrnService {
	public static final Logger LOGGER = LoggerFactory.getLogger(GrnServiceImpl.class);

	@Autowired
	GrnRepo grnRepo;
	
	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	GrnDetailsRepo grnDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	ThirdPartyInspectionRepo thirdPartyInspectionRepo;

	@Autowired
	ThirdPartyInspectionDetailsRepo thirdPartyInspectionDetailsRepo;

	@Autowired
	ThirdPartyAttachmentRepo thirdPartyAttachmentRepo;

	@Autowired
	PurchaseOrderRepo purchaseOrderRepo;

	@Autowired
	PurchaseOrderDetailsRepo purchaseOrderDetailsRepo;

	@Override
	public List<GrnVO> getGrnByOrgId(Long orgId) {
		List<GrnVO> grnVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			grnVO = grnRepo.findGrnByOrgId(orgId);
		}
		return grnVO;
	}

	@Override
	public List<GrnVO> getGrnById(Long id) {
		List<GrnVO> grnVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			grnVO = grnRepo.getGrnById(id);
		}
		return grnVO;
	}

	@Override
	public Map<String, Object> updateCreateGrn(GrnDTO grndto) throws ApplicationException {
		GrnVO grnVO = new GrnVO();
		String message;
		String screenCode = "GRN";
		if (ObjectUtils.isNotEmpty(grndto.getId())) {
			grnVO = grnRepo.findById(grndto.getId()).orElseThrow(() -> new ApplicationException("Invalid Grn details"));
			message = "Enquiry Updated Successfully";
			grnVO.setUpdatedBy(grndto.getCreatedBy());

		} else {

			String docId = grnRepo.getGrnDocId(grndto.getOrgId(), screenCode);
			grnVO.setGrnNo(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(grndto.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			grnVO.setCreatedBy(grndto.getCreatedBy());
			grnVO.setUpdatedBy(grndto.getCreatedBy());

			message = "Enquiry Created Successfully";
		}
		createUpdateGrnVOByGrnDTO(grndto, grnVO);
		grnRepo.save(grnVO);
		Map<String, Object> response = new HashMap<>();
		response.put("grnVO", grnVO);
		response.put("message", message);
		return response;

	}

	private void createUpdateGrnVOByGrnDTO(@Valid GrnDTO grndto, GrnVO grnVO) throws ApplicationException {
		grnVO.setInwardNo(grndto.getInwardNo());
		grnVO.setLocation(grndto.getLocation());
		grnVO.setGstNo(grndto.getGstNo());
		grnVO.setPoNo(grndto.getPoNo());
		grnVO.setOrgId(grndto.getOrgId());
		grnVO.setGstType(grndto.getGstType());
		grnVO.setAdress(grndto.getAdress());
		grnVO.setCurrency(grndto.getCurrency());
		grnVO.setExchangeRate(grndto.getExchangeRate());
		grnVO.setGrnClearTime(grndto.getGrnClearTime());
		grnVO.setInvDcNo(grndto.getInvDcNo());
		grnVO.setInvDcDate(grndto.getInvDcDate());
		grnVO.setCustomer(grndto.getCustomer());
		grnVO.setSupplierName(grndto.getSupplierName());

		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(grnVO.getId())) {
			List<GrnDetailsVO> grnDetailsVo1 = grnDetailsRepo.findByGrnVO(grnVO);
			grnDetailsRepo.deleteAll(grnDetailsVo1);
		}

		List<GrnDetailsVO> grnDetailsVOs = new ArrayList<>();
		for (GrnDetailsDTO grnDetailsDTO : grndto.getGrnDetailsDTO()) {
			GrnDetailsVO grnDetailsVO = new GrnDetailsVO();
			grnDetailsVO.setItemCode(grnDetailsDTO.getItemCode());
			grnDetailsVO.setItemDesc(grnDetailsDTO.getItemDesc());
			grnDetailsVO.setHsnSacCode(grnDetailsDTO.getHsnSacCode());
			grnDetailsVO.setTaxType(grnDetailsDTO.getTaxType());
			grnDetailsVO.setPrimaryUnit(grnDetailsDTO.getPrimaryUnit());
			grnDetailsVO.setAcceptQty(grnDetailsDTO.getAcceptQty());
			grnDetailsVO.setPoRate(grnDetailsDTO.getPoRate());
			grnDetailsVO.setChallanQty(grnDetailsDTO.getChallanQty());
			grnDetailsVO.setExcessQty(grnDetailsDTO.getExcessQty());
			grnDetailsVO.setRecievedQty(grnDetailsDTO.getRecievedQty());
			grnDetailsVO.setRejectQty(grnDetailsDTO.getRejectQty());
			grnDetailsVO.setIgst(grnDetailsDTO.getIgst());
			grnDetailsVO.setInspectionable(grnDetailsDTO.getInspectionable());
			grnDetailsVO.setSgst(grnDetailsDTO.getSgst());
			grnDetailsVO.setStock(grnDetailsDTO.getStock());
			grnDetailsVO.setHsnSacCode(grnDetailsDTO.getHsnSacCode());
			grnDetailsVO.setCgst(grnDetailsDTO.getCgst());
			grnDetailsVO.setOrderQty(grnDetailsDTO.getOrderQty());

			BigDecimal taxAmount = BigDecimal.ZERO;
			BigDecimal landedValues = BigDecimal.ZERO;

			BigDecimal amountSet = grnDetailsDTO.getPoRate().multiply(grnDetailsDTO.getAcceptQty());
			grnDetailsVO.setAmount(amountSet);
			grossAmount = grossAmount.add(grnDetailsVO.getAmount());
			grnDetailsVO.setPendingQty(grnDetailsDTO.getOrderQty().subtract(grnDetailsDTO.getChallanQty()));

			BigDecimal sgstamount = grnDetailsDTO.getSgst().multiply(grnDetailsVO.getAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal cgstamount = grnDetailsDTO.getCgst().multiply(grnDetailsVO.getAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal igstamount = grnDetailsDTO.getIgst().multiply(grnDetailsVO.getAmount())
					.divide(BigDecimal.valueOf(100));

			taxAmount = taxAmount.add(cgstamount).add(sgstamount).add(igstamount);
			grnDetailsVO.setTaxValue(taxAmount);
			totalTaxAmount = totalTaxAmount.add(grnDetailsVO.getTaxValue());

			landedValues = grnDetailsVO.getAmount().add(grnDetailsVO.getTaxValue());
			grnDetailsVO.setLandedValue(landedValues);
			netAmount = netAmount.add(grnDetailsVO.getLandedValue());

			grnDetailsVO.setGrnVO(grnVO); // Set the reference in child entity
			grnDetailsVOs.add(grnDetailsVO);
		}

		grnVO.setGrossAmount(grossAmount);
		grnVO.setNetAmount(netAmount);
		grnVO.setTotalAmountTax(totalTaxAmount);

		grnVO.setGrnDetailsVO(grnDetailsVOs);

	}

	@Override
	public String getGrnDocId(Long orgId) {
		String screenCode = "GRN";
		String result = grnRepo.getGrnDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getInwardNoForGRN(Long orgId) {
		Set<Object[]> address = grnRepo.findInwardNoForGRNDetails(orgId);
		return getInwardNoForGRN(address);
	}

	private List<Map<String, Object>> getInwardNoForGRN(Set<Object[]> chCode) {
		List<Map<String, Object>> inward = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("ponumber", ch[1] != null ? ch[1].toString() : "");
			map.put("suppliername", ch[2] != null ? ch[2].toString() : "");
			map.put("vehicleno", ch[3] != null ? ch[3].toString() : "");
			map.put("invoiceno", ch[4] != null ? ch[4].toString() : "");
			map.put("invoicedate", ch[5] != null ? ch[5].toString() : "");
			map.put("currency", ch[6] != null ? ch[6].toString() : "");
			map.put("gstin", ch[7] != null ? ch[7].toString() : "");

			inward.add(map);
		}
		return inward;
	}

	@Override
	public List<Map<String, Object>> getItemForGRN(Long orgId, String InwardNo) {
		Set<Object[]> grnitem = grnRepo.findItemForGRNDetails(orgId, InwardNo);
		return getItemForGRN(grnitem);
	}

	private List<Map<String, Object>> getItemForGRN(Set<Object[]> chCode) {
		List<Map<String, Object>> itemgrn = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemname", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("inwardqty", ch[2] != null ? ch[2].toString() : "");
			map.put("invoiceqty", ch[3] != null ? ch[3].toString() : "");
			map.put("poqty", ch[4] != null ? ch[4].toString() : "");
			map.put("uom", ch[5] != null ? ch[5].toString() : "");
			map.put("hsncode", ch[6] != null ? ch[6].toString() : "");
			map.put("inspection", ch[7] != null ? ch[7].toString() : "");
			map.put("needqcapproval", ch[8] != null ? ch[8].toString() : "");
			map.put("price", ch[9] != null ? ch[9].toString() : "");
			map.put("taxslab", ch[10] != null ? ch[10].toString() : "");

			itemgrn.add(map);
		}
		return itemgrn;
	}

	@Override
	public List<Map<String, Object>> getGRNForThirdPartyInsp(Long orgId) {
		Set<Object[]> grnthirdpartyinsp = thirdPartyInspectionRepo.findGRNForThirdPartyInspDetails(orgId);
		return getGRNForThirdPartyInsp(grnthirdpartyinsp);
	}

	private List<Map<String, Object>> getGRNForThirdPartyInsp(Set<Object[]> grnthirdpartyinsp) {
		List<Map<String, Object>> grnthirdpaty = new ArrayList<>();
		for (Object[] ch : grnthirdpartyinsp) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnno", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("inwardno", ch[1] != null ? ch[1].toString() : "");
			map.put("pono", ch[2] != null ? ch[2].toString() : "");
			map.put("customer", ch[3] != null ? ch[3].toString() : "");
			map.put("suppliername", ch[4] != null ? ch[4].toString() : "");

			grnthirdpaty.add(map);
		}
		return grnthirdpaty;
	}

	@Override
	public List<ThirdPartyInspectionVO> getThirdPartyInspByOrgId(Long orgId) {
		List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			thirdPartyInspectionVO = thirdPartyInspectionRepo.findThirdPartyInspectionOrgId(orgId);
		}
		return thirdPartyInspectionVO;
	}

	@Override
	public List<ThirdPartyInspectionVO> getThirdPartyInspById(Long id) {
		List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			thirdPartyInspectionVO = thirdPartyInspectionRepo.getThirdPartyInspectionById(id);
		}
		return thirdPartyInspectionVO;
	}

	@Override
	public Map<String, Object> updateCreateThirdPartyInsp(ThirdPartyInspectionDTO thirdPartyInspectionDTO)
			throws ApplicationException {
		ThirdPartyInspectionVO thirdPartyInspectionVO = new ThirdPartyInspectionVO();
		String message;
		String screenCode = "TPI";
		if (ObjectUtils.isNotEmpty(thirdPartyInspectionDTO.getId())) {
			thirdPartyInspectionVO = thirdPartyInspectionRepo.findById(thirdPartyInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Thirdparty Inspection  details"));
			message = " Thirdparty Inspection Updated Successfully";
			thirdPartyInspectionVO.setUpdatedBy(thirdPartyInspectionDTO.getCreatedBy());

		} else {

			String docId = thirdPartyInspectionRepo.getThirdPartyInspectionDocId(thirdPartyInspectionDTO.getOrgId(),
					screenCode);
			thirdPartyInspectionVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(thirdPartyInspectionDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			thirdPartyInspectionVO.setCreatedBy(thirdPartyInspectionDTO.getCreatedBy());
			thirdPartyInspectionVO.setUpdatedBy(thirdPartyInspectionDTO.getCreatedBy());

			message = " Thirdparty Inspection Created Successfully";
		}
		createUpdateThirdPartyInspectionVOByThirdPartyInspectionDTO(thirdPartyInspectionDTO, thirdPartyInspectionVO);
		thirdPartyInspectionRepo.save(thirdPartyInspectionVO);
		Map<String, Object> response = new HashMap<>();
		response.put("thirdPartyInspectionVO", thirdPartyInspectionVO);
		response.put("message", message);
		return response;

	}

	private void createUpdateThirdPartyInspectionVOByThirdPartyInspectionDTO(
			@Valid ThirdPartyInspectionDTO thirdPartyInspectionDTO, ThirdPartyInspectionVO thirdPartyInspectionVO)
			throws ApplicationException {
		thirdPartyInspectionVO.setId(thirdPartyInspectionDTO.getId());
		thirdPartyInspectionVO.setOrgId(thirdPartyInspectionDTO.getOrgId());
		thirdPartyInspectionVO.setGrnNo(thirdPartyInspectionDTO.getGrnNo());
		thirdPartyInspectionVO.setWorkOrderNo(thirdPartyInspectionDTO.getWorkOrderNo());
		thirdPartyInspectionVO.setPoNo(thirdPartyInspectionDTO.getPoNo());
		thirdPartyInspectionVO.setCustomerName(thirdPartyInspectionDTO.getCustomerName());
		thirdPartyInspectionVO.setSupplierName(thirdPartyInspectionDTO.getSupplierName());
		thirdPartyInspectionVO.setThirdPartyDetails(thirdPartyInspectionDTO.getThirdPartyDetails());
		thirdPartyInspectionVO.setThirdPartyAddress(thirdPartyInspectionDTO.getThirdPartyAddress());

		if (ObjectUtils.isNotEmpty(thirdPartyInspectionVO.getId())) {
			List<ThirdPartyInspectionDetailsVO> thirdPartyInspectionDetailsVO1 = thirdPartyInspectionDetailsRepo
					.findByThirdPartyInspectionVO(thirdPartyInspectionVO);
			thirdPartyInspectionDetailsRepo.deleteAll(thirdPartyInspectionDetailsVO1);

			List<ThirdPartyAttachmentVO> thirdPartyAttachmentVO1 = thirdPartyAttachmentRepo
					.findByThirdPartyInspectionVO(thirdPartyInspectionVO);
			thirdPartyAttachmentRepo.deleteAll(thirdPartyAttachmentVO1);
		}

		List<ThirdPartyInspectionDetailsVO> thirdPartyInspectionDetailsVOs = new ArrayList<>();
		for (ThirdPartyInspectionDetailsDTO thirdPartyInspectionDetailsDTO : thirdPartyInspectionDTO
				.getThirdPartyInspectionDetailsDTO()) {
			ThirdPartyInspectionDetailsVO thirdPartyInspectionDetailsVO = new ThirdPartyInspectionDetailsVO();
			thirdPartyInspectionDetailsVO.setItemId(thirdPartyInspectionDetailsDTO.getItemId());
			thirdPartyInspectionDetailsVO.setItemDesc(thirdPartyInspectionDetailsDTO.getItemDesc());
			thirdPartyInspectionDetailsVO.setInspectionType(thirdPartyInspectionDetailsDTO.getInspectionType());
			thirdPartyInspectionDetailsVO.setCertificateNo(thirdPartyInspectionDetailsDTO.getCertificateNo());
			thirdPartyInspectionDetailsVO.setRemarks(thirdPartyInspectionDetailsDTO.getRemarks());
			thirdPartyInspectionDetailsVO.setStatus(thirdPartyInspectionDetailsDTO.getStatus());
			thirdPartyInspectionDetailsVO.setThirdPartyInspectionVO(thirdPartyInspectionVO);
			thirdPartyInspectionDetailsVOs.add(thirdPartyInspectionDetailsVO);
		}
		thirdPartyInspectionVO.setThirdPartyInspectionDetailsVO(thirdPartyInspectionDetailsVOs);

		List<ThirdPartyAttachmentVO> thirdPartyAttachmentVOs = new ArrayList<>();
		for (ThirdPartyAttachmentDTO thirdPartyAttachmentDTO : thirdPartyInspectionDTO.getThirdPartyAttachmentDTO()) {
			ThirdPartyAttachmentVO thirdPartyAttachmentVO = new ThirdPartyAttachmentVO();
			thirdPartyAttachmentVO.setItemId(thirdPartyAttachmentDTO.getItemId());

			thirdPartyAttachmentVO.setThirdPartyInspectionVO(thirdPartyInspectionVO); // Set the reference in child
																						// entity
			thirdPartyAttachmentVOs.add(thirdPartyAttachmentVO);
		}
		thirdPartyInspectionVO.setThirdPartyAttachmentVO(thirdPartyAttachmentVOs);
	}

	@Override
	public String getThirdPartyInspectionDocId(Long orgId) {
		String screenCode = "TPI";
		String result = thirdPartyInspectionRepo.getThirdPartyInspectionDocId(orgId, screenCode);
		return result;
	}

	@Override
	public ThirdPartyAttachmentVO uploadFileForThirdPartyInspection(MultipartFile file, Long id) throws IOException {
		ThirdPartyAttachmentVO thirdPartyAttachmentVO = thirdPartyAttachmentRepo.findById(id).get();
		thirdPartyAttachmentVO.setAttachement(file.getBytes());
		return thirdPartyAttachmentRepo.save(thirdPartyAttachmentVO);
	}

	@Override
	public List<Map<String, Object>> getSupplierAddressForGRN(Long orgId, String supplierName) {
		Set<Object[]> address = grnRepo.findSupplierAddressDetails(orgId, supplierName);
		return getSupplierAddressForGRN(address);
	}

	private List<Map<String, Object>> getSupplierAddressForGRN(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("full_address", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("stategstin", ch[1] != null ? ch[1].toString() : "");
			map.put("taxtype", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSGSTandCGSTForGRN(Long orgId, String taxType, String gstType) {
		Set<Object[]> chType = grnRepo.findgetSGSTandCGSTForGRN(orgId, taxType, gstType);
		return getCGST(chType);
	}

	private List<Map<String, Object>> getCGST(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("sgstpercentage", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getIGSTForGRN(Long orgId, String taxType, String gstType) {
		Set<Object[]> chType = grnRepo.findgetIGSTForGRN(orgId, taxType, gstType);
		return getIGST(chType);
	}

	private List<Map<String, Object>> getIGST(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("sgstpercentage", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getThirdPartyDetailsForThirdPartyInsp(Long orgId) {
		Set<Object[]> chType = thirdPartyInspectionRepo.findgetThirdPartyDetailsForThirdPartyInsp(orgId);
		return getThirdPartyDetailsForThirdPartyInsp(chType);
	}

	private List<Map<String, Object>> getThirdPartyDetailsForThirdPartyInsp(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partyName", ch[0] != null ? ch[0].toString() : "");
			map.put("address", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	// purchase Order
	@Override
	public List<PurchaseOrderVO> getPurchaseOrderByOrgId(Long orgId) {
		List<PurchaseOrderVO> purchaseOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			purchaseOrderVO = purchaseOrderRepo.findPurchaseOrderByOrgId(orgId);
		}
		return purchaseOrderVO;
	}

	@Override
	public List<PurchaseOrderVO> getPurchaseOrderById(Long id) {
		List<PurchaseOrderVO> purchaseOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			purchaseOrderVO = purchaseOrderRepo.getPurchaseOrderById(id);
		}
		return purchaseOrderVO;
	}

	@Override
	public Map<String, Object> updateCreatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO)
			throws ApplicationException {
		PurchaseOrderVO purchaseOrderVO = new PurchaseOrderVO();
		String message;
		String screenCode = "PONO";
		if (ObjectUtils.isNotEmpty(purchaseOrderDTO.getId())) {
			purchaseOrderVO = purchaseOrderRepo.findById(purchaseOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid PO details"));
			message = "Purchase Order Updated Successfully";
			purchaseOrderVO.setUpdatedBy(purchaseOrderDTO.getCreatedBy());

		} else {

			String docId = purchaseOrderRepo.getPurchaseDocId(purchaseOrderDTO.getOrgId(), screenCode);
			purchaseOrderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(purchaseOrderDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			purchaseOrderVO.setCreatedBy(purchaseOrderDTO.getCreatedBy());
			purchaseOrderVO.setUpdatedBy(purchaseOrderDTO.getCreatedBy());

			message = "Enquiry Created Successfully";
		}
		createUpdatePurchaseorderVOByPurchaseorderDTO(purchaseOrderDTO, purchaseOrderVO);
		purchaseOrderRepo.save(purchaseOrderVO);
		Map<String, Object> response = new HashMap<>();
		response.put("purchaseOrderVO", purchaseOrderVO);
		response.put("message", message);
		return response;

	}

	private void createUpdatePurchaseorderVOByPurchaseorderDTO(@Valid PurchaseOrderDTO purchaseOrderDTO,
			PurchaseOrderVO purchaseOrderVO) throws ApplicationException {
		purchaseOrderVO.setCustomerName(purchaseOrderDTO.getCustomerName());
		purchaseOrderVO.setCustomerCode(purchaseOrderDTO.getCustomerCode());
		purchaseOrderVO.setWorkOrderNo(purchaseOrderDTO.getWorkOrderNo());
		purchaseOrderVO.setBasedOn(purchaseOrderDTO.getBasedOn());
		purchaseOrderVO.setQuotationNo(purchaseOrderDTO.getQuotationNo());
		purchaseOrderVO.setPurchaseIndentNo(purchaseOrderDTO.getPurchaseIndentNo());
		purchaseOrderVO.setSupplierName(purchaseOrderDTO.getSupplierName());
		purchaseOrderVO.setSupplierCode(purchaseOrderDTO.getSupplierCode());
		purchaseOrderVO.setContactperson(purchaseOrderDTO.getContactperson());
		purchaseOrderVO.setMobileNo(purchaseOrderDTO.getMobileNo());
		purchaseOrderVO.setEMail(purchaseOrderDTO.getEMail());
		purchaseOrderVO.setState(purchaseOrderDTO.getState());
		purchaseOrderVO.setCountry(purchaseOrderDTO.getCountry());
		purchaseOrderVO.setTaxCode(purchaseOrderDTO.getTaxCode());
		purchaseOrderVO.setAddress(purchaseOrderDTO.getAddress());
		purchaseOrderVO.setRemarks(purchaseOrderDTO.getRemarks());

		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;
		BigDecimal totalLandedAmount =BigDecimal.ZERO;


		if (ObjectUtils.isNotEmpty(purchaseOrderVO.getId())) {
			List<PurchaseOrderDetailsVO> purchaseOrderDetailsVo1 = purchaseOrderDetailsRepo
					.findByPurchaseOrderVO(purchaseOrderVO);
			purchaseOrderDetailsRepo.deleteAll(purchaseOrderDetailsVo1);
		}

		List<PurchaseOrderDetailsVO> purchaseOrderDetailsVOs = new ArrayList<>();
		for (PurchaseOrderDetailsDTO purchaseOrderDetailsDTO : purchaseOrderDTO.getPurchaseOrderDetailsDTO()) {
			PurchaseOrderDetailsVO purchaseOrderDetailsVO = new PurchaseOrderDetailsVO();
			purchaseOrderDetailsVO.setItem(purchaseOrderDetailsDTO.getItem());
			purchaseOrderDetailsVO.setItemDesc(purchaseOrderDetailsDTO.getItemDesc());
			purchaseOrderDetailsVO.setHsnSacCode(purchaseOrderDetailsDTO.getHsnSacCode());
			purchaseOrderDetailsVO.setTaxType(purchaseOrderDetailsDTO.getTaxType());
			purchaseOrderDetailsVO.setUom(purchaseOrderDetailsDTO.getUom());
			purchaseOrderDetailsVO.setQty(purchaseOrderDetailsDTO.getQty());
			purchaseOrderDetailsVO.setPrice(purchaseOrderDetailsDTO.getPrice());
			purchaseOrderDetailsVO.setPrevRate(purchaseOrderDetailsDTO.getPrevRate());
			purchaseOrderDetailsVO.setDiscount(purchaseOrderDetailsDTO.getDiscount());

			purchaseOrderDetailsVO.setIgst(purchaseOrderDetailsDTO.getIgst());
			purchaseOrderDetailsVO.setSgst(purchaseOrderDetailsDTO.getSgst());
			purchaseOrderDetailsVO.setHsnSacCode(purchaseOrderDetailsDTO.getHsnSacCode());
			purchaseOrderDetailsVO.setCgst(purchaseOrderDetailsDTO.getCgst());

			BigDecimal taxAmount = BigDecimal.ZERO;
			BigDecimal landedValues = BigDecimal.ZERO;
		
			BigDecimal amountSet = purchaseOrderDetailsDTO.getPrice().multiply(purchaseOrderDetailsDTO.getQty());
			purchaseOrderDetailsVO.setAmount(amountSet);
			
			
			grossAmount = grossAmount.add(purchaseOrderDetailsVO.getAmount());
			BigDecimal discountAmount =     purchaseOrderDetailsVO.getAmount().multiply(purchaseOrderDetailsDTO.getDiscount()).divide(BigDecimal.valueOf(100));
			purchaseOrderDetailsVO.setDiscountAmt(discountAmount);
			BigDecimal amountSubtractDiscountAmount = purchaseOrderDetailsVO.getAmount().subtract(purchaseOrderDetailsVO.getDiscountAmt());
			 purchaseOrderDetailsVO.setNetAmount(amountSubtractDiscountAmount);
			 netAmount=netAmount.add(amountSubtractDiscountAmount);
			
			
			BigDecimal sgstamount = purchaseOrderDetailsDTO.getSgst().multiply(amountSubtractDiscountAmount.divide(BigDecimal.valueOf(100)));
			BigDecimal cgstamount = purchaseOrderDetailsDTO.getCgst().multiply(amountSubtractDiscountAmount.divide(BigDecimal.valueOf(100)));
			BigDecimal igstamount = purchaseOrderDetailsDTO.getIgst().multiply(amountSubtractDiscountAmount.divide(BigDecimal.valueOf(100)));
			
			taxAmount = taxAmount.add(cgstamount).add(sgstamount).add(igstamount);
			purchaseOrderDetailsVO.setTaxValue(taxAmount);
			totalTaxAmount = totalTaxAmount.add(purchaseOrderDetailsVO.getTaxValue());

			landedValues = amountSubtractDiscountAmount.add(purchaseOrderDetailsVO.getTaxValue());
			purchaseOrderDetailsVO.setLandedValue(landedValues);
			totalLandedAmount = totalLandedAmount.add(purchaseOrderDetailsVO.getLandedValue());

			purchaseOrderDetailsVO.setPurchaseOrderVO(purchaseOrderVO); // Set the reference in child entity
			purchaseOrderDetailsVOs.add(purchaseOrderDetailsVO);
		}

		purchaseOrderVO.setGrossAmount(grossAmount);
		purchaseOrderVO.setNetAmount(netAmount);
		purchaseOrderVO.setTotalLandedAmount(totalLandedAmount);
		
		purchaseOrderVO.setTotalAmountTax(totalTaxAmount);
		purchaseOrderVO.setAmtInWords(
				amountInWordsConverterService.convert(purchaseOrderVO.getTotalLandedAmount().longValue()));

		purchaseOrderVO.setPurchaseOrderDetailsVO(purchaseOrderDetailsVOs);

	}

	@Override
	public String getPurchaseOrderDocId(Long orgId) {
		String screenCode = "TPI";
		String result = purchaseOrderRepo.getPurchaseOrderDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getSupplierAddressForPurchaseOrder(Long orgId, String supplierName) {
		Set<Object[]> chType = purchaseOrderRepo.findgetSupplierAddressForPurchaseOrder(orgId, supplierName);
		return getSupplierAddressForPurchaseOrder(chType);
	}

	private List<Map<String, Object>> getSupplierAddressForPurchaseOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("contactperson", ch[0] != null ? ch[0].toString() : "");
			map.put("contact", ch[1] != null ? ch[1].toString() : "");
			map.put("full_address", ch[2] != null ? ch[2].toString() : "");
			map.put("stategstin", ch[3] != null ? ch[3].toString() : "");
			map.put("taxtype", ch[4] != null ? ch[4].toString() : "");
			map.put("state", ch[5] != null ? ch[5].toString() : "");
			map.put("pincode", ch[5] != null ? ch[5].toString() : "");
			map.put("city", ch[5] != null ? ch[5].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseIndentForPurchaseOrder(Long orgId, String customerCode,
			String workorderno, String basedOn) {
		Set<Object[]> chType = purchaseOrderRepo.findgetPurchaseIndentForPurchaseOrder(orgId, customerCode, workorderno,
				basedOn);
		return getPurchaseIndentForPurchaseOrder(chType);
	}

	private List<Map<String, Object>> getPurchaseIndentForPurchaseOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getQuotationForPurchaseOrder(Long orgId, String customerCode, String workorderno,
			String basedOn) {
		Set<Object[]> chType = purchaseOrderRepo.findgetQuotationForPurchaseOrder(orgId, customerCode, workorderno,
				basedOn);
		return getQuotationForPurchaseOrder(chType);
	}

	private List<Map<String, Object>> getQuotationForPurchaseOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemForPurchaseOrder(Long orgId, String purchaseIndentNo,String quotationNo) {
		Set<Object[]> chType = purchaseOrderRepo.findgetItemForPurchaseOrder(orgId, purchaseIndentNo,quotationNo);
		return getItemForPurchaseOrder(chType);
	}

	private List<Map<String, Object>> getItemForPurchaseOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("indentqty", ch[2] != null ? ch[2].toString() : "");
			map.put("uom", ch[3] != null ? ch[3].toString() : "");
			map.put("taxslab", ch[4] != null ? ch[4].toString() : "");
			map.put("price", ch[5] != null ? ch[5].toString() : "");
			

			List1.add(map);
		}
		return List1;
	}
}
