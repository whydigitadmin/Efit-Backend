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
import com.efitops.basesetup.dto.ProductionPlanDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.ProductionPlanVO;
import com.efitops.basesetup.service.ProductionPlanService;

@RestController
@RequestMapping("/api/productionPlan")
public class ProductionPlanController extends BaseController {

	@Autowired
	ProductionPlanService productionPlanService;

	@GetMapping("/getAllProductionPlanByOrgId")
	public ResponseEntity<ResponseDTO> getAllProductionPlanByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllProductionPlanByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProductionPlanVO> productionPlanVO = new ArrayList<>();
		try {
			productionPlanVO = productionPlanService.getAllProductionPlanByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ProductionPlan information get successfully By OrgId");
			responseObjectsMap.put("productionPlanVO", productionPlanVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProductionPlan information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getProductionPlanById")
	public ResponseEntity<ResponseDTO> getProductionPlanById(@RequestParam(required = false) Long id) {
		String methodName = "getProductionPlanById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProductionPlanVO> productionPlanVO = new ArrayList<>();
		try {
			productionPlanVO = productionPlanService.getProductionPlanById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProductionPlan information get successfully By id");
			responseObjectsMap.put("productionPlanVO", productionPlanVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProductionPlan information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateProductionPlan")
	public ResponseEntity<ResponseDTO> updateCreateProductionPlan(@RequestBody ProductionPlanDTO productionPlanDTO) {
		String methodName = "updateCreateProductionPlan()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> productionPlanVO = productionPlanService.createUpdateProductionPlan(productionPlanDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, productionPlanVO.get("message"));
			responseObjectsMap.put("productionPlanVO", productionPlanVO.get("productionPlanVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProductionPlanByDocId")
	public ResponseEntity<ResponseDTO> getProductionPlanByDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode, @RequestParam String screenCode) {
		String methodName = "getProductionPlanByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String productionPlanVO = null;
		try {
			productionPlanVO = productionPlanService.getProductionPlanDocId(orgId, finYear, branchCode, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ProductionPlan  information get successfully By docid");
			responseObjectsMap.put("productionPlanVO", productionPlanVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"OriginBill information receive failed By docid", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
}
