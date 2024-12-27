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

import com.efitops.basesetup.dto.ToolIssueEntryDTO;
import com.efitops.basesetup.dto.ToolsIssueToCalibrationDTO;
import com.efitops.basesetup.dto.ToolsIssueToCalibrationDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ToolIssueEntryVO;
import com.efitops.basesetup.entity.ToolsIssueToCalibrationDetailsVO;
import com.efitops.basesetup.entity.ToolsIssueToCalibrationVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ToolIssueEntryRepo;
import com.efitops.basesetup.repo.ToolsIssueToCalibrationDetailsRepo;
import com.efitops.basesetup.repo.ToolsIssueToCalibrationRepo;

@Service
public class ToolIssueEntryServiceImpl implements ToolIssueEntryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ToolIssueEntryServiceImpl.class);
	@Autowired
	ToolIssueEntryRepo toolIssueEntryRepo;

	@Autowired
	ToolsIssueToCalibrationRepo toolsIssueToCalibrationRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	ToolsIssueToCalibrationDetailsRepo toolsIssueToCalibrationDetailsRepo;

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
		ToolIssueEntryVO toolIssueEntryVO = new ToolIssueEntryVO();
		String message = null;
		if (ObjectUtils.isNotEmpty(toolIssueEntryDTO.getId())) {
			toolIssueEntryVO = toolIssueEntryRepo.findById(toolIssueEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Gst details"));
			message = "Tool issue Entry Updated Successfully";

			toolIssueEntryVO.setCreatedBy(toolIssueEntryDTO.getCreatedBy());
			toolIssueEntryVO.setUpdatedBy(toolIssueEntryDTO.getCreatedBy());

			message = "Tool issue Entry Created Successfully";
		}
		createUpdateToolIssueEntryVOByToolIssueEntryDTO(toolIssueEntryDTO, toolIssueEntryVO);
		toolIssueEntryRepo.save(toolIssueEntryVO);
		Map<String, Object> response = new HashMap<>();
		response.put("toolIssueEntryVO", toolIssueEntryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateToolIssueEntryVOByToolIssueEntryDTO(ToolIssueEntryDTO toolIssueEntryDTO,
			ToolIssueEntryVO toolIssueEntryVO) {
		toolIssueEntryVO.setInstrumentCode(toolIssueEntryDTO.getInstrumentCode().toUpperCase());
		toolIssueEntryVO.setInstrumentName(toolIssueEntryDTO.getInstrumentName());
		toolIssueEntryVO.setSeqCode(toolIssueEntryDTO.getSeqCode());
		toolIssueEntryVO.setInstrumentRange(toolIssueEntryDTO.getInstrumentRange());
		toolIssueEntryVO.setLocation(toolIssueEntryDTO.getLocation());
		toolIssueEntryVO.setOrgId(toolIssueEntryDTO.getOrgId());
		toolIssueEntryVO.setCreatedBy(toolIssueEntryDTO.getCreatedBy());
		// toolIssueEntryVO.setActive(toolIssueEntryDTO.isActive());
		toolIssueEntryVO.setLeastCount(toolIssueEntryDTO.getLeastCount());
		toolIssueEntryVO.setFrequencyOfCalib(toolIssueEntryDTO.getFrequencyOfCalib());
		toolIssueEntryVO.setCalibratedDate(toolIssueEntryDTO.getCalibratedDate());
		toolIssueEntryVO.setDueForCalib(toolIssueEntryDTO.getDueForCalib());
		toolIssueEntryVO.setCalibratedCertificateNo(toolIssueEntryDTO.getCalibratedCertificateNo());
		toolIssueEntryVO.setPreparedBy(toolIssueEntryDTO.getPreparedBy());
		toolIssueEntryVO.setApporvedBy(toolIssueEntryDTO.getApporvedBy());
		toolIssueEntryVO.setRemarks(toolIssueEntryDTO.getRemarks());

	}

	@Override
	public List<Map<String, Object>> getInstrumentforTollIssueForEntry(Long orgId) {
		Set<Object[]> instrument = toolIssueEntryRepo.getInstrumentforTollIssueForEntry(orgId);
		return getItemForGRN(instrument);
	}

	private List<Map<String, Object>> getItemForGRN(Set<Object[]> chCode) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemname", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("instrumentseqcode", ch[2] != null ? ch[2].toString() : "");

			instrumrntname.add(map);
		}
		return instrumrntname;
	}

	@Override
	public List<Map<String, Object>> getlastcountforTollIssueForEntry(Long orgId) {
		Set<Object[]> instrument = toolIssueEntryRepo.getlastcountforTollIssueForEntry(orgId);
		return getlastcountforToll(instrument);
	}

	private List<Map<String, Object>> getlastcountforToll(Set<Object[]> instrument) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : instrument) {
			Map<String, Object> map = new HashMap<>();
			map.put("lastcount", ch[0] != null ? ch[0].toString() : ""); // Empty
			instrumrntname.add(map);
		}
		return instrumrntname;
	}

	@Override
	public List<ToolsIssueToCalibrationVO> getToolsIssueToCalibrationByOrgId(Long orgId) {
		List<ToolsIssueToCalibrationVO> toolsIssueToCalibrationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received tools isssueed BY OrgId : {}", orgId);
			toolsIssueToCalibrationVO = toolsIssueToCalibrationRepo.findToolsIssueToCalibrationByOrgId(orgId);
		}
		return toolsIssueToCalibrationVO;
	}

	@Override
	public List<ToolsIssueToCalibrationVO> getToolsIssueToCalibrationById(Long id) {
		List<ToolsIssueToCalibrationVO> toolsIssueToCalibrationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received tools isssued to calibration BY Id : {}", id);
			toolsIssueToCalibrationVO = toolsIssueToCalibrationRepo.findToolsIssueToCalibrationById(id);
		}
		return toolsIssueToCalibrationVO;
	}

	@Override
	public Map<String, Object> updateCreateToolsIssueToCalibration(
			ToolsIssueToCalibrationDTO toolsIssueToCalibrationDTO) throws ApplicationException {
		String message;
		String screenCode = "TIC";

		ToolsIssueToCalibrationVO toolsIssueToCalibrationVO = new ToolsIssueToCalibrationVO();

		if (toolsIssueToCalibrationDTO.getId() != null) {
			toolsIssueToCalibrationVO = toolsIssueToCalibrationRepo.findById(toolsIssueToCalibrationVO.getId())
					.orElseThrow(() -> new ApplicationException("tools Issue To Calibration not found"));
			toolsIssueToCalibrationVO.setUpdatedBy(toolsIssueToCalibrationDTO.getCreatedBy());
			message = " Updated Successfully";

		} else {

			// GETDOCID API
			String docId = toolsIssueToCalibrationRepo
					.getToolsIssueToCalibrationDocId(toolsIssueToCalibrationDTO.getOrgId(), screenCode);

			toolsIssueToCalibrationVO.setDocId(docId);

//					        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(toolsIssueToCalibrationDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			toolsIssueToCalibrationVO.setCreatedBy(toolsIssueToCalibrationDTO.getCreatedBy());
			toolsIssueToCalibrationVO.setUpdatedBy(toolsIssueToCalibrationDTO.getCreatedBy());
			message = "Putaway Created Successfully";
		}

		toolsIssueToCalibrationRepo.save(toolsIssueToCalibrationVO);
		createUpdateToolsIssueToCalibrationVOByToolsIssueToCalibrationDTO(toolsIssueToCalibrationDTO,
				toolsIssueToCalibrationVO);
		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("toolsIssueToCalibrationVO", toolsIssueToCalibrationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateToolsIssueToCalibrationVOByToolsIssueToCalibrationDTO(
			@Valid ToolsIssueToCalibrationDTO toolsIssueToCalibrationDTO,
			ToolsIssueToCalibrationVO toolsIssueToCalibrationVO) {
		toolsIssueToCalibrationVO.setIssuePartyName(toolsIssueToCalibrationDTO.getIssuePartyName());
		toolsIssueToCalibrationVO.setIssuePartyAddress(toolsIssueToCalibrationDTO.getIssuePartyAddress());
		toolsIssueToCalibrationVO.setOrgId(toolsIssueToCalibrationDTO.getOrgId());
		toolsIssueToCalibrationVO.setCreatedBy(toolsIssueToCalibrationDTO.getCreatedBy());
		toolsIssueToCalibrationVO.setActive(toolsIssueToCalibrationDTO.isActive());

		if (ObjectUtils.isNotEmpty(toolsIssueToCalibrationDTO.getId())) {
			List<ToolsIssueToCalibrationDetailsVO> toolsIssueToCalibrationDetailsVO1 = toolsIssueToCalibrationDetailsRepo
					.findByToolsIssueToCalibrationVO(toolsIssueToCalibrationVO);
			toolsIssueToCalibrationDetailsRepo.deleteAll(toolsIssueToCalibrationDetailsVO1);

		}

		List<ToolsIssueToCalibrationDetailsVO> toolsIssueToCalibrationDetailsVOs = new ArrayList<>();
		for (ToolsIssueToCalibrationDetailsDTO toolsIssueToCalibrationDetailsDTO : toolsIssueToCalibrationDTO
				.getToolsIssueToCalibrationDetailsDTO()) {
			ToolsIssueToCalibrationDetailsVO toolsIssueToCalibrationDetailsVO = new ToolsIssueToCalibrationDetailsVO();
			toolsIssueToCalibrationDetailsVO.setInstrumentId(toolsIssueToCalibrationDetailsDTO.getInstrumentId());
			toolsIssueToCalibrationDetailsVO.setInstrumentName(toolsIssueToCalibrationDetailsDTO.getInstrumentName());
			toolsIssueToCalibrationDetailsVO.setInstrumentDesc(toolsIssueToCalibrationDetailsDTO.getInstrumentDesc());
			toolsIssueToCalibrationDetailsVO.setIssQty(toolsIssueToCalibrationDetailsDTO.getIssQty());
			toolsIssueToCalibrationDetailsVO.setUnit(toolsIssueToCalibrationDetailsDTO.getUnit());

			toolsIssueToCalibrationDetailsVO.setToolsIssueToCalibrationVO(toolsIssueToCalibrationVO); // Set the
																										// reference in
																										// child entity
			toolsIssueToCalibrationDetailsVOs.add(toolsIssueToCalibrationDetailsVO);
		}
		toolsIssueToCalibrationVO.setToolsIssueToCalibrationDetailsVO(toolsIssueToCalibrationDetailsVOs);

	}

}
