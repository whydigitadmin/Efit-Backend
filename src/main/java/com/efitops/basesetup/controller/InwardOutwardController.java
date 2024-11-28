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
import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.GateInwardEntryVO;
import com.efitops.basesetup.service.InwardOutwardService;

@CrossOrigin
@RestController
@RequestMapping("/api/inwardoutward")
public class InwardOutwardController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardOutwardController.class);
	
	@Autowired
	InwardOutwardService inwardOutwardService;
	
	@GetMapping("/getGateInwardEntryByOrgId")
	public ResponseEntity<ResponseDTO> getGateInwardEntryByOrgId(@RequestParam Long orgId) {
		String methodName = "getGateInwardEntryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GateInwardEntryVO> gateInwardEntryVO = new ArrayList<>();
		try {
			gateInwardEntryVO = inwardOutwardService.getGateInwardEntryByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GateInwardEntry information get successfully ByOrgId");
			responseObjectsMap.put("gateInwardEntryVO", gateInwardEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "GateInwardEntry information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGateInwardEntryById")
	public ResponseEntity<ResponseDTO> getGateInwardEntryById(@RequestParam Long id) {
		String methodName = "getGateInwardEntryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GateInwardEntryVO> gateInwardEntryVO = new ArrayList<>();
		try {
			gateInwardEntryVO = inwardOutwardService.getGateInwardEntryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GateInwardEntry information get successfully By Id");
			responseObjectsMap.put("gateInwardEntryVO", gateInwardEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "GateInwardEntry information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PutMapping("/updateCreateGateInwardEntry")
	public ResponseEntity<ResponseDTO> updateCreateGateInwardEntry(@RequestBody GateInwardEntryDTO gateInwardEntryDTO) {
		String methodName = "updateCreateGateInwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> gateInwardEntryVO = inwardOutwardService.updateCreateGateInwardEntry(gateInwardEntryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, gateInwardEntryVO.get("message"));
			responseObjectsMap.put("gateInwardEntryVO", gateInwardEntryVO.get("gateInwardEntryVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	
	@GetMapping("/getGateInwardEntryDocId")
	public ResponseEntity<ResponseDTO> getGateInwardEntryDocId(@RequestParam Long orgId) {

		String methodName = "getGateInwardEntryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = inwardOutwardService.getGateInwardEntryDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GateInwardEntry DocId information retrieved successfully");
			responseObjectsMap.put("GateInwardEntry DocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GateInwardEntry DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
