package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.entity.JobOrderVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface JobOrderService {

	List<JobOrderVO> getAllJobOrderByOrgId(Long orgId);

	List<JobOrderVO> getJobOrderById(Long jobOrderId);

	Map<String, Object> createUpdateJobOrder(JobOrderDTO jobOrderDTO) throws ApplicationException;

}