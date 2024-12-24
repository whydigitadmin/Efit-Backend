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
import com.efitops.basesetup.entity.SampleApprovalVO;
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
	
	@GetMapping("/getRouteCardDetailsForSetingApproval")
	public ResponseEntity<ResponseDTO> getRouteCardDetailsForSetingApproval(@RequestParam Long orgId) {
		String methodName = "getRouteCardDetailsForSetingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> routeCardDetails = new ArrayList<>();
		try {
			routeCardDetails = qualityApprovalServive.getRouteCardDetailsForSetingApproval(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardDetails for SetingApproval information get successfully By OrgId");
			responseObjectsMap.put("routeCardDetails", routeCardDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"RouteCardDetails for SetingApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	

	@GetMapping("/getDrawingNoForSetingApproval")
	public ResponseEntity<ResponseDTO> getDrawingNoForSetingApproval(@RequestParam Long orgId,@RequestParam String partNo) {
		String methodName = "getDrawingNoForSetingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> drawingNo = new ArrayList<>();
		try {
			drawingNo = qualityApprovalServive.getDrawingNoForSetingApproval(orgId,partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DrawingNo for SetingApproval information get successfully By OrgId");
			responseObjectsMap.put("drawingNo", drawingNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DrawingNo for SetingApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getMachineNoForSetingApproval")
	public ResponseEntity<ResponseDTO> getMachineNoForSetingApproval(@RequestParam Long orgId) {
		String methodName = "getMachineNoForSetingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> machineNo = new ArrayList<>();
		try {
			machineNo = qualityApprovalServive.getMachineNoForSetingApproval(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MachineNo for SetingApproval information get successfully By OrgId");
			responseObjectsMap.put("machineNo", machineNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MachineNo for SetingApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getOperatorNameForSetingApproval")
	public ResponseEntity<ResponseDTO> getOperatorNameForSetingApproval(@RequestParam Long orgId) {
		String methodName = "getOperatorNameForSetingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityApprovalServive.getOperatorNameForSetingApproval(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OperatorName Detail For SetingApproval retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve OperatorName Detail For SetingApproval",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getSetterNameForSetingApproval")
	public ResponseEntity<ResponseDTO> getSetterNameForSetingApproval(@RequestParam Long orgId) {
		String methodName = "getSetterNameForSetingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityApprovalServive.getSetterNameForSetingApproval(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SetterName Detail For SetingApproval retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SetterName Detail For SetingApproval",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getQualityNameForSetingApproval")
	public ResponseEntity<ResponseDTO> getQualityNameForSetingApproval(@RequestParam Long orgId) {
		String methodName = "getQualityNameForSetingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityApprovalServive.getQualityNameForSetingApproval(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "QualityName Detail For SetingApproval retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve QualityName Detail For SetingApproval",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getShiftInChargeForSetingApproval")
	public ResponseEntity<ResponseDTO> getShiftInChargeForSetingApproval(@RequestParam Long orgId) {
		String methodName = "getShiftInChargeForSetingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityApprovalServive.getShiftInChargeForSetingApproval(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ShiftInCharge Detail For SetingApproval retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ShiftInCharge Detail For SetingApproval",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
//	@GetMapping("/getAllSampleApprovalByOrgId")
//	public ResponseEntity<ResponseDTO> getAllSampleApprovalByOrgId(@RequestParam Long orgId) {
//		String methodName = "getAllSampleApprovalByOrgId()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		List<SampleApprovalVO> sampleApprovalVO = new ArrayList<>();
//		try {
//			sampleApprovalVO = qualityApprovalServive.getAllSampleApprovalByOrgId(orgId);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"SampleApproval information get successfully ByOrgId");
//			responseObjectsMap.put("sampleApprovalVO", sampleApprovalVO);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"SampleApproval information receive failed By OrgId", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//
//	}
//
//	
//	@GetMapping("/getSampleApprovalById")
//	public ResponseEntity<ResponseDTO> getSampleApprovalById(@RequestParam Long id) {
//		String methodName = "getSampleApprovalById()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		SampleApprovalVO  sampleApprovalVO = new SampleApprovalVO();
//		try {
//			sampleApprovalVO = qualityApprovalServive.getSampleApprovalById(id);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"SampleApproval information get successfully By Id");
//			responseObjectsMap.put("sampleApprovalVO", sampleApprovalVO);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"SampleApproval information receive failedBy Id", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
//	
//	
//	@GetMapping("/getSampleApprovalDocId")
//	public ResponseEntity<ResponseDTO> getSampleApprovalDocId(@RequestParam Long orgId) {
//
//		String methodName = "getSampleApprovalDocId()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		String mapp = "";
//
//		try {
//			mapp = qualityApprovalServive.getSampleApprovalDocId(orgId);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"SampleApprovalDocId information retrieved successfully");
//			responseObjectsMap.put("sampleApprovalDocId", mapp);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Failed to retrieve SampleApprovalDocId information", errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}


}
