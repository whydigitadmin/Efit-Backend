package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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
import com.efitops.basesetup.dto.DailyPatrolInspectionDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;
import com.efitops.basesetup.service.DailyPatrolInspectionService;

@CrossOrigin
@RestController
@RequestMapping("/api/dailypatrolinspectioncontroller")
public class DailyPatrolInspectionController extends BaseController{

@Autowired
DailyPatrolInspectionService dailyPatrolInspectionService;
	
	static final Logger LOGGER = LoggerFactory.getLogger(DailyPatrolInspectionController.class);
	
	
	@PutMapping("/updateCreateDailyPatrolInspection")
	public ResponseEntity<ResponseDTO> updateCreateDailyPatrolInspection(@Valid @RequestBody DailyPatrolInspectionDTO dailyPatrolInspectionDTO) {
		String methodName = "updateCreatePurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> dailyPatrolInspectionVO = dailyPatrolInspectionService.updateCreateDailyPatrolInspection(dailyPatrolInspectionDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, dailyPatrolInspectionVO.get("message"));
			responseObjectsMap.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO.get("dailyPatrolInspectionVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getDailyPatrolInspectionDocId")
	public ResponseEntity<ResponseDTO> getDailyPatrolInspectionDocId(@RequestParam Long orgId) {

		String methodName = "getDailyPatrolInspectionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = dailyPatrolInspectionService.getDailyPatrolInspectionDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DailyPatrolInspection DocId information retrieved successfully");
			responseObjectsMap.put("dailyPatrolInspectionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DailyPatrolInspection DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getDailyPatrolInspectionById")
	public ResponseEntity<ResponseDTO> getDailyPatrolInspectionById(@RequestParam Long id) {
		String methodName = "getDailyPatrolInspectionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<DailyPatrolInspectionVO> dailyPatrolInspectionVO = null;
		try {
			dailyPatrolInspectionVO = dailyPatrolInspectionService.getDailyPatrolInspectionById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DailyPatrolInspection information get successfully");
			responseObjectsMap.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DailyPatrolInspection information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDailyPatrolInspection")
	public ResponseEntity<ResponseDTO> getAllDailyPatrolInspection(@RequestParam Long orgId) {
		String methodName = "getAllDailyPatrolInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DailyPatrolInspectionVO> dailyPatrolInspectionVO = new  ArrayList<DailyPatrolInspectionVO>();
		try {
			dailyPatrolInspectionVO = dailyPatrolInspectionService.getAllDailyPatrolInspection(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DailyPatrolInspection All information get successfully");
			responseObjectsMap.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DailyPatrolInspection information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardNo")
	public ResponseEntity<ResponseDTO> getRouteCardNo(@RequestParam Long orgId) {
		String methodName = "getRouteCardNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> routeCardNo = new ArrayList<Map<String,Object>>();
		try {
			routeCardNo = dailyPatrolInspectionService.getRouteCardNo(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardNo  information get successfully");
			responseObjectsMap.put("routeCardNo", routeCardNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "RouteCardNo information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getMachineDetail")
	public ResponseEntity<ResponseDTO> getMachineDetail(@RequestParam Long orgId) {
		String methodName = "getRouteCardNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> machineDeatils = new ArrayList<Map<String,Object>>();
		try {
			machineDeatils = dailyPatrolInspectionService.getMachineDetail(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Machine  information get successfully");
			responseObjectsMap.put("machineDeatils", machineDeatils);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Machine information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getShiftDetails")
	public ResponseEntity<ResponseDTO> getShiftDetails() {
		String methodName = "getShiftDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> shiftDetails = new ArrayList<Map<String,Object>>();
		try {
			shiftDetails = dailyPatrolInspectionService.getShiftDetails();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Shift  information get successfully");
			responseObjectsMap.put("shiftDetails", shiftDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Shift information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getJobOrderNo")
	public ResponseEntity<ResponseDTO> getJobOrderNo() {
		String methodName = "getJobOrderNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> jobOrderNo = new ArrayList<Map<String,Object>>();
		try {
			jobOrderNo = dailyPatrolInspectionService.getJobOrderNo();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobOrder  information get successfully");
			responseObjectsMap.put("jobOrderNo", jobOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "JobOrder information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
}
