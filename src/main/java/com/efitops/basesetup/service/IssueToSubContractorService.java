package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface IssueToSubContractorService {
	
	//IssueToSubContractor
	Map<String, Object> createUpdateIssueToSubContractor(IssueToSubContractorDTO  issueToSubContractorDTO) throws ApplicationException;

	List<IssueToSubContractorVO> getAllIssueToSubContractorByOrgId(Long orgId);

	List<IssueToSubContractorVO> getIssueToSubContractorById(Long id);

	String getIssueToSubContractorDocId(Long orgId);
		
	List<Map<String, Object>> getRouteCardNoAndItemNo(Long orgId);
	
	List<Map<String, Object>> getDepartmentName(Long orgId);

	List<Map<String, Object>> getProcessNameFormItemWiseProcess(Long orgId,String item);
}
