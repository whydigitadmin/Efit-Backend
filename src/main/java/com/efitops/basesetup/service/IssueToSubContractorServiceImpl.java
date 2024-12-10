package com.efitops.basesetup.service;

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

import com.efitops.basesetup.dto.DcForSubContractDTO;
import com.efitops.basesetup.dto.DcForSubContractDetailsDTO;
import com.efitops.basesetup.dto.IssueItemDetailsDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.dto.JobWorkOutDTO;
import com.efitops.basesetup.dto.JobWorkOutDetailsDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDetailsDTO;
import com.efitops.basesetup.dto.SubContractInvoiceDTO;
import com.efitops.basesetup.dto.SubContractQuotationDTO;
import com.efitops.basesetup.dto.SubContractQuotationDetailsDTO;
import com.efitops.basesetup.dto.SubContractTaxInvoiceDetailsDTO;
import com.efitops.basesetup.dto.SubContractTermsAndConditionsDTO;
import com.efitops.basesetup.entity.DcForSubContractDetailsVO;
import com.efitops.basesetup.entity.DcForSubContractVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.IssueItemDetailsVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.entity.JobWorkOutDetailsVO;
import com.efitops.basesetup.entity.JobWorkOutVO;
import com.efitops.basesetup.entity.SubContractEnquiryDetailsVO;
import com.efitops.basesetup.entity.SubContractEnquiryVO;
import com.efitops.basesetup.entity.SubContractInvoiceVO;
import com.efitops.basesetup.entity.SubContractQuotationDetailsVO;
import com.efitops.basesetup.entity.SubContractQuotationVO;
import com.efitops.basesetup.entity.SubContractTaxInvoiceDetailsVO;
import com.efitops.basesetup.entity.SubContractTermsAndConditionsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DcForSubContractDetailsRepo;
import com.efitops.basesetup.repo.DcForSubContractRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.IssueItemDetailsRepo;
import com.efitops.basesetup.repo.IssueToSubContractorRepo;
import com.efitops.basesetup.repo.JobWorkOutDetailsRepo;
import com.efitops.basesetup.repo.JobWorkOutRepo;
import com.efitops.basesetup.repo.SubContractEnquiryDetailsRepo;
import com.efitops.basesetup.repo.SubContractEnquiryRepo;
import com.efitops.basesetup.repo.SubContractInvoiceRepo;
import com.efitops.basesetup.repo.SubContractQuotationDetailsRepo;
import com.efitops.basesetup.repo.SubContractQuotationRepo;
import com.efitops.basesetup.repo.SubContractTaxInvoiceDetailsRepo;
import com.efitops.basesetup.repo.SubContractTermsAndConditionsRepo;

@Service
public class IssueToSubContractorServiceImpl implements IssueToSubContractorService {

	public static final Logger LOGGER = LoggerFactory.getLogger(IssueToSubContractorServiceImpl.class);

	@Autowired
	IssueToSubContractorRepo issueToSubContractorRepo;

	@Autowired
	IssueItemDetailsRepo issueItemDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	DcForSubContractRepo dcForSubContractRepo;

	@Autowired
	DcForSubContractDetailsRepo dcForSubContractDetailsRepo;

	@Autowired
	SubContractEnquiryRepo subContractEnquiryRepo;

	@Autowired
	SubContractEnquiryDetailsRepo subContractEnquiryDetailsRepo;

	@Autowired
	SubContractQuotationRepo subContractQuotationRepo;

	@Autowired
	SubContractQuotationDetailsRepo subContractQuotationDetailsRepo;

	@Autowired
	SubContractInvoiceRepo subContractInvoiceRepo;

	@Autowired
	SubContractTaxInvoiceDetailsRepo subContractTaxInvoiceDetailsRepo;

	@Autowired
	SubContractTermsAndConditionsRepo subContractTermsAndConditionsRepo;

	@Autowired
	JobWorkOutRepo jobWorkOutRepo;

	@Autowired
	JobWorkOutDetailsRepo jobWorkOutDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	// IssueToSubContract
	@Override
	public Map<String, Object> createUpdateIssueToSubContractor(IssueToSubContractorDTO issueToSubContractorDTO)
			throws ApplicationException {
		IssueToSubContractorVO issueToSubContractorVO = new IssueToSubContractorVO();
		String message;
		String screenCode = "ITSC";
		if (ObjectUtils.isNotEmpty(issueToSubContractorDTO.getId())) {
			issueToSubContractorVO = issueToSubContractorRepo.findById(issueToSubContractorDTO.getId())
					.orElseThrow(() -> new ApplicationException("IssueToSubContractor Enquiry details"));
			message = "IssueToSubContractor Updated Successfully";
			issueToSubContractorVO.setUpdatedBy(issueToSubContractorDTO.getCreatedBy());

		} else {

			String docId = issueToSubContractorRepo.getIssueToSubContractorDocId(issueToSubContractorDTO.getOrgId(),
					screenCode);
			issueToSubContractorVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(issueToSubContractorDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			issueToSubContractorVO.setCreatedBy(issueToSubContractorDTO.getCreatedBy());
			issueToSubContractorVO.setUpdatedBy(issueToSubContractorDTO.getCreatedBy());

			message = "IssueToSubContractor Created Successfully";
		}
		createUpdatedIssueToSubContractorVOFromIssueToSubContractorDTO(issueToSubContractorDTO, issueToSubContractorVO);
		issueToSubContractorRepo.save(issueToSubContractorVO);
		Map<String, Object> response = new HashMap<>();
		response.put("issueToSubContractorVO", issueToSubContractorVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedIssueToSubContractorVOFromIssueToSubContractorDTO(
			IssueToSubContractorDTO issueToSubContractorDTO, IssueToSubContractorVO issueToSubContractorVO) {
		issueToSubContractorVO.setRouteCardNo(issueToSubContractorDTO.getRouteCardNo());
		issueToSubContractorVO.setCustomerName(issueToSubContractorDTO.getCustomerName());
		issueToSubContractorVO.setDepartment(issueToSubContractorDTO.getDepartment());
		issueToSubContractorVO.setStatus(issueToSubContractorDTO.getStatus());
		issueToSubContractorVO.setOrgId(issueToSubContractorDTO.getOrgId());
		issueToSubContractorVO.setActive(issueToSubContractorDTO.isActive());
		issueToSubContractorVO.setCreatedBy(issueToSubContractorDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(issueToSubContractorDTO.getId())) {
			List<IssueItemDetailsVO> issueItemDetailsVO1 = issueItemDetailsRepo
					.findByIssueToSubContractorVO(issueToSubContractorVO);
			issueItemDetailsRepo.deleteAll(issueItemDetailsVO1);

		}

		List<IssueItemDetailsVO> issueItemDetailsVOs = new ArrayList<>();
		for (IssueItemDetailsDTO issueItemDetailsDTO : issueToSubContractorDTO.getIssueItemDetailsDTO()) {
			IssueItemDetailsVO issueItemDetailsVO = new IssueItemDetailsVO();
			issueItemDetailsVO.setItem(issueItemDetailsDTO.getItem());
			issueItemDetailsVO.setItemDescription(issueItemDetailsDTO.getItemDescription());
			issueItemDetailsVO.setProcess(issueItemDetailsDTO.getProcess());
			issueItemDetailsVO.setQuantity(issueItemDetailsDTO.getQuantity());
			issueItemDetailsVO.setRemarks(issueItemDetailsDTO.getRemarks());

			issueItemDetailsVO.setIssueToSubContractorVO(issueToSubContractorVO);
			issueItemDetailsVOs.add(issueItemDetailsVO);
		}
		issueToSubContractorVO.setIssueItemDetailsVO(issueItemDetailsVOs);

	}

	@Override
	public List<IssueToSubContractorVO> getAllIssueToSubContractorByOrgId(Long orgId) {

		return issueToSubContractorRepo.getAllIssueToSubContractorByOrgId(orgId);
	}

	@Override
	public List<IssueToSubContractorVO> getIssueToSubContractorById(Long id) {

		return issueToSubContractorRepo.getIssueToSubContractorById(id);
	}

	@Override
	public String getIssueToSubContractorDocId(Long orgId) {
		String ScreenCode = "ITSC";
		String result = issueToSubContractorRepo.getIssueToSubContractorDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getRouteCardNoAndItemNo(Long orgId) {
		Set<Object[]> chType = issueToSubContractorRepo.getRouteCardNoAndItemNo(orgId);
		return getRouteCardNo(chType);
	}

	private List<Map<String, Object>> getRouteCardNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("customerName", ch[1] != null ? ch[1].toString() : "");
			map.put("item", ch[2] != null ? ch[2].toString() : "");
			map.put("itemDescription", ch[3] != null ? ch[3].toString() : "");
			map.put("quantity", ch[4] != null ? ch[4].toString() : "");
			map.put("woNo", ch[5] != null ? ch[5].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getDepartmentName(Long orgId) {
		Set<Object[]> chType = issueToSubContractorRepo.getDepartmentName(orgId);
		return getDepartment(chType);
	}

	private List<Map<String, Object>> getDepartment(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("departmentName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getProcessNameFormItemWiseProcess(Long orgId, String item) {
		Set<Object[]> chType = issueToSubContractorRepo.getProcessNameFormItemWiseProcess(orgId, item);
		return getProcessName(chType);
	}

	private List<Map<String, Object>> getProcessName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("processName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// DcForSubContractor

	@Override
	public List<DcForSubContractVO> getDcforSCByOrgId(Long orgId) {
		List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			dcForSubContractVO = dcForSubContractRepo.findDcforSCByOrgId(orgId);
		}
		return dcForSubContractVO;
	}

	@Override
	public List<DcForSubContractVO> getDcforSCById(Long id) {
		List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			dcForSubContractVO = dcForSubContractRepo.getDcforSCById(id);
		}
		return dcForSubContractVO;
	}

	@Override
	public List<Map<String, Object>> getIssueSCNoForDcForSubContracto(Long orgId) {
		Set<Object[]> issuescno = dcForSubContractRepo.findIssueSCNoDetails(orgId);
		return getIssueSCNoForDcForSubContracto(issuescno);
	}

	private List<Map<String, Object>> getIssueSCNoForDcForSubContracto(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("routeCardNo", ch[2] != null ? ch[2].toString() : "");
			map.put("customerName", ch[3] != null ? ch[3].toString() : "");
			map.put("gstin", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getAddressForDcForSubContract(Long orgId, String customerName) {
		Set<Object[]> address = dcForSubContractRepo.findAddressDetails(orgId, customerName);
		return getAddressForDcForSubContract(address);
	}

	private List<Map<String, Object>> getAddressForDcForSubContract(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("routeCardNo", ch[2] != null ? ch[2].toString() : "");
			map.put("customerName", ch[3] != null ? ch[3].toString() : "");
			map.put("gstin", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public Map<String, Object> updateCreateDcForSubContract(DcForSubContractDTO dcForSubContractDTO)
			throws ApplicationException {
		DcForSubContractVO dcForSubContractVO = new DcForSubContractVO();
		String message;
		String screenCode = "DCSC";
		if (ObjectUtils.isNotEmpty(dcForSubContractDTO.getId())) {
			dcForSubContractVO = dcForSubContractRepo.findById(dcForSubContractDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid DcForSubContract details"));
			message = "DcForSubContract Updated Successfully";
			dcForSubContractVO.setUpdatedBy(dcForSubContractDTO.getCreatedBy());

		} else {

			String docId = dcForSubContractRepo.getdcForSubcontractDocId(dcForSubContractDTO.getOrgId(), screenCode);
			dcForSubContractVO.setDeliveryChallanNo(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(dcForSubContractDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			dcForSubContractVO.setCreatedBy(dcForSubContractDTO.getCreatedBy());
			dcForSubContractVO.setUpdatedBy(dcForSubContractDTO.getCreatedBy());

			message = "DcForSubContract Created Successfully";
		}
		createUpdateDcForSubContractVOByDcForSubContractDTO(dcForSubContractDTO, dcForSubContractVO);
		dcForSubContractRepo.save(dcForSubContractVO);
		Map<String, Object> response = new HashMap<>();
		response.put("dcForSubContractVO", dcForSubContractVO);
		response.put("message", message);
		return response;

	}

	private void createUpdateDcForSubContractVOByDcForSubContractDTO(@Valid DcForSubContractDTO dcForSubContractDTO,
			DcForSubContractVO dcForSubContractVO) throws ApplicationException {
		dcForSubContractVO.setDeliveryChallanNo(dcForSubContractDTO.getDeliveryChallanNo());
		dcForSubContractVO.setDeliveryChallanDate(dcForSubContractDTO.getDeliveryChallanDate());
		dcForSubContractVO.setScIssueNo(dcForSubContractDTO.getScIssueNo());
		dcForSubContractVO.setCustomerName(dcForSubContractDTO.getCustomerName());
		dcForSubContractVO.setCustomerAddress(dcForSubContractDTO.getCustomerAddress());
		dcForSubContractVO.setGstNo(dcForSubContractDTO.getGstNo());
		dcForSubContractVO.setSubContractorName(dcForSubContractDTO.getSubContractorName());
		dcForSubContractVO.setSubContractorId(dcForSubContractDTO.getSubContractorId());
		dcForSubContractVO.setSubContractoraddress(dcForSubContractDTO.getSubContractoraddress());
		dcForSubContractVO.setVehicleNo(dcForSubContractDTO.getVehicleNo());
		dcForSubContractVO.setDuedate(dcForSubContractDTO.getDuedate());
		dcForSubContractVO.setDisatchThrough(dcForSubContractDTO.getDisatchThrough());
		dcForSubContractVO.setEwayBillNo(dcForSubContractDTO.getEwayBillNo());
		dcForSubContractVO.setActive(dcForSubContractDTO.isActive());
		dcForSubContractVO.setCreatedBy(dcForSubContractDTO.getCreatedBy());
		dcForSubContractVO.setOrgId(dcForSubContractDTO.getOrgId());

		if (ObjectUtils.isNotEmpty(dcForSubContractVO.getId())) {
			List<DcForSubContractDetailsVO> dcForSubContractDetails1 = dcForSubContractDetailsRepo
					.findByDcForSubContractVO(dcForSubContractVO);
			dcForSubContractDetailsRepo.deleteAll(dcForSubContractDetails1);
		}

		List<DcForSubContractDetailsVO> dcForSubContractDetailsVOs = new ArrayList<>();
		for (DcForSubContractDetailsDTO dcForSubContractDetailsDTO : dcForSubContractDTO
				.getDcForSubContractDetailsDTO()) {
			DcForSubContractDetailsVO dcForSubContractDetailsVO = new DcForSubContractDetailsVO();
			dcForSubContractDetailsVO.setItem(dcForSubContractDetailsDTO.getItem());
			dcForSubContractDetailsVO.setItemDesc(dcForSubContractDetailsDTO.getItemDesc());
			dcForSubContractDetailsVO.setProcess(dcForSubContractDetailsDTO.getProcess());
			dcForSubContractDetailsVO.setUnit(dcForSubContractDetailsDTO.getUnit());
			dcForSubContractDetailsVO.setQty(dcForSubContractDetailsDTO.getQty());
			dcForSubContractDetailsVO.setWeight(dcForSubContractDetailsDTO.getWeight());
			dcForSubContractDetailsVO.setRemarks(dcForSubContractDetailsDTO.getRemarks());
			dcForSubContractDetailsVO.setDcForSubContractVO(dcForSubContractVO); // Set the reference in child entity
			dcForSubContractDetailsVOs.add(dcForSubContractDetailsVO);
		}
		dcForSubContractVO.setDcForSubContractDetailsVO(dcForSubContractDetailsVOs);
	}

	@Override
	public String getDcForSubContractDocId(Long orgId) {
		String screenCode = "DCSC";
		String result = dcForSubContractRepo.getdcForSubcontractDocId(orgId, screenCode);
		return result;
	}

	// SubContractorEnquiry

	@Override
	public Map<String, Object> createUpdateSubContractEnquiry(SubContractEnquiryDTO subContractEnquiryDTO)
			throws ApplicationException {
		SubContractEnquiryVO subContractEnquiryVO = new SubContractEnquiryVO();
		String message;
		String screenCode = "SUB";
		if (ObjectUtils.isNotEmpty(subContractEnquiryDTO.getId())) {
			subContractEnquiryVO = subContractEnquiryRepo.findById(subContractEnquiryDTO.getId())
					.orElseThrow(() -> new ApplicationException("SubContractEnquiry Enquiry details"));
			message = "SubContractEnquiry Updated Successfully";
			subContractEnquiryVO.setUpdatedBy(subContractEnquiryDTO.getCreatedBy());

		} else {

			String docId = subContractEnquiryRepo.getSubContractEnquiryDocId(subContractEnquiryDTO.getOrgId(),
					screenCode);
			subContractEnquiryVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(subContractEnquiryDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			subContractEnquiryVO.setCreatedBy(subContractEnquiryDTO.getCreatedBy());
			subContractEnquiryVO.setUpdatedBy(subContractEnquiryDTO.getCreatedBy());

			message = "SubContractEnquiry Created Successfully";
		}
		createUpdatedSubContractEnquiryVOFromSubContractEnquiryDTO(subContractEnquiryDTO, subContractEnquiryVO);
		subContractEnquiryRepo.save(subContractEnquiryVO);
		Map<String, Object> response = new HashMap<>();
		response.put("subContractEnquiryVO", subContractEnquiryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSubContractEnquiryVOFromSubContractEnquiryDTO(SubContractEnquiryDTO subContractEnquiryDTO,
			SubContractEnquiryVO subContractEnquiryVO) {
		subContractEnquiryVO.setEnquiryType(subContractEnquiryDTO.getEnquiryType());
		subContractEnquiryVO.setSubContractorName(subContractEnquiryDTO.getSubContractorName());
		subContractEnquiryVO.setSubContractorRefNo(subContractEnquiryDTO.getSubContractorRefNo());
		subContractEnquiryVO.setSubContractorRefDate(subContractEnquiryDTO.getSubContractorRefDate());
		subContractEnquiryVO.setRouteCardNo(subContractEnquiryDTO.getRouteCardNo());
		subContractEnquiryVO.setEnquiryDueDate(subContractEnquiryDTO.getEnquiryDueDate());
		subContractEnquiryVO.setRouteCardNo(subContractEnquiryDTO.getRouteCardNo());
		subContractEnquiryVO.setContactNo(subContractEnquiryDTO.getContactNo());
		subContractEnquiryVO.setContactName(subContractEnquiryDTO.getContactName());
		subContractEnquiryVO.setScIssueNo(subContractEnquiryDTO.getScIssueNo());
		subContractEnquiryVO.setOrgId(subContractEnquiryDTO.getOrgId());
		subContractEnquiryVO.setActive(subContractEnquiryDTO.isActive());
		subContractEnquiryVO.setCreatedBy(subContractEnquiryDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(subContractEnquiryDTO.getId())) {
			List<SubContractEnquiryDetailsVO> subContractEnquiryDetailsVO1 = subContractEnquiryDetailsRepo
					.findBySubContractEnquiryVO(subContractEnquiryVO);
			subContractEnquiryDetailsRepo.deleteAll(subContractEnquiryDetailsVO1);

		}

		List<SubContractEnquiryDetailsVO> subContractEnquiryDetailsVOs = new ArrayList<>();
		for (SubContractEnquiryDetailsDTO subContractEnquiryDetailsDTO : subContractEnquiryDTO
				.getSubContractEnquiryDetailsDTO()) {
			SubContractEnquiryDetailsVO subContractEnquiryDetailsVO = new SubContractEnquiryDetailsVO();
			subContractEnquiryDetailsVO.setPart(subContractEnquiryDetailsDTO.getPart());
			subContractEnquiryDetailsVO.setPartDescription(subContractEnquiryDetailsDTO.getPartDescription());
			subContractEnquiryDetailsVO.setProcess(subContractEnquiryDetailsDTO.getProcess());
			subContractEnquiryDetailsVO.setQty(subContractEnquiryDetailsDTO.getQty());
			subContractEnquiryDetailsVO.setDeliveryDate(subContractEnquiryDetailsDTO.getDeliveryDate());
			subContractEnquiryDetailsVO.setRemarks(subContractEnquiryDetailsDTO.getRemarks());

			subContractEnquiryDetailsVO.setSubContractEnquiryVO(subContractEnquiryVO);
			subContractEnquiryDetailsVOs.add(subContractEnquiryDetailsVO);
		}
		subContractEnquiryVO.setSubContractEnquiryDetailsVO(subContractEnquiryDetailsVOs);

	}

	@Override
	public List<SubContractEnquiryVO> getAllSubContractEnquiryByOrgId(Long orgId) {

		return subContractEnquiryRepo.getAllSubContractEnquiryByOrgId(orgId);
	}

	@Override
	public List<SubContractEnquiryVO> getSubContractEnquiryById(Long id) {

		return subContractEnquiryRepo.getSubContractEnquiryById(id);
	}

	@Override
	public String getSubContractEnquiryDocId(Long orgId) {

		String ScreenCode = "SUB";
		String result = subContractEnquiryRepo.getSubContractEnquiryDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getSubContractCustomerNameAndCode(Long orgId) {
		Set<Object[]> chType = subContractEnquiryRepo.getSubContractCustomerNameAndCode(orgId);
		return getSubCustomerName(chType);
	}

	private List<Map<String, Object>> getSubCustomerName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("subContractName", ch[0] != null ? ch[0].toString() : "");
			map.put("subContractRefNo", ch[1] != null ? ch[1].toString() : "");
			map.put("taxCode", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubContractContactNameAndNo(Long orgId, String partyCode) {
		Set<Object[]> chType = subContractEnquiryRepo.getSubContractContactNameAndNo(orgId, partyCode);
		return getSubContactName(chType);
	}

	private List<Map<String, Object>> getSubContactName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("contractorName", ch[0] != null ? ch[0].toString() : "");
			map.put("contractorNo", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubContractPartNoAndDescription(Long orgId, String scIssueNo) {
		Set<Object[]> chType = subContractEnquiryRepo.getSubContractPartNoAndDescription(orgId, scIssueNo);
		return getSubContractPartNoAndDes(chType);
	}

	private List<Map<String, Object>> getSubContractPartNoAndDes(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("part", ch[0] != null ? ch[0].toString() : "");
			map.put("partDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("process", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubRouteCardNo(Long orgId) {
		Set<Object[]> chType = subContractEnquiryRepo.getSubRouteCardNo(orgId);
		return getSubRouteCard(chType);
	}

	private List<Map<String, Object>> getSubRouteCard(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getScIssueNoFormSubContract(Long orgId, String routeCardNo) {
		Set<Object[]> chType = subContractEnquiryRepo.getScIssueNoFormSubContract(orgId, routeCardNo);
		return getScIssueNo(chType);
	}

	private List<Map<String, Object>> getScIssueNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("scIssueNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// SubContractQuotation

	@Override
	public Map<String, Object> createUpdateSubContractQuotation(SubContractQuotationDTO subContractQuotationDTO)
			throws ApplicationException {
		SubContractQuotationVO subContractQuotationVO = new SubContractQuotationVO();
		String message;
		String screenCode = "SCQ";
		if (ObjectUtils.isNotEmpty(subContractQuotationDTO.getId())) {
			subContractQuotationVO = subContractQuotationRepo.findById(subContractQuotationDTO.getId())
					.orElseThrow(() -> new ApplicationException("SubContractQuotation Enquiry details"));
			message = "SubContractQuotation Updated Successfully";
			subContractQuotationVO.setUpdatedBy(subContractQuotationDTO.getCreatedBy());

		} else {

			String docId = subContractQuotationRepo.getSubContractQuotationDocId(subContractQuotationDTO.getOrgId(),
					screenCode);
			subContractQuotationVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(subContractQuotationDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			subContractQuotationVO.setCreatedBy(subContractQuotationDTO.getCreatedBy());
			subContractQuotationVO.setUpdatedBy(subContractQuotationDTO.getCreatedBy());

			message = "SubContractQuotation Created Successfully";
		}
		createUpdatedSubContractQuotationVOFromSubContractQuotationDTO(subContractQuotationDTO, subContractQuotationVO);
		subContractQuotationRepo.save(subContractQuotationVO);
		Map<String, Object> response = new HashMap<>();
		response.put("subContractQuotationVO", subContractQuotationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSubContractQuotationVOFromSubContractQuotationDTO(
			SubContractQuotationDTO subContractQuotationDTO, SubContractQuotationVO subContractQuotationVO) {

		subContractQuotationVO.setEnquiryNo(subContractQuotationDTO.getEnquiryNo());
		subContractQuotationVO.setEnquiryDate(subContractQuotationDTO.getEnquiryDate());
		subContractQuotationVO.setSubContractorId(subContractQuotationDTO.getSubContractorId());
		subContractQuotationVO.setSubContractorName(subContractQuotationDTO.getSubContractorName());
		subContractQuotationVO.setVaildTill(subContractQuotationDTO.getVaildTill());
		subContractQuotationVO.setTaxCode(subContractQuotationDTO.getTaxCode());
		subContractQuotationVO.setRouteCardNo(subContractQuotationDTO.getRouteCardNo());
		subContractQuotationVO.setContactPerson(subContractQuotationDTO.getContactPerson());
		subContractQuotationVO.setContactNo(subContractQuotationDTO.getContactNo());
		subContractQuotationVO.setScIssueNo(subContractQuotationDTO.getScIssueNo());
		subContractQuotationVO.setNarration(subContractQuotationDTO.getNarration());
		subContractQuotationVO.setOrgId(subContractQuotationDTO.getOrgId());
		subContractQuotationVO.setActive(subContractQuotationDTO.isActive());
		subContractQuotationVO.setCreatedBy(subContractQuotationDTO.getCreatedBy());

		BigDecimal grocessAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(subContractQuotationDTO.getId())) {
			List<SubContractQuotationDetailsVO> subContractQuotationDetailsVO1 = subContractQuotationDetailsRepo
					.findBySubContractQuotationVO(subContractQuotationVO);
			subContractQuotationDetailsRepo.deleteAll(subContractQuotationDetailsVO1);
		}

		List<SubContractQuotationDetailsVO> subContractQuotationDetailsVOs = new ArrayList<>();

		for (SubContractQuotationDetailsDTO subContractQuotationDetailsDTO : subContractQuotationDTO
				.getSubContractQuotationDetailsDTO()) {

			SubContractQuotationDetailsVO subContractQuotationDetailsVO = new SubContractQuotationDetailsVO();

			subContractQuotationDetailsVO.setPart(subContractQuotationDetailsDTO.getPart());
			subContractQuotationDetailsVO.setPartDescription(subContractQuotationDetailsDTO.getPartDescription());
			subContractQuotationDetailsVO.setProcess(subContractQuotationDetailsDTO.getProcess());
			subContractQuotationDetailsVO.setQty(subContractQuotationDetailsDTO.getQty());
			subContractQuotationDetailsVO.setRate(subContractQuotationDetailsDTO.getRate());
			subContractQuotationDetailsVO.setDiscount(subContractQuotationDetailsDTO.getDiscount());
			subContractQuotationDetailsVO.setTax(subContractQuotationDetailsDTO.getTax());

			BigDecimal discountAmount = BigDecimal.ZERO;
			BigDecimal afterDiscountAmount = BigDecimal.ZERO;
			BigDecimal afterQuotationAmount = BigDecimal.ZERO;

			BigDecimal amount = subContractQuotationDetailsDTO.getRate()
					.multiply(subContractQuotationDetailsDTO.getQty());
			subContractQuotationDetailsVO.setAmount(amount);
			grocessAmount = grocessAmount.add(amount);

			discountAmount = subContractQuotationDetailsVO.getAmount()
					.multiply(subContractQuotationDetailsDTO.getDiscount()).divide(BigDecimal.valueOf(100));

			subContractQuotationDetailsVO.setDiscountAmount(discountAmount);

			afterDiscountAmount = amount.subtract(discountAmount);
//			subContractQuotationDetailsVO.setAfterDiscountAmount(afterDiscountAmount);
			subContractQuotationDetailsDTO.setAfterDiscountAmount(afterDiscountAmount);

			afterQuotationAmount = subContractQuotationDetailsDTO.getAfterDiscountAmount()
					.multiply(subContractQuotationDetailsDTO.getTax()).divide(BigDecimal.valueOf(100));

//			subContractQuotationDetailsVO.setAfterQuotationAmount(afterQuotationAmount);
			subContractQuotationDetailsDTO.setAfterQuotationAmount(afterQuotationAmount);

			BigDecimal quotationAmount = afterDiscountAmount.add(afterQuotationAmount);
			subContractQuotationDetailsVO.setQuotationAmount(quotationAmount);
			netAmount = netAmount.add(quotationAmount);

			subContractQuotationDetailsVO.setDeliveryDate(subContractQuotationDetailsDTO.getDeliveryDate());

			subContractQuotationDetailsVO.setSubContractQuotationVO(subContractQuotationVO);

			subContractQuotationDetailsVOs.add(subContractQuotationDetailsVO);
		}

		subContractQuotationVO.setGrossAmount(grocessAmount);
		subContractQuotationVO.setNetAmount(netAmount);
		subContractQuotationVO.setAmountInWords(
				amountInWordsConverterService.convert(subContractQuotationVO.getNetAmount().longValue()));
		subContractQuotationVO.setSubContractQuotationDetailsVO(subContractQuotationDetailsVOs);
	}

	@Override
	public List<SubContractQuotationVO> getAllSubContractQuotationByOrgId(Long orgId) {

		return subContractQuotationRepo.getAllSubContractQuotationByOrgId(orgId);
	}

	@Override
	public List<SubContractQuotationVO> getSubContractQuotationById(Long id) {

		return subContractQuotationRepo.getSubContractQuotationById(id);
	}

	@Override
	public String getSubContractQuotationDocId(Long orgId) {
		String ScreenCode = "SCQ";
		String result = subContractQuotationRepo.getSubContractQuotationDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPartNoAndPartDesBasedOnSubContractEnquiryNo(Long orgId, String docId) {
		Set<Object[]> chType = subContractQuotationRepo.getPartNoAndPartDesBasedOnSubContractEnquiryNo(orgId, docId);
		return PartNoAndPartDesBasedOnSubContract(chType);
	}

	private List<Map<String, Object>> PartNoAndPartDesBasedOnSubContract(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("enquiryNo", ch[0] != null ? ch[0].toString() : "");
			map.put("enquiryDate", ch[1] != null ? ch[1].toString() : "");
			map.put("subContractId", ch[2] != null ? ch[2].toString() : "");
			map.put("validTill", ch[3] != null ? ch[3].toString() : "");
			map.put("subContractorName", ch[4] != null ? ch[4].toString() : "");
			map.put("routeCardNo", ch[5] != null ? ch[5].toString() : "");
			map.put("scIssueNo", ch[6] != null ? ch[6].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// SubContractInvoice

	@Override
	public Map<String, Object> createUpdateSubContractInvoice(SubContractInvoiceDTO subContractInvoiceDTO)
			throws ApplicationException {
		SubContractInvoiceVO subContractInvoiceVO = new SubContractInvoiceVO();
		String message;
		String screenCode = "SCI";
		if (ObjectUtils.isNotEmpty(subContractInvoiceDTO.getId())) {
			subContractInvoiceVO = subContractInvoiceRepo.findById(subContractInvoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("SubContractInvoice Enquiry details"));
			message = "SubContractInvoice Updated Successfully";
			subContractInvoiceVO.setUpdatedBy(subContractInvoiceDTO.getCreatedBy());

		} else {

			String docId = subContractInvoiceRepo.getSubContractInvoiceDocId(subContractInvoiceDTO.getOrgId(),
					screenCode);
			subContractInvoiceVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(subContractInvoiceDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			subContractInvoiceVO.setCreatedBy(subContractInvoiceDTO.getCreatedBy());
			subContractInvoiceVO.setUpdatedBy(subContractInvoiceDTO.getCreatedBy());

			message = "SubContractInvoice Created Successfully";
		}
		createUpdatedSubContractInvoiceVOFromSubContractInvoiceDTO(subContractInvoiceDTO, subContractInvoiceVO);
		subContractInvoiceRepo.save(subContractInvoiceVO);
		Map<String, Object> response = new HashMap<>();
		response.put("subContractInvoiceVO", subContractInvoiceVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSubContractInvoiceVOFromSubContractInvoiceDTO(SubContractInvoiceDTO subContractInvoiceDTO,
			SubContractInvoiceVO subContractInvoiceVO) {
		subContractInvoiceVO.setJobWorkOrderNo(subContractInvoiceDTO.getJobWorkOrderNo());
		subContractInvoiceVO.setDcno(subContractInvoiceDTO.getDcno());
		subContractInvoiceVO.setDeliveryNoteDate(subContractInvoiceDTO.getDeliveryNoteDate());
		subContractInvoiceVO.setDispatchedThrough(subContractInvoiceDTO.getDispatchedThrough());
		subContractInvoiceVO.setRouteCardNo(subContractInvoiceDTO.getRouteCardNo());
		subContractInvoiceVO.setSubContractorCode(subContractInvoiceDTO.getSubContractorCode());
		subContractInvoiceVO.setSubContractorName(subContractInvoiceDTO.getSubContractorName());
		subContractInvoiceVO.setSubContractorAddress(subContractInvoiceDTO.getSubContractorAddress());
		subContractInvoiceVO.setCreatedBy(subContractInvoiceDTO.getCreatedBy());
		subContractInvoiceVO.setOrgId(subContractInvoiceDTO.getOrgId());
		subContractInvoiceVO.setActive(subContractInvoiceDTO.isActive());
		subContractInvoiceVO.setNarration(subContractInvoiceDTO.getNarration());

		if (ObjectUtils.isNotEmpty(subContractInvoiceDTO.getId())) {
			List<SubContractTaxInvoiceDetailsVO> subContractTaxInvoiceDetailsVO1 = subContractTaxInvoiceDetailsRepo
					.findBySubContractInvoiceVO(subContractInvoiceVO);
			subContractTaxInvoiceDetailsRepo.deleteAll(subContractTaxInvoiceDetailsVO1);

			List<SubContractTermsAndConditionsVO> subContractTermsAndConditionsVO1 = subContractTermsAndConditionsRepo
					.findBySubContractInvoiceVO(subContractInvoiceVO);
			subContractTermsAndConditionsRepo.deleteAll(subContractTermsAndConditionsVO1);
		}

		List<SubContractTaxInvoiceDetailsVO> subContractTaxInvoiceDetailsVOs = new ArrayList<>();
		for (SubContractTaxInvoiceDetailsDTO subContractTaxInvoiceDetailsDTO : subContractInvoiceDTO
				.getSubContractTaxInvoiceDetailsDTO()) {
			SubContractTaxInvoiceDetailsVO subContractTaxInvoiceDetailsVO = new SubContractTaxInvoiceDetailsVO();
			subContractTaxInvoiceDetailsVO.setPartNo(subContractTaxInvoiceDetailsDTO.getPartNo());
			subContractTaxInvoiceDetailsVO.setPartDes(subContractTaxInvoiceDetailsDTO.getPartDes());
			subContractTaxInvoiceDetailsVO.setProcess(subContractTaxInvoiceDetailsDTO.getProcess());
			subContractTaxInvoiceDetailsVO.setQuantityNos(subContractTaxInvoiceDetailsDTO.getQuantityNos());
			subContractTaxInvoiceDetailsVO.setRate(subContractTaxInvoiceDetailsDTO.getRate());
			subContractTaxInvoiceDetailsVO.setAmount(subContractTaxInvoiceDetailsDTO.getAmount());
			subContractTaxInvoiceDetailsVO.setCgst(subContractTaxInvoiceDetailsDTO.getCgst());
			subContractTaxInvoiceDetailsVO.setSgst(subContractTaxInvoiceDetailsDTO.getSgst());
			subContractTaxInvoiceDetailsVO.setLandedAmount(subContractTaxInvoiceDetailsDTO.getLandedAmount());
			subContractTaxInvoiceDetailsVO.setQuotationAmount(subContractTaxInvoiceDetailsDTO.getQuotationAmount());
			subContractTaxInvoiceDetailsVO.setSubContractInvoiceVO(subContractInvoiceVO);
			subContractTaxInvoiceDetailsVOs.add(subContractTaxInvoiceDetailsVO);
		}
		subContractInvoiceVO.setSubContractTaxInvoiceDetailsVO(subContractTaxInvoiceDetailsVOs);

		List<SubContractTermsAndConditionsVO> subContractTermsAndConditionsVOs = new ArrayList<>();
		for (SubContractTermsAndConditionsDTO subContractTermsAndConditionsDTO : subContractInvoiceDTO
				.getSubContractTermsAndConditionsDTO()) {
			SubContractTermsAndConditionsVO subContractTermsAndConditionsVO = new SubContractTermsAndConditionsVO();
			subContractTermsAndConditionsVO.setTerms(subContractTermsAndConditionsDTO.getTerms());
			subContractTermsAndConditionsVO.setDescription(subContractTermsAndConditionsDTO.getDescription());
			subContractTermsAndConditionsVO.setSubContractInvoiceVO(subContractInvoiceVO);
			subContractTermsAndConditionsVOs.add(subContractTermsAndConditionsVO);
		}
		subContractInvoiceVO.setSubContractTermsAndConditionsVO(subContractTermsAndConditionsVOs);
	}

	@Override
	public List<SubContractInvoiceVO> getAllSubContractInvoiceByOrgId(Long orgId) {

		return subContractInvoiceRepo.getAllSubContractInvoiceByOrgId(orgId);
	}

	@Override
	public List<SubContractInvoiceVO> getSubContractInvoiceById(Long id) {

		return subContractInvoiceRepo.getSubContractInvoiceById(id);
	}

	@Override
	public String getSubContractInvoiceDocId(Long orgId) {
		String ScreenCode = "SCI";
		String result = subContractInvoiceRepo.getSubContractInvoiceDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutOrderNo(Long orgId) {
		Set<Object[]> chType = subContractInvoiceRepo.getJobWorkOutOrderNo(orgId);
		return getWorkOrderOut(chType);
	}

	private List<Map<String, Object>> getWorkOrderOut(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobworkorderno", ch[0] != null ? ch[0].toString() : "");
			map.put("dcno", ch[1] != null ? ch[1].toString() : "");
			map.put("deliverydDate", ch[2] != null ? ch[2].toString() : "");
			map.put("dispatchedthrough", ch[3] != null ? ch[3].toString() : "");
			map.put("routeCardNo", ch[4] != null ? ch[4].toString() : "");
			map.put("contractorCode", ch[5] != null ? ch[5].toString() : "");
			map.put("contractorName", ch[6] != null ? ch[6].toString() : "");
			map.put("subContractorAddress", ch[7] != null ? ch[7].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutOrderFromPartNoAndDesc(Long orgId, String docId) {
		Set<Object[]> chType = subContractInvoiceRepo.getJobWorkOutOrderFromPartNoAndDesc(orgId, docId);
		return getJobWorkOutOrderFromPartNo(chType);
	}

	private List<Map<String, Object>> getJobWorkOutOrderFromPartNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("part", ch[0] != null ? ch[0].toString() : "");
			map.put("partDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("process", ch[2] != null ? ch[2].toString() : "");
			map.put("quantityNos", ch[3] != null ? ch[3].toString() : "");
			map.put("rate", ch[4] != null ? ch[4].toString() : "");
			map.put("amount", ch[5] != null ? ch[5].toString() : "");
			map.put("cgst", ch[6] != null ? ch[6].toString() : "");
			map.put("sgst", ch[7] != null ? ch[7].toString() : "");
			map.put("landedAmount", ch[8] != null ? ch[8].toString() : "");
			map.put("grossAmount", ch[9] != null ? ch[9].toString() : "");
			map.put("totalTaxAmount", ch[10] != null ? ch[10].toString() : "");
			map.put("netAmount", ch[11] != null ? ch[11].toString() : "");
			map.put("amountInWords", ch[12] != null ? ch[12].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// JoibWorkOut

	@Override
	public Map<String, Object> createUpdateJobWorkOut(JobWorkOutDTO jobWorkOutDTO) throws ApplicationException {
		JobWorkOutVO jobWorkOutVO = new JobWorkOutVO();
		String message;
		String screenCode = "JWO";
		if (ObjectUtils.isNotEmpty(jobWorkOutDTO.getId())) {
			jobWorkOutVO = jobWorkOutRepo.findById(jobWorkOutDTO.getId())
					.orElseThrow(() -> new ApplicationException("SubContractEnquiry Enquiry details"));
			message = "jobWorkOut Updated Successfully";
			jobWorkOutVO.setUpdatedBy(jobWorkOutDTO.getCreatedBy());

		} else {

			String docId = jobWorkOutRepo.getJobWorkOutDocId(jobWorkOutDTO.getOrgId(), screenCode);
			jobWorkOutVO.setJobWorkOrderNo(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(jobWorkOutDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			jobWorkOutVO.setCreatedBy(jobWorkOutDTO.getCreatedBy());
			jobWorkOutVO.setUpdatedBy(jobWorkOutDTO.getCreatedBy());

			message = "jobWorkOut Created Successfully";
		}
		createUpdatedJodWorkOutVOFromJodWorkOuDTO(jobWorkOutDTO, jobWorkOutVO);
		jobWorkOutRepo.save(jobWorkOutVO);
		Map<String, Object> response = new HashMap<>();
		response.put("jobWorkOutVO", jobWorkOutVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedJodWorkOutVOFromJodWorkOuDTO(JobWorkOutDTO jobWorkOutDTO, JobWorkOutVO jobWorkOutVO) {
		jobWorkOutVO.setJobWorkOrderDate(jobWorkOutDTO.getJobWorkOrderDate());
		jobWorkOutVO.setDcNo(jobWorkOutDTO.getDcNo());
		jobWorkOutVO.setRouteCardNo(jobWorkOutDTO.getRouteCardNo());
		jobWorkOutVO.setPoNo(jobWorkOutDTO.getPoNo());
		jobWorkOutVO.setQuotationNo(jobWorkOutDTO.getQuotationNo());
		jobWorkOutVO.setContractorName(jobWorkOutDTO.getContractorName());
		jobWorkOutVO.setContractorCode(jobWorkOutDTO.getContractorCode());
		jobWorkOutVO.setDestination(jobWorkOutDTO.getDestination());
		jobWorkOutVO.setDurationOfProcess(jobWorkOutDTO.getDurationOfProcess());
		jobWorkOutVO.setDispatchedThrough(jobWorkOutDTO.getDispatchedThrough());
		jobWorkOutVO.setTaxType(jobWorkOutDTO.getTaxType());
		jobWorkOutVO.setTermsOfPayment(jobWorkOutDTO.getTermsOfPayment());
		jobWorkOutVO.setNarration(jobWorkOutDTO.getNarration());
		jobWorkOutVO.setOrgId(jobWorkOutDTO.getOrgId());
		jobWorkOutVO.setActive(jobWorkOutDTO.isActive());
		jobWorkOutVO.setCreatedBy(jobWorkOutDTO.getCreatedBy());

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalGrossAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(jobWorkOutDTO.getId())) {
			List<JobWorkOutDetailsVO> jobWorkOutDetailsVO1 = jobWorkOutDetailsRepo.findByJobWorkOutVO(jobWorkOutVO);
			jobWorkOutDetailsRepo.deleteAll(jobWorkOutDetailsVO1);

		}

		List<JobWorkOutDetailsVO> jobWorkOutDetailsVOs = new ArrayList<>();
		for (JobWorkOutDetailsDTO jobWorkOutDetailsDTO : jobWorkOutDTO.getJobWorkOutDetailsDTO()) {
			JobWorkOutDetailsVO jobWorkOutDetailsVO = new JobWorkOutDetailsVO();
			jobWorkOutDetailsVO.setPart(jobWorkOutDetailsDTO.getPart());
			jobWorkOutDetailsVO.setPartDesc(jobWorkOutDetailsDTO.getPartDesc());
			jobWorkOutDetailsVO.setProcess(jobWorkOutDetailsDTO.getProcess());
			jobWorkOutDetailsVO.setDueOn(jobWorkOutDetailsDTO.getDueOn());
			jobWorkOutDetailsVO.setQuantityNos(jobWorkOutDetailsDTO.getQuantityNos());
			jobWorkOutDetailsVO.setDiscount(jobWorkOutDetailsDTO.getDiscount());
			jobWorkOutDetailsVO.setRate(jobWorkOutDetailsDTO.getRate());
			jobWorkOutDetailsVO.setCgst(jobWorkOutDetailsDTO.getCgst());
			jobWorkOutDetailsVO.setSgst(jobWorkOutDetailsDTO.getSgst());
			jobWorkOutDetailsVO.setIgst(jobWorkOutDetailsDTO.getIgst());

			BigDecimal taxAmountAll = BigDecimal.ZERO;
			BigDecimal amount = BigDecimal.ZERO;

			BigDecimal setAmountGross = jobWorkOutDetailsDTO.getQuantityNos().multiply(jobWorkOutDetailsDTO.getRate());
			jobWorkOutDetailsVO.setGrossAmt(setAmountGross);

			BigDecimal discountAmount = jobWorkOutDetailsVO.getGrossAmt().multiply(jobWorkOutDetailsDTO.getDiscount())
					.divide(BigDecimal.valueOf(100));
			jobWorkOutDetailsVO.setDiscountAmount(discountAmount);

			BigDecimal netAmounts = jobWorkOutDetailsVO.getGrossAmt().subtract(jobWorkOutDetailsVO.getDiscountAmount());
			jobWorkOutDetailsVO.setNetAmount(netAmounts);
			totalGrossAmount = totalGrossAmount.add(jobWorkOutDetailsVO.getNetAmount());

			BigDecimal sgstamount = jobWorkOutDetailsDTO.getSgst().multiply(jobWorkOutDetailsVO.getNetAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal cgstamount = jobWorkOutDetailsDTO.getCgst().multiply(jobWorkOutDetailsVO.getNetAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal igstamount = jobWorkOutDetailsDTO.getIgst().multiply(jobWorkOutDetailsVO.getNetAmount())
					.divide(BigDecimal.valueOf(100));

			taxAmountAll = taxAmountAll.add(cgstamount).add(sgstamount).add(igstamount);
			jobWorkOutDetailsVO.setTaxAmt(taxAmountAll);
			totalTaxAmount = totalTaxAmount.add(jobWorkOutDetailsVO.getTaxAmt());

			amount = jobWorkOutDetailsVO.getNetAmount().add(jobWorkOutDetailsVO.getTaxAmt());
			jobWorkOutDetailsVO.setAmount(amount);
			totalAmount = totalAmount.add(jobWorkOutDetailsVO.getAmount());

			jobWorkOutDetailsVO.setJobWorkOutVO(jobWorkOutVO);
			jobWorkOutDetailsVOs.add(jobWorkOutDetailsVO);
		}
		jobWorkOutVO.setTotalAmount(totalAmount);
		jobWorkOutVO.setTotalGrossAmt(totalGrossAmount);
		jobWorkOutVO.setTotalTax(totalTaxAmount);
		jobWorkOutVO.setAmountInWords(amountInWordsConverterService.convert(jobWorkOutVO.getTotalAmount().longValue()));
		jobWorkOutVO.setJobWorkOutDetailsVO(jobWorkOutDetailsVOs);

	}

	@Override
	public List<JobWorkOutVO> getAllJobWorkOutById(Long id) {

		return jobWorkOutRepo.getAllJobWorkOutById(id);
	}

	@Override
	public String getJobWorkOutDocId(Long orgId) {
		String ScreenCode = "JWO";
		String result = jobWorkOutRepo.getJobWorkOutDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<JobWorkOutVO> getAllJobWorkOutByOrgId(Long orgId) {

		return jobWorkOutRepo.getAllJobWorkOutByOrgId(orgId);
	}

	@Override
	public List<Map<String, Object>> getDcForSubContractForJobWorkOut(Long orgId) {
		Set<Object[]> address = jobWorkOutRepo.findDcForSubContractDetails(orgId);
		return getDcForSubContractForJobWorkOut(address);
	}

	private List<Map<String, Object>> getDcForSubContractForJobWorkOut(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("customername", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("customeraddress", ch[1] != null ? ch[1].toString() : "");
			map.put("dcno", ch[2] != null ? ch[2].toString() : "");
			map.put("gstno", ch[3] != null ? ch[3].toString() : "");
			map.put("routecardno", ch[4] != null ? ch[4].toString() : "");
			map.put("scissueno", ch[5] != null ? ch[5].toString() : "");
			map.put("subcontractorid", ch[6] != null ? ch[6].toString() : "");
			map.put("subcontractorname", ch[7] != null ? ch[7].toString() : "");
			map.put("subcontractoraddress", ch[8] != null ? ch[8].toString() : "");
			map.put("vehicleno", ch[9] != null ? ch[9].toString() : "");

			List1.add(map);
		}
		return List1;
	}

}
