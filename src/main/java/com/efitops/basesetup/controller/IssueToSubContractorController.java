package com.efitops.basesetup.controller;

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

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.DcForSubContractDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.DcForSubContractVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.service.IssueToSubContractorService;

@CrossOrigin
@RestController
@RequestMapping("/api/issuetosubcontractor")
public class IssueToSubContractorController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(IssueToSubContractorController.class);

	@Autowired
	IssueToSubContractorService issueToSubContractorService;

	@GetMapping("/getAllIssueToSubContractorByOrgId")
	public ResponseEntity<ResponseDTO> getAllIssueToSubContractorByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllIssueToSubContractorByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IssueToSubContractorVO> issueToSubContractorVO = new ArrayList<>();
		try {
			issueToSubContractorVO = issueToSubContractorService.getAllIssueToSubContractorByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IssueToSubContractor information get successfully ByOrgId");
			responseObjectsMap.put("issueToSubContractorVO", issueToSubContractorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IssueToSubContractor information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIssueToSubContractorById")
	public ResponseEntity<ResponseDTO> getIssueToSubContractorById(@RequestParam Long id) {
		String methodName = "getIssueToSubContractorById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IssueToSubContractorVO> issueToSubContractorVO = new ArrayList<>();
		try {
			issueToSubContractorVO = issueToSubContractorService.getIssueToSubContractorById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IssueToSubContractor information get successfully By Id");
			responseObjectsMap.put("issueToSubContractorVO", issueToSubContractorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IssueToSubContractor information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateIssueToSubContractor")
	public ResponseEntity<ResponseDTO> createUpdateIssueToSubContractor(
			@Valid @RequestBody IssueToSubContractorDTO issueToSubContractorDTO) {
		String methodName = "createUpdateIssueToSubContractor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> issueToSubContractorVO = issueToSubContractorService
					.createUpdateIssueToSubContractor(issueToSubContractorDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, issueToSubContractorVO.get("message"));
			responseObjectsMap.put("issueToSubContractorVO", issueToSubContractorVO.get("issueToSubContractorVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIssueToSubContractorDocId")
	public ResponseEntity<ResponseDTO> getIssueToSubContractorDocId(@RequestParam Long orgId) {

		String methodName = "getIssueToSubContractorDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getIssueToSubContractorDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IssueToSubContractorDocId information retrieved successfully");
			responseObjectsMap.put("issueToSubContractorDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve IssueToSubContractorDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardNoAndItemNo")
	public ResponseEntity<ResponseDTO> getRouteCardNoAndItemNo(@RequestParam Long orgId) {
		String methodName = "getRouteCardNoAndItemNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getRouteCardNoAndItemNo(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardNo Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve RouteCardNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDepartmentName")
	public ResponseEntity<ResponseDTO> getDepartmentName(@RequestParam Long orgId) {
		String methodName = "getDepartmentName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getDepartmentName(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DepartmentName Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DepartmentName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProcessNameFormItemWiseProcess")
	public ResponseEntity<ResponseDTO> getProcessNameFormItemWiseProcess(@RequestParam Long orgId,
			@RequestParam String item) {
		String methodName = "getProcessNameFormItemWiseProcess()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getProcessNameFormItemWiseProcess(orgId, item);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessName Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ProcessName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	//DcForSubContractor
	
	
	@GetMapping("/getDcforSCByOrgId")
	public ResponseEntity<ResponseDTO> getDcforSCByOrgId(@RequestParam Long orgId) {
		String methodName = "getDcforSCByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
		try {
			dcForSubContractVO = issueToSubContractorService.getDcforSCByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DcForSubContract information get successfully ByOrgId");
			responseObjectsMap.put("dcForSubContractVO", dcForSubContractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DcForSubContract information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getDcforSCById")
	public ResponseEntity<ResponseDTO> getDcforSCById(@RequestParam Long id) {
		String methodName = "getDcforSCById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
		try {
			dcForSubContractVO = issueToSubContractorService.getDcforSCById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Dc For SubContract information get successfully By Id");
			responseObjectsMap.put("dcForSubContractVO", dcForSubContractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Dc For SubContract information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PutMapping("/updateCreateDcForSubContract")
	public ResponseEntity<ResponseDTO> updateCreateDcForSubContract(@Valid @RequestBody DcForSubContractDTO dcForSubContractDTO) {
		String methodName = "updateCreateDcForSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> dcForSubContractVO = issueToSubContractorService.updateCreateDcForSubContract(dcForSubContractDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, dcForSubContractVO.get("message"));
			responseObjectsMap.put("dcForSubContractVO", dcForSubContractVO.get("dcForSubContractVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@GetMapping("/getDcForSubContractDocId")
	public ResponseEntity<ResponseDTO> getDcForSubContractDocId(@RequestParam Long orgId) 
	{
		String methodName = "getDcForSubContractDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getDcForSubContractDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnDocId information retrieved successfully");
			responseObjectsMap.put("dcForSubContractDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GrnDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
}
