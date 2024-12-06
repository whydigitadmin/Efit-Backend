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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.QuotationDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.WorkOrderDTO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.QuotationVO;
import com.efitops.basesetup.entity.WorkOrderVO;
import com.efitops.basesetup.service.CustomerEnquiryService;

@CrossOrigin
@RestController
@RequestMapping("/api/customerenquiry")
public class CustomerEnquiryController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(CustomerEnquiryController.class);

	@Autowired
	CustomerEnquiryService customerEnquiryService;

	// Enquiry

	@GetMapping("/getAllEnquiryByOrgId")
	public ResponseEntity<ResponseDTO> getAllEnquiryByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllEnquiryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EnquiryVO> enquiryVO = new ArrayList<>();
		try {
			enquiryVO = customerEnquiryService.getAllEnquiryByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Enquiry information get successfully ByOrgId");
			responseObjectsMap.put("enquiryVO", enquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Enquiry information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getEnquiryById")
	public ResponseEntity<ResponseDTO> getEnquiryById(@RequestParam Long id) {
		String methodName = "getEnquiryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EnquiryVO> enquiryVO = new ArrayList<>();
		try {
			enquiryVO = customerEnquiryService.getEnquiryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Enquiry information get successfully By Id");
			responseObjectsMap.put("enquiryVO", enquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Enquiry information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateEnquiry")
	public ResponseEntity<ResponseDTO> createUpdateEnquiry(@Valid @RequestBody EnquiryDTO enquiryDTO) {
		String methodName = "createUpdateEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> enquiryVO = customerEnquiryService.createUpdateEnquiry(enquiryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, enquiryVO.get("message"));
			responseObjectsMap.put("enquiryVO", enquiryVO.get("enquiryVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEnquiryDocId")
	public ResponseEntity<ResponseDTO> getEnquiryDocId(@RequestParam Long orgId) {

		String methodName = "getEnquiryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = customerEnquiryService.getEnquiryDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EnquiryDocId information retrieved successfully");
			responseObjectsMap.put("enquiryDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve EnquiryDocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCustomerNameAndCode")
	public ResponseEntity<ResponseDTO> getCustomerNameAndCode(@RequestParam Long orgId) {
		String methodName = "getCustomerNameAndCode()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = customerEnquiryService.getCustomerNameAndCode(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer Details retrieved successfully");
			responseObjectsMap.put("partymasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Customer Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getContactNameAndNo")
	public ResponseEntity<ResponseDTO> getContactNameAndNo(@RequestParam Long orgId, @RequestParam String partyCode) {
		String methodName = "getContactNameAndNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = customerEnquiryService.getContactNameAndNo(orgId, partyCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Contact Details retrieved successfully");
			responseObjectsMap.put("partymasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Contact Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoAndDescription")
	public ResponseEntity<ResponseDTO> getPartNoAndDescription(@RequestParam Long orgId) {
		String methodName = "getPartNoAndDescription()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = customerEnquiryService.getPartNoAndDescription(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details retrieved successfully");
			responseObjectsMap.put("itemVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDrawingNoAndRevNo")
	public ResponseEntity<ResponseDTO> getDrawingNoAndRevNo(@RequestParam Long orgId, @RequestParam String partNo) {
		String methodName = "getDrawingNoAndRevNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = customerEnquiryService.getDrawingNoAndRevNo(orgId, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DrawingNo Details retrieved successfully");
			responseObjectsMap.put("drawingVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DrawingNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Quotation

	@GetMapping("/getAllQuotationByOrgId")
	public ResponseEntity<ResponseDTO> getAllQuotationByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllQuotationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<QuotationVO> quotationVO = new ArrayList<>();
		try {
			quotationVO = customerEnquiryService.getAllQuotationByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quotation information get successfully ByOrgId");
			responseObjectsMap.put("quotationVO", quotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Quotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getQuotationById")
	public ResponseEntity<ResponseDTO> getQuotationById(@RequestParam Long id) {
		String methodName = "getQuotationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<QuotationVO> quotationVO = new ArrayList<>();
		try {
			quotationVO = customerEnquiryService.getQuotationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quotation information get successfully By Id");
			responseObjectsMap.put("quotationVO", quotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Quotation information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateQuotation")
	public ResponseEntity<ResponseDTO> createUpdateQuotation(@Valid @RequestBody QuotationDTO quotationDTO) {
		String methodName = "createUpdateQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> quotationVO = customerEnquiryService.createUpdateQuotation(quotationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, quotationVO.get("message"));
			responseObjectsMap.put("quotationVO", quotationVO.get("quotationVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getQuotationDocId")
	public ResponseEntity<ResponseDTO> getQuotationDocId(@RequestParam Long orgId) {

		String methodName = "getEnquiryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = customerEnquiryService.getQuotationDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "QuotationDocId information retrieved successfully");
			responseObjectsMap.put("quotationDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve QuotationDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getEnquiryNoAndDate")
	public ResponseEntity<ResponseDTO> getEnquiryNoAndDate(@RequestParam Long orgId,@RequestParam String customer) {
		String methodName = "getEnquiryNoAndDate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = customerEnquiryService.getEnquiryNoAndDate(orgId,customer);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EnquiryNo Details retrieved successfully");
			responseObjectsMap.put("enquiryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "EnquiryNo to retrieve Customer Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getProductionManager")
	public ResponseEntity<ResponseDTO> getProductionManager(@RequestParam Long orgId) {
		String methodName = "getProductionManager()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = customerEnquiryService.getProductionManager(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProductionManager Details retrieved successfully");
			responseObjectsMap.put("employeeVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ProductionManager Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getPartNoAndPartDesBasedOnEnquiryNo")
	public ResponseEntity<ResponseDTO> getPartNoAndPartDesBasedOnEnquiryNo(@RequestParam Long orgId,@RequestParam String docId,@RequestParam String customer) {
		String methodName = "getPartNoAndPartDesBasedOnEnquiryNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = customerEnquiryService.getPartNoAndPartDesBasedOnEnquiryNo(orgId,docId,customer);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details retrieved successfully");
			responseObjectsMap.put("enquiryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// WorkOrder

	@GetMapping("/getAllWorkOrderByOrgId")
	public ResponseEntity<ResponseDTO> getAllWorkOrderByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllWorkOrderByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WorkOrderVO> workOrderVO = new ArrayList<>();
		try {
			workOrderVO = customerEnquiryService.getAllWorkOrderByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOrder information get successfully ByOrgId");
			responseObjectsMap.put("workOrderVO", workOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "WorkOrder information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getWorkOrderById")
	public ResponseEntity<ResponseDTO> getWorkOrderById(@RequestParam Long id) {
		String methodName = "getWorkOrderById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WorkOrderVO> workOrderVO = new ArrayList<>();
		try {
			workOrderVO = customerEnquiryService.getWorkOrderById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOder information get successfully By Id");
			responseObjectsMap.put("workOrderVO", workOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "WorkOder information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateWorkOrder")
	public ResponseEntity<ResponseDTO> createUpdateWorkOrder(@Valid @RequestBody WorkOrderDTO workOrderDTO) {
		String methodName = "createUpdateWorkOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> workOrderVO = customerEnquiryService.createUpdateWorkOrder(workOrderDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, workOrderVO.get("message"));
			responseObjectsMap.put("workOrderVO", workOrderVO.get("workOrderVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWorkOrderDocId")
	public ResponseEntity<ResponseDTO> getWorkOrderDocId(@RequestParam Long orgId) {

		String methodName = "getWorkOrderDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = customerEnquiryService.getWorkOrderDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOrderDocId information retrieved successfully");
			responseObjectsMap.put("workOrderDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve WorkOrderDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	
	@GetMapping("/getQuotationNumber")
	public ResponseEntity<ResponseDTO> getQuotationNumber(@RequestParam Long orgId,@RequestParam String customerName) {
		String methodName = "getQuotationNumber()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = customerEnquiryService.getQuotationNumber(orgId,customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "QuotationNo Details retrieved successfully");
			responseObjectsMap.put("quotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve QuotationNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	

	@GetMapping("/getWorkOrderPartNo")
	public ResponseEntity<ResponseDTO> getWorkOrderPartNo(@RequestParam Long orgId,@RequestParam String docId,@RequestParam String custmoerName) {
		String methodName = "getWorkOrderPartNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = customerEnquiryService.getWorkOrderPartNo(orgId,docId,custmoerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details retrieved successfully");
			responseObjectsMap.put("quotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
}
}

