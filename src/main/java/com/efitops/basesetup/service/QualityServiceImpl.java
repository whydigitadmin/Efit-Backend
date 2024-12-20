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

import com.efitops.basesetup.dto.IncomingMaterialInspectionAppearanceDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDetailsDTO;
import com.efitops.basesetup.dto.InprocessAppearanceInspectionDTO;
import com.efitops.basesetup.dto.InprocessInspectionDTO;
import com.efitops.basesetup.dto.InprocessInspectionDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionAppearanceVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionDetailsVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.entity.InprocessAppearanceInspectionVO;
import com.efitops.basesetup.entity.InprocessInspectionDetailsVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionAppearanceRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionDetailsRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionRepo;
import com.efitops.basesetup.repo.InprocessAppearanceInspectionRepo;
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
	InprocessAppearanceInspectionRepo inprocessAppearanceInspectionRepo;

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

			List<InprocessAppearanceInspectionVO> inprocessAppearanceInspectionVO1 = inprocessAppearanceInspectionRepo
					.findByInprocessInspectionVO(inprocessInspectionVO);
			inprocessAppearanceInspectionRepo.deleteAll(inprocessAppearanceInspectionVO1);
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

		List<InprocessAppearanceInspectionVO> inprocessAppearanceInspectionVOs = new ArrayList<>();
		for (InprocessAppearanceInspectionDTO inprocessAppearanceInspectionDTO : inprocessInspectionDTO
				.getInprocessAppearanceInspectionDTO()) {
			InprocessAppearanceInspectionVO inprocessAppearanceInspectionVO = new InprocessAppearanceInspectionVO();
			inprocessAppearanceInspectionVO.setCharacteristics(inprocessAppearanceInspectionDTO.getCharacteristics());
			inprocessAppearanceInspectionVO
					.setMethodOfInspection(inprocessAppearanceInspectionDTO.getMethodOfInspection());
			inprocessAppearanceInspectionVO.setSpecification(inprocessAppearanceInspectionDTO.getSpecification());
			inprocessAppearanceInspectionVO.setObservation(inprocessAppearanceInspectionDTO.getObservation());
			inprocessAppearanceInspectionVO.setInprocessInspectionVO(inprocessInspectionVO);
			inprocessAppearanceInspectionVOs.add(inprocessAppearanceInspectionVO);
		}
		inprocessInspectionVO.setInprocessAppearanceInspectionVO(inprocessAppearanceInspectionVOs);
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
}
