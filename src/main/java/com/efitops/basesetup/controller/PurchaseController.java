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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PurchaseEnquiryDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseQuotationDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseQuotationAttachmentVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;
import com.efitops.basesetup.service.PurchaseService;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);
	
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
	public ResponseEntity<ResponseDTO> getAllPurchaseIndentByOrgId(@RequestParam Long orgId) {
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
	public ResponseEntity<ResponseDTO> getPurchaseIndentById(@RequestParam Long id) {
		String methodName = "getPurchaseIndentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<PurchaseIndentVO>  purchaseIndentVO  =null;
		try {
			purchaseIndentVO = purchaseService.getPurchaseIndentById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "purchaseService information get successfully By Id");
			responseObjectsMap.put("purchaseIndentVO", purchaseIndentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseService information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	
	@GetMapping("/getCustomerNameForPurchaseIndent")
	public ResponseEntity<ResponseDTO> getCustomerNameForPurchaseIndent(@RequestParam Long orgId) {
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
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomerName for PurchaseIndent information get successfully By OrgId");
			responseObjectsMap.put("customerNameList", customerNameList);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CustomerName for PurchaseIndent information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getIndentType")
	public ResponseEntity<ResponseDTO> getIndentType(@RequestParam Long orgId) {
		String methodName = "getCustomerNameForPurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> indentType = new ArrayList<>();
		try {
			indentType = purchaseService.getIndentType(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IndentType for PurchaseIndent  information get successfully");
			responseObjectsMap.put("indentType", indentType);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IndentType for PurchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getDepartmentForPurchase")
	public ResponseEntity<ResponseDTO> getDepartmentForPurchase(@RequestParam Long orgId) {
		String methodName = "getDepartmentForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> department = new ArrayList<>();
		try {
			department = purchaseService.getDepartmentForPurchase(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Department for PurchaseIndent information get successfully");
			responseObjectsMap.put("department", department);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Department for PurchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getRequestedByForPurchase")
	public ResponseEntity<ResponseDTO> getRequestedByForPurchase(@RequestParam Long orgId) {
		String methodName = "getRequestedByForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> department = new ArrayList<>();
		try {
			department = purchaseService.getRequestedByForPurchase(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RequestedBy for PurchaseIndent information get successfully");
			responseObjectsMap.put("RequestedBy", department);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"RequestedBy for PurchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getVerifiedByForPurchase")
	public ResponseEntity<ResponseDTO> getVerifiedByForPurchase(@RequestParam Long orgId) {
		String methodName = "getVerifiedByForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> Employee = new ArrayList<>();
		try {
			Employee = purchaseService.getVerifiedByForPurchase(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VerifiedBy for PurchaseIndent information get successfully");
			responseObjectsMap.put("VerifiedBy", Employee);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"VerifiedBy for PurchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@GetMapping("/getBomItemDetailsForPurchase")
	public ResponseEntity<ResponseDTO> getBomItemDetailsForPurchase(@RequestParam Long orgId,@RequestParam String fgPart) {
		String methodName = "getBomItemDetailsForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemDetails = new ArrayList<>();
		try {
			itemDetails = purchaseService.getBomItemDetailsForPurchase(orgId,fgPart);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item for purchaseIndent information get successfully");
			responseObjectsMap.put("itemDetails", itemDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Item for purchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getpurchaseIndentDocId")
	public ResponseEntity<ResponseDTO> getpurchaseIndentDocId(@RequestParam Long orgId) {

		String methodName = "getpurchaseIndentDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getpurchaseIndentDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "purchaseIndent DocId information retrieved successfully");
			responseObjectsMap.put("purchaseIndentDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve purchaseIndent DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getWorkOrderNoForPurchaseIndent")
	public ResponseEntity<ResponseDTO> getWorkOrderNoForPurchaseIndent(@RequestParam Long orgId,@RequestParam String customerCode) {
		String methodName = "getWorkOrderNoForPurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderNo = new ArrayList<>();
		try {
			workOrderNo = purchaseService.getWorkOrderNoForPurchaseIndent(orgId,customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOrderNo for PurchaseIndent information get successfully By OrgId");
			responseObjectsMap.put("workOrderNo", workOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"WorkOrderNo for PurchaseIndent information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	//PurchaseEnquiry

	@PutMapping("/updateCreatePurchaseEnquiry")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseEnquiry(@Valid @RequestBody PurchaseEnquiryDTO purchaseEnquiryDTO) {
		String methodName = "updateCreatePurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> purchaseEnquiryVO = purchaseService.updateCreatePurchaseEnquiry(purchaseEnquiryDTO);
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
	
	@GetMapping("/getAllPurchaseEnquiryByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseEnquiryByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllPurchaseEnquiryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseEnquiryVO> purchaseEnquiryVO = new ArrayList<>();
		try {
			purchaseEnquiryVO = purchaseService.getAllPurchaseEnquiryByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "purchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("purchaseEnquiryVO", purchaseEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getAllPurchaseEnquiryById")
	public ResponseEntity<ResponseDTO> getAllPurchaseEnquiryById(@RequestParam Long id) {
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
	
	
	
	@GetMapping("/getPurchaseEnquiryDocId")
	public ResponseEntity<ResponseDTO> getPurchaseEnquiryDocId(@RequestParam Long orgId,@RequestParam String finYear,
			@RequestParam String screenCode) {

		String methodName = "getPurchaseEnquiryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getPurchaseEnquiryDocId(orgId,finYear,screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseEnquiry DocId information retrieved successfully");
			responseObjectsMap.put("purchaseIndentDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseEnquiry DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getSupplierNameForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getSupplierNameForPurchaseEnquiry(@RequestParam Long orgId) {
		String methodName = "getSupplierNameForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> supplierNameList = new ArrayList<>();
		try {
			supplierNameList = purchaseService.getSupplierNameForPurchaseEnquiry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SupplierName for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("SupplierNameList", supplierNameList);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SupplierName for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@GetMapping("/getContactPersonDetailsForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getContactPersonDetailsForPurchaseEnquiry(@RequestParam Long orgId,@RequestParam String supplierCode) {
		String methodName = "getContactPersonDetailsForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> ContactPersonDetails = new ArrayList<>();
		try {
			ContactPersonDetails = purchaseService.getContactPersonDetailsForPurchaseEnquiry(orgId,supplierCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ContactPersonDetails for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("purchaseEnquiryVO", ContactPersonDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ContactPersonDetails for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getPurchaseIndentNoForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getPurchaseIndentNoForPurchaseEnquiry(@RequestParam Long orgId,@RequestParam String customerCode,@RequestParam String workOrderNo) {
		String methodName = "getPurchaseIndentNoForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseIndentNo = new ArrayList<>();
		try {
			purchaseIndentNo = purchaseService.getPurchaseIndentNoForPurchaseEnquiry(orgId,customerCode,workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseIndentNo for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("purchaseIndentNo", purchaseIndentNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseIndentNo for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@GetMapping("/getItemDetailsForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getItemDetailsForPurchaseEnquiry(@RequestParam Long orgId,@RequestParam String purchaseIndentNo,@RequestParam String fgItem) {
		String methodName = "getItemDetailsForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemDetails = new ArrayList<>();
		try {
			itemDetails = purchaseService.getItemDetailsForPurchaseEnquiry(orgId,purchaseIndentNo,fgItem);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemDetails for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("itemDetails", itemDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ItemDetails for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getWorkOrderNoForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getWorkOrderNoForPurchaseEnquiry(@RequestParam Long orgId,@RequestParam String customerCode) {
		String methodName = "getWorkOrderNoForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderNo = new ArrayList<>();
		try {
			workOrderNo = purchaseService.getWorkOrderNoForPurchaseEnquiry(orgId,customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOrderNo for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("workOrderNo", workOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"WorkOrderNo for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	//PurchaseQuotation
	
	@GetMapping("/getAllPurchaseQuotationByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseQuotationByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllPurchaseQuotationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseQuotationVO> purchaseQuotationVO = new ArrayList<>();
		try {
			purchaseQuotationVO = purchaseService.getAllPurchaseQuotationByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "purchaseQuotation information get successfully By OrgId");
			responseObjectsMap.put("purchaseQuotationVO", purchaseQuotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getPurchaseQuotationById")
	public ResponseEntity<ResponseDTO> getPurchaseQuotationById(@RequestParam Long id) {
		String methodName = "getPurchaseQuotationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<PurchaseQuotationVO>  purchaseQuotationVO  =null;
		try {
			purchaseQuotationVO = purchaseService.getPurchaseQuotationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "purchaseQuotation information get successfully By Id");
			responseObjectsMap.put("purchaseQuotationVO", purchaseQuotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseQuotation information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@PutMapping("/updateCreatePurchaseQuotation")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseQuotation(@Valid @RequestBody PurchaseQuotationDTO purchaseQuotationDTO) {
		String methodName = "updateCreatePurchaseQuotation()"; 
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> purchaseQuotationVO = purchaseService.updateCreatePurchaseQuotation(purchaseQuotationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseQuotationVO.get("message"));
			responseObjectsMap.put("purchaseQuotationVO", purchaseQuotationVO.get("purchaseQuotationVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getpurchaseQuotationDocId")
	public ResponseEntity<ResponseDTO> getpurchaseQuotationDocId(@RequestParam Long orgId) {

		String methodName = "getpurchaseQuotationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getpurchaseQuotationDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "purchaseQuotation DocId information retrieved successfully");
			responseObjectsMap.put("purchaseQuotationDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve purchaseQuotation DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getPurchaseEnquiryNoForPurchaseQuotation")
	public ResponseEntity<ResponseDTO> getPurchaseEnquiryNoForPurchaseQuotation(@RequestParam Long orgId,@RequestParam String customerCode,@RequestParam String workOrderNo) {
		String methodName = "getPurchaseEnquiryNoForPurchaseQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseEnquiryNo = new ArrayList<>();
		try {
			purchaseEnquiryNo = purchaseService.getPurchaseEnquiryNoForPurchaseQuotation(orgId,customerCode,workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseEnquiryNo for PurchaseQuotation information get successfully By OrgId");
			responseObjectsMap.put("purchaseEnquiryNo", purchaseEnquiryNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseEnquiryNo for PurchaseQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getItemDetailsForPurchaseQuotation")
	public ResponseEntity<ResponseDTO> getItemDetailsForPurchaseQuotation(@RequestParam Long orgId,@RequestParam String purchaseEnquiryNo) {
		String methodName = "getItemDetailsForPurchaseQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemDetails = new ArrayList<>();
		try {
			itemDetails = purchaseService.getItemDetailsForPurchaseQuotation(orgId,purchaseEnquiryNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemDetails for PurchaseQuotation information get successfully By OrgId");
			responseObjectsMap.put("itemDetails", itemDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ItemDetails for PurchaseQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PostMapping("/uploadPurchaseQuatationAttachementsInBloob")
	public ResponseEntity<ResponseDTO> uploadPurchaseQuatationAttachementsInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadPurchaseQuatationAttachementsInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PurchaseQuotationAttachmentVO purchaseQuotationAttachmentVO = null;
		try {
			purchaseQuotationAttachmentVO = purchaseService.uploadPurchaseQuatationAttachementsInBloob(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload attachements", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseQuatation Attachments Successfully Upload");
			responseObjectsMap.put("purchaseQuotationAttachmentVO", purchaseQuotationAttachmentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Attachments Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getWorkOrderNoForPurchaseQuotation")
	public ResponseEntity<ResponseDTO> getWorkOrderNoForPurchaseQuotation(@RequestParam Long orgId,@RequestParam String customerCode) {
		String methodName = "getWorkOrderNoForPurchaseQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderNo = new ArrayList<>();
		try {
			workOrderNo = purchaseService.getWorkOrderNoForPurchaseQuotation(orgId,customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOrderNo for PurchaseQuotation information get successfully By OrgId");
			responseObjectsMap.put("workOrderNo", workOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"WorkOrderNo for PurchaseQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
}
