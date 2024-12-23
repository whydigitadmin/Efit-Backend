package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ToolIssueEntryDTO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.ToolIssueEntryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.ToolIssueEntryRepo;

@Service
public class ToolIssueEntryServiceImpl implements ToolIssueEntryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ToolIssueEntryServiceImpl.class);
	@Autowired
	ToolIssueEntryRepo toolIssueEntryRepo;

	@Override
	public List<ToolIssueEntryVO> getToolIssueEntryByOrgId(Long orgId) {
		List<ToolIssueEntryVO> toolIssueEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			toolIssueEntryVO = toolIssueEntryRepo.findToolIssueEntryByOrgId(orgId);
		}
		return toolIssueEntryVO;
	}

	@Override
	public List<ToolIssueEntryVO> getToolIssueEntryById(Long id) {
		List<ToolIssueEntryVO> toolIssueEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			toolIssueEntryVO = toolIssueEntryRepo.getToolIssueEntryById(id);
		}
		return toolIssueEntryVO;
	}

	@Override
	public Map<String, Object> updateCreateToolIssueEntry(ToolIssueEntryDTO toolIssueEntryDTO)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}
