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

import com.efitops.basesetup.dto.ProcessDoneDTO;
import com.efitops.basesetup.dto.ProcessDoneDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ProcessDoneDetailsVO;
import com.efitops.basesetup.entity.ProcessDoneVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ProcessDoneDetailsRepo;
import com.efitops.basesetup.repo.ProcessDoneRepo;

@Service
public class ProcessDoneServiceImpl implements ProcessDoneService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProcessDoneServiceImpl.class);

	@Autowired
	ProcessDoneRepo processDoneRepo;

	@Autowired
	ProcessDoneDetailsRepo processDoneDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public List<ProcessDoneVO> getAllProcessDoneByOrgId(Long orgId) {
		List<ProcessDoneVO> processDoneVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  ProcessDone BY OrgId : {}", orgId);
			processDoneVO = processDoneRepo.getAllProcessDoneByOrgId(orgId);
		}
		return processDoneVO;
	}

	@Override
	public List<ProcessDoneVO> getProcessDoneById(Long id) {
		List<ProcessDoneVO> processDoneVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ProcessDone BY Id : {}", id);
			processDoneVO = processDoneRepo.getAllProcessDoneById(id);
		}
		return processDoneVO;
	}

	@Override
	public Map<String, Object> createUpdateProcessDone(ProcessDoneDTO processDoneDTO) throws ApplicationException {
		String screenCode = "PP";
		ProcessDoneVO processDoneVO = new ProcessDoneVO();
		String message;
		if (ObjectUtils.isNotEmpty(processDoneDTO.getId())) {
			processDoneVO = processDoneRepo.findById(processDoneDTO.getId())
					.orElseThrow(() -> new ApplicationException("Production Plan not found"));

			processDoneVO.setModifiedBy(processDoneDTO.getCreatedBy());
			createUpdateProcessDoneVOByProcessDoneDTO(processDoneDTO, processDoneVO);
			message = "ProcessDone Updated Successfully";
		} else {
			// GETDOCID API
			String docId = processDoneRepo.getProcessDoneDocId(processDoneVO.getOrgId(), processDoneVO.getFinYear(),
					processDoneVO.getBranchCode(), screenCode);
			processDoneVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(processDoneVO.getOrgId(),
							processDoneVO.getFinYear(), processDoneVO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			processDoneVO.setCreatedBy(processDoneDTO.getCreatedBy());
			processDoneVO.setModifiedBy(processDoneDTO.getCreatedBy());
			createUpdateProcessDoneVOByProcessDoneDTO(processDoneDTO, processDoneVO);
			message = "Tax Invoice Created Successfully";
		}

		processDoneRepo.save(processDoneVO);
		Map<String, Object> response = new HashMap<>();
		response.put("processDoneVO", processDoneVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateProcessDoneVOByProcessDoneDTO(ProcessDoneDTO processDoneDTO, ProcessDoneVO processDoneVO) {

		// Map fields from DTO to VO
		processDoneVO.setOrgId(processDoneDTO.getOrgId());
		processDoneVO.setBranch(processDoneDTO.getBranch());
		processDoneVO.setBranchCode(processDoneDTO.getBranchCode());
		processDoneVO.setFinYear(processDoneDTO.getFinYear());
		processDoneVO.setActive(processDoneDTO.isActive());
		processDoneVO.setNarration(processDoneDTO.getNarration());
		processDoneVO.setDocId(processDoneDTO.getDocId());
		processDoneVO.setDocDate(processDoneDTO.getDocDate());
		processDoneVO.setCustomerName(processDoneDTO.getCustomerName());
		processDoneVO.setRouteCardNo(processDoneDTO.getRouteCardNo());
		processDoneVO.setJobOrderNo(processDoneDTO.getJobOrderNo());
		processDoneVO.setFgPartName(processDoneDTO.getFgPartName());
		processDoneVO.setFgPartNo(processDoneDTO.getFgPartNo());
		processDoneVO.setFrom(processDoneDTO.getFrom());
		processDoneVO.setTo(processDoneDTO.getTo());

		if (ObjectUtils.isNotEmpty(processDoneVO.getId())) {
			List<ProcessDoneDetailsVO> processDoneDetailsVO1 = processDoneDetailsRepo
					.findByProcessDoneVO(processDoneVO);
			processDoneDetailsRepo.deleteAll(processDoneDetailsVO1);
		}

		List<ProcessDoneDetailsVO> processDoneDetailsVOs = new ArrayList<>();
		for (ProcessDoneDetailsDTO processDoneDetailsDTO : processDoneDTO.getProcessDoneDetailsDTO()) {

			ProcessDoneDetailsVO processDoneDetailsVO = new ProcessDoneDetailsVO();
			processDoneDetailsVO.setProcess(processDoneDetailsDTO.getProcess());
			processDoneDetailsVO.setStatus(processDoneDetailsDTO.getStatus());
			processDoneDetailsVO.setRemarks(processDoneDetailsDTO.getRemarks());
			processDoneDetailsVO.setProcessDoneVO(processDoneVO);
		}
		processDoneVO.setProcessDoneDetailsVO(processDoneDetailsVOs);
	}

	@Override
	public String getProcessDoneDocId(Long orgId, String finYear, String branchCode, String screenCode) {
		return processDoneRepo.getProcessDoneDocId(orgId, finYear, branchCode, screenCode);
	}
}
