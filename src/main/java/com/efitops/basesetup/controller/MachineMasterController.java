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
import com.efitops.basesetup.dto.DrawingMasterDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.StockLocationDTO;
import com.efitops.basesetup.entity.DrawingMaster1VO;
import com.efitops.basesetup.entity.DrawingMaster2VO;
import com.efitops.basesetup.entity.DrawingMasterVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineMasterVO3;
import com.efitops.basesetup.entity.StockLocationVO;
import com.efitops.basesetup.service.MachineMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/machinemaster")
public class MachineMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(MachineMasterController.class);

	@Autowired
	MachineMasterService machineMasterService;
	
	
	@PutMapping("/updateCreateMachineMaster")
	public ResponseEntity<ResponseDTO> updateCreateCostDebitNote(@Valid @RequestBody MachineMasterDTO machineMasterDTO) {
		String methodName = "updateCreateMachineMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> machineMaster = machineMasterService.updateCreateMachineMaster(machineMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, machineMaster.get("message"));
			responseObjectsMap.put("machineMasterVO", machineMaster.get("machineMasterVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getAllMachineMasterByOrgId")
	public ResponseEntity<ResponseDTO> getAllCostInvoiceByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllMachineMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MachineMasterVO> machineMasterVOs = new ArrayList<>();
		try {
			machineMasterVOs = machineMasterService.getAllMachineMasterByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MachineMaster information get successfully By OrgId");
			responseObjectsMap.put("machineMasterVO", machineMasterVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MachineMaster information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllMachineMasterById")
	public ResponseEntity<ResponseDTO> getAllMachineMasterById(@RequestParam(required = false) Long id) {
		String methodName = "getAllMachineMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		//List<MachineMasterVO> machineMasterVOs = new ArrayList<>();
		Optional<MachineMasterVO> machineMasterVOs=null;
		try {
			machineMasterVOs = machineMasterService.getAllMachineMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MachineMaster information get successfully By Id");
			responseObjectsMap.put("machineMasterVO", machineMasterVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MachineMaster information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PostMapping("/uploadMachineAttachementsInBloob")
	public ResponseEntity<ResponseDTO> uploadMachineAttachementsInBloob1(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadMachineAttachementsInBloob1()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		MachineMasterVO3 machineMasterVO3 = null;
		try {
			machineMasterVO3 = machineMasterService.uploadMachineAttachementsInBloob(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload attachements", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Attachments Successfully Upload");
			responseObjectsMap.put("machineMasterVO3", machineMasterVO3);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Attachments Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	//STOCKLOCATION
	
	@PutMapping("/updateCreateStockLocation")
	public ResponseEntity<ResponseDTO> updateCreateStockLocation(@Valid @RequestBody StockLocationDTO stockLocationDTO) {
		String methodName = "updateCreateStockLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> stockLocationVO = machineMasterService.updateCreateStockLocation(stockLocationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, stockLocationVO.get("message"));
			responseObjectsMap.put("stockLocationVO", stockLocationVO.get("stockLocationVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getAllStockLocationByOrgId")
	public ResponseEntity<ResponseDTO> getAllStockLocationByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllStockLocationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StockLocationVO> stockLocationVOs = new ArrayList<>();
		try {
			stockLocationVOs = machineMasterService.getAllStockLocationByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock Location information get successfully By OrgId");
			responseObjectsMap.put("stockLocationVO", stockLocationVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Stock Location information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllStockLocationById")
	public ResponseEntity<ResponseDTO> getAllStockLocationById(@RequestParam(required = false) Long id) {
		String methodName = "getAllMachineMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		//List<MachineMasterVO> machineMasterVOs = new ArrayList<>();
		Optional<StockLocationVO> stockLocationVO=null;
		try {
			stockLocationVO = machineMasterService.getAllStockLocationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock Location information get successfully By Id");
			responseObjectsMap.put("stockLocationVO", stockLocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Stock Location information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
     //DRAWING MASTER
	
	@PutMapping("/updateDrawingMaster")
	public ResponseEntity<ResponseDTO> updateDrawingMaster(@Valid @RequestBody DrawingMasterDTO drawingMasterDTO) {
		String methodName = "updateDrawingMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> drawingMasterVO = machineMasterService.updateDrawingMaster(drawingMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, drawingMasterVO.get("message"));
			responseObjectsMap.put("drawingMasterVO", drawingMasterVO.get("drawingMasterVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getAllDrawingMasterByOrgId")
	public ResponseEntity<ResponseDTO> getAllDrawingMasterByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllDrawingMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DrawingMasterVO> drawingMasterVOs = new ArrayList<>();
		try {
			drawingMasterVOs = machineMasterService.getAllDrawingMasterByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Drawing Master information get successfully By OrgId");
			responseObjectsMap.put("drawingMasterVO", drawingMasterVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Drawing Master information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllDrawingMasterById")
	public ResponseEntity<ResponseDTO> getAllDrawingMasterById(@RequestParam(required = false) Long id) {
		String methodName = "getAllDrawingMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<DrawingMasterVO> drawingMasterVO=null;
		try {
			drawingMasterVO = machineMasterService.getAllDrawingMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Drawing Master information get successfully By Id");
			responseObjectsMap.put("drawingMasterVO", drawingMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Drawing Master information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PostMapping("/uploadAttachementsInBloob")
	public ResponseEntity<ResponseDTO> uploadAttachementsInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadAttachementsInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DrawingMaster1VO drawingMaster1VO = null;
		try {
			drawingMaster1VO = machineMasterService.uploadAttachementsInBloob(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload attachements", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Attachments Successfully Upload");
			responseObjectsMap.put("drawingMaster1VO", drawingMaster1VO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Attachments Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/uploadAttachementsInBloob1")
	public ResponseEntity<ResponseDTO> uploadAttachementsInBloob1(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadAttachementsInBloob1()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DrawingMaster2VO drawingMaster2VO = null;
		try {
			drawingMaster2VO = machineMasterService.uploadAttachementsInBloob1(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload attachements", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Attachments Successfully Upload");
			responseObjectsMap.put("drawingMaster2VO", drawingMaster2VO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Attachments Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}

