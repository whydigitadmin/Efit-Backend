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
import com.efitops.basesetup.dto.DetailsSubmissionToBankDTO;
import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankVO;
import com.efitops.basesetup.entity.JobOrderVO;
import com.efitops.basesetup.service.DetailsSubmissionToBankService;

@RestController
@RequestMapping("/api/detailsSubmissionToBank")
public class DetailsSubmissionToBankController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(DetailsSubmissionToBankController.class);

	@Autowired
	DetailsSubmissionToBankService detailsSubmissionToBankService;

	@GetMapping("/getAllDetailsSubmissionToBankByOrgId")
	public ResponseEntity<ResponseDTO> getAllDetailsSubmissionToBankByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllDetailsSubmissionToBankByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DetailsSubmissionToBankVO> bankVO = new ArrayList<>();
		try {
			bankVO = detailsSubmissionToBankService.getAllDetailsSubmissionToBankByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bank Details get successfully By OrgId");
			responseObjectsMap.put("bankDetailsVO", bankVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Bank Details receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDetailsSubmissionToBankById")
	public ResponseEntity<ResponseDTO> getDetailsSubmissionToBankById(@RequestParam Long id) {
		String methodName = "getDetailsSubmissionToBankById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DetailsSubmissionToBankVO> bankVO = new ArrayList<>();
		try {
			bankVO = detailsSubmissionToBankService.getDetailsSubmissionToBankById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bank Details get successfully By id");
			responseObjectsMap.put("bankDetailsVO", bankVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Bank Details receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateDetailsSubmissionToBank")
	public ResponseEntity<ResponseDTO> updateCreateDetailsSubmissionToBank(
			@RequestBody DetailsSubmissionToBankDTO jobOrderDTO) {
		String methodName = "updateCreateDetailsSubmissionToBank()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> bankVO = detailsSubmissionToBankService
					.createUpdateDetailsSubmissionToBank(jobOrderDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, bankVO.get("message"));
			responseObjectsMap.put("bankDetailsVO", bankVO.get("jobOrderVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDetailsSubmissionToBankByDocId")
	public ResponseEntity<ResponseDTO> getDetailsSubmissionToBankByDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String screenCode) {
		String methodName = "getDetailsSubmissionToBankByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String bankVO = null;
		try {
			bankVO = detailsSubmissionToBankService.getDetailsSubmissionToBankDocId(orgId, finYear, branchCode,
					screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bank Details information get successfully By docid");
			responseObjectsMap.put("bankDetailsVO", bankVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Bank Detail information receive failed By docid", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
