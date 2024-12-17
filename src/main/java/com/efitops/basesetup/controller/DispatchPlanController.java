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
import com.efitops.basesetup.dto.DispatchPlanDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.DispatchPlanVO;
import com.efitops.basesetup.service.DispatchPlanService;

@CrossOrigin
@RestController
@RequestMapping("/api/dispatchcontroller")
public class DispatchPlanController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(IrnCreditNoteController.class);

	@Autowired
	DispatchPlanService dispatchPlanService;
	
	@GetMapping("/getDispatchPlanByOrgId")
	public ResponseEntity<ResponseDTO> getDispatchPlanByOrgId(@RequestParam Long orgId) {
		String methodName = "getDispatchPlanByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DispatchPlanVO> dispatchPlanVO = new ArrayList<>();
		try {
			dispatchPlanVO = dispatchPlanService.getDispatchPlanByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DispatchPlan information get successfully ByOrgId");
			responseObjectsMap.put("dispatchPlanVO", dispatchPlanVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DispatchPlan information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getDispatchPlanById")
	public ResponseEntity<ResponseDTO> getDispatchPlanById(@RequestParam Long id) {
		String methodName = "getDispatchPlanById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DispatchPlanVO dispatchPlanVO = new DispatchPlanVO();
		try {
			dispatchPlanVO = dispatchPlanService.getDispatchPlanById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DispatchPlan information get successfully By id");
			responseObjectsMap.put("dispatchPlanVO", dispatchPlanVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DispatchPlan information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@PutMapping("/updateCreateDispatchPlan")
	public ResponseEntity<ResponseDTO> updateCreateDispatchPlan(@RequestBody DispatchPlanDTO dispatchPlanDTO) {
		String methodName = "updateCreateDispatchPlan()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> dispatchPlanVO = dispatchPlanService.updateCreateDispatchPlan(dispatchPlanDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, dispatchPlanVO.get("message"));
			responseObjectsMap.put("dispatchPlanVO", dispatchPlanVO.get("dispatchPlanVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getDispatchPlanDocId")
	public ResponseEntity<ResponseDTO> getDispatchPlanDocId(@RequestParam Long orgId) {

		String methodName = "getDispatchPlanDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = dispatchPlanService.getDispatchPlanDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DispatchPlanDocId information retrieved successfully");
			responseObjectsMap.put("dispatchPlanDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DispatchPlanDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
 
	
}
