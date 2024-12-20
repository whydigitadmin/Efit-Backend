package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.JobOrderVO;
import com.efitops.basesetup.entity.JobOrderVO;
import com.efitops.basesetup.service.JobOrderService;

@RestController
@RequestMapping("/api/jobOrder")
public class JobOrderController extends BaseController {

	@Autowired
	JobOrderService jobOrderService;

	@GetMapping("/getAllJobOrderByOrgId")
	public ResponseEntity<ResponseDTO> getAllJobOrderByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllJobOrderByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<JobOrderVO> jobOrderVO = new ArrayList<>();
		try {
			jobOrderVO = jobOrderService.getAllJobOrderByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobOrder information get successfully By OrgId");
			responseObjectsMap.put("jobOrderVO", jobOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "JobOrder information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getJobOrderById")
	public ResponseEntity<ResponseDTO> getJobOrderById(@RequestParam(required = false) Long id) {
		String methodName = "getJobOrderById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<JobOrderVO> jobOrderVO = new ArrayList<>();
		try {
			jobOrderVO = jobOrderService.getJobOrderById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobOrder information get successfully By id");
			responseObjectsMap.put("jobOrderVO", jobOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "JobOrder information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateJobOrder")
	public ResponseEntity<ResponseDTO> updateCreateJobOrder(@RequestBody JobOrderDTO jobOrderDTO) {
		String methodName = "updateCreateJobOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> jobOrderVO = jobOrderService.createUpdateJobOrder(jobOrderDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, jobOrderVO.get("message"));
			responseObjectsMap.put("jobOrderVO", jobOrderVO.get("jobOrderVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobOrderByDocId")
	public ResponseEntity<ResponseDTO> getJobOrderByDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode, @RequestParam String screenCode) {
		String methodName = "getJobOrderByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String jobOrderVO = null;
		try {
			jobOrderVO = jobOrderService.getJobOrderDocId(orgId, finYear, branchCode, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OriginBill information get successfully By docid");
			responseObjectsMap.put("jobOrderVO", jobOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"OriginBill information receive failed By docid", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
}
