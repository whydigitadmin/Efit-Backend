package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.DetailsSubmissionToBankDTO;
import com.efitops.basesetup.dto.DetailsSubmissionToBankDetailsDTO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankDetailsVO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DrawingMaster1VO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DetailsSubmissionToBankDetailsRepo;
import com.efitops.basesetup.repo.DetailsSubmissionToBankRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;

@Service
public class DetailsSubmissionToBankServiceImpl implements DetailsSubmissionToBankService {

	public static final Logger LOGGER = LoggerFactory.getLogger(DetailsSubmissionToBankServiceImpl.class);

	@Autowired
	DetailsSubmissionToBankRepo detailsSubmissionToBankRepo;

	@Autowired
	DetailsSubmissionToBankDetailsRepo detailsSubmissionToBankDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public List<DetailsSubmissionToBankVO> getAllDetailsSubmissionToBankByOrgId(Long orgId) {
		List<DetailsSubmissionToBankVO> bankVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Bank Details BY OrgId : {}", orgId);
			bankVO = detailsSubmissionToBankRepo.getAllDetailsSubmissionToBankByOrgId(orgId);
		}
		return bankVO;
	}

	@Override
	public List<DetailsSubmissionToBankVO> getDetailsSubmissionToBankById(Long id) {
		List<DetailsSubmissionToBankVO> bankVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Bank Details BY Id : {}", id);
			bankVO = detailsSubmissionToBankRepo.getAllDetailsSubmissionToBankById(id);
		}
		return bankVO;
	}

	@Override
	public Map<String, Object> createUpdateDetailsSubmissionToBank(DetailsSubmissionToBankDTO bankDTO)
			throws ApplicationException {
		String screenCode = "DSB";
		DetailsSubmissionToBankVO bankVO = new DetailsSubmissionToBankVO();
		String message;
		if (ObjectUtils.isNotEmpty(bankDTO.getId())) {
			bankVO = detailsSubmissionToBankRepo.findById(bankDTO.getId())
					.orElseThrow(() -> new ApplicationException("Production Plan not found"));

			bankVO.setModifiedBy(bankDTO.getCreatedBy());
			createUpdateBankDetailsVOByBankDetailsDTO(bankDTO, bankVO);
			message = "JobOrder Updated Successfully";
		} else {
			// GETDOCID API
			String docId = detailsSubmissionToBankRepo.getBankDetailsDocId(bankVO.getOrgId(), bankVO.getFinYear(),
					bankVO.getBranchCode(), screenCode);
			bankVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(bankVO.getOrgId(), bankVO.getFinYear(),
							bankVO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			bankVO.setCreatedBy(bankDTO.getCreatedBy());
			bankVO.setModifiedBy(bankDTO.getCreatedBy());
			createUpdateBankDetailsVOByBankDetailsDTO(bankDTO, bankVO);
			message = "Bank Details Submitted Successfully";
		}

		detailsSubmissionToBankRepo.save(bankVO);
		Map<String, Object> response = new HashMap<>();
		response.put("detailsSubmissionToBankVO", bankVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateBankDetailsVOByBankDetailsDTO(DetailsSubmissionToBankDTO bankDTO,
			DetailsSubmissionToBankVO bankVO) {

		// Map fields from DTO to VO
		bankVO.setOrgId(bankDTO.getOrgId());
		bankVO.setBranch(bankDTO.getBranch());
		bankVO.setBranchCode(bankDTO.getBranchCode());
		bankVO.setFinYear(bankDTO.getFinYear());
		bankVO.setDocId(bankDTO.getDocId());
		bankVO.setDocDate(bankDTO.getDocDate());
		bankVO.setInvoiceNo(bankDTO.getInvoiceNo());
		bankVO.setInvoiceDate(bankDTO.getInvoiceDate());
		bankVO.setOrgId(bankDTO.getOrgId());
		bankVO.setBranch(bankDTO.getBranch());
		bankVO.setBranchCode(bankDTO.getBranchCode());

		if (ObjectUtils.isNotEmpty(bankVO.getId())) {
			List<DetailsSubmissionToBankDetailsVO> bankDetailsVO1 = detailsSubmissionToBankDetailsRepo
					.findByDetailsSubmissionToBankVO(bankVO);
			detailsSubmissionToBankDetailsRepo.deleteAll(bankDetailsVO1);
		}

		List<DetailsSubmissionToBankDetailsVO> bankDetailsVOs = new ArrayList<>();
		for (DetailsSubmissionToBankDetailsDTO bankDetailsDTO : bankDTO.getDetailsSubmissionToBankDetailsDTO()) {

			DetailsSubmissionToBankDetailsVO bankDetailsVO = new DetailsSubmissionToBankDetailsVO();
			bankDetailsVO.setDocumentName(bankDetailsDTO.getDocumentName());
			bankDetailsVO.setStatus(bankDetailsDTO.getStatus());
			bankDetailsVO.setAttachements(bankDetailsDTO.getAttachements());
			bankDetailsVO.setDetailsSubmissionToBankVO(bankVO);
		}
		bankVO.setDetailsSubmissionToBankDetailsVO(bankDetailsVOs);
	}

	@Override
	public String getDetailsSubmissionToBankDocId(Long orgId, String finYear, String branchCode, String screenCode) {
		return detailsSubmissionToBankRepo.getBankDetailsDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<DetailsSubmissionToBankDetailsVO> uploadAttachmentsInBloob(List<MultipartFile> files, List<Long> ids)
			throws IOException {
		List<DetailsSubmissionToBankDetailsVO> updatedMasterList = new ArrayList<>();

		for (int i = 0; i < ids.size(); i++) {
			Long id = ids.get(i);
			MultipartFile file = files.get(i);

			// Find the entity by its ID
			DetailsSubmissionToBankDetailsVO masterDetailsVO = detailsSubmissionToBankDetailsRepo.findById(id)
					.orElseThrow(() -> new RuntimeException("DrawingMaster1VO not found with ID: " + id));

			// Process the file and set it as the attachment for this entity
			masterDetailsVO.setAttachements(file.getBytes());

			// Save the updated entity to the database
			DetailsSubmissionToBankDetailsVO updatedMaster = detailsSubmissionToBankDetailsRepo.save(masterDetailsVO);

			// Add the updated entity to the list
			updatedMasterList.add(updatedMaster);
		}

		// Return the list of updated entities
		return updatedMasterList;
	}
}