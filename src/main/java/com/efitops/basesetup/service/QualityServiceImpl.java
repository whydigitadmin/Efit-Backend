package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.FinalInspectionReportDTO;
import com.efitops.basesetup.dto.FirAppearanceInspectionDTO;
import com.efitops.basesetup.dto.FirDimensionalInspectionDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionAppearanceDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDetailsDTO;
import com.efitops.basesetup.dto.InprocessInspectionAppearanceDTO;
import com.efitops.basesetup.dto.InprocessInspectionDTO;
import com.efitops.basesetup.dto.InprocessInspectionDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.FinalInspectionReportVO;
import com.efitops.basesetup.entity.FirAppearanceInspectionVO;
import com.efitops.basesetup.entity.FirDimensionalInspectionVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionAppearanceVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionDetailsVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.entity.InprocessInspectionAppearanceVO;
import com.efitops.basesetup.entity.InprocessInspectionDetailsVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.FinalInspectionReportRepo;
import com.efitops.basesetup.repo.FirAppearanceInspectionRepo;
import com.efitops.basesetup.repo.FirDimensionalInspectionRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionAppearanceRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionDetailsRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionRepo;
import com.efitops.basesetup.repo.InprocessInspectionAppearanceRepo;
import com.efitops.basesetup.repo.InprocessInspectionDetailsRepo;
import com.efitops.basesetup.repo.InprocessInspectionRepo;

@Service
public class QualityServiceImpl implements QualityService {

	public static final Logger LOGGER = LoggerFactory.getLogger(QualityServiceImpl.class);

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	IncomingMaterialInspectionRepo incomingMaterialInspectionRepo;

	@Autowired
	IncomingMaterialInspectionDetailsRepo incomingMaterialInspectionDetailsRepo;

	@Autowired
	IncomingMaterialInspectionAppearanceRepo incomingMaterialInspectionAppearanceRepo;

	@Autowired
	InprocessInspectionRepo inprocessInspectionRepo;

	@Autowired
	InprocessInspectionDetailsRepo inprocessInspectionDetailsRepo;

	@Autowired
	InprocessInspectionAppearanceRepo inprocessInspectionAppearanceRepo;

	@Autowired
	FinalInspectionReportRepo finalInspectionReportRepo;

	@Autowired
	FirDimensionalInspectionRepo firDimensionalInspectionRepo;

	@Autowired
	FirAppearanceInspectionRepo firAppearanceInspectionRepo;

	// IncomingMaterialInspection

	@Override
	public Map<String, Object> createUpdateIncomingMaterialInspection(
			IncomingMaterialInspectionDTO incomingMaterialInspectionDTO) throws ApplicationException {
		IncomingMaterialInspectionVO incomingMaterialInspectionVO = new IncomingMaterialInspectionVO();
		String message;
		String screenCode = "INMI";
		if (ObjectUtils.isNotEmpty(incomingMaterialInspectionDTO.getId())) {
			incomingMaterialInspectionVO = incomingMaterialInspectionRepo
					.findById(incomingMaterialInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid IncomingMaterialInspection details"));
			message = "IncomingMaterialInspection Updated Successfully";
			incomingMaterialInspectionVO.setUpdatedBy(incomingMaterialInspectionDTO.getCreatedBy());

		} else {

			String docId = incomingMaterialInspectionRepo
					.getIncomingMaterialInspectionDocId(incomingMaterialInspectionDTO.getOrgId(), screenCode);
			incomingMaterialInspectionVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(incomingMaterialInspectionDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			incomingMaterialInspectionVO.setCreatedBy(incomingMaterialInspectionDTO.getCreatedBy());
			incomingMaterialInspectionVO.setUpdatedBy(incomingMaterialInspectionDTO.getCreatedBy());

			message = "IncomingMaterialInspection Created Successfully";
		}
		createUpdatedIncomingMaterialInspectionVOFromIncomingMaterialInspectionDTO(incomingMaterialInspectionDTO,
				incomingMaterialInspectionVO);
		incomingMaterialInspectionRepo.save(incomingMaterialInspectionVO);
		Map<String, Object> response = new HashMap<>();
		response.put("incomingMaterialInspectionVO", incomingMaterialInspectionVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedIncomingMaterialInspectionVOFromIncomingMaterialInspectionDTO(
			IncomingMaterialInspectionDTO incomingMaterialInspectionDTO,
			IncomingMaterialInspectionVO incomingMaterialInspectionVO) {
		incomingMaterialInspectionVO.setMaterialType(incomingMaterialInspectionDTO.getMaterialType());
		incomingMaterialInspectionVO.setGrnNo(incomingMaterialInspectionDTO.getGrnNo());
		incomingMaterialInspectionVO.setPoNo(incomingMaterialInspectionDTO.getPoNo());
		incomingMaterialInspectionVO.setSupplierName(incomingMaterialInspectionDTO.getSupplierName());
		incomingMaterialInspectionVO.setDcInvNo(incomingMaterialInspectionDTO.getDcInvNo());
		incomingMaterialInspectionVO.setType(incomingMaterialInspectionDTO.getType());
		incomingMaterialInspectionVO.setItemNo(incomingMaterialInspectionDTO.getItemNo());
		incomingMaterialInspectionVO.setMaterial(incomingMaterialInspectionDTO.getMaterial());
		incomingMaterialInspectionVO.setQtyReceived(incomingMaterialInspectionDTO.getQtyReceived());
		incomingMaterialInspectionVO.setInspectedQuantity(incomingMaterialInspectionDTO.getInspectedQuantity());
		incomingMaterialInspectionVO.setDocumentFormatNo(incomingMaterialInspectionDTO.getDocumentFormatNo());
		incomingMaterialInspectionVO.setDate(incomingMaterialInspectionDTO.getDate());

		//// VisualInspection
		incomingMaterialInspectionVO.setTestCertificate(incomingMaterialInspectionDTO.getTestCertificate());
		incomingMaterialInspectionVO.setAcceptedQty(incomingMaterialInspectionDTO.getAcceptedQty());
		incomingMaterialInspectionVO.setRework(incomingMaterialInspectionDTO.getRework());
		incomingMaterialInspectionVO.setSegregate(incomingMaterialInspectionDTO.getSegregate());
		incomingMaterialInspectionVO
				.setConcessionallyAccepted(incomingMaterialInspectionDTO.getConcessionallyAccepted());
		incomingMaterialInspectionVO.setScrap(incomingMaterialInspectionDTO.getScrap());

		// Summary
		incomingMaterialInspectionVO.setInspectedBy(incomingMaterialInspectionDTO.getInspectedBy());
		incomingMaterialInspectionVO.setInspectedDate(incomingMaterialInspectionDTO.getInspectedDate());
		incomingMaterialInspectionVO.setDcInvNo(incomingMaterialInspectionDTO.getDcInvNo());
		incomingMaterialInspectionVO.setApprovedBy(incomingMaterialInspectionDTO.getApprovedBy());
		incomingMaterialInspectionVO.setApprovedDate(incomingMaterialInspectionDTO.getApprovedDate());
		incomingMaterialInspectionVO.setNaration(incomingMaterialInspectionDTO.getNaration());

		incomingMaterialInspectionVO.setOrgId(incomingMaterialInspectionDTO.getOrgId());
		incomingMaterialInspectionVO.setActive(incomingMaterialInspectionDTO.isActive());
		incomingMaterialInspectionVO.setCreatedBy(incomingMaterialInspectionDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(incomingMaterialInspectionDTO.getId())) {
			List<IncomingMaterialInspectionDetailsVO> IncomingMaterialInspectionDetailsVO1 = incomingMaterialInspectionDetailsRepo
					.findByIncomingMaterialInspectionVO(incomingMaterialInspectionVO);
			incomingMaterialInspectionDetailsRepo.deleteAll(IncomingMaterialInspectionDetailsVO1);

			List<IncomingMaterialInspectionAppearanceVO> IncomingMaterialInspectionAppearanceVO1 = incomingMaterialInspectionAppearanceRepo
					.findByIncomingMaterialInspectionVO(incomingMaterialInspectionVO);
			incomingMaterialInspectionAppearanceRepo.deleteAll(IncomingMaterialInspectionAppearanceVO1);
		}

		List<IncomingMaterialInspectionDetailsVO> incomingMaterialInspectionDetailsVOs = new ArrayList<>();
		for (IncomingMaterialInspectionDetailsDTO incomingMaterialInspectionDetailsDTO : incomingMaterialInspectionDTO
				.getIncomingMaterialInspectionDetailsDTO()) {
			IncomingMaterialInspectionDetailsVO incomingMaterialInspectionDetailsVO = new IncomingMaterialInspectionDetailsVO();
			incomingMaterialInspectionDetailsVO.setParameter(incomingMaterialInspectionDetailsDTO.getParameter());
			incomingMaterialInspectionDetailsVO
					.setSpecification(incomingMaterialInspectionDetailsDTO.getSpecification());
			incomingMaterialInspectionDetailsVO.setLsl(incomingMaterialInspectionDetailsDTO.getLsl());
			incomingMaterialInspectionDetailsVO.setUsl(incomingMaterialInspectionDetailsDTO.getUsl());
			incomingMaterialInspectionDetailsVO
					.setObservedvalue(incomingMaterialInspectionDetailsDTO.getObservedvalue());
			incomingMaterialInspectionDetailsVO.setSample1(incomingMaterialInspectionDetailsDTO.getSample1());
			incomingMaterialInspectionDetailsVO.setSample2(incomingMaterialInspectionDetailsDTO.getSample2());
			incomingMaterialInspectionDetailsVO.setSample3(incomingMaterialInspectionDetailsDTO.getSample3());
			incomingMaterialInspectionDetailsVO.setSample4(incomingMaterialInspectionDetailsDTO.getSample4());
			incomingMaterialInspectionDetailsVO.setSample5(incomingMaterialInspectionDetailsDTO.getSample5());
			incomingMaterialInspectionDetailsVO.setSample6(incomingMaterialInspectionDetailsDTO.getSample6());
			incomingMaterialInspectionDetailsVO.setSample7(incomingMaterialInspectionDetailsDTO.getSample7());
			incomingMaterialInspectionDetailsVO.setSample8(incomingMaterialInspectionDetailsDTO.getSample8());
			incomingMaterialInspectionDetailsVO.setRemarks(incomingMaterialInspectionDetailsDTO.getRemarks());
			incomingMaterialInspectionDetailsVO.setIncomingMaterialInspectionVO(incomingMaterialInspectionVO);
			incomingMaterialInspectionDetailsVOs.add(incomingMaterialInspectionDetailsVO);
		}
		incomingMaterialInspectionVO.setIncomingMaterialInspectionDetailsVO(incomingMaterialInspectionDetailsVOs);

		List<IncomingMaterialInspectionAppearanceVO> incomingMaterialInspectionAppearanceVOs = new ArrayList<>();
		for (IncomingMaterialInspectionAppearanceDTO incomingMaterialInspectionAppearanceDTO : incomingMaterialInspectionDTO
				.getIncomingMaterialInspectionAppearanceDTO()) {
			IncomingMaterialInspectionAppearanceVO incomingMaterialInspectionAppearanceVO = new IncomingMaterialInspectionAppearanceVO();
			incomingMaterialInspectionAppearanceVO
					.setCharacteristics(incomingMaterialInspectionAppearanceDTO.getCharacteristics());
			incomingMaterialInspectionAppearanceVO
					.setMethodOfInspection(incomingMaterialInspectionAppearanceDTO.getMethodOfInspection());
			incomingMaterialInspectionAppearanceVO
					.setSpecifications(incomingMaterialInspectionAppearanceDTO.getSpecifications());
			incomingMaterialInspectionAppearanceVO.setIncomingMaterialInspectionVO(incomingMaterialInspectionVO);
			incomingMaterialInspectionAppearanceVOs.add(incomingMaterialInspectionAppearanceVO);
		}
		incomingMaterialInspectionVO.setIncomingMaterialInspectionAppearanceVO(incomingMaterialInspectionAppearanceVOs);
	}

	@Override
	public List<IncomingMaterialInspectionVO> getAllIncomingMaterialInspectionByOrgId(Long orgId) {

		return incomingMaterialInspectionRepo.getAllIncomingMaterialInspectionByOrgId(orgId);
	}

	@Override
	public IncomingMaterialInspectionVO getIncomingMaterialInspectionById(Long id) {

		return incomingMaterialInspectionRepo.getIncomingMaterialInspectionById(id);
	}

	@Override
	public String getIncomingMaterialInspectionDocId(Long orgId) {
		String ScreenCode = "INMI";
		String result = incomingMaterialInspectionRepo.getIncomingMaterialInspectionDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getGrnNoFromGrnScreen(Long orgId, String grnNo) {
		Set<Object[]> chType = incomingMaterialInspectionRepo.getGrnNoFromGrnScreen(orgId, grnNo);
		return getGrnNo(chType);
	}

	private List<Map<String, Object>> getGrnNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnNo", ch[0] != null ? ch[0].toString() : "");
			map.put("poNo", ch[1] != null ? ch[1].toString() : "");
			map.put("supplierName", ch[2] != null ? ch[2].toString() : "");
			map.put("dcInvNo", ch[3] != null ? ch[3].toString() : "");
			map.put("qtyReceived", ch[4] != null ? ch[4].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getItemNoFromGrn(Long orgId, String grnNo) {
		Set<Object[]> chType = incomingMaterialInspectionRepo.getItemNoFromGrn(orgId, grnNo);
		return getItemNo(chType);
	}

	private List<Map<String, Object>> getItemNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemCode", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// InprocesInspection

	@Override
	public Map<String, Object> createUpdateInprocessInspection(InprocessInspectionDTO inprocessInspectionDTO)
			throws ApplicationException {
		InprocessInspectionVO inprocessInspectionVO = new InprocessInspectionVO();
		String message;
		String screenCode = "IIN";
		if (ObjectUtils.isNotEmpty(inprocessInspectionDTO.getId())) {
			inprocessInspectionVO = inprocessInspectionRepo.findById(inprocessInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid InprocessInspection details"));
			message = "InprocessInspection Updated Successfully";
			inprocessInspectionVO.setUpdatedBy(inprocessInspectionDTO.getCreatedBy());

		} else {

			String docId = inprocessInspectionRepo.getInprocessInspectionDocId(inprocessInspectionDTO.getOrgId(),
					screenCode);
			inprocessInspectionVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(inprocessInspectionDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			inprocessInspectionVO.setCreatedBy(inprocessInspectionDTO.getCreatedBy());
			inprocessInspectionVO.setUpdatedBy(inprocessInspectionDTO.getCreatedBy());

			message = "InprocessInspection Created Successfully";
		}
		createUpdatedInprocessInspectionVOFromInprocessInspectionDTO(inprocessInspectionDTO, inprocessInspectionVO);
		inprocessInspectionRepo.save(inprocessInspectionVO);
		Map<String, Object> response = new HashMap<>();
		response.put("inprocessInspectionVO", inprocessInspectionVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedInprocessInspectionVOFromInprocessInspectionDTO(
			InprocessInspectionDTO inprocessInspectionDTO, InprocessInspectionVO inprocessInspectionVO) {
		inprocessInspectionVO.setRouteCardNo(inprocessInspectionDTO.getRouteCardNo());
		inprocessInspectionVO.setWorkOrderNo(inprocessInspectionDTO.getWorkOrderNo());
		inprocessInspectionVO.setPartNo(inprocessInspectionDTO.getPartNo());
		inprocessInspectionVO.setPartName(inprocessInspectionDTO.getPartName());
		inprocessInspectionVO.setMaterialDrawingNo(inprocessInspectionDTO.getMaterialDrawingNo());
		inprocessInspectionVO.setCustomer(inprocessInspectionDTO.getCustomer());
		inprocessInspectionVO.setLotQty(inprocessInspectionDTO.getLotQty());
		inprocessInspectionVO.setDrawingNo(inprocessInspectionDTO.getDrawingNo());
		inprocessInspectionVO.setReceivedQty(inprocessInspectionDTO.getReceivedQty());
		inprocessInspectionVO.setSampleQty(inprocessInspectionDTO.getSampleQty());

		inprocessInspectionVO.setOrgId(inprocessInspectionDTO.getOrgId());
		inprocessInspectionVO.setActive(inprocessInspectionDTO.isActive());
		inprocessInspectionVO.setCreatedBy(inprocessInspectionDTO.getCreatedBy());

		// Summary
		inprocessInspectionVO.setCheckedBy(inprocessInspectionDTO.getCheckedBy());
		inprocessInspectionVO.setApprovedBy(inprocessInspectionDTO.getApprovedBy());
		inprocessInspectionVO.setNaration(inprocessInspectionDTO.getNaration());

		if (ObjectUtils.isNotEmpty(inprocessInspectionDTO.getId())) {
			List<InprocessInspectionDetailsVO> inprocessInspectionDetailsVO1 = inprocessInspectionDetailsRepo
					.findByInprocessInspectionVO(inprocessInspectionVO);
			inprocessInspectionDetailsRepo.deleteAll(inprocessInspectionDetailsVO1);

			List<InprocessInspectionAppearanceVO> inprocessInspectionAppearanceVO1 = inprocessInspectionAppearanceRepo
					.findByInprocessInspectionVO(inprocessInspectionVO);
			inprocessInspectionAppearanceRepo.deleteAll(inprocessInspectionAppearanceVO1);
		}

		List<InprocessInspectionDetailsVO> inprocessInspectionDetailsVOs = new ArrayList<>();
		for (InprocessInspectionDetailsDTO inprocessInspectionDetailsDTO : inprocessInspectionDTO
				.getInprocessInspectionDetailsDTO()) {
			InprocessInspectionDetailsVO inprocessInspectionDetailsVO = new InprocessInspectionDetailsVO();
			inprocessInspectionDetailsVO.setCharacteristics(inprocessInspectionDetailsDTO.getCharacteristics());
			inprocessInspectionDetailsVO.setMethodOfInspection(inprocessInspectionDetailsDTO.getMethodOfInspection());
			inprocessInspectionDetailsVO.setSpecification(inprocessInspectionDetailsDTO.getSpecification());
			inprocessInspectionDetailsVO.setLsl(inprocessInspectionDetailsDTO.getLsl());
			inprocessInspectionDetailsVO.setUsl(inprocessInspectionDetailsDTO.getUsl());
			inprocessInspectionDetailsVO.setSample1(inprocessInspectionDetailsDTO.getSample1());
			inprocessInspectionDetailsVO.setSample2(inprocessInspectionDetailsDTO.getSample2());
			inprocessInspectionDetailsVO.setSample3(inprocessInspectionDetailsDTO.getSample3());
			inprocessInspectionDetailsVO.setSample4(inprocessInspectionDetailsDTO.getSample4());
			inprocessInspectionDetailsVO.setSample5(inprocessInspectionDetailsDTO.getSample5());
			inprocessInspectionDetailsVO.setSample6(inprocessInspectionDetailsDTO.getSample6());
			inprocessInspectionDetailsVO.setSample7(inprocessInspectionDetailsDTO.getSample7());
			inprocessInspectionDetailsVO.setSample8(inprocessInspectionDetailsDTO.getSample8());
			inprocessInspectionDetailsVO.setRemarks(inprocessInspectionDetailsDTO.getRemarks());
			inprocessInspectionDetailsVO.setInprocessInspectionVO(inprocessInspectionVO);
			inprocessInspectionDetailsVOs.add(inprocessInspectionDetailsVO);
		}
		inprocessInspectionVO.setInprocessInspectionDetailsVO(inprocessInspectionDetailsVOs);

		List<InprocessInspectionAppearanceVO> inprocessInspectionAppearanceVOs = new ArrayList<>();
		for (InprocessInspectionAppearanceDTO inprocessInspectionAppearanceDTO : inprocessInspectionDTO
				.getInprocessInspectionAppearanceDTO()) {
			InprocessInspectionAppearanceVO inprocessInspectionAppearanceVO = new InprocessInspectionAppearanceVO();
			inprocessInspectionAppearanceVO.setCharacteristics(inprocessInspectionAppearanceDTO.getCharacteristics());
			inprocessInspectionAppearanceVO
					.setMethodOfInspection(inprocessInspectionAppearanceDTO.getMethodOfInspection());
			inprocessInspectionAppearanceVO.setSpecification(inprocessInspectionAppearanceDTO.getSpecification());
			inprocessInspectionAppearanceVO.setObservation(inprocessInspectionAppearanceDTO.getObservation());
			inprocessInspectionAppearanceVO.setRemarks1(inprocessInspectionAppearanceDTO.getRemarks1());
			inprocessInspectionAppearanceVO.setInprocessInspectionVO(inprocessInspectionVO);
			inprocessInspectionAppearanceVOs.add(inprocessInspectionAppearanceVO);
		}
		inprocessInspectionVO.setInprocessInspectionAppearanceVO(inprocessInspectionAppearanceVOs);
	}

	@Override
	public List<InprocessInspectionVO> getAllInprocessInspectionByOrgId(Long orgId) {

		return inprocessInspectionRepo.getAllInprocessInspectionByOrgId(orgId);
	}

	@Override
	public InprocessInspectionVO getInprocessInspectionById(Long id) {

		return inprocessInspectionRepo.getInprocessInspectionById(id);
	}

	@Override
	public String getInprocessInspectionDocId(Long orgId) {
		String ScreenCode = "IIN";
		String result = inprocessInspectionRepo.getInprocessInspectionDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getDocIdFromRouteCardNumber(Long orgId) {
		Set<Object[]> chType = inprocessInspectionRepo.getDocIdFromRouteCardNumber(orgId);
		return getDocIdFromRoute(chType);
	}

	private List<Map<String, Object>> getDocIdFromRoute(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNumber", ch[0] != null ? ch[0].toString() : "");
			map.put("workOrderNumber", ch[1] != null ? ch[1].toString() : "");
			map.put("partNo", ch[2] != null ? ch[2].toString() : "");
			map.put("partName", ch[3] != null ? ch[3].toString() : "");
			map.put("customerName", ch[4] != null ? ch[4].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDrawingNumberFromDrawingMaster(Long orgId, String fgPartno) {
		Set<Object[]> chType = inprocessInspectionRepo.getDrawingNumberFromDrawingMaster(orgId, fgPartno);
		return getDrawingNumber(chType);
	}

	private List<Map<String, Object>> getDrawingNumber(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getEmployeeNameFromEmployeeMaster(Long orgId) {
		Set<Object[]> chType = inprocessInspectionRepo.getEmployeeNameFromEmployeeMaster(orgId);
		return getEmployee(chType);
	}

	private List<Map<String, Object>> getEmployee(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("employee", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// FinalInspectionReport

	@Override
	public Map<String, Object> createUpdateFinalInspectionReport(FinalInspectionReportDTO finalInspectionReportDTO)
			throws ApplicationException {
		FinalInspectionReportVO finalInspectionReportVO = new FinalInspectionReportVO();
		String message;
		String screenCode = "FINR";
		if (ObjectUtils.isNotEmpty(finalInspectionReportDTO.getId())) {
			finalInspectionReportVO = finalInspectionReportRepo.findById(finalInspectionReportDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid FinalInspectionReport details"));
			message = "FinalInspectionReport Updated Successfully";
			finalInspectionReportVO.setUpdatedBy(finalInspectionReportDTO.getCreatedBy());

		} else {

			String docId = finalInspectionReportRepo.getFinalInspectionReportDocId(finalInspectionReportDTO.getOrgId(),
					screenCode);
			finalInspectionReportVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(finalInspectionReportDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			finalInspectionReportVO.setCreatedBy(finalInspectionReportDTO.getCreatedBy());
			finalInspectionReportVO.setUpdatedBy(finalInspectionReportDTO.getCreatedBy());

			message = "FinalInspectionReport Created Successfully";
		}
		createUpdatedFinalInspectionReportVOFromFinalInspectionReportDTO(finalInspectionReportDTO,
				finalInspectionReportVO);
		finalInspectionReportRepo.save(finalInspectionReportVO);
		Map<String, Object> response = new HashMap<>();
		response.put("finalInspectionReportVO", finalInspectionReportVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedFinalInspectionReportVOFromFinalInspectionReportDTO(
			FinalInspectionReportDTO finalInspectionReportDTO, FinalInspectionReportVO finalInspectionReportVO) {
		finalInspectionReportVO.setRouteCard(finalInspectionReportDTO.getRouteCard());
		finalInspectionReportVO.setPartName(finalInspectionReportDTO.getPartName());
		finalInspectionReportVO.setPartNo(finalInspectionReportDTO.getPartNo());
		finalInspectionReportVO.setUntis(finalInspectionReportDTO.getUntis());
		finalInspectionReportVO.setCustomer(finalInspectionReportDTO.getCustomer());
		finalInspectionReportVO.setPoNo(finalInspectionReportDTO.getPoNo());
		finalInspectionReportVO.setInspectionDate(finalInspectionReportDTO.getInspectionDate());
		finalInspectionReportVO.setInvoiceNo(finalInspectionReportDTO.getInvoiceNo());
		finalInspectionReportVO.setLotQty(finalInspectionReportDTO.getLotQty());
		finalInspectionReportVO.setSampleQty(finalInspectionReportDTO.getSampleQty());

		finalInspectionReportVO.setOrgId(finalInspectionReportDTO.getOrgId());
		finalInspectionReportVO.setActive(finalInspectionReportDTO.isActive());
		finalInspectionReportVO.setCreatedBy(finalInspectionReportDTO.getCreatedBy());

		// Summary
		finalInspectionReportVO.setCheckedBy(finalInspectionReportDTO.getCheckedBy());
		finalInspectionReportVO.setApprovedBy(finalInspectionReportDTO.getApprovedBy());
		finalInspectionReportVO.setNaration(finalInspectionReportDTO.getNaration());

		if (ObjectUtils.isNotEmpty(finalInspectionReportDTO.getId())) {
			List<FirDimensionalInspectionVO> firDimensionalInspectionVO1 = firDimensionalInspectionRepo
					.findByFinalInspectionReportVO(finalInspectionReportVO);
			firDimensionalInspectionRepo.deleteAll(firDimensionalInspectionVO1);

			List<FirAppearanceInspectionVO> firAppearanceInspectionVO1 = firAppearanceInspectionRepo
					.findByFinalInspectionReportVO(finalInspectionReportVO);
			firAppearanceInspectionRepo.deleteAll(firAppearanceInspectionVO1);
		}

		List<FirDimensionalInspectionVO> firDimensionalInspectionVOs = new ArrayList<>();
		for (FirDimensionalInspectionDTO firDimensionalInspectionDTO : finalInspectionReportDTO
				.getFirDimensionalInspectionDTO()) {
			FirDimensionalInspectionVO firDimensionalInspectionVO = new FirDimensionalInspectionVO();
			firDimensionalInspectionVO.setCharacteristics(firDimensionalInspectionDTO.getCharacteristics());
			firDimensionalInspectionVO.setMethodOfInspection(firDimensionalInspectionDTO.getMethodOfInspection());
			firDimensionalInspectionVO.setSpecification(firDimensionalInspectionDTO.getSpecification());
			firDimensionalInspectionVO.setLsl(firDimensionalInspectionDTO.getLsl());
			firDimensionalInspectionVO.setUsl(firDimensionalInspectionDTO.getUsl());
			firDimensionalInspectionVO.setSample1(firDimensionalInspectionDTO.getSample1());
			firDimensionalInspectionVO.setSample2(firDimensionalInspectionDTO.getSample2());
			firDimensionalInspectionVO.setSample3(firDimensionalInspectionDTO.getSample3());
			firDimensionalInspectionVO.setSample4(firDimensionalInspectionDTO.getSample4());
			firDimensionalInspectionVO.setSample5(firDimensionalInspectionDTO.getSample5());
			firDimensionalInspectionVO.setSample6(firDimensionalInspectionDTO.getSample6());
			firDimensionalInspectionVO.setSample7(firDimensionalInspectionDTO.getSample7());
			firDimensionalInspectionVO.setSample8(firDimensionalInspectionDTO.getSample8());
			firDimensionalInspectionVO.setSample9(firDimensionalInspectionDTO.getSample9());
			firDimensionalInspectionVO.setSample10(firDimensionalInspectionDTO.getSample10());
			firDimensionalInspectionVO.setRemarks(firDimensionalInspectionDTO.getRemarks());
			firDimensionalInspectionVO.setFinalInspectionReportVO(finalInspectionReportVO);
			firDimensionalInspectionVOs.add(firDimensionalInspectionVO);
		}
		finalInspectionReportVO.setFirDimensionalInspectionVO(firDimensionalInspectionVOs);

		List<FirAppearanceInspectionVO> firAppearanceInspectionVOs = new ArrayList<>();
		for (FirAppearanceInspectionDTO firAppearanceInspectionDTO : finalInspectionReportDTO
				.getFirAppearanceInspectionDTO()) {
			FirAppearanceInspectionVO firAppearanceInspectionVO = new FirAppearanceInspectionVO();
			firAppearanceInspectionVO.setCharacteristics(firAppearanceInspectionDTO.getCharacteristics());
			firAppearanceInspectionVO.setMethodOfInspection(firAppearanceInspectionDTO.getMethodOfInspection());
			firAppearanceInspectionVO.setSpecification(firAppearanceInspectionDTO.getSpecification());
			firAppearanceInspectionVO.setLsl(firAppearanceInspectionDTO.getLsl());
			firAppearanceInspectionVO.setUsl(firAppearanceInspectionDTO.getUsl());
			firAppearanceInspectionVO.setObservation(firAppearanceInspectionDTO.getObservation());
			firAppearanceInspectionVO.setRemarks(firAppearanceInspectionDTO.getRemarks());
			firAppearanceInspectionVO.setFinalInspectionReportVO(finalInspectionReportVO);
			firAppearanceInspectionVOs.add(firAppearanceInspectionVO);
		}
		finalInspectionReportVO.setFirAppearanceInspectionVO(firAppearanceInspectionVOs);
	}

	@Override
	public List<FinalInspectionReportVO> getAllFinalInspectionReportByOrgId(Long orgId) {

		return finalInspectionReportRepo.getAllFinalInspectionReportByOrgId(orgId);
	}

	@Override
	public FinalInspectionReportVO getFinalInspectionReportById(Long id) {

		return finalInspectionReportRepo.getFinalInspectionReportById(id);
	}

	@Override
	public String getFinalInspectionReportDocId(Long orgId) {
		String ScreenCode = "FINR";
		String result = finalInspectionReportRepo.getFinalInspectionReportDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPartNameFromRouteCard(Long orgId, String routeCardNumber) {
		Set<Object[]> chType = finalInspectionReportRepo.getPartNameFromRouteCard(orgId, routeCardNumber);
		return getRouteCardNumber(chType);
	}

	private List<Map<String, Object>> getRouteCardNumber(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partName", ch[0] != null ? ch[0].toString() : "");
			map.put("partNo", ch[1] != null ? ch[1].toString() : "");
			map.put("units", ch[2] != null ? ch[2].toString() : "");
			map.put("customer", ch[3] != null ? ch[3].toString() : "");
			map.put("poNo", ch[4] != null ? ch[4].toString() : "");
			map.put("invoiceNo", ch[5] != null ? ch[5].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRouteCardNumberFromRouteCard(Long orgId) {
		Set<Object[]> chType = finalInspectionReportRepo.getRouteCardNumberFromRouteCard(orgId);
		return getRouteCardNumberFromRoute(chType);
	}

	private List<Map<String, Object>> getRouteCardNumberFromRoute(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNumber", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
}
}
