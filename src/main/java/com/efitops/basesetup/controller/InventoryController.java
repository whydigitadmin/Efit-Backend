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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ItemIssueToProductionDTO;
import com.efitops.basesetup.dto.PickListDTO;
import com.efitops.basesetup.dto.PutawayDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.RouteCardEntryDTO;
import com.efitops.basesetup.entity.DrawingMaster1VO;
import com.efitops.basesetup.entity.ItemIssueToProductionVO;
import com.efitops.basesetup.entity.PickListVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.service.InventoryService;

@CrossOrigin
@RestController
@RequestMapping("/api/inventory")
public class InventoryController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	InventoryService inventoryService;
	
	@PutMapping("/updateCreatePutaway")
	public ResponseEntity<ResponseDTO> updateCreatePutaway(
			@Valid @RequestBody PutawayDTO putawayDTO) {
		String methodName = "updateCreatePutaway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> putawayVO = inventoryService.updateCreatePutaway(putawayDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, putawayVO.get("message"));
			responseObjectsMap.put("putawayVO", putawayVO.get("putawayVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getPutawayByOrgId")
	public ResponseEntity<ResponseDTO> getPutawayByOrgId(@RequestParam Long orgId) {
		String methodName = "getPutawayByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PutawayVO> putawayVO = new ArrayList<>();
		try {
			putawayVO = inventoryService.getPutawayByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Putaway information get successfully ByOrgId");
			responseObjectsMap.put("putawayVO", putawayVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Putaway information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getPutawayById")
	public ResponseEntity<ResponseDTO> getPutawayById(@RequestParam Long id) {
		String methodName = "getPutawayById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PutawayVO> putawayVO = new ArrayList<>();
		try {
			putawayVO = inventoryService.getPutawayById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Putaway information get successfully By Id");
			responseObjectsMap.put("putawayVO", putawayVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Putaway information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getPutawayDocId")
	public ResponseEntity<ResponseDTO> getPutawayDocId(@RequestParam Long orgId) {

		String methodName = "getPutawayDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = inventoryService.getPutawayDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Putaway DocId information retrieved successfully");
			responseObjectsMap.put("putawayDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Putaway DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	
	@GetMapping("/getGrnDetailsForPutaway")
	public ResponseEntity<ResponseDTO> getGrnDetailsForPutaway(
			@RequestParam Long orgId) {

		String methodName = "getGrnDetailsForPutaway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = inventoryService.getGrnDetailsForPutaway(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" GrnDetails for Putaway information retrieved successfully");
			responseObjectsMap.put("PutawayVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  GrnDetails for Putaway information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getLocationCodeForPutaway")
	public ResponseEntity<ResponseDTO> getLocationCodeForPutaway(
			@RequestParam Long orgId) {

		String methodName = "getLocationCodeForPutaway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = inventoryService.getLocationCodeForPutaway(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" LocationCode for Putaway information retrieved successfully");
			responseObjectsMap.put("putawayVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  LocationCode for Putaway information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getRackNoForPutaway")
	public ResponseEntity<ResponseDTO> getRackNoForPutaway(
			@RequestParam Long orgId) {

		String methodName = "getRackNoForPutaway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = inventoryService.getRackNoForPutaway(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" RackNo for Putaway information retrieved successfully");
			responseObjectsMap.put("putawayVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  RackNo for Putaway information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getFillGridForPutaway")
	public ResponseEntity<ResponseDTO> getFillGridForPutaway(
			@RequestParam Long orgId, @RequestParam String grnNo) {

		String methodName = "getFillGridForPutaway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = inventoryService.getFillGridForPutaway(orgId,grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" FillGrid for Putaway information retrieved successfully");
			responseObjectsMap.put("putawayVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  FillGrid for Putaway information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//RouteCardEntry
	@PutMapping("/updateCreateRouteCardEntry")
	public ResponseEntity<ResponseDTO> updateCreateRouteCardEntry(
			@Valid @RequestBody RouteCardEntryDTO routeCardEntryDTO) {
		String methodName = "updateCreateRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> routeCardEntryVO = inventoryService.updateCreateRouteCardEntry(routeCardEntryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, routeCardEntryVO.get("message"));
			responseObjectsMap.put("routeCardEntryVO", routeCardEntryVO.get("routeCardEntryVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getRouteCardEntryByOrgId")
	public ResponseEntity<ResponseDTO> getRouteCardEntryByOrgId(@RequestParam Long orgId) {
		String methodName = "getRouteCardEntryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RouteCardEntryVO> routeCardEntryVO = new ArrayList<>();
		try {
			routeCardEntryVO = inventoryService.getRouteCardEntryByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardEntry information get successfully ByOrgId");
			responseObjectsMap.put("routeCardEntryVO", routeCardEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "RouteCardEntry information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getRouteCardEntryById")
	public ResponseEntity<ResponseDTO> getRouteCardEntryById(@RequestParam Long id) {
		String methodName = "getRouteCardEntryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RouteCardEntryVO> routeCardEntryVO = new ArrayList<>();
		try {
			routeCardEntryVO = inventoryService.getRouteCardEntryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardEntry information get successfully By Id");
			responseObjectsMap.put("routeCardEntryVO", routeCardEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "RouteCardEntry information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@GetMapping("/getRouteCardEntryDocId")
	public ResponseEntity<ResponseDTO> getRouteCardEntryDocId(@RequestParam Long orgId) {

		String methodName = "getRouteCardEntryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = inventoryService.getRouteCardEntryDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardEntry DocId information retrieved successfully");
			responseObjectsMap.put("RouteCardEntryDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve RouteCardEntry DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getCustomerNameAndCodeFromRouteCardEntry")
	public ResponseEntity<ResponseDTO> getCustomerNameAndCodeFromRouteCardEntry(@RequestParam Long orgId) {
		String methodName = "getCustomerNameAndCodeFromRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inventoryService.getCustomerNameAndCodeFromRouteCardEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer Details retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Customer Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWorkOrderNoFromRouteCardEntry")
	public ResponseEntity<ResponseDTO> getWorkOrderNoFromRouteCardEntry(@RequestParam Long orgId,@RequestParam String customerCode) {
		String methodName = "getWorkOrderNoFromRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inventoryService.getWorkOrderNoFromRouteCardEntry(orgId,customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOrderNo Detail from RouteCardEntry retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve WorkOrderNo Detail from RouteCardEntry",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getFgPartNameAndDescAndQtyFromRouteCardEntry")
	public ResponseEntity<ResponseDTO> getFgPartNameAndDescAndQtyFromRouteCardEntry(@RequestParam Long orgId,@RequestParam String workOrderNo) {
		String methodName = "getFgPartNameAndDescAndQtyFromRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inventoryService.getFgPartNameAndDescAndQtyFromRouteCardEntry(orgId,workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FgPartNameAndDescAndQty Detail from RouteCardEntry retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve FgPartNameAndDescAndQty Detail from RouteCardEntry",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getOptrSignFromRouteCardEntry")
	public ResponseEntity<ResponseDTO> getOptrSignFromRouteCardEntry(@RequestParam Long orgId) {
		String methodName = "getOptrSignFromRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inventoryService.getOptrSignFromRouteCardEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OPTRSign Detail from RouteCardEntry retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve OPTRSign Detail from RouteCardEntry",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getPreparedByFromRouteCardEntry")
	public ResponseEntity<ResponseDTO> getPreparedByFromRouteCardEntry(@RequestParam Long orgId) {
		String methodName = "getPreparedByFromRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inventoryService.getPreparedByFromRouteCardEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PreparedBy Detail from RouteCardEntry retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PreparedBy Detail from RouteCardEntry",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getApprovedByFromRouteCardEntry")
	public ResponseEntity<ResponseDTO> getApprovedByFromRouteCardEntry(@RequestParam Long orgId) {
		String methodName = "getApprovedByFromRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inventoryService.getApprovedByFromRouteCardEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ApprovedBy Detail from RouteCardEntry retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ApprovedBy Detail from RouteCardEntry",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getQAManagerSignFromRouteCardEntry")
	public ResponseEntity<ResponseDTO> getQAManagerSignFromRouteCardEntry(@RequestParam Long orgId) {
		String methodName = "getQAManagerSignFromRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inventoryService.getQAManagerSignFromRouteCardEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "QAManagerSign Detail from RouteCardEntry retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve QAManagerSign Detail from RouteCardEntry",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getPlantManagerSignFromRouteCardEntry")
	public ResponseEntity<ResponseDTO> getPlantManagerSignFromRouteCardEntry(@RequestParam Long orgId) {
		String methodName = "getPlantManagerSignFromRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inventoryService.getPlantManagerSignFromRouteCardEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PlantManagerSign Detail from RouteCardEntry retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PlantManagerSign Detail from RouteCardEntry",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@PostMapping("/uploadFileForRouteCardEntry")
	public ResponseEntity<ResponseDTO> uploadFileForRouteCardEntry(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadFileForRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		RouteCardEntryVO routeCardEntryVO = null;
		try {
			routeCardEntryVO = inventoryService.uploadFileForRouteCardEntry(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload File", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FileUpload for RouteCardEntry Successfully Upload");
			responseObjectsMap.put("routeCardEntryVO", routeCardEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "FileUpload for RouteCardEntry Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//PickList
	
	@PutMapping("/updateCreatePickList")
	public ResponseEntity<ResponseDTO> updateCreatePickList(
			@Valid @RequestBody PickListDTO pickListDTO) {
		String methodName = "updateCreatePickList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> pickListVO = inventoryService.updateCreatePickList(pickListDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, pickListVO.get("message"));
			responseObjectsMap.put("pickListVO", pickListVO.get("pickListVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getPickListByOrgId")
	public ResponseEntity<ResponseDTO> getPickListByOrgId(@RequestParam Long orgId) {
		String methodName = "getPickListByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PickListVO> pickListVO = new ArrayList<>();
		try {
			pickListVO = inventoryService.getPickListByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PickList information get successfully ByOrgId");
			responseObjectsMap.put("pickListVO", pickListVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PickList information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getPickListById")
	public ResponseEntity<ResponseDTO> getPickListById(@RequestParam Long id) {
		String methodName = "getPickListById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PickListVO> pickListVO = new ArrayList<>();
		try {
			pickListVO = inventoryService.getPickListById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PickList information get successfully By Id");
			responseObjectsMap.put("pickListVO", pickListVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PickList information receive failedBy Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPickListDocId")
	public ResponseEntity<ResponseDTO> getPickListDocId(@RequestParam Long orgId) {

		String methodName = "getPickListDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = inventoryService.getPickListDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PickList DocId information retrieved successfully");
			responseObjectsMap.put("PickListDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PickList DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getItemIssueToProductionDetailsfromPickList")
	public ResponseEntity<ResponseDTO> getItemIssueToProductionDetailsfromPickList(
			@RequestParam Long orgId , @RequestParam String itemIssueToProduction) {

		String methodName = "getItemIssueToProductionDetailsfromPickList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemIssueToProductionDtls = new ArrayList<>();
		try {
			itemIssueToProductionDtls = inventoryService.getItemIssueToProductionDetailsfromPickList(orgId,itemIssueToProduction);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ItemIssueToProductionDetails from PickList information retrieved successfully");
			responseObjectsMap.put("itemIssueToProductionVO", itemIssueToProductionDtls);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  ItemIssueToProductionDetails from PickList information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getItemIssueToProductionNofromPickList")
	public ResponseEntity<ResponseDTO> getItemIssueToProductionNofromPickList(
			@RequestParam Long orgId , @RequestParam String routeCardEntryNo) {

		String methodName = "getItemIssueToProductionNofromPickList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemIssueToProductionNo = new ArrayList<>();
		try {
			itemIssueToProductionNo = inventoryService.getItemIssueToProductionNofromPickList(orgId,routeCardEntryNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ItemIssueToProductionNo from PickList information retrieved successfully");
			responseObjectsMap.put("itemIssueToProductionNo", itemIssueToProductionNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  ItemIssueToProductionNo from PickList information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//ItemIssueToProduction
	
	@PutMapping("/updateCreateItemIssToProd")
	public ResponseEntity<ResponseDTO> updateCreateItemIssToProd(
			@Valid @RequestBody ItemIssueToProductionDTO itemIssueToProductionDTO) {
		String methodName = "updateCreateItemIssToProd()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> itemIssToProdVO = inventoryService.updateCreateItemIssToProd(itemIssueToProductionDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, itemIssToProdVO.get("message"));
			responseObjectsMap.put("itemIssToProdVO", itemIssToProdVO.get("itemIssToProdVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getItemIssToProdByOrgId")
	public ResponseEntity<ResponseDTO> getItemIssToProdByOrgId(@RequestParam Long orgId) {
		String methodName = "getItemIssToProdByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemIssueToProductionVO> itemIssueToProductionVO = new ArrayList<>();
		try {
			itemIssueToProductionVO = inventoryService.getItemIssToProdByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemIssueToProduction information get successfully ByOrgId");
			responseObjectsMap.put("itemIssueToProductionVO", itemIssueToProductionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ItemIssueToProduction information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@GetMapping("/getItemIssToProdById")
	public ResponseEntity<ResponseDTO> getItemIssToProdById(@RequestParam Long id) {
		String methodName = "getItemIssToProdById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemIssueToProductionVO> itemIssueToProductionVO = new ArrayList<>();
		try {
			itemIssueToProductionVO = inventoryService.getItemIssToProdById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemIssueToProduction information get successfully By Id");
			responseObjectsMap.put("itemIssueToProductionVO", itemIssueToProductionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ItemIssueToProduction information receive failedBy Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@GetMapping("/getItemIssueToProductionDocId")
	public ResponseEntity<ResponseDTO> getItemIssueToProductionDocId(@RequestParam Long orgId) {

		String methodName = "getItemIssueToProductionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = inventoryService.getItemIssueToProductionDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemIssueToProduction DocId information retrieved successfully");
			responseObjectsMap.put("ItemIssueToProductionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ItemIssueToProduction DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getRouteCardEntryNoForItemIssueToProduction")
	public ResponseEntity<ResponseDTO> getRouteCardEntryNoForItemIssueToProduction(
			@RequestParam Long orgId,@RequestParam String customerCode) {

		String methodName = "getRouteCardEntryNoForItemIssueToProduction()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemIssueToProduction = new ArrayList<>();
		try {
			itemIssueToProduction = inventoryService.getRouteCardEntryNoForItemIssueToProduction(orgId,customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" RouteCardEntryNo For ItemIssueToProduction information retrieved successfully");
			responseObjectsMap.put("itemIssueToProductionVO", itemIssueToProduction);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  RouteCardEntryNo For ItemIssueToProduction information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getRouteCardEntryDetailsForItemIssueToProduction")
	public ResponseEntity<ResponseDTO> getRouteCardEntryDetailsForItemIssueToProduction(
			@RequestParam Long orgId , @RequestParam String routeCardNo) {

		String methodName = "getRouteCardEntryDetailsForItemIssueToProduction()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemIssueToProduction = new ArrayList<>();
		try {
			itemIssueToProduction = inventoryService.getRouteCardEntryDetailsForItemIssueToProduction(orgId,routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" RouteCardEntryDetails For ItemIssueToProduction information retrieved successfully");
			responseObjectsMap.put("itemIssueToProductionVO", itemIssueToProduction);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  RouteCardEntryDetails For ItemIssueToProduction information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getItemIssueToProductionDetailsfromBom")
	public ResponseEntity<ResponseDTO> getItemIssueToProductionDetailsfromBom(
			@RequestParam Long orgId , @RequestParam String fgItemId) {

		String methodName = "getItemIssueToProductionDetailsfromBom()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemIssueToProduction = new ArrayList<>();
		try {
			itemIssueToProduction = inventoryService.getItemIssueToProductionDetailsfromBom(orgId,fgItemId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ItemIssueToProductionDetails from Bom information retrieved successfully");
			responseObjectsMap.put("itemIssueToProductionVO", itemIssueToProduction);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  ItemIssueToProductionDetails from Bom information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
}
