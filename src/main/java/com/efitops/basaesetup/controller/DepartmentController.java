package com.efitops.basaesetup.controller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.efitops.basaesetup.common.CommonConstant;
import com.efitops.basaesetup.common.UserConstants;
import com.efitops.basaesetup.dto.DepartmentDTO;
import com.efitops.basaesetup.dto.GstDTO;
import com.efitops.basaesetup.dto.MaterialTypeDTO;
import com.efitops.basaesetup.dto.ProcessMasterDTO;
import com.efitops.basaesetup.dto.ResponseDTO;
import com.efitops.basaesetup.entity.DepartmentVO;
import com.efitops.basaesetup.entity.GstVO;
import com.efitops.basaesetup.entity.MaterialTypeVO;
import com.efitops.basaesetup.entity.ProcessMasterVO;
import com.efitops.basaesetup.service.DepartmentService;

@CrossOrigin
@RestController
@RequestMapping("/api/department")
public class DepartmentController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService departmentService;

	// Department

	@GetMapping("/getAllDepartmentByOrgId")
	public ResponseEntity<ResponseDTO> getAllDepartmentByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllDepartmentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DepartmentVO> departmentVO = new ArrayList<>();
		try {
			departmentVO = departmentService.getAllDepartmentByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Department information get successfully ByOrgId");
			responseObjectsMap.put("departmentVO", departmentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Department information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDepartmentById")
	public ResponseEntity<ResponseDTO> getDepartmentById(@RequestParam Long id) {
		String methodName = "getDepartmentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DepartmentVO> departmentVO = new ArrayList<>();
		try {
			departmentVO = departmentService.getDepartmentById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Department information get successfully By Id");
			responseObjectsMap.put("departmentVO", departmentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Department information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateDepartment")
	public ResponseEntity<ResponseDTO> createUpdateDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
		String methodName = "createUpdateDepartment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> departmentVO = departmentService.createUpdateDepartment(departmentDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, departmentVO.get("message"));
			responseObjectsMap.put("departmentVO", departmentVO.get("departmentVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getDepartmentDocId")
	public ResponseEntity<ResponseDTO> getDepartmentDocId(@RequestParam Long orgId) {

		String methodName = "getDepartmentDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = departmentService.getDepartmentDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DepartmentDocId information retrieved successfully");
			responseObjectsMap.put("departmentDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DepartmentDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	

	// Gst

	@GetMapping("/getAllGstByOrgId")
	public ResponseEntity<ResponseDTO> getAllGstByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllGstByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GstVO> gstVO = new ArrayList<>();
		try {
			gstVO = departmentService.getAllGstByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Gst information get successfully ByOrgId");
			responseObjectsMap.put("gstVO", gstVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Gst information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGstById")
	public ResponseEntity<ResponseDTO> getGstById(@RequestParam Long id) {
		String methodName = "getGstById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GstVO> gstVO = new ArrayList<>();
		try {
			gstVO = departmentService.getGstById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Gst information get successfully By Id");
			responseObjectsMap.put("gstVO", gstVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Gst information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateGst")
	public ResponseEntity<ResponseDTO> createUpdateGst(@Valid @RequestBody GstDTO gstDTO) {
		String methodName = "createUpdateGst()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> gstVO = departmentService.createUpdateGst(gstDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, gstVO.get("message"));
			responseObjectsMap.put("gstVO", gstVO.get("gstVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// ProessMaster

	@GetMapping("/getAllProcessMasterByOrgId")
	public ResponseEntity<ResponseDTO> getAllProcessMasterByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllProcessMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		try {
			processMasterVO = departmentService.getAllProcessMasterByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessMaster information get successfully ByOrgId");
			responseObjectsMap.put("processMasterVO", processMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProcessMaster information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getProcessMasterById")
	public ResponseEntity<ResponseDTO> getProcessMasterById(@RequestParam Long id) {
		String methodName = "getProcessMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		try {
			processMasterVO = departmentService.getProcessMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessMaster information get successfully By Id");
			responseObjectsMap.put("processMasterVO", processMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProcessMaster information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateProcessMaster")
	public ResponseEntity<ResponseDTO> createUpdateProcessMaster(
			@Valid @RequestBody ProcessMasterDTO processMasterDTO) {
		String methodName = "createUpdateProcessMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> processMasterVO = departmentService.createUpdateProcessMaster(processMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, processMasterVO.get("message"));
			responseObjectsMap.put("processMasterVO", processMasterVO.get("processMasterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getProcessMasterDocId")
	public ResponseEntity<ResponseDTO> getProcessMasterDocId(@RequestParam Long orgId) {

		String methodName = "getProcessMasterDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = departmentService.getProcessMasterDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessMasterDocId information retrieved successfully");
			responseObjectsMap.put("processMasterDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ProcessMasterDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Material Type

	@GetMapping("/getAllMaterialTypeByOrgId")
	public ResponseEntity<ResponseDTO> getAllMaterialTypeByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllMaterialTypeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		try {
			materialTypeVO = departmentService.getAllMaterialTypeByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MaterialType information get successfully ByOrgId");
			responseObjectsMap.put("materialTypeVO", materialTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MaterialType information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getMaterialTypeById")
	public ResponseEntity<ResponseDTO> getMaterialTypeById(@RequestParam Long id) {
		String methodName = "getMaterialTypeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		try {
			materialTypeVO = departmentService.getMaterialTypeById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MaterialType information get successfully By Id");
			responseObjectsMap.put("materialTypeVO", materialTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MaterialType information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateMaterialType")
	public ResponseEntity<ResponseDTO> createUpdateMaterialType(@Valid @RequestBody MaterialTypeDTO materialTypeDTO) {
		String methodName = "createUpdateMaterialType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> materialTypeVO = departmentService.createUpdateMaterialType(materialTypeDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, materialTypeVO.get("message"));
			responseObjectsMap.put("materialTypeVO", materialTypeVO.get("materialTypeVO"));
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
