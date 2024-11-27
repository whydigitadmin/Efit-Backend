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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ItemDTO;
import com.efitops.basesetup.dto.MeasuringInstrumentsDTO;
import com.efitops.basesetup.dto.ItemWiseProcessMasterDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.ItemVO;
import com.efitops.basesetup.entity.MeasuringInstrumentsVO;
import com.efitops.basesetup.entity.ItemWiseProcessMasterVO;
import com.efitops.basesetup.service.ItemService;

@CrossOrigin
@RestController
@RequestMapping("/api/item")
public class ItemController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(IrnCreditNoteController.class);

	@Autowired
	ItemService itemService;
	
	@GetMapping("/getItemByOrgId")
	public ResponseEntity<ResponseDTO> getItemByOrgId(@RequestParam Long orgId) {
		String methodName = "getItemByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemVO> itemVO = new ArrayList<>();
		try {
			itemVO = itemService.getItemByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information get successfully ByOrgId");
			responseObjectsMap.put("itemVO", itemVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Item information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getItemById")
	public ResponseEntity<ResponseDTO> getItemById(@RequestParam Long id) {
		String methodName = "getItemById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemVO> itemVO = new ArrayList<>();
		try {
			itemVO = itemService.getItemById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information get successfully By Id");
			responseObjectsMap.put("itemVO", itemVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Item information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@PutMapping("/updateCreateItemMaster")
	public ResponseEntity<ResponseDTO> updateCreateItemMaster(@RequestBody ItemDTO itemDTO) {
		String methodName = "updateCreateItemMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> itemVO = itemService.updateCreateItemMaster(itemDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, itemVO.get("message"));
			responseObjectsMap.put("itemVO", itemVO.get("itemVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	//MeasuringInstrument
	
	@GetMapping("/getMeasuringInstrumentByOrgId")
	public ResponseEntity<ResponseDTO> getMeasuringInstrumentByOrgId(@RequestParam Long orgId) {
		String methodName = "getMeasuringInstrumentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MeasuringInstrumentsVO> measuringInstrumentsVO = new ArrayList<>();
		try {
			measuringInstrumentsVO = itemService.getMeasuringInstrumentByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MeasuringInstruments information get successfully ByOrgId");
			responseObjectsMap.put("measuringInstrumentsVO", measuringInstrumentsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "MeasuringInstruments information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getMeasuringInstrumentById")
	public ResponseEntity<ResponseDTO> getMeasuringInstrumentById(@RequestParam Long id) {
		String methodName = "getMeasuringInstrumentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MeasuringInstrumentsVO> measuringInstrumentsVO = new ArrayList<>();
		try {
			measuringInstrumentsVO = itemService.getMeasuringInstrumentById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MeasuringInstruments information get successfully By Id");
			responseObjectsMap.put("measuringInstrumentsVO", measuringInstrumentsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "MeasuringInstruments information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	
	@PutMapping("/updateCreateMeasuringInstruments")
	public ResponseEntity<ResponseDTO> updateCreateMeasuringInstruments(@RequestBody MeasuringInstrumentsDTO measuringInstrumentsDTO) {
		String methodName = "updateCreateMeasuringInstruments()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> measuringInstrumentsVO = itemService.updateCreateMeasuringInstruments(measuringInstrumentsDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, measuringInstrumentsVO.get("message"));
			responseObjectsMap.put("measuringInstrumentsVO", measuringInstrumentsVO.get("measuringInstrumentsVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@GetMapping("/getMeasuringInstrumentsDocId")
	public ResponseEntity<ResponseDTO> getMeasuringInstrumentsDocId(@RequestParam Long orgId) {

		String methodName = "getMeasuringInstrumentsDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = itemService.getMeasuringInstrumentsDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MeasuringInstruments DocId information retrieved successfully");
			responseObjectsMap.put("MeasuringInstruments DocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve MeasuringInstruments DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@GetMapping("/getInstrumentNameFromItemMaster")
	public ResponseEntity<ResponseDTO> getInstrumentNameFromItemMaster(
			@RequestParam Long orgId) {

		String methodName = "getInstrumentNameFromItemMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = itemService.getInstrumentNameFromItemMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" InstrumentName from ItemMaster information retrieved successfully");
			responseObjectsMap.put("measuringInstrumentVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve InstrumentName from ItemMaster information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	//ProcessMaster
	
	@GetMapping("/getItemWiseProcessMasterByOrgId")
	public ResponseEntity<ResponseDTO> getItemWiseProcessMasterByOrgId(@RequestParam Long orgId) {
		String methodName = "getItemWiseProcessMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemWiseProcessMasterVO> itemWiseProcessMasterVO = new ArrayList<>();
		try {
			itemWiseProcessMasterVO = itemService.getItemWiseProcessMasterByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemWiseProcessMaster information get successfully ByOrgId");
			responseObjectsMap.put("ItemWiseProcessMasterVO", itemWiseProcessMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ItemWiseProcessMaster information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getItemWiseProcessMasterById")
	public ResponseEntity<ResponseDTO> getItemWiseProcessMasterById(@RequestParam Long id) {
		String methodName = "getItemWiseProcessMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemWiseProcessMasterVO> itemWiseProcessMasterVO = new ArrayList<>();
		try {
			itemWiseProcessMasterVO = itemService.getItemWiseProcessMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemWiseProcessMaster information get successfully By Id");
			responseObjectsMap.put("ItemWiseProcessMasterVO", itemWiseProcessMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ItemWiseProcessMaster information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PutMapping("/updateCreateItemWiseProcessMaster")
	public ResponseEntity<ResponseDTO> updateCreateItemWiseProcessMaster(@RequestBody ItemWiseProcessMasterDTO itemWiseProcessMasterDTO) {
		String methodName = "updateCreateItemWiseProcessMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> itemWiseProcessMasterVO = itemService.updateCreateItemWiseProcessMaster(itemWiseProcessMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, itemWiseProcessMasterVO.get("message"));
			responseObjectsMap.put("itemWiseProcessMasterVO", itemWiseProcessMasterVO.get("itemWiseProcessMasterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getItemAndItemDescforItemWiseProcess")
	public ResponseEntity<ResponseDTO> getItemAndItemDescforItemWiseProcess(
			@RequestParam Long orgId) {

		String methodName = "getItemAndItemDescforItemWiseProcess()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = itemService.getItemAndItemDescforItemWiseProcess(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Item and ItemDesc for ItemWiseProcess information retrieved successfully");
			responseObjectsMap.put("itemWiseProcessVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Item and ItemDesc  for ItemWiseProcess  information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}


	
}
