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

import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.JobOrderDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.JobOrderDetailsVO;
import com.efitops.basesetup.entity.JobOrderVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.JobOrderDetailsRepo;
import com.efitops.basesetup.repo.JobOrderRepo;

@Service
public class JobOrderServiceImpl implements JobOrderService {

	public static final Logger LOGGER = LoggerFactory.getLogger(JobOrderServiceImpl.class);

	@Autowired
	JobOrderRepo jobOrderRepo;

	@Autowired
	JobOrderDetailsRepo jobOrderDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public List<JobOrderVO> getAllJobOrderByOrgId(Long orgId) {
		List<JobOrderVO> jobOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  JobOrder BY OrgId : {}", orgId);
			jobOrderVO = jobOrderRepo.getAllJobOrderByOrgId(orgId);
		}
		return jobOrderVO;
	}

	@Override
	public List<JobOrderVO> getJobOrderById(Long id) {
		List<JobOrderVO> jobOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received JobOrder BY Id : {}", id);
			jobOrderVO = jobOrderRepo.getAllJobOrderById(id);
		}
		return jobOrderVO;
	}

	@Override
	public Map<String, Object> createUpdateJobOrder(JobOrderDTO jobOrderDTO) throws ApplicationException {
		String screenCode = "PP";
		JobOrderVO jobOrderVO = new JobOrderVO();
		String message;
		if (ObjectUtils.isNotEmpty(jobOrderDTO.getId())) {
			jobOrderVO = jobOrderRepo.findById(jobOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("Production Plan not found"));

			jobOrderVO.setModifiedBy(jobOrderDTO.getCreatedBy());
			createUpdateJobOrderVOByJobOrderDTO(jobOrderDTO, jobOrderVO);
			message = "JobOrder Updated Successfully";
		} else {
			// GETDOCID API
			String docId = jobOrderRepo.getJobOrderDocId(jobOrderVO.getOrgId(), jobOrderVO.getFinYear(),
					jobOrderVO.getBranchCode(), screenCode);
			jobOrderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(jobOrderVO.getOrgId(), jobOrderVO.getFinYear(),
							jobOrderVO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			jobOrderVO.setCreatedBy(jobOrderDTO.getCreatedBy());
			jobOrderVO.setModifiedBy(jobOrderDTO.getCreatedBy());
			createUpdateJobOrderVOByJobOrderDTO(jobOrderDTO, jobOrderVO);
			message = "Tax Invoice Created Successfully";
		}

		jobOrderRepo.save(jobOrderVO);
		Map<String, Object> response = new HashMap<>();
		response.put("jobOrderVO", jobOrderVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateJobOrderVOByJobOrderDTO(JobOrderDTO jobOrderDTO, JobOrderVO jobOrderVO) {

		// Map fields from DTO to VO
		jobOrderVO.setOrgId(jobOrderDTO.getOrgId());
		jobOrderVO.setBranch(jobOrderDTO.getBranch());
		jobOrderVO.setBranchCode(jobOrderDTO.getBranchCode());
		jobOrderVO.setFinYear(jobOrderDTO.getFinYear());
		jobOrderVO.setActive(jobOrderDTO.isActive());
		jobOrderVO.setShift(jobOrderDTO.getShift());
		jobOrderVO.setRouteCardNo(jobOrderDTO.getRouteCardNo());
		jobOrderVO.setWorkOrderNo(jobOrderDTO.getWorkOrderNo());
		jobOrderVO.setCustomerName(jobOrderDTO.getCustomerName());
		jobOrderVO.setCustomerPoNo(jobOrderDTO.getCustomerPoNo());
		jobOrderVO.setPartName(jobOrderDTO.getPartName());
		jobOrderVO.setPartNo(jobOrderDTO.getPartNo());
		jobOrderVO.setOperationName(jobOrderDTO.getOperationName());
		jobOrderVO.setCycleTimeInSecs(jobOrderDTO.getCycleTimeInSecs());
		jobOrderVO.setNormsHr(jobOrderDTO.getNormsHr());
		jobOrderVO.setStatus(jobOrderDTO.getStatus());
		jobOrderVO.setOperatorName(jobOrderDTO.getOperatorName());

		if (ObjectUtils.isNotEmpty(jobOrderVO.getId())) {
			List<JobOrderDetailsVO> jobOrderDetailsVO1 = jobOrderDetailsRepo.findByJobOrderVO(jobOrderVO);
			jobOrderDetailsRepo.deleteAll(jobOrderDetailsVO1);
		}

		List<JobOrderDetailsVO> jobOrderDetailsVOs = new ArrayList<>();
		for (JobOrderDetailsDTO jobOrderDetailsDTO : jobOrderDTO.getJobOrderDetailsDTO()) {

			JobOrderDetailsVO jobOrderDetailsVO = new JobOrderDetailsVO();
			jobOrderDetailsVO.setTimeInHours(jobOrderDetailsDTO.getTimeInHours());
			jobOrderDetailsVO.setUnit(jobOrderDetailsDTO.getUnit());
			jobOrderDetailsVO.setHoursProduction(jobOrderDetailsDTO.getHoursProduction());
			jobOrderDetailsVO.setRework(jobOrderDetailsDTO.getRework());
			jobOrderDetailsVO.setReject(jobOrderDetailsDTO.getReject());
			jobOrderDetailsVO.setIdealTime(jobOrderDetailsDTO.getIdealTime());
			jobOrderDetailsVO.setCumulativeTest(jobOrderDetailsDTO.getCumulativeTest());
			jobOrderDetailsVO.setRemarks(jobOrderDetailsDTO.getRemarks());
			jobOrderDetailsVO.setJobOrderVO(jobOrderVO);
		}
		jobOrderVO.setJobOrderDetailsVO(jobOrderDetailsVOs);
	}
}
