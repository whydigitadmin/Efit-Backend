package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ToolIssueEntryDTO;
import com.efitops.basesetup.dto.ToolsIssueToCalibrationDTO;
import com.efitops.basesetup.entity.ToolIssueEntryVO;
import com.efitops.basesetup.entity.ToolsIssueToCalibrationVO;
import com.efitops.basesetup.service.ToolIssueEntryService;

@CrossOrigin
@RestController
@RequestMapping("/api/toolmanagement")
public class ToolIssueEntryController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ToolIssueEntryController.class);

	@Autowired
    ToolIssueEntryService toolIssueEntryService;
	
	
	
	@GetMapping("/getToolIssueEntryByOrgId")
	public ResponseEntity<ResponseDTO> getToolIssueEntryByOrgId(@RequestParam Long orgId) {
		String methodName = "getToolIssueEntryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ToolIssueEntryVO> toolIssueEntryVO = new ArrayList<>();
		try {
			toolIssueEntryVO = toolIssueEntryService.getToolIssueEntryByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ToolIssueEntry information get successfully ByOrgId");
			responseObjectsMap.put("toolIssueEntryVO", toolIssueEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ToolIssueEntry information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	
	

	@GetMapping("/getToolIssueEntryById")
	public ResponseEntity<ResponseDTO> getToolIssueEntryById(@RequestParam Long id) {
		String methodName = "getToolIssueEntryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ToolIssueEntryVO> toolIssueEntryVO = new ArrayList<>();
		try {
			toolIssueEntryVO = toolIssueEntryService.getToolIssueEntryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information get successfully By Id");
			responseObjectsMap.put("toolIssueEntryVO", toolIssueEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Item information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PutMapping("/updateCreateToolIssueEntry")
	public ResponseEntity<ResponseDTO> updateCreateToolIssueEntry(@RequestBody ToolIssueEntryDTO toolIssueEntryDTO) {
		String methodName = "updateCreateToolIssueEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> toolIssueEntryVO = toolIssueEntryService.updateCreateToolIssueEntry(toolIssueEntryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, toolIssueEntryVO.get("message"));
			responseObjectsMap.put("toolIssueEntryVO", toolIssueEntryVO.get("toolIssueEntryVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getInstrumentforTollIssueForEntry")
	public ResponseEntity<ResponseDTO> getInstrumentforTollIssueForEntry(@RequestParam(required = false) Long orgId) {
		String methodName = "getInstrumentforTollIssueForEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> inwardforgrn = new ArrayList<>();
		try {
			inwardforgrn = toolIssueEntryService.getInstrumentforTollIssueForEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Instrumentname for Grn information get successfully By OrgId");
			responseObjectsMap.put("inwardforgrn", inwardforgrn);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Instrumentname for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	

	@GetMapping("/getlastcountforTollIssueForEntry")
	public ResponseEntity<ResponseDTO> getlastcountforTollIssueForEntry(@RequestParam(required = false) Long orgId) {
		String methodName = "getlastcountforTollIssueForEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> toolissueforentry = new ArrayList<>();
		try {
			toolissueforentry = toolIssueEntryService.getlastcountforTollIssueForEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Lastcount for Grn information get successfully By OrgId");
			responseObjectsMap.put("toolissueforentry", toolissueforentry);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Lastcount for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getToolsIssueToCalibrationByOrgId")
	public ResponseEntity<ResponseDTO> getToolsIssueToCalibrationByOrgId(@RequestParam Long orgId) {
		String methodName = "getToolsIssueToCalibrationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ToolsIssueToCalibrationVO> toolsIssueToCalibrationVO = new ArrayList<>();
		try {
			toolsIssueToCalibrationVO = toolIssueEntryService.getToolsIssueToCalibrationByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tool Issue to Calibration information get successfully ByOrgId");
			responseObjectsMap.put("toolsIssueToCalibrationVO", toolsIssueToCalibrationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Tool Issue to Calibration information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getToolsIssueToCalibrationById")
	public ResponseEntity<ResponseDTO> getToolsIssueToCalibrationById(@RequestParam Long id) {
		String methodName = "getToolsIssueToCalibrationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ToolsIssueToCalibrationVO> toolsIssueToCalibrationVO = new ArrayList<>();
		try {
			toolsIssueToCalibrationVO = toolIssueEntryService.getToolsIssueToCalibrationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tool Issue to Calibration information get successfully By Id");
			responseObjectsMap.put("toolsIssueToCalibrationVO", toolsIssueToCalibrationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Tool Issue to Calibration information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PutMapping("/updateCreateToolsIssueToCalibration")
	public ResponseEntity<ResponseDTO> updateCreateToolsIssueToCalibration(@RequestBody ToolsIssueToCalibrationDTO toolsIssueToCalibrationDTO) {
		String methodName = "updateCreateToolIssueEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> toolsIssueToCalibrationVO = toolIssueEntryService.updateCreateToolsIssueToCalibration(toolsIssueToCalibrationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, toolsIssueToCalibrationVO.get("message"));
			responseObjectsMap.put("toolsIssueToCalibrationVO", toolsIssueToCalibrationVO.get("toolsIssueToCalibrationVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
}
