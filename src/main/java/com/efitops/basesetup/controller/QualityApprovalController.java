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
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SettingApprovalDTO;
import com.efitops.basesetup.entity.SettingApprovalVO;
import com.efitops.basesetup.service.QualityApprovalServive;

@RestController
@RequestMapping("/api/qualityapproval")
public class QualityApprovalController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(QualityApprovalController.class);
	
	@Autowired
	QualityApprovalServive qualityApprovalServive;
	
	
	@GetMapping("/getAllSettingApprovalByOrgId")
	public ResponseEntity<ResponseDTO> getAllSettingApprovalByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllSettingApprovalByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SettingApprovalVO> settingApprovalVO = new ArrayList<>();
		try {
			settingApprovalVO = qualityApprovalServive.getAllSettingApprovalByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"settingApproval information get successfully ByOrgId");
			responseObjectsMap.put("settingApprovalVO", settingApprovalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"settingApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getSettingApprovalById")
	public ResponseEntity<ResponseDTO> getSettingApprovalById(@RequestParam Long id) {
		String methodName = "getSettingApprovalById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SettingApprovalVO  settingApprovalVO = new SettingApprovalVO();
		try {
			settingApprovalVO = qualityApprovalServive.getSettingApprovalById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SettingApproval information get successfully By id");
			responseObjectsMap.put("settingApprovalVO", settingApprovalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SettingApproval information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSettingApprovalDocId")
	public ResponseEntity<ResponseDTO> getSettingApprovalDocId(@RequestParam Long orgId) {

		String methodName = "getSettingApprovalDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityApprovalServive.getSettingApprovalDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SettingApprovalDocId information retrieved successfully");
			responseObjectsMap.put("settingApprovalDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SettingApprovalDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateSettingApproval")
	public ResponseEntity<ResponseDTO> createUpdateSettingApproval(
			@RequestBody SettingApprovalDTO settingApprovalDTO) {
		String methodName = "createUpdateSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> settingApprovalVO = qualityApprovalServive.createUpdateSettingApproval(settingApprovalDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, settingApprovalVO.get("message"));
			responseObjectsMap.put("settingApprovalVO",settingApprovalVO.get("settingApprovalVO"));
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
