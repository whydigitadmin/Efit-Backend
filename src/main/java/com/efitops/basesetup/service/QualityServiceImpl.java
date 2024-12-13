package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.IncomingMaterialInspectionAppearanceDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionAppearanceVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionDetailsVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionAppearanceRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionDetailsRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionRepo;

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

	// IncomingMaterialInspection

	@Override
	public Map<String, Object> createUpdateIncomingMaterialInspection(IncomingMaterialInspectionDTO incomingMaterialInspectionDTO)
			throws ApplicationException {
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

}