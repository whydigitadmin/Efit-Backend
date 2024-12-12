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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDTO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;
import com.efitops.basesetup.service.GrnService;

@CrossOrigin
@RestController
@RequestMapping("/api/grn")
public class GrnController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(GrnController.class);

	@Autowired
	GrnService grnService;

	@GetMapping("/getGrnByOrgId")
	public ResponseEntity<ResponseDTO> getGrnByOrgId(@RequestParam Long orgId) {
		String methodName = "getGrnByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GrnVO> grnVO = new ArrayList<>();
		try {
			grnVO = grnService.getGrnByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn information get successfully ByOrgId");
			responseObjectsMap.put("grnVO", grnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGrnById")
	public ResponseEntity<ResponseDTO> getItemById(@RequestParam Long id) {
		String methodName = "getGrnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GrnVO> grnVO = new ArrayList<>();
		try {
			grnVO = grnService.getGrnById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information get successfully By Id");
			responseObjectsMap.put("grnVO", grnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Item information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateGrn")
	public ResponseEntity<ResponseDTO> updateCreateGrn(@RequestBody GrnDTO grnDTO) {
		String methodName = "updateCreateGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> grnVO = grnService.updateCreateGrn(grnDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, grnVO.get("message"));
			responseObjectsMap.put("grnVO", grnVO.get("grnVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnDocId")
	public ResponseEntity<ResponseDTO> getGrnDocId(@RequestParam Long orgId) {

		String methodName = "getGrnDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = grnService.getGrnDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnDocId information retrieved successfully");
			responseObjectsMap.put("grnDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnDocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInwardNoForGRN")
	public ResponseEntity<ResponseDTO> getInwardNoForGRN(@RequestParam(required = false) Long orgId) {
		String methodName = "getInwardNoForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> inwardforgrn = new ArrayList<>();
		try {
			inwardforgrn = grnService.getInwardNoForGRN(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Inward for Grn information get successfully By OrgId");
			responseObjectsMap.put("inwardforgrn", inwardforgrn);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Inward for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSupplierAddressForGRN")
	public ResponseEntity<ResponseDTO> getSupplierAddressForGRN(@RequestParam(required = false) Long orgId,
			String supplierName) {
		String methodName = "getSupplierAddressForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> supplieraddressforgrn = new ArrayList<>();
		try {
			supplieraddressforgrn = grnService.getSupplierAddressForGRN(orgId, supplierName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" supplieraddress for Grn information get successfully By OrgId");
			responseObjectsMap.put("supplieraddressforgrn", supplieraddressforgrn);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"supplieraddress For Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSGSTandCGSTForGRN")
	public ResponseEntity<ResponseDTO> getSGSTandCGSTForGRN(@RequestParam(required = false) Long orgId, String taxType,
			String gstType) {
		String methodName = "getSGSTandCGSTForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> cgstandsgst = new ArrayList<>();
		try {
			cgstandsgst = grnService.getSGSTandCGSTForGRN(orgId, taxType, gstType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" CGST and SGST for Grn information get successfully By OrgId");
			responseObjectsMap.put("cgstandsgst", cgstandsgst);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CGST and SGST for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIGSTForGRN")
	public ResponseEntity<ResponseDTO> getIGSTForGRN(@RequestParam(required = false) Long orgId, String taxType,
			String gstType) {
		String methodName = "getIGSTForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> igst = new ArrayList<>();
		try {
			igst = grnService.getIGSTForGRN(orgId, taxType, gstType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" IGST for Grn information get successfully By OrgId");
			responseObjectsMap.put("igst", igst);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IGST for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getItemForGRN")
	public ResponseEntity<ResponseDTO> getItemForGRN(@RequestParam(required = false) Long orgId, String inwardNo) {
		String methodName = "getItemForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemforgrn = new ArrayList<>();
		try {
			itemforgrn = grnService.getItemForGRN(orgId, inwardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" itemforgrn for Grn information get successfully By OrgId");
			responseObjectsMap.put("itemforgrn", itemforgrn);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"itemforgrn for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getThirdPartyInspectionByOrgId")
	public ResponseEntity<ResponseDTO> getThirdPartyInspectionByOrgId(@RequestParam Long orgId) {
		String methodName = "getThirdPartyInspectionByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
		try {
			thirdPartyInspectionVO = grnService.getThirdPartyInspByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Third Party information get successfully ByOrgId");
			responseObjectsMap.put("thirdPartyInspectionVO", thirdPartyInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Third Party information information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGRNForThirdPartyInsp")
	public ResponseEntity<ResponseDTO> getGRNForThirdPartyInsp(@RequestParam(required = false) Long orgId) {
		String methodName = "getGRNForThirdPartyInsp()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chCode = new ArrayList<>();
		try {
			chCode = grnService.getGRNForThirdPartyInsp(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" GRNForThirdPartyInsp for Grn information get successfully By OrgId");
			responseObjectsMap.put("chCode", chCode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"GRNForThirdPartyInsp for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getThirdPartyDetailsForThirdPartyInsp")
	public ResponseEntity<ResponseDTO> getThirdPartyDetailsForThirdPartyInsp(@RequestParam(required = false) Long orgId) {
		String methodName = "getThirdPartyDetailsForThirdPartyInsp()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chCode = new ArrayList<>();
		try {
			chCode = grnService.getThirdPartyDetailsForThirdPartyInsp(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ThirdPartyDetails for Grn information get successfully By OrgId");
			responseObjectsMap.put("chCode", chCode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ThirdPartyDetails for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getThirdPartyInspectionById")
	public ResponseEntity<ResponseDTO> getThirdPartyInspectionById(@RequestParam Long id) {
		String methodName = "getThirdPartyInspectionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
		try {
			thirdPartyInspectionVO = grnService.getThirdPartyInspById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ThirdPartyInspection information get successfully By Id");
			responseObjectsMap.put("thirdPartyInspectionVO", thirdPartyInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ThirdPartyInspection information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateThirdPartyInsp")
	public ResponseEntity<ResponseDTO> updateCreateThirdPartyInsp(
			@RequestBody ThirdPartyInspectionDTO thirdPartyInspectionDTO) {
		String methodName = "updateCreateThirdPartyInsp()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> thirdPartyInspectionVO = grnService.updateCreateThirdPartyInsp(thirdPartyInspectionDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, thirdPartyInspectionVO.get("message"));
			responseObjectsMap.put("thirdPartyInspectionVO", thirdPartyInspectionVO.get("thirdPartyInspectionVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getThirdPartyInspectionDocId")
	public ResponseEntity<ResponseDTO> getThirdPartyInspectionDocId(@RequestParam Long orgId) {

		String methodName = "getThirdPartyInspectionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = grnService.getThirdPartyInspectionDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ThirdPartyInspectionDocId information retrieved successfully");
			responseObjectsMap.put("thirdPartyInspectionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ThirdPartyInspectionDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadFileForThirdPartyInspection")
	public ResponseEntity<ResponseDTO> uploadFileForThirdPartyInspection(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadFileForThirdPartyInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ThirdPartyInspectionVO thirdPartyInspectionVO = null;
		try {
			thirdPartyInspectionVO = grnService.uploadFileForThirdPartyInspection(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload File", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FileUpload for RouteCardEntry Successfully Upload");
			responseObjectsMap.put("thirdPartyInspectionVO", thirdPartyInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "FileUpload for RouteCardEntry Upload Failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
