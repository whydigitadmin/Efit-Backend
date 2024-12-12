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
import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.service.QualityService;

@RestController
@RequestMapping("/api/quality")
public class QualityServiceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(QualityServiceController.class);

	@Autowired
	QualityService qualityService;

	// IncomingMaterialInspection

	@GetMapping("/getAllIncomingMaterialInspectionByOrgId")
	public ResponseEntity<ResponseDTO> getAllIncomingMaterialInspectionByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllIncomingMaterialInspectionByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IncomingMaterialInspectionVO> incomingMaterialInspectionVO = new ArrayList<>();
		try {
			incomingMaterialInspectionVO = qualityService.getAllIncomingMaterialInspectionByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IncomingMaterialInspection information get successfully ByOrgId");
			responseObjectsMap.put("incomingMaterialInspectionVO", incomingMaterialInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IncomingMaterialInspection information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIncomingMaterialInspectionById")
	public ResponseEntity<ResponseDTO> getIncomingMaterialInspectionById(@RequestParam Long id) {
		String methodName = "getIncomingMaterialInspectionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		IncomingMaterialInspectionVO incomingMaterialInspectionVO = new IncomingMaterialInspectionVO();
		try {
			incomingMaterialInspectionVO = qualityService.getIncomingMaterialInspectionById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IncomingMaterialInspection information get successfully By id");
			responseObjectsMap.put("incomingMaterialInspectionVO", incomingMaterialInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IncomingMaterialInspection information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateIncomingMaterialInspection")
	public ResponseEntity<ResponseDTO> createUpdateIncomingMaterialInspection(
			@RequestBody IncomingMaterialInspectionDTO incomingMaterialInspectionDTO) {
		String methodName = "createUpdateIncomingMaterialInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> incomingMaterialInspectionVO = qualityService
					.createUpdateIncomingMaterialInspection(incomingMaterialInspectionDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, incomingMaterialInspectionVO.get("message"));
			responseObjectsMap.put("incomingMaterialInspectionVO",
					incomingMaterialInspectionVO.get("incomingMaterialInspectionVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIncomingMaterialInspectionDocId")
	public ResponseEntity<ResponseDTO> getIncomingMaterialInspectionDocId(@RequestParam Long orgId) {

		String methodName = "getIncomingMaterialInspectionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getIncomingMaterialInspectionDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IncomingMaterialInspectionDocId information retrieved successfully");
			responseObjectsMap.put("incomingMaterialInspectionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve IncomingMaterialInspectionDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
