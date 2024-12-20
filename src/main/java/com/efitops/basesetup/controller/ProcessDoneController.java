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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ProcessDoneDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.ProcessDoneVO;
import com.efitops.basesetup.service.ProcessDoneService;

@RestController
@RequestMapping(name = "/api/processDone")
public class ProcessDoneController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProcessDoneController.class);

	@Autowired
	ProcessDoneService processDoneService;

	@GetMapping("/getAllProcessDoneByOrgId")
	public ResponseEntity<ResponseDTO> getAllProcessDoneByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllProcessDoneByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProcessDoneVO> processDoneVO = new ArrayList<>();
		try {
			processDoneVO = processDoneService.getAllProcessDoneByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessDone information get successfully By OrgId");
			responseObjectsMap.put("processDoneVO", processDoneVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProcessDone information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getProcessDoneById")
	public ResponseEntity<ResponseDTO> getProcessDoneById(@RequestParam(required = false) Long id) {
		String methodName = "getProcessDoneById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProcessDoneVO> processDoneVO = new ArrayList<>();
		try {
			processDoneVO = processDoneService.getProcessDoneById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessDone information get successfully By id");
			responseObjectsMap.put("processDoneVO", processDoneVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProcessDone information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateProcessDone")
	public ResponseEntity<ResponseDTO> updateCreateProcessDone(@RequestBody ProcessDoneDTO processDoneDTO) {
		String methodName = "updateCreateProcessDone()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> processDoneVO = processDoneService.createUpdateProcessDone(processDoneDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, processDoneVO.get("message"));
			responseObjectsMap.put("processDoneVO", processDoneVO.get("processDoneVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProcessDoneByDocId")
	public ResponseEntity<ResponseDTO> getProcessDoneByDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode, @RequestParam String screenCode) {
		String methodName = "getProcessDoneByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String processDoneVO = null;
		try {
			processDoneVO = processDoneService.getProcessDoneDocId(orgId, finYear, branchCode, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OriginBill information get successfully By docid");
			responseObjectsMap.put("processDoneVO", processDoneVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"OriginBill information receive failed By docid", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
}
