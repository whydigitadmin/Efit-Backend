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
import com.efitops.basesetup.dto.DcForSubContractDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.dto.JobWorkOutDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDTO;
import com.efitops.basesetup.dto.SubContractInvoiceDTO;
import com.efitops.basesetup.dto.SubContractQuotationDTO;
import com.efitops.basesetup.entity.DcForSubContractVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.entity.JobWorkOutVO;
import com.efitops.basesetup.entity.SubContractEnquiryVO;
import com.efitops.basesetup.entity.SubContractInvoiceVO;
import com.efitops.basesetup.entity.SubContractQuotationVO;
import com.efitops.basesetup.service.IssueToSubContractorService;

@CrossOrigin
@RestController
@RequestMapping("/api/issuetosubcontractor")
public class IssueToSubContractorController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(IssueToSubContractorController.class);

	@Autowired
	IssueToSubContractorService issueToSubContractorService;

	// IssueToSubContractor

	@GetMapping("/getAllIssueToSubContractorByOrgId")
	public ResponseEntity<ResponseDTO> getAllIssueToSubContractorByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllIssueToSubContractorByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IssueToSubContractorVO> issueToSubContractorVO = new ArrayList<>();
		try {
			issueToSubContractorVO = issueToSubContractorService.getAllIssueToSubContractorByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IssueToSubContractor information get successfully ByOrgId");
			responseObjectsMap.put("issueToSubContractorVO", issueToSubContractorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IssueToSubContractor information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIssueToSubContractorById")
	public ResponseEntity<ResponseDTO> getIssueToSubContractorById(@RequestParam Long id) {
		String methodName = "getIssueToSubContractorById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IssueToSubContractorVO> issueToSubContractorVO = new ArrayList<>();
		try {
			issueToSubContractorVO = issueToSubContractorService.getIssueToSubContractorById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IssueToSubContractor information get successfully By Id");
			responseObjectsMap.put("issueToSubContractorVO", issueToSubContractorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IssueToSubContractor information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateIssueToSubContractor")
	public ResponseEntity<ResponseDTO> createUpdateIssueToSubContractor(
			@Valid @RequestBody IssueToSubContractorDTO issueToSubContractorDTO) {
		String methodName = "createUpdateIssueToSubContractor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> issueToSubContractorVO = issueToSubContractorService
					.createUpdateIssueToSubContractor(issueToSubContractorDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, issueToSubContractorVO.get("message"));
			responseObjectsMap.put("issueToSubContractorVO", issueToSubContractorVO.get("issueToSubContractorVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIssueToSubContractorDocId")
	public ResponseEntity<ResponseDTO> getIssueToSubContractorDocId(@RequestParam Long orgId) {

		String methodName = "getIssueToSubContractorDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getIssueToSubContractorDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IssueToSubContractorDocId information retrieved successfully");
			responseObjectsMap.put("issueToSubContractorDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve IssueToSubContractorDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardNoAndItemNo")
	public ResponseEntity<ResponseDTO> getRouteCardNoAndItemNo(@RequestParam Long orgId) {
		String methodName = "getRouteCardNoAndItemNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getRouteCardNoAndItemNo(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardNo Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve RouteCardNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDepartmentName")
	public ResponseEntity<ResponseDTO> getDepartmentName(@RequestParam Long orgId) {
		String methodName = "getDepartmentName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getDepartmentName(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DepartmentName Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DepartmentName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProcessNameFormItemWiseProcess")
	public ResponseEntity<ResponseDTO> getProcessNameFormItemWiseProcess(@RequestParam Long orgId,
			@RequestParam String item) {
		String methodName = "getProcessNameFormItemWiseProcess()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getProcessNameFormItemWiseProcess(orgId, item);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessName Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ProcessName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// DcForSubContractor

	@GetMapping("/getDcforSCByOrgId")
	public ResponseEntity<ResponseDTO> getDcforSCByOrgId(@RequestParam Long orgId) {
		String methodName = "getDcforSCByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
		try {
			dcForSubContractVO = issueToSubContractorService.getDcforSCByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DcForSubContract information get successfully ByOrgId");
			responseObjectsMap.put("dcForSubContractVO", dcForSubContractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DcForSubContract information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDcforSCById")
	public ResponseEntity<ResponseDTO> getDcforSCById(@RequestParam Long id) {
		String methodName = "getDcforSCById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
		try {
			dcForSubContractVO = issueToSubContractorService.getDcforSCById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Dc For SubContract information get successfully By Id");
			responseObjectsMap.put("dcForSubContractVO", dcForSubContractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Dc For SubContract information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIssueSCNoForDcForSubContract")
	public ResponseEntity<ResponseDTO> getIssueSCNoForDcForSubContract(@RequestParam(required = false) Long orgId) {
		String methodName = "getIssueSCNoForDcForSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> issuescno = new ArrayList<>();
		try {
			issuescno = issueToSubContractorService.getIssueSCNoForDcForSubContracto(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomerName information get successfully By OrgId");
			responseObjectsMap.put("issuescno", issuescno);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CustomerName information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAddressForDcForSubContract")
	public ResponseEntity<ResponseDTO> getAddressForDcForSubContract(@RequestParam(required = false) Long orgId,
			String customerName) {
		String methodName = "getAddressForDcForSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> address = new ArrayList<>();
		try {
			address = issueToSubContractorService.getAddressForDcForSubContract(orgId, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Address information get successfully By OrgId");
			responseObjectsMap.put("address", address);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Address information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateDcForSubContract")
	public ResponseEntity<ResponseDTO> updateCreateDcForSubContract(
			@Valid @RequestBody DcForSubContractDTO dcForSubContractDTO) {
		String methodName = "updateCreateDcForSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> dcForSubContractVO = issueToSubContractorService
					.updateCreateDcForSubContract(dcForSubContractDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, dcForSubContractVO.get("message"));
			responseObjectsMap.put("dcForSubContractVO", dcForSubContractVO.get("dcForSubContractVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDcForSubContractDocId")
	public ResponseEntity<ResponseDTO> getDcForSubContractDocId(@RequestParam Long orgId) {
		String methodName = "getDcForSubContractDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getDcForSubContractDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnDocId information retrieved successfully");
			responseObjectsMap.put("dcForSubContractDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnDocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SubContractEnquiry

	@GetMapping("/getAllSubContractEnquiryByOrgId")
	public ResponseEntity<ResponseDTO> getAllSubContractEnquiryByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllSubContractEnquiryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractEnquiryVO> subContractEnquiryVO = new ArrayList<>();
		try {
			subContractEnquiryVO = issueToSubContractorService.getAllSubContractEnquiryByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiry information get successfully ByOrgId");
			responseObjectsMap.put("subContractEnquiryVO", subContractEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSubContractEnquiryById")
	public ResponseEntity<ResponseDTO> getSubContractEnquiryById(@RequestParam Long id) {
		String methodName = "getSubContractEnquiryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractEnquiryVO> subContractEnquiryVO = new ArrayList<>();
		try {
			subContractEnquiryVO = issueToSubContractorService.getSubContractEnquiryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiry information get successfully By Id");
			responseObjectsMap.put("issueToSubContractorVO", subContractEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractEnquiry information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateSubContractEnquiry")
	public ResponseEntity<ResponseDTO> createUpdateSubContractEnquiry(
			@Valid @RequestBody SubContractEnquiryDTO subContractEnquiryDTO) {
		String methodName = "createUpdateSubContractEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> subContractEnquiryVO = issueToSubContractorService
					.createUpdateSubContractEnquiry(subContractEnquiryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, subContractEnquiryVO.get("message"));
			responseObjectsMap.put("subContractEnquiryVO", subContractEnquiryVO.get("subContractEnquiryVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractEnquiryDocId")
	public ResponseEntity<ResponseDTO> getSubContractEnquiryDocId(@RequestParam Long orgId) {

		String methodName = "getSubContractEnquiryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getSubContractEnquiryDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiryDocId information retrieved successfully");
			responseObjectsMap.put("subContractEnquiryDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SubContractEnquiryDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractCustomerNameAndCode")
	public ResponseEntity<ResponseDTO> getSubContractCustomerNameAndCode(@RequestParam Long orgId) {
		String methodName = "getSubContractCustomerNameAndCode()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubContractCustomerNameAndCode(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartName Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractContactNameAndNo")
	public ResponseEntity<ResponseDTO> getSubContractContactNameAndNo(@RequestParam Long orgId,
			@RequestParam String partyCode) {
		String methodName = "getSubContractContactNameAndNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubContractContactNameAndNo(orgId, partyCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ContactName Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ContactName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractPartNoAndDescription")
	public ResponseEntity<ResponseDTO> getSubContractPartNoAndDescription(@RequestParam Long orgId,
			@RequestParam String scIssueNo) {
		String methodName = "getSubContractPartNoAndDescription()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubContractPartNoAndDescription(orgId, scIssueNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details retrieved successfully");
			responseObjectsMap.put("issuetoSubContractorVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubRouteCardNo")
	public ResponseEntity<ResponseDTO> getSubRouteCardNo(@RequestParam Long orgId) {
		String methodName = "getSubRouteCardNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubRouteCardNo(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCard Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve RouteCard Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getScIssueNoFormSubContract")
	public ResponseEntity<ResponseDTO> getScIssueNoFormSubContract(@RequestParam Long orgId,
			@RequestParam String routeCardNo) {
		String methodName = "getScIssueNoFormSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getScIssueNoFormSubContract(orgId, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ScIssueNo Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ScIssueNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SubContractorQuotation

	@GetMapping("/getAllSubContractQuotationByOrgId")
	public ResponseEntity<ResponseDTO> getAllSubContractQuotationByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllSubContractQuotationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractQuotationVO> subContractQuotationVO = new ArrayList<>();
		try {
			subContractQuotationVO = issueToSubContractorService.getAllSubContractQuotationByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractQuotation information get successfully ByOrgId");
			responseObjectsMap.put("subContractQuotationVO", subContractQuotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSubContractQuotationById")
	public ResponseEntity<ResponseDTO> getSubContractQuotationById(@RequestParam Long id) {
		String methodName = "getSubContractQuotationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractQuotationVO> subContractQuotationVO = new ArrayList<>();
		try {
			subContractQuotationVO = issueToSubContractorService.getSubContractQuotationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractQuotation information get successfully By Id");
			responseObjectsMap.put("subContractQuotationVO", subContractQuotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractQuotation information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateSubContractQuotation")
	public ResponseEntity<ResponseDTO> createUpdateSubContractQuotation(
			@Valid @RequestBody SubContractQuotationDTO subContractQuotationDTO) {
		String methodName = "createUpdateSubContractQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> subContractQuotationVO = issueToSubContractorService
					.createUpdateSubContractQuotation(subContractQuotationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, subContractQuotationVO.get("message"));
			responseObjectsMap.put("subContractQuotationVO", subContractQuotationVO.get("subContractQuotationVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractQuotationDocId")
	public ResponseEntity<ResponseDTO> getSubContractQuotationDocId(@RequestParam Long orgId) {

		String methodName = "getSubContractQuotationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getSubContractQuotationDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractQuotationDocId information retrieved successfully");
			responseObjectsMap.put("subContractQuotationDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SubContractQuotationDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoAndPartDesBasedOnSubContractEnquiryNo")
	public ResponseEntity<ResponseDTO> getPartNoAndPartDesBasedOnSubContractEnquiryNo(@RequestParam Long orgId,
			@RequestParam String docId) {
		String methodName = "getPartNoAndPartDesBasedOnSubContractEnquiryNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getPartNoAndPartDesBasedOnSubContractEnquiryNo(orgId, docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EnquiryNo Details retrieved successfully");
			responseObjectsMap.put("enquiryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve EnquiryNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SubContractInvoice

	@GetMapping("/getAllSubContractInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getAllSubContractInvoiceByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllSubContractInvoiceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractInvoiceVO> subContractInvoiceVO = new ArrayList<>();
		try {
			subContractInvoiceVO = issueToSubContractorService.getAllSubContractInvoiceByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractInvoice information get successfully ByOrgId");
			responseObjectsMap.put("subContractInvoiceVO", subContractInvoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractInvoice information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSubContractInvoiceById")
	public ResponseEntity<ResponseDTO> getSubContractInvoiceById(@RequestParam Long id) {
		String methodName = "getSubContractInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractInvoiceVO> subContractInvoiceVO = new ArrayList<>();
		try {
			subContractInvoiceVO = issueToSubContractorService.getSubContractInvoiceById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractInvoice information get successfully By Id");
			responseObjectsMap.put("subContractInvoiceVO", subContractInvoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractInvoice information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateSubContractInvoice")
	public ResponseEntity<ResponseDTO> createUpdateSubContractInvoice(
			@Valid @RequestBody SubContractInvoiceDTO subContractQuotationDTO) {
		String methodName = "createUpdateSubContractInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> subContractInvoiceVO = issueToSubContractorService
					.createUpdateSubContractInvoice(subContractQuotationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, subContractInvoiceVO.get("message"));
			responseObjectsMap.put("subContractInvoiceVO", subContractInvoiceVO.get("subContractInvoiceVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractInvoiceDocId")
	public ResponseEntity<ResponseDTO> getSubContractInvoiceDocId(@RequestParam Long orgId) {

		String methodName = "getSubContractInvoiceDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getSubContractInvoiceDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractInvoiceDocId information retrieved successfully");
			responseObjectsMap.put("subContractInvoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SubContractInvoiceDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getJobWorkOutOrderNo")
	public ResponseEntity<ResponseDTO> getJobWorkOutOrderNo(@RequestParam Long orgId) {
		String methodName = "getJobWorkOutOrderNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getJobWorkOutOrderNo(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobWorkOutOrder Details retrieved successfully");
			responseObjectsMap.put("jobWorkOutOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve JobWorkOutOrder Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getJobWorkOutOrderFromPartNoAndDesc")
	public ResponseEntity<ResponseDTO> getJobWorkOutOrderFromPartNoAndDesc(@RequestParam Long orgId,@RequestParam String docId) {
		String methodName = "getJobWorkOutOrderFromPartNoAndDesc()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getJobWorkOutOrderFromPartNoAndDesc(orgId,docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details retrieved successfully");
			responseObjectsMap.put("jobWorkOutOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// JobWorkOut

	@GetMapping("/getAllJobWorkOutByOrgId")
	public ResponseEntity<ResponseDTO> getAllJobWorkOutByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllJobWorkOutByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<JobWorkOutVO> jobWorkOutVO = new ArrayList<>();
		try {
			jobWorkOutVO = issueToSubContractorService.getAllJobWorkOutByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiry information get successfully ByOrgId");
			responseObjectsMap.put("jobWorkOutVO", jobWorkOutVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllJobWorkOutById")
	public ResponseEntity<ResponseDTO> getAllJobWorkOutById(@RequestParam Long id) {
		String methodName = "getAllJobWorkOutById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<JobWorkOutVO> jobWorkOutVO = new ArrayList<>();
		try {
			jobWorkOutVO = issueToSubContractorService.getAllJobWorkOutById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiry information get successfully By Id");
			responseObjectsMap.put("jobWorkOutVO", jobWorkOutVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractEnquiry information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateJobWorkOut")
	public ResponseEntity<ResponseDTO> createUpdateJobWorkOut(@Valid @RequestBody JobWorkOutDTO jobWorkOutDTO) {
		String methodName = "createUpdateJobWorkOut()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> jobWorkOutVO = issueToSubContractorService.createUpdateJobWorkOut(jobWorkOutDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, jobWorkOutVO.get("message"));
			responseObjectsMap.put("jobWorkOutVO", jobWorkOutVO.get("jobWorkOutVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobWorkOutDocId")
	public ResponseEntity<ResponseDTO> getJobWorkOutDocId(@RequestParam Long orgId) {

		String methodName = "getJobWorkOutDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getJobWorkOutDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobWorkOutDocId information retrieved successfully");
			responseObjectsMap.put("jobWorkOutDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve JobWorkOutDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDcForSubContractForJobWorkOut")
	public ResponseEntity<ResponseDTO> getDcForSubContractForJobWorkOut(@RequestParam(required = false) Long orgId,
			String customerName) {
		String methodName = "getDcForSubContractForJobWorkOut()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> dcforsub = new ArrayList<>();
		try {
			dcforsub = issueToSubContractorService.getDcForSubContractForJobWorkOut(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"dc for subcontract information get successfully By OrgId");
			responseObjectsMap.put("dcforsub", dcforsub);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Address information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

}
