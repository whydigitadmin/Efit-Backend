package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ToolIssueEntryDTO;
import com.efitops.basesetup.entity.ToolIssueEntryVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface ToolIssueEntryService {
	
	List<ToolIssueEntryVO> getToolIssueEntryByOrgId(Long orgId);

	List<ToolIssueEntryVO> getToolIssueEntryById(Long id);

	Map<String, Object> updateCreateToolIssueEntry(ToolIssueEntryDTO toolIssueEntryDTO) throws ApplicationException;

}
