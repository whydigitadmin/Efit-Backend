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
import com.efitops.basesetup.dto.FinalInspectionReportDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.dto.InprocessInspectionDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.FinalInspectionReportVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;
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

	@GetMapping("/getGrnNoFromGrnScreen")
	public ResponseEntity<ResponseDTO> getGrnNoFromGrnScreen(@RequestParam Long orgId, @RequestParam String grnNo) {
		String methodName = "getGrnNoFromGrnScreen()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getGrnNoFromGrnScreen(orgId, grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnNo Details retrieved successfully");
			responseObjectsMap.put("grnVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemNoFromGrn")
	public ResponseEntity<ResponseDTO> getItemNoFromGrn(@RequestParam Long orgId, @RequestParam String grnNo) {
		String methodName = "getItemNoFromGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getItemNoFromGrn(orgId, grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemCode Details retrieved successfully");
			responseObjectsMap.put("grnVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemCode Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// InprocessInspection

	@GetMapping("/getAllInprocessInspectionByOrgId")
	public ResponseEntity<ResponseDTO> getAllInprocessInspectionByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllInprocessInspectionByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<InprocessInspectionVO> inprocessInspectionVO = new ArrayList<>();
		try {
			inprocessInspectionVO = qualityService.getAllInprocessInspectionByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"InprocessInspection information get successfully ByOrgId");
			responseObjectsMap.put("inprocessInspectionVO", inprocessInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"InprocessInspection information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getInprocessInspectionById")
	public ResponseEntity<ResponseDTO> getInprocessInspectionById(@RequestParam Long id) {
		String methodName = "getInprocessInspectionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		InprocessInspectionVO inprocessInspectionVO = new InprocessInspectionVO();
		try {
			inprocessInspectionVO = qualityService.getInprocessInspectionById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"InprocessInspection information get successfully By id");
			responseObjectsMap.put("inprocessInspectionVO", inprocessInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"InprocessInspection information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateInprocessInspection")
	public ResponseEntity<ResponseDTO> createUpdateInprocessInspection(
			@RequestBody InprocessInspectionDTO inprocessInspectionDTO) {
		String methodName = "createUpdateInprocessInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> inprocessInspectionVO = qualityService
					.createUpdateInprocessInspection(inprocessInspectionDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, inprocessInspectionVO.get("message"));
			responseObjectsMap.put("inprocessInspectionVO", inprocessInspectionVO.get("inprocessInspectionVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInprocessInspectionDocId")
	public ResponseEntity<ResponseDTO> getInprocessInspectionDocId(@RequestParam Long orgId) {

		String methodName = "getInprocessInspectionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getInprocessInspectionDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"InprocessInspectionDocId information retrieved successfully");
			responseObjectsMap.put("inprocessInspectionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve InprocessInspectionDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocIdFromRouteCardNumber")
	public ResponseEntity<ResponseDTO> getDocIdFromRouteCardNumber(@RequestParam Long orgId) {
		String methodName = "getDocIdFromRouteCardNumber()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getDocIdFromRouteCardNumber(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RoutrCardNumber Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve RoutrCardNumber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDrawingNumberFromDrawingMaster")
	public ResponseEntity<ResponseDTO> getDrawingNumberFromDrawingMaster(@RequestParam Long orgId,
			@RequestParam String fgPartno) {
		String methodName = "getDrawingNumberFromDrawingMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getDrawingNumberFromDrawingMaster(orgId, fgPartno);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DrawingNumber Details retrieved successfully");
			responseObjectsMap.put("drawingMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DrawingNumber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEmployeeNameFromEmployeeMaster")
	public ResponseEntity<ResponseDTO> getEmployeeFromEmployeeMaster(@RequestParam Long orgId) {
		String methodName = "getEmployeeNameFromEmployeeMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getEmployeeNameFromEmployeeMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmployeeName Details retrieved successfully");
			responseObjectsMap.put("employeeVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve EmployeeName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// FinalInspectionReport

	@GetMapping("/getAllFinalInspectionReportByOrgId")
	public ResponseEntity<ResponseDTO> getAllFinalInspectionReportByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllFinalInspectionReportByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<FinalInspectionReportVO> finalInspectionReportVO = new ArrayList<>();
		try {
			finalInspectionReportVO = qualityService.getAllFinalInspectionReportByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FinalInspectionReport information get successfully ByOrgId");
			responseObjectsMap.put("finalInspectionReportVO", finalInspectionReportVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FinalInspectionReport information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getFinalInspectionReportById")
	public ResponseEntity<ResponseDTO> getFinalInspectionReportById(@RequestParam Long id) {
		String methodName = "getFinalInspectionReportById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		FinalInspectionReportVO finalInspectionReportVO = new FinalInspectionReportVO();
		try {
			finalInspectionReportVO = qualityService.getFinalInspectionReportById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FinalInspectionReport information get successfully By id");
			responseObjectsMap.put("finalInspectionReportVO", finalInspectionReportVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FinalInspectionReport information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateFinalInspectionReport")
	public ResponseEntity<ResponseDTO> createUpdateFinalInspectionReport(
			@RequestBody FinalInspectionReportDTO finalInspectionReportDTO) {
		String methodName = "createUpdateFinalInspectionReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> finalInspectionReportVO = qualityService
					.createUpdateFinalInspectionReport(finalInspectionReportDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, finalInspectionReportVO.get("message"));
			responseObjectsMap.put("finalInspectionReportVO", finalInspectionReportVO.get("finalInspectionReportVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFinalInspectionReportDocId")
	public ResponseEntity<ResponseDTO> getFinalInspectionReportDocId(@RequestParam Long orgId) {

		String methodName = "getFinalInspectionReportDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getFinalInspectionReportDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FinalInspectionReportDocId information retrieved successfully");
			responseObjectsMap.put("finalInspectionReportDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve FinalInspectionReportDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardNumberFromRouteCard")
	public ResponseEntity<ResponseDTO> getRouteCardNumberFromRouteCard(@RequestParam Long orgId,
			@RequestParam String fgPartName) {
		String methodName = "getRouteCardNumberFromRouteCard()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getRouteCardNumberFromRouteCard(orgId, fgPartName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardNumber Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve RouteCardNumber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
