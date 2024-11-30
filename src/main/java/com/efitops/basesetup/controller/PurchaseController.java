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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PurchaseEnquiryDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.service.PurchaseService;

@RestController
@RequestMapping("/api/documentType")
public class PurchaseController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeController.class);
	
	@Autowired
	PurchaseService purchaseService;
	
	@PutMapping("/updateCreatePurchaseIndent")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseIndent(@Valid @RequestBody PurchaseIndentDTO purchaseIndentDTO) {
		String methodName = "updateCreatePurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> purchaseIndentVO = purchaseService.updateCreatePurchaseIndent(purchaseIndentDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseIndentVO.get("message"));
			responseObjectsMap.put("purchaseIndentVO", purchaseIndentVO.get("purchaseIndentVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getAllPurchaseIndentByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseIndentByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllPurchaseIndentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseIndentVO> purchaseIndentVOs = new ArrayList<>();
		try {
			purchaseIndentVOs = purchaseService.getAllPurchaseIndentByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "purchaseService information get successfully By OrgId");
			responseObjectsMap.put("purchaseIndentVO", purchaseIndentVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseService information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getPurchaseIndentById")
	public ResponseEntity<ResponseDTO> getPurchaseIndentById(@RequestParam(required = false) Long id) {
		String methodName = "getPurchaseIndentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<PurchaseIndentVO>  purchaseIndentVOs  =null;
		try {
			purchaseIndentVOs = purchaseService.getPurchaseIndentById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "purchaseService information get successfully By Id");
			responseObjectsMap.put("purchaseIndentVO", purchaseIndentVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseService information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getCustomerNameForPurchaseIndent")
	public ResponseEntity<ResponseDTO> getCustomerNameForPurchaseIndent(@RequestParam(required = false) Long orgId) {
		String methodName = "getCustomerNameForPurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> customerNameList = new ArrayList<>();
		try {
			customerNameList = purchaseService.getCustomerNameForPurchaseIndent(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomerName information get successfully By OrgId");
			responseObjectsMap.put("customerNameList", customerNameList);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CustomerName information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getIndentType")
	public ResponseEntity<ResponseDTO> getIndentType() {
		String methodName = "getCustomerNameForPurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> indentType = new ArrayList<>();
		try {
			indentType = purchaseService.getIndentType();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IndentType information get successfully");
			responseObjectsMap.put("indentType", indentType);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IndentType information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getDepartmentForPurchase")
	public ResponseEntity<ResponseDTO> getDepartmentForPurchase() {
		String methodName = "getDepartmentForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> department = new ArrayList<>();
		try {
			department = purchaseService.getDepartmentForPurchase();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Department information get successfully");
			responseObjectsMap.put("department", department);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Department information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getRequestedByForPurchase")
	public ResponseEntity<ResponseDTO> getRequestedByForPurchase() {
		String methodName = "getRequestedByForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> department = new ArrayList<>();
		try {
			department = purchaseService.getRequestedByForPurchase();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RequestedBy information get successfully");
			responseObjectsMap.put("RequestedBy", department);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"RequestedBy information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getItemDetailsForPurchase")
	public ResponseEntity<ResponseDTO> getItemDetailsForPurchase(@RequestParam(required =false) String itemName) {
		String methodName = "getItemDetailsForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemDetails = new ArrayList<>();
		try {
			itemDetails = purchaseService.getItemDetailsForPurchase(itemName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information get successfully");
			responseObjectsMap.put("itemDetails", itemDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Item information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	//PurchaseEnquiry

	@PutMapping("/updateCreatePurchaseEnquiry")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseEnquiry(@Valid @RequestBody PurchaseEnquiryDTO purchaseIndentDTO) {
		String methodName = "updateCreatePurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> purchaseEnquiryVO = purchaseService.updateCreatePurchaseEnquiry(purchaseIndentDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseEnquiryVO.get("message"));
			responseObjectsMap.put("purchaseEnquiryVO", purchaseEnquiryVO.get("purchaseEnquiryVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getAllPurchaseEnquiryById")
	public ResponseEntity<ResponseDTO> getAllPurchaseEnquiryById(@RequestParam(required = false) Long id) {
		String methodName = "getAllPurchaseEnquiryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<PurchaseEnquiryVO> purchaseEnquiryVO=null;
		try {
			purchaseEnquiryVO = purchaseService.getAllPurchaseEnquiryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseEnquiry information get successfully By id");
			responseObjectsMap.put("purchaseEnquiryVO", purchaseEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseEnquiry information receive failed By id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getAllPurchaseEnquiryByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseEnquiryByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllPurchaseEnquiryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseEnquiryVO> purchaseEnquiryVO=new ArrayList<PurchaseEnquiryVO>();
		try {
			purchaseEnquiryVO = purchaseService.getAllPurchaseEnquiryByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseEnquiry information get successfully By orgId");
			responseObjectsMap.put("purchaseEnquiryVO", purchaseEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseEnquiry information receive failed By orgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
}
