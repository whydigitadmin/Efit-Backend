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

import com.efitops.basesetup.dto.DcForSubContractDTO;
import com.efitops.basesetup.dto.DcForSubContractDetailsDTO;
import com.efitops.basesetup.dto.IssueItemDetailsDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDetailsDTO;
import com.efitops.basesetup.entity.DcForSubContractDetailsVO;
import com.efitops.basesetup.entity.DcForSubContractVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.IssueItemDetailsVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.entity.SubContractEnquiryDetailsVO;
import com.efitops.basesetup.entity.SubContractEnquiryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DcForSubContractDetailsRepo;
import com.efitops.basesetup.repo.DcForSubContractRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.IssueItemDetailsRepo;
import com.efitops.basesetup.repo.IssueToSubContractorRepo;
import com.efitops.basesetup.repo.SubContractEnquiryDetailsRepo;
import com.efitops.basesetup.repo.SubContractEnquiryRepo;

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
	DcForSubContractDetailsRepo  dcForSubContractDetailsRepo;
	
	@Autowired
	SubContractEnquiryRepo subContractEnquiryRepo;
	
	@Autowired
	SubContractEnquiryDetailsRepo subContractEnquiryDetailsRepo;
	

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

	private void createUpdatedIssueToSubContractorVOFromIssueToSubContractorDTO(IssueToSubContractorDTO issueToSubContractorDTO,
			IssueToSubContractorVO issueToSubContractorVO) {
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
	
	
	//DcForSubContractor
	
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
		public List<DcForSubContractVO> getDcforSCById (Long id) {
			List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
			if (ObjectUtils.isNotEmpty(id)) {
				LOGGER.info("Successfully Received Shift BY Id : {}", id);
				dcForSubContractVO = dcForSubContractRepo.getDcforSCById(id);
			}
			return dcForSubContractVO;
	}


	
		@Override
		public Map<String, Object> updateCreateDcForSubContract(DcForSubContractDTO dcForSubContractDTO) throws ApplicationException {
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
			createUpdateDcForSubContractVOByDcForSubContractDTO(dcForSubContractDTO,dcForSubContractVO);
			dcForSubContractRepo.save(dcForSubContractVO);
			Map<String, Object> response = new HashMap<>();
			response.put("dcForSubContractVO", dcForSubContractVO);
			response.put("message", message);
			return response;

		}
		

		private void createUpdateDcForSubContractVOByDcForSubContractDTO(@Valid DcForSubContractDTO dcForSubContractDTO, DcForSubContractVO dcForSubContractVO) throws ApplicationException {
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
			

			
			if(ObjectUtils.isNotEmpty(dcForSubContractVO.getId())) {	
		List<DcForSubContractDetailsVO>dcForSubContractDetails1= dcForSubContractDetailsRepo.findByDcForSubContractVO(dcForSubContractVO);
		dcForSubContractDetailsRepo.deleteAll(dcForSubContractDetails1);
			}
			
			
			List<DcForSubContractDetailsVO> dcForSubContractDetailsVOs = new ArrayList<>();
			for (DcForSubContractDetailsDTO dcForSubContractDetailsDTO : dcForSubContractDTO.getDcForSubContractDetailsDTO()) {
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
		
		
		//SubContractorEnquiry

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
			for (SubContractEnquiryDetailsDTO subContractEnquiryDetailsDTO : subContractEnquiryDTO.getSubContractEnquiryDetailsDTO()) {
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
	
	
	
	
}
