package com.efitops.basesetup.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.PurchaseEnquiryDTO;
import com.efitops.basesetup.dto.PurchaseEnquiryDetailsDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO1;
import com.efitops.basesetup.dto.PurchaseIndentDTO2;
import com.efitops.basesetup.dto.PurchaseQuotation1DTO;
import com.efitops.basesetup.dto.PurchaseQuotationAttachmentDTO;
import com.efitops.basesetup.dto.PurchaseQuotationDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.PurchaseEnquiryDetailsVO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseIndentVO1;
import com.efitops.basesetup.entity.PurchaseIndentVO2;
import com.efitops.basesetup.entity.PurchaseQuotation1VO;
import com.efitops.basesetup.entity.PurchaseQuotationAttachmentVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DepartmentRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.EmployeeRepo;
import com.efitops.basesetup.repo.ItemRepo;
import com.efitops.basesetup.repo.PartyMasterRepo;
import com.efitops.basesetup.repo.PurchaseEnquiryDetailsRepo;
import com.efitops.basesetup.repo.PurchaseEnquiryRepo;
import com.efitops.basesetup.repo.PurchaseIndentRepo;
import com.efitops.basesetup.repo.PurchaseIndentRepo1;
import com.efitops.basesetup.repo.PurchaseIndentRepo2;
import com.efitops.basesetup.repo.PurchaseQuotation1Repo;
import com.efitops.basesetup.repo.PurchaseQuotationAttachmentRepo;
import com.efitops.basesetup.repo.PurchaseQuotationRepo;

@Repository
public class PurchaseServiceImpl implements PurchaseService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImpl.class);

	@Autowired
	PurchaseIndentRepo purchaseIndentRepo;

	@Autowired
	PurchaseIndentRepo1 purchaseIndentRepo1;

	@Autowired
	PurchaseIndentRepo2 purchaseIndentRepo2;

	@Autowired
	PartyMasterRepo partyMasterRepo;

	@Autowired
	ItemRepo itemRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	PurchaseEnquiryRepo purchaseEnquiryRepo;

	@Autowired
	PurchaseEnquiryDetailsRepo purchaseEnquiryDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	PurchaseQuotationRepo purchaseQuotationRepo;
	
	@Autowired
	PurchaseQuotation1Repo purchaseQuotation1Repo;
	
	@Autowired
	PurchaseQuotationAttachmentRepo purchaseQuotationAttachmentRepo;
	
	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;
	
	@Override
	public Map<String, Object> updateCreatePurchaseIndent(@Valid PurchaseIndentDTO purchaseIndentDTO)
			throws ApplicationException {

		PurchaseIndentVO purchaseIndentVO;
		String message = null;
		String screenCode="PI";

		if (ObjectUtils.isEmpty(purchaseIndentDTO.getId())) {

			
			purchaseIndentVO = new PurchaseIndentVO();
			purchaseIndentVO.setCreatedBy(purchaseIndentDTO.getCreatedBy());
			purchaseIndentVO.setUpdatedBy(purchaseIndentDTO.getCreatedBy());
			
			String docId = purchaseIndentRepo.getPurchaseIndentByDocId(purchaseIndentDTO.getOrgId(),
					screenCode);

			purchaseIndentVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(purchaseIndentDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			
			message = "PurchaseIndent Creation SuccessFully";
			
		} else {

			purchaseIndentVO = purchaseIndentRepo.findById(purchaseIndentDTO.getId()).orElseThrow(
					() -> new ApplicationException("PurchaseIndent  Not Found with id: " + purchaseIndentDTO.getId()));
			purchaseIndentVO.setUpdatedBy(purchaseIndentDTO.getCreatedBy());

			message = "PurchaseIndent Updation Successfully";
		}

		purchaseIndentVO = getPurchaseIndentVOFromPurchaseIndentDTO(purchaseIndentVO, purchaseIndentDTO);
		purchaseIndentRepo.save(purchaseIndentVO);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("purchaseIndentVO", purchaseIndentVO);
		return response;

	}

	private PurchaseIndentVO getPurchaseIndentVOFromPurchaseIndentDTO(PurchaseIndentVO purchaseIndentVO,
			@Valid PurchaseIndentDTO purchaseIndentDTO) {

		purchaseIndentVO.setIndentType(purchaseIndentDTO.getIndentType());
		purchaseIndentVO.setCustomerName(purchaseIndentDTO.getCustomerName());
		purchaseIndentVO.setCustomerCode(purchaseIndentDTO.getCustomerCode());
		purchaseIndentVO.setWorkOrderNo(purchaseIndentDTO.getWorkOrderNo());
		purchaseIndentVO.setDepartment(purchaseIndentDTO.getDepartment());
		purchaseIndentVO.setFgPart(purchaseIndentDTO.getFgPart());
		purchaseIndentVO.setFgPartDesc(purchaseIndentDTO.getFgPartDesc());
		purchaseIndentVO.setFgQty(purchaseIndentDTO.getFgQty());
		purchaseIndentVO.setRequestedBy(purchaseIndentDTO.getRequestedBy());
		purchaseIndentVO.setCustomerPoNo(purchaseIndentDTO.getCustomerPoNo());
		purchaseIndentVO.setOrgId(purchaseIndentDTO.getOrgId());
//		purchaseIndentVO.setFinYear(purchaseIndentDTO.getFinYear());

		if (purchaseIndentDTO.getId() != null) {

			List<PurchaseIndentVO1> purchaseIndentVO1s = purchaseIndentRepo1.findByPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentRepo1.deleteAll(purchaseIndentVO1s);

			List<PurchaseIndentVO2> purchaseIndentVO2s = purchaseIndentRepo2.findByPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentRepo2.deleteAll(purchaseIndentVO2s);

		}

		List<PurchaseIndentVO1> purchaseIndentVO1s = new ArrayList<PurchaseIndentVO1>();
		for (PurchaseIndentDTO1 purchaseIndentDTO1 : purchaseIndentDTO.getPurchaseIndentDTO1()) {

			PurchaseIndentVO1 purchaseIndentVO1 = new PurchaseIndentVO1();
			purchaseIndentVO1.setItem(purchaseIndentDTO1.getItem());
			purchaseIndentVO1.setItemDescription(purchaseIndentDTO1.getItemDescription());
			purchaseIndentVO1.setUom(purchaseIndentDTO1.getUom());
			purchaseIndentVO1.setReqQty(purchaseIndentDTO1.getReqQty());
			purchaseIndentVO1.setAvlStock(purchaseIndentDTO1.getAvlStock());
			purchaseIndentVO1.setIndentQty(purchaseIndentDTO.getFgQty() * purchaseIndentDTO1.getReqQty());

			purchaseIndentVO1.setPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentVO1s.add(purchaseIndentVO1);
		}

		purchaseIndentVO.setPurchaseIndentVO1(purchaseIndentVO1s);

		List<PurchaseIndentVO2> purchaseIndentVO2s = new ArrayList<PurchaseIndentVO2>();
		for (PurchaseIndentDTO2 purchaseIndentDTO2 : purchaseIndentDTO.getPurchaseIndentDTO2()) {

			PurchaseIndentVO2 purchaseIndentVO2 = new PurchaseIndentVO2();

			purchaseIndentVO2.setVerifiedBy(purchaseIndentDTO2.getVerifiedBy());
			purchaseIndentVO2.setCancelRemarks(purchaseIndentDTO2.getCancelRemarks());

			purchaseIndentVO2.setPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentVO2s.add(purchaseIndentVO2);
		}

		purchaseIndentVO.setPurchaseIndentVO2(purchaseIndentVO2s);
		return purchaseIndentVO;
	}
	
	@Override
	public String getpurchaseIndentDocId(Long orgId) {
		String ScreenCode = "PI";
		String result = purchaseIndentRepo.getPurchaseIndentByDocId(orgId,ScreenCode);
		return result;
	}

	@Override
	public List<PurchaseIndentVO> getAllPurchaseIndentByOrgId(Long orgId) {
		return purchaseIndentRepo.getAllPurchaseIndentByOrgId(orgId);
	}

	@Override
	public Optional<PurchaseIndentVO> getPurchaseIndentById(Long id) {
		return purchaseIndentRepo.getPurchaseIndentById(id);
	}

	@Override
	public List<Map<String, Object>> getCustomerNameForPurchaseIndent(Long orgId) {
		Set<Object[]> cstname = purchaseIndentRepo.findCustomerDetails(orgId);
		return getCustomerDetails(cstname);
	}

	private List<Map<String, Object>> getCustomerDetails(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");
			
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getIndentType(Long orgId) {
		Set<Object[]> materialType = purchaseIndentRepo.findIndentType(orgId);
		return getItem(materialType);
	}

	private List<Map<String, Object>> getItem(Set<Object[]> materialType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : materialType) {
			Map<String, Object> map = new HashMap<>();
			map.put("materialType", ch[0] != null ? ch[0].toString() : ""); // Empty string if null

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getDepartmentForPurchase(Long orgId) {
		Set<Object[]> departmentDetails = purchaseIndentRepo.getDepartmentDetails(orgId);
		return getDepart(departmentDetails);
	}

	private List<Map<String, Object>> getDepart(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
//			map.put("departmentId", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("departmentName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getRequestedByForPurchase(Long orgId) {
		Set<Object[]> requestedByDetails = purchaseIndentRepo.getRequestedByDetails(orgId);
		return getRequested(requestedByDetails);
	}

	private List<Map<String, Object>> getRequested(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
//			map.put("employeeId", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}
	@Override
	public List<Map<String, Object>> getVerifiedByForPurchase(Long orgId) {
		Set<Object[]> verifiedByDetails = purchaseIndentRepo.getVerifiedByForPurchase(orgId);
		return getVerifiedByForPurchase(verifiedByDetails);
	}

	private List<Map<String, Object>> getVerifiedByForPurchase(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getBomItemDetailsForPurchase(Long orgId, String fgPart) {
		Set<Object[]> item = purchaseIndentRepo.findBomItemDetailsForPurchase(orgId,fgPart);
		return getItem1(item);
	}

	private List<Map<String, Object>> getItem1(Set<Object[]> it) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : it) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryUnit", ch[2] != null ? ch[2].toString() : "");
			map.put("bomQty", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getWorkOrderNoForPurchaseIndent(Long orgId, String customerCode) {
		Set<Object[]> workOrderNo = purchaseIndentRepo.findWorkOrderNoForPurchaseIndent(orgId, customerCode);
		return getWorkOrderNoForPurchaseIndent(workOrderNo);
	}

	private List<Map<String, Object>> getWorkOrderNoForPurchaseIndent(Set<Object[]> workOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("fgPart", ch[1] != null ? ch[1].toString() : "");
			map.put("fgPartDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("fgQty", ch[3] != null ? ch[3].toString() : "");
			map.put("customerPoNo", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	// PurchaseEnquiry

	@Override
	public Map<String, Object> updateCreatePurchaseEnquiry(@Valid PurchaseEnquiryDTO purchaseEnquiryDTO)
			throws ApplicationException {

		PurchaseEnquiryVO purchaseEnquiryVO;
		String message = null;
		String screenCode="PE";

		if (ObjectUtils.isEmpty(purchaseEnquiryDTO.getId())) {

			purchaseEnquiryVO = new PurchaseEnquiryVO();
			
			String docId = purchaseEnquiryRepo.getPurchaseEnquiryByDocId(purchaseEnquiryDTO.getOrgId(),
					screenCode);

			purchaseEnquiryVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(purchaseEnquiryDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			
			purchaseEnquiryVO.setCreatedBy(purchaseEnquiryDTO.getCreatedBy());
			purchaseEnquiryVO.setUpdatedBy(purchaseEnquiryDTO.getCreatedBy());

			message = "PurchaseEnquiry Created SuccessFully";
		} else {

			purchaseEnquiryVO = purchaseEnquiryRepo.findById(purchaseEnquiryDTO.getId())
					.orElseThrow(() -> new ApplicationException(
							"purchaseenquiry  Not Found with id: " + purchaseEnquiryDTO.getId()));
			purchaseEnquiryVO.setUpdatedBy(purchaseEnquiryDTO.getCreatedBy());

			message = "PurchaseEnquiry Updation Successfully";

		}

		purchaseEnquiryVO = getPurchaseEnquiryVOFromPurchaseEnquiryDTO(purchaseEnquiryVO, purchaseEnquiryDTO);
		purchaseEnquiryRepo.save(purchaseEnquiryVO);

		Map<String, Object> reponse = new HashMap<String, Object>();
		reponse.put("message", message);
		reponse.put("purchaseEnquiryVO", purchaseEnquiryVO);
		return reponse;

	}

	private PurchaseEnquiryVO getPurchaseEnquiryVOFromPurchaseEnquiryDTO(PurchaseEnquiryVO purchaseEnquiryVO,
			@Valid PurchaseEnquiryDTO purchaseEnquiryDTO) {

		purchaseEnquiryVO.setPurchaseEnquiryNo(purchaseEnquiryDTO.getPurchaseEnquiryNo());
		purchaseEnquiryVO.setPurchaseEnquiryDate(purchaseEnquiryDTO.getPurchaseEnquiryDate());
		purchaseEnquiryVO.setCustomerName(purchaseEnquiryDTO.getCustomerName());
		purchaseEnquiryVO.setCustomerCode(purchaseEnquiryDTO.getCustomerCode());
		purchaseEnquiryVO.setWorkOrderNo(purchaseEnquiryDTO.getWorkOrderNo());
		purchaseEnquiryVO.setPurchaseIndentNo(purchaseEnquiryDTO.getPurchaseIndentNo());
		purchaseEnquiryVO.setCustomerPoNo(purchaseEnquiryDTO.getCustomerPoNo());
		purchaseEnquiryVO.setFgPartName(purchaseEnquiryDTO.getFgPartName());
		purchaseEnquiryVO.setFgPartDesc(purchaseEnquiryDTO.getFgPartDesc());
		purchaseEnquiryVO.setSupplierName(purchaseEnquiryDTO.getSupplierName());
		purchaseEnquiryVO.setSupplierCode(purchaseEnquiryDTO.getSupplierCode());
		purchaseEnquiryVO.setContactPerson(purchaseEnquiryDTO.getContactPerson());
		purchaseEnquiryVO.setContactNo(purchaseEnquiryDTO.getContactNo());
		purchaseEnquiryVO.setEnquiryType(purchaseEnquiryDTO.getEnquiryType());
		purchaseEnquiryVO.setEnquiryDueDate(purchaseEnquiryDTO.getEnquiryDueDate());
		purchaseEnquiryVO.setExpectedDeliveryDate(purchaseEnquiryDTO.getExpectedDeliveryDate());
		purchaseEnquiryVO.setSummary(purchaseEnquiryDTO.getSummary());
		purchaseEnquiryVO.setBranch(purchaseEnquiryDTO.getBranch());
		purchaseEnquiryVO.setBranchCode(purchaseEnquiryDTO.getBranchCode());
		purchaseEnquiryVO.setCreatedBy(purchaseEnquiryDTO.getCreatedBy());
		purchaseEnquiryVO.setCancelRemarks(purchaseEnquiryDTO.getCancelRemarks());
		purchaseEnquiryVO.setFinYear(purchaseEnquiryDTO.getFinYear());
		purchaseEnquiryVO.setOrgId(purchaseEnquiryDTO.getOrgId());

		if (purchaseEnquiryDTO.getId() != null) {

			List<PurchaseEnquiryDetailsVO> detailsVOs = purchaseEnquiryDetailsRepo
					.findByPurchaseEnquiryVO(purchaseEnquiryVO);
			purchaseEnquiryDetailsRepo.deleteAll(detailsVOs);

		}

		List<PurchaseEnquiryDetailsVO> purchaseEnquiryDetailsVOs = new ArrayList<PurchaseEnquiryDetailsVO>();

		for (PurchaseEnquiryDetailsDTO purchaseEnquiryDetailsDTO : purchaseEnquiryDTO.getPurchaseEnquiryDetailsDTO()) {

			PurchaseEnquiryDetailsVO purchaseEnquiryDetailsVO = new PurchaseEnquiryDetailsVO();

			purchaseEnquiryDetailsVO.setItem(purchaseEnquiryDetailsDTO.getItem());
			purchaseEnquiryDetailsVO.setItemDesc(purchaseEnquiryDetailsDTO.getItemDesc());
			purchaseEnquiryDetailsVO.setUnit(purchaseEnquiryDetailsDTO.getUnit());
			purchaseEnquiryDetailsVO.setQtyRequired(purchaseEnquiryDetailsDTO.getQtyRequired());
			purchaseEnquiryDetailsVO.setRemarks(purchaseEnquiryDetailsDTO.getRemarks());

			purchaseEnquiryDetailsVO.setPurchaseEnquiryVO(purchaseEnquiryVO);
			purchaseEnquiryDetailsVOs.add(purchaseEnquiryDetailsVO);

		}

		purchaseEnquiryVO.setPurchaseEnquiryDetailsVO(purchaseEnquiryDetailsVOs);

		return purchaseEnquiryVO;
	}

	@Override
	public List<PurchaseEnquiryVO> getAllPurchaseEnquiryByOrgId(Long orgId) {
		return purchaseEnquiryRepo.getPurchaseEnquiry(orgId);
	}

	@Override
	public Optional<PurchaseEnquiryVO> getAllPurchaseEnquiryById(Long id) {
		return purchaseEnquiryRepo.getPurchaseEnquiryById(id);
	}

	@Override
	public String getPurchaseEnquiryDocId(Long orgId, String finYear, String screenCode) {
		return purchaseEnquiryRepo.getPurchaseEnquiryDocId(orgId,finYear,screenCode);
	}

	@Override
	public List<Map<String, Object>> getSupplierNameForPurchaseEnquiry(Long orgId) {
		Set<Object[]> supplierName = purchaseEnquiryRepo.findSupplierNameForPurchaseEnquiry(orgId);
		return getSupplierNameForPurchaseEnquiry(supplierName);
	}

	private List<Map<String, Object>> getSupplierNameForPurchaseEnquiry(Set<Object[]> supplierName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : supplierName) {
			Map<String, Object> map = new HashMap<>();
			map.put("supplierName", ch[0] != null ? ch[0].toString() : ""); 
			map.put("supplierCode", ch[1] != null ? ch[1].toString() : "");
			
			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public List<Map<String, Object>> getContactPersonDetailsForPurchaseEnquiry(Long orgId ,String supplierCode) {
		Set<Object[]> ContactPersonDetails = purchaseEnquiryRepo.findContactPersonDetailsForPurchaseEnquiry(orgId,supplierCode);
		return getContactPersonDetailsForPurchaseEnquiry(ContactPersonDetails);
	}

	private List<Map<String, Object>> getContactPersonDetailsForPurchaseEnquiry(Set<Object[]> ContactPersonDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : ContactPersonDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("contactPerson", ch[0] != null ? ch[0].toString() : ""); 
			map.put("contactNo", ch[1] != null ? ch[1].toString() : "");
			map.put("taxType", ch[2] != null ? ch[2].toString() : "");

			
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getPurchaseIndentNoForPurchaseEnquiry(Long orgId, String customerCode,String workOrderNo) {
		Set<Object[]> purchaseIndentNo = purchaseEnquiryRepo.findPurchaseIndentNoForPurchaseEnquiry(orgId, customerCode, workOrderNo);
		return getPurchaseIndentNoForPurchaseEnquiry(purchaseIndentNo);
	}

	private List<Map<String, Object>> getPurchaseIndentNoForPurchaseEnquiry(Set<Object[]> purchaseIndentNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : purchaseIndentNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("purchaseIndentNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public List<Map<String, Object>> getItemDetailsForPurchaseEnquiry(Long orgId, String purchaseIndentNo,String fgItem) {
		Set<Object[]> itemDetails = purchaseEnquiryRepo.findItemDetailsForPurchaseEnquiry(orgId, purchaseIndentNo,fgItem);
		return getItemDetailsForPurchaseEnquiry(itemDetails);
	}

	private List<Map<String, Object>> getItemDetailsForPurchaseEnquiry(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("uom", ch[2] != null ? ch[2].toString() : "");
			map.put("qtyRequired", ch[3] != null ? Integer.parseInt(ch[3].toString()) : 0);

			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public List<Map<String, Object>> getWorkOrderNoForPurchaseEnquiry(Long orgId, String customerCode) {
		Set<Object[]> workOrderNo = purchaseEnquiryRepo.findWorkOrderNoForPurchaseEnquiry(orgId, customerCode);
		return getWorkOrderNoForPurchaseEnquiry(workOrderNo);
	}

	private List<Map<String, Object>> getWorkOrderNoForPurchaseEnquiry(Set<Object[]> workOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("fgPart", ch[1] != null ? ch[1].toString() : "");
			map.put("fgPartDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("fgQty", ch[3] != null ? ch[3].toString() : "");
			map.put("customerPoNo", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	
	//PurchaseQuotation
	
	@Override
	public List<PurchaseQuotationVO> getAllPurchaseQuotationByOrgId(Long orgId) {
		return purchaseQuotationRepo.getAllPurchaseQuotationByOrgId(orgId);
	}

	@Override
	public Optional<PurchaseQuotationVO> getPurchaseQuotationById(Long id) {
		return purchaseQuotationRepo.getAllPurchaseQuotationById(id);
	}
	
	
	@Override
	public Map<String, Object> updateCreatePurchaseQuotation(@Valid PurchaseQuotationDTO purchaseQuotationDTO)
			throws ApplicationException {

		PurchaseQuotationVO purchaseQuotationVO = new PurchaseQuotationVO();
		String message = null;
		String screenCode="PQ";

		if (ObjectUtils.isEmpty(purchaseQuotationDTO.getId())) {

			
			purchaseQuotationVO.setCreatedBy(purchaseQuotationDTO.getCreatedBy());
			purchaseQuotationVO.setUpdatedBy(purchaseQuotationDTO.getCreatedBy());
			
			String docId = purchaseQuotationRepo.getPurchaseQuotationByDocId(purchaseQuotationDTO.getOrgId(),
					screenCode);

			purchaseQuotationVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(purchaseQuotationDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			
			message = "PurchaseQuotation Creation SuccessFully";
			
		} else {
			
			purchaseQuotationVO = purchaseQuotationRepo.findById(purchaseQuotationDTO.getId()).orElseThrow(
					() -> new ApplicationException("PurchaseQuotation  Not Found with id: " + purchaseQuotationDTO.getId()));
			purchaseQuotationVO.setUpdatedBy(purchaseQuotationDTO.getCreatedBy());

			message = "PurchaseQuotation Updation Successfully";
			
		}

		purchaseQuotationVO = getPurchaseQuotationVOFromPurchaseQuotationDTO(purchaseQuotationVO, purchaseQuotationDTO);
		purchaseQuotationRepo.save(purchaseQuotationVO);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("purchaseQuotationVO", purchaseQuotationVO);
		return response;

	}

	BigDecimal basicPrice = BigDecimal.ZERO;
	BigDecimal discountAmount = BigDecimal.ZERO;
	BigDecimal quoteAmount = BigDecimal.ZERO;
	private PurchaseQuotationVO getPurchaseQuotationVOFromPurchaseQuotationDTO(PurchaseQuotationVO purchaseQuotationVO,
			@Valid PurchaseQuotationDTO purchaseQuotationDTO) {
		
		if (purchaseQuotationDTO.getId() != null) {
		List<PurchaseQuotation1VO> purchaseQuotation1VOs = purchaseQuotation1Repo.findByPurchaseQuotationVO(purchaseQuotationVO);
		purchaseQuotation1Repo.deleteAll(purchaseQuotation1VOs);

		List<PurchaseQuotationAttachmentVO> purchaseQuotationAttachmentVOs = purchaseQuotationAttachmentRepo.findByPurchaseQuotationVO(purchaseQuotationVO);
		purchaseQuotationAttachmentRepo.deleteAll(purchaseQuotationAttachmentVOs);
		}
		
		List<PurchaseQuotation1VO> purchaseQuotation1VOs = new ArrayList<>();
		for (PurchaseQuotation1DTO purchaseQuotation1DTO : purchaseQuotationDTO.getPurchaseQuotation1DTO()) {

			PurchaseQuotation1VO purchaseQuotation1VO = new PurchaseQuotation1VO();
			purchaseQuotation1VO.setItem(purchaseQuotation1DTO.getItem());
			purchaseQuotation1VO.setItemDesc(purchaseQuotation1DTO.getItemDesc());
			purchaseQuotation1VO.setUnit(purchaseQuotation1DTO.getUnit());
			purchaseQuotation1VO.setQty(purchaseQuotation1DTO.getQty());
			purchaseQuotation1VO.setUnitPrice(purchaseQuotation1DTO.getUnitPrice());
		    
			 basicPrice = purchaseQuotation1DTO.getQty().multiply(purchaseQuotation1DTO.getUnitPrice());
			purchaseQuotation1VO.setBasicPrice(basicPrice);
			
			purchaseQuotation1VO.setDiscount(purchaseQuotation1DTO.getDiscount());
			
		    discountAmount =	basicPrice.divide(BigDecimal.valueOf(100)).multiply(purchaseQuotation1DTO.getDiscount());
			purchaseQuotation1VO.setDiscountAmount(discountAmount);
			
			quoteAmount = basicPrice.subtract(discountAmount);
			purchaseQuotation1VO.setQuoteAmount(quoteAmount);

			purchaseQuotation1VO.setPurchaseQuotationVO(purchaseQuotationVO);
			purchaseQuotation1VOs.add(purchaseQuotation1VO);
		}

		purchaseQuotationVO.setPurchaseQuotation1VO(purchaseQuotation1VOs);

        //Header
		purchaseQuotationVO.setCustomerName(purchaseQuotationDTO.getCustomerName());
		purchaseQuotationVO.setCustomerCode(purchaseQuotationDTO.getCustomerCode());
		purchaseQuotationVO.setWorkOrderNo(purchaseQuotationDTO.getWorkOrderNo());
		purchaseQuotationVO.setEnquiryNo(purchaseQuotationDTO.getEnquiryNo());
		purchaseQuotationVO.setEnquiryDate(purchaseQuotationDTO.getEnquiryDate());
		purchaseQuotationVO.setSuppliername(purchaseQuotationDTO.getSupplierName());
		purchaseQuotationVO.setSupplierId(purchaseQuotationDTO.getSupplierId());
		purchaseQuotationVO.setValidTill(purchaseQuotationDTO.getValidTill());
		purchaseQuotationVO.setKindAttention(purchaseQuotationDTO.getKindAttention());
		purchaseQuotationVO.setTaxCode(purchaseQuotationDTO.getTaxCode());
		purchaseQuotationVO.setContactPerson(purchaseQuotationDTO.getContactPerson());
		purchaseQuotationVO.setContactNo(purchaseQuotationDTO.getContactNo());
		purchaseQuotationVO.setQStatus(purchaseQuotationDTO.getQStatus());
		purchaseQuotationVO.setActive(purchaseQuotationDTO.isActive());
		purchaseQuotationVO.setGrossAmount(basicPrice);
		purchaseQuotationVO.setNetAmount(quoteAmount);
		purchaseQuotationVO.setTotalDiscount(discountAmount);
		purchaseQuotationVO.setNarration(purchaseQuotationDTO.getNarration());
		//Convert AmountInWords
		purchaseQuotationVO.setAmountInWords(
				amountInWordsConverterService.convert(quoteAmount.longValue()));
        
		purchaseQuotationVO.setOrgId(purchaseQuotationDTO.getOrgId());
	

		List<PurchaseQuotationAttachmentVO> purchaseQuotationAttachmentVOs = new ArrayList<>();
		for (PurchaseQuotationAttachmentDTO purchaseQuotationAttachmentDTO : purchaseQuotationDTO.getPurchaseQuotationAttachmentDTO()) {

			PurchaseQuotationAttachmentVO purchaseQuotationAttachmentVO = new PurchaseQuotationAttachmentVO();

			purchaseQuotationAttachmentVO.setFileName(purchaseQuotationAttachmentDTO.getFileName());

			purchaseQuotationAttachmentVO.setPurchaseQuotationVO(purchaseQuotationVO);
			purchaseQuotationAttachmentVOs.add(purchaseQuotationAttachmentVO);
		} 

		purchaseQuotationVO.setPurchaseQuotationAttachmentVO(purchaseQuotationAttachmentVOs);
		return purchaseQuotationVO; 
	}
	
	@Override
	public String getpurchaseQuotationDocId(Long orgId) {
		String ScreenCode = "PQ";
		String result = purchaseQuotationRepo.getPurchaseQuotationByDocId(orgId,ScreenCode);
		return result;
	}
	
	
	@Override
	public List<Map<String, Object>> getPurchaseEnquiryNoForPurchaseQuotation(Long orgId, String customerCode,String workOrderNo) {
		Set<Object[]> purchaseEnquiryNo = purchaseQuotationRepo.findPurchaseEnquiryNoForPurchaseQuotation(orgId, customerCode, workOrderNo);
		return getPurchaseEnquiryNoForPurchaseQuotation(purchaseEnquiryNo);
	}

	private List<Map<String, Object>> getPurchaseEnquiryNoForPurchaseQuotation(Set<Object[]> purchaseEnquiryNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : purchaseEnquiryNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("purchaseEnquiryNo", ch[0] != null ? ch[0].toString() : "");
			map.put("purchaseDate", ch[2] != null ? ch[1].toString() : "");
			map.put("SupplierNo", ch[3] != null ? ch[2].toString() : "");
			map.put("SupplierId", ch[4] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;

	}
	
	
	@Override
	public List<Map<String, Object>> getItemDetailsForPurchaseQuotation(Long orgId, String purchaseIndentNo) {
		Set<Object[]> itemDetails = purchaseQuotationRepo.findItemDetailsForPurchaseQuotation(orgId, purchaseIndentNo);
		return getItemDetailsForPurchaseQuotation(itemDetails);
	}

	private List<Map<String, Object>> getItemDetailsForPurchaseQuotation(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("uom", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public PurchaseQuotationAttachmentVO uploadPurchaseQuatationAttachementsInBloob(MultipartFile file, Long id) throws IOException {
		PurchaseQuotationAttachmentVO purchaseQuotationAttachmentVO = purchaseQuotationAttachmentRepo.findById(id).get();
		purchaseQuotationAttachmentVO.setAttachements(file.getBytes());
		return purchaseQuotationAttachmentRepo.save(purchaseQuotationAttachmentVO);
	}
	
	@Override
	public List<Map<String, Object>> getWorkOrderNoForPurchaseQuotation(Long orgId, String customerCode) {
		Set<Object[]> workOrderNo = purchaseQuotationRepo.findWorkOrderNoForPurchaseQuotation(orgId, customerCode);
		return getWorkOrderNoForPurchaseQuotation(workOrderNo);
	}

	private List<Map<String, Object>> getWorkOrderNoForPurchaseQuotation(Set<Object[]> workOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("fgPart", ch[1] != null ? ch[1].toString() : "");
			map.put("fgPartDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("fgQty", ch[3] != null ? ch[3].toString() : "");
			map.put("customerPoNo", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;

	}
	
}
