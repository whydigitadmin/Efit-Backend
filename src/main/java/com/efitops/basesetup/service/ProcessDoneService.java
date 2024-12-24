package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ProcessDoneDTO;
import com.efitops.basesetup.entity.ProcessDoneVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface ProcessDoneService {

	List<ProcessDoneVO> getAllProcessDoneByOrgId(Long orgId);

	List<ProcessDoneVO> getProcessDoneById(Long processDoneId);

	Map<String, Object> createUpdateProcessDone(ProcessDoneDTO processDoneDTO) throws ApplicationException;

	String getProcessDoneDocId(Long orgId, String finYear, String branchCode, String screenCode);

	List<Map<String, Object>> getRouteCardNo(Long orgId, String customerName);

	List<Map<String, Object>> getJobCardNo(Long orgId, String routeCardNo);

	List<Map<String, Object>> getFrom(Long orgid);

	List<Map<String, Object>> getTo(Long orgid);

	// Process API take from JobCard

}