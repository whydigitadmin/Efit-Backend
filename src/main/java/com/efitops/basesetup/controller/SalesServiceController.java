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
import com.efitops.basesetup.dto.DeliveryChalanForFgDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.DeliveryChalanForFgVO;
import com.efitops.basesetup.service.SalesService;

@RestController
@RequestMapping("/api/sales")
public class SalesServiceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(SalesServiceController.class);

	@Autowired
	SalesService salesService;

	// DeliveryChalanForFg

	@GetMapping("/getAllDeliveryChalanForFgByOrgId")
	public ResponseEntity<ResponseDTO> getAllDeliveryChalanForFgByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllDeliveryChalanForFgByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DeliveryChalanForFgVO> deliveryChalanForFgVO = new ArrayList<>();
		try {
			deliveryChalanForFgVO = salesService.getAllDeliveryChalanForFgByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DeliveryChalanForFg information get successfully ByOrgId");
			responseObjectsMap.put("deliveryChalanForFgVO", deliveryChalanForFgVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DeliveryChalanForFg information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDeliveryChalanForFgById")
	public ResponseEntity<ResponseDTO> getDeliveryChalanForFgById(@RequestParam Long id) {
		String methodName = "getDeliveryChalanForFgById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DeliveryChalanForFgVO deliveryChalanForFgVO = new DeliveryChalanForFgVO();
		try {
			deliveryChalanForFgVO = salesService.getDeliveryChalanForFgById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DeliveryChalanForFg information get successfully By id");
			responseObjectsMap.put("deliveryChalanForFgVO", deliveryChalanForFgVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DeliveryChalanForFg information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateDeliveryChalanForFg")
	public ResponseEntity<ResponseDTO> createUpdateDeliveryChalanForFg(
			@RequestBody DeliveryChalanForFgDTO deliveryChalanForFgDTO) {
		String methodName = "createUpdateDeliveryChalanForFg()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> deliveryChalanForFgVO = salesService
					.createUpdateDeliveryChalanForFg(deliveryChalanForFgDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, deliveryChalanForFgVO.get("message"));
			responseObjectsMap.put("deliveryChalanForFgVO", deliveryChalanForFgVO.get("deliveryChalanForFgVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeliveryChalanForFgDocId")
	public ResponseEntity<ResponseDTO> getDeliveryChalanForFgDocId(@RequestParam Long orgId) {

		String methodName = "getDeliveryChalanForFgDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = salesService.getDeliveryChalanForFgDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DeliveryChalanForFgDocId information retrieved successfully");
			responseObjectsMap.put("deliveryChalanForFgDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DeliveryChalanForFgDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
