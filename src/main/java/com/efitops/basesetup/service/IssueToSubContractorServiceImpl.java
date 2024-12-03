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

import com.efitops.basesetup.dto.IssueItemDetailsDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.IssueItemDetailsVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.IssueItemDetailsRepo;
import com.efitops.basesetup.repo.IssueToSubContractorRepo;

@Service
public class IssueToSubContractorServiceImpl implements IssueToSubContractorService {

	public static final Logger LOGGER = LoggerFactory.getLogger(IssueToSubContractorServiceImpl.class);

	@Autowired
	IssueToSubContractorRepo issueToSubContractorRepo;

	@Autowired
	IssueItemDetailsRepo issueItemDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

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
		createUpdatedQuotationVOFromQuotationDTO(issueToSubContractorDTO, issueToSubContractorVO);
		issueToSubContractorRepo.save(issueToSubContractorVO);
		Map<String, Object> response = new HashMap<>();
		response.put("issueToSubContractorVO", issueToSubContractorVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedQuotationVOFromQuotationDTO(IssueToSubContractorDTO issueToSubContractorDTO,
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

}
