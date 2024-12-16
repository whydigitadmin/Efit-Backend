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
import com.efitops.basesetup.dto.PurchaseInvoiceDTO;
import com.efitops.basesetup.dto.PurchaseReturnDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.PurchaseInvoiceVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
import com.efitops.basesetup.service.PurchaseReturnService;

@RestController
@RequestMapping("/api/purchaseReturn")
public class PurchaseReturnController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseReturnController.class);

	@Autowired
	PurchaseReturnService purchaseReturnService;

	// PurchaseReturn

	@GetMapping("/getAllPurchaseReturnByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseReturnByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllPurchaseReturnByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseReturnVO> purchaseReturnVO = new ArrayList<>();
		try {
			purchaseReturnVO = purchaseReturnService.getAllPurchaseReturnByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseReturn information get successfully ByOrgId");
			responseObjectsMap.put("purchaseReturnVO", purchaseReturnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseReturn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseReturnById")
	public ResponseEntity<ResponseDTO> getPurchaseReturnById(@RequestParam Long id) {
		String methodName = "getPurchaseReturnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PurchaseReturnVO purchaseReturnVO = new PurchaseReturnVO();
		try {
			purchaseReturnVO = purchaseReturnService.getPurchaseReturnById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseReturn information get successfully By id");
			responseObjectsMap.put("purchaseReturnVO", purchaseReturnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseReturn information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdatePurchaseReturn")
	public ResponseEntity<ResponseDTO> createUpdatePurchaseReturn(@RequestBody PurchaseReturnDTO purchaseReturnDTO) {
		String methodName = "createUpdatePurchaseReturn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> purchaseReturnVO = purchaseReturnService.createUpdatePurchaseReturn(purchaseReturnDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseReturnVO.get("message"));
			responseObjectsMap.put("purchaseReturnVO", purchaseReturnVO.get("purchaseReturnVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseReturnDocId")
	public ResponseEntity<ResponseDTO> getPurchaseReturnDocId(@RequestParam Long orgId) {

		String methodName = "getPurchaseReturnDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseReturnService.getPurchaseReturnDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"purchaseReturnDocId information retrieved successfully");
			responseObjectsMap.put("purchaseReturnDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve purchaseReturnDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseInvoiceNumberFromPurchaseInvoice")
	public ResponseEntity<ResponseDTO> getPurchaseInvoiceNumberFromPurchaseInvoice(@RequestParam Long orgId,
			@RequestParam String purchaseInvoiceNo, @RequestParam String supplierCode) {
		String methodName = "getPurchaseInvoiceNumberFromPurchaseInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseReturnService.getPurchaseInvoiceNumberFromPurchaseInvoice(orgId, purchaseInvoiceNo,
					supplierCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseInvoiceNumber Details retrieved successfully");
			responseObjectsMap.put("purchaseInvoiceVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseInvoiceNumber Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getLocationFromStockLocation")
	public ResponseEntity<ResponseDTO> getLocationFromStockLocation(@RequestParam Long orgId) {
		String methodName = "getLocationFromStockLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseReturnService.getLocationFromStockLocation(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "location Details retrieved successfully");
			responseObjectsMap.put("stockLocationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve location Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemCodeAndItemDescFromPurchsaeInvoice")
	public ResponseEntity<ResponseDTO> getItemCodeAndItemDescFromPurchsaeInvoice(@RequestParam Long orgId,
			@RequestParam String purchaseInvoiceNo) {
		String methodName = "getItemCodeAndItemDescFromPurchsaeInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseReturnService.getItemCodeAndItemDescFromPurchsaeInvoice(orgId, purchaseInvoiceNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemCode Details retrieved successfully");
			responseObjectsMap.put("purchaseInvoiceVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemCode Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// PurchaseInvoice

	@GetMapping("/getAllPurchaseInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseInvoiceByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllPurchaseInvoiceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseInvoiceVO> purchaseInvoiceVO = new ArrayList<>();
		try {
			purchaseInvoiceVO = purchaseReturnService.getAllPurchaseInvoiceByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseInvoice information get successfully ByOrgId");
			responseObjectsMap.put("purchaseInvoiceVO", purchaseInvoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseInvoice information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseInvoiceById")
	public ResponseEntity<ResponseDTO> getPurchaseInvoiceById(@RequestParam Long id) {
		String methodName = "getPurchaseInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PurchaseInvoiceVO purchaseInvoiceVO = new PurchaseInvoiceVO();
		try {
			purchaseInvoiceVO = purchaseReturnService.getPurchaseInvoiceById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseInvoice information get successfully By id");
			responseObjectsMap.put("purchaseInvoiceVO", purchaseInvoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseInvoice information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdatePurchaseInvoice")
	public ResponseEntity<ResponseDTO> createUpdatePurchaseInvoice(@RequestBody PurchaseInvoiceDTO purchaseInvoiceDTO) {
		String methodName = "createUpdatePurchaseInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> purchaseInvoiceVO = purchaseReturnService
					.createUpdatePurchaseInvoice(purchaseInvoiceDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseInvoiceVO.get("message"));
			responseObjectsMap.put("purchaseInvoiceVO", purchaseInvoiceVO.get("purchaseInvoiceVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseInvoiceDocId")
	public ResponseEntity<ResponseDTO> getPurchaseInvoiceDocId(@RequestParam Long orgId) {

		String methodName = "getPurchaseInvoiceDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseReturnService.getPurchaseInvoiceDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseInvoiceDocId information retrieved successfully");
			responseObjectsMap.put("purchaseInvoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseInvoiceDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderPoNumber")
	public ResponseEntity<ResponseDTO> getPurchaseOrderPoNumber(@RequestParam Long orgId,
			@RequestParam String supplierName) {
		String methodName = "getPurchaseOrderPoNumber()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseReturnService.getPurchaseOrderPoNumber(orgId, supplierName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PoNo Details retrieved successfully");
			responseObjectsMap.put("purchaseOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PoNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnNoAndGrnDateFromGrnDetails")
	public ResponseEntity<ResponseDTO> getGrnNoAndGrnDateFromGrnDetails(@RequestParam Long orgId,
			@RequestParam String poNo, @RequestParam String grnNo) {
		String methodName = "getGrnNoAndGrnDateFromGrnDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseReturnService.getGrnNoAndGrnDateFromGrnDetails(orgId, poNo, grnNo);
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

	@GetMapping("/getItemCodeAndItemDescFromGrn")
	public ResponseEntity<ResponseDTO> getItemCodeAndItemDescFromGrn(@RequestParam Long orgId,
			@RequestParam String grnNo) {
		String methodName = "getItemCodeAndItemDescFromGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseReturnService.getItemCodeAndItemDescFromGrn(orgId, grnNo);
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

}
