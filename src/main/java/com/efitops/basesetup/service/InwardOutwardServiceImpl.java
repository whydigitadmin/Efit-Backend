package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.GateInwardEntryDetailsDTO;
import com.efitops.basesetup.dto.GateOutwardEntryDTO;
import com.efitops.basesetup.dto.GateOutwardEntryDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.GateInwardEntryDetailsVO;
import com.efitops.basesetup.entity.GateInwardEntryVO;
import com.efitops.basesetup.entity.GateOutwardEntryDetailsVO;
import com.efitops.basesetup.entity.GateOutwardEntryVO;
import com.efitops.basesetup.entity.ParticularsJournalVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.GateInwardEntryDetailsRepo;
import com.efitops.basesetup.repo.GateInwardEntryRepo;
import com.efitops.basesetup.repo.GateOutwardEntryDetailsRepo;
import com.efitops.basesetup.repo.GateOutwardEntryRepo;

@Service
public class InwardOutwardServiceImpl implements InwardOutwardService{

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardOutwardServiceImpl.class);

	@Autowired
	GateInwardEntryRepo gateInwardEntryRepo;
	
	@Autowired
	GateInwardEntryDetailsRepo gateInwardEntryDetailsRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	GateOutwardEntryRepo gateOutwardEntryRepo;
	
	@Autowired
	GateOutwardEntryDetailsRepo gateOutwardEntryDetailsRepo;
	
	@Override
	public List<GateInwardEntryVO> getGateInwardEntryByOrgId(Long orgId) {
		List<GateInwardEntryVO> gateInwardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received GateInwardEntry BY OrgId : {}", orgId);
			gateInwardEntryVO = gateInwardEntryRepo.findgetGateInwardEntryByOrgId(orgId);
		}
		return gateInwardEntryVO;
	}
	
	@Override
	public List<GateInwardEntryVO> getGateInwardEntryById(Long id) {
		List<GateInwardEntryVO> gateInwardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received GateInwardEntry BY Id : {}", id);
			gateInwardEntryVO = gateInwardEntryRepo.findgetGateInwardEntryById(id);
		}
		return gateInwardEntryVO;
	}
	
	@Override
	public Map<String, Object> updateCreateGateInwardEntry(@Valid GateInwardEntryDTO gateInwardEntryDTO) throws ApplicationException {
		String message;
        String screenCode="GIE";
		GateInwardEntryVO gateInwardEntryVO = new GateInwardEntryVO();

		if (gateInwardEntryDTO.getId() != null) {
			// Fetch existing ItemVO for update
			gateInwardEntryVO = gateInwardEntryRepo.findById(gateInwardEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("GateInwardEntry not found"));
			gateInwardEntryVO.setUpdatedBy(gateInwardEntryDTO.getCreatedBy());
			createUpdateGateInwardEntryVOByGateInwardEntryDTO(gateInwardEntryDTO, gateInwardEntryVO);
			message = "GateInwardEntry Updated Successfully";

			List<GateInwardEntryDetailsVO> gateInwardEntryDetailsVOs = gateInwardEntryDetailsRepo.findByGateInwardEntryVO(gateInwardEntryVO);
			gateInwardEntryDetailsRepo.deleteAll(gateInwardEntryDetailsVOs);

			
		} else {
			
			// GETDOCID API
						String docId = gateInwardEntryRepo.getGateInwardEntryByDocId(gateInwardEntryDTO.getOrgId(),
								screenCode);

						gateInwardEntryVO.setDocId(docId);

//			        							// GETDOCID LASTNO +1
						DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
								.findByOrgIdAndScreenCode(gateInwardEntryDTO.getOrgId(), screenCode);
						documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
						documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			
			
			// Create new ItemVO
			gateInwardEntryVO.setCreatedBy(gateInwardEntryDTO.getCreatedBy());
			gateInwardEntryVO.setUpdatedBy(gateInwardEntryDTO.getCreatedBy());
			createUpdateGateInwardEntryVOByGateInwardEntryDTO(gateInwardEntryDTO, gateInwardEntryVO);
			message = "GateInwardEntry Created Successfully";
		}

		// Save the ItemVO
		gateInwardEntryRepo.save(gateInwardEntryVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("gateInwardEntryVO", gateInwardEntryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateGateInwardEntryVOByGateInwardEntryDTO(@Valid GateInwardEntryDTO gateInwardEntryDTO, GateInwardEntryVO gateInwardEntryVO) {
		gateInwardEntryVO.setSupplierName(gateInwardEntryDTO.getSupplierName());
		gateInwardEntryVO.setPoNumber(gateInwardEntryDTO.getPoNumber());
		gateInwardEntryVO.setInvoiceNo(gateInwardEntryDTO.getInvoiceNo());
		gateInwardEntryVO.setInvoiceDate(gateInwardEntryDTO.getInvoiceDate());
		gateInwardEntryVO.setVehicleNo(gateInwardEntryDTO.getVehicleNo());
		gateInwardEntryVO.setCourierNo(gateInwardEntryDTO.getCourierNo());
		gateInwardEntryVO.setCourierName(gateInwardEntryDTO.getCourierName());
		gateInwardEntryVO.setNarration(gateInwardEntryDTO.getNarration());
		gateInwardEntryVO.setOrgId(gateInwardEntryDTO.getOrgId());

		// Handling ItemInventoryVO
		List<GateInwardEntryDetailsVO> gateInwardEntryDetailsVOs = new ArrayList<>();
		for (GateInwardEntryDetailsDTO gateInwardEntryDetailsDTO : gateInwardEntryDTO.getGateInwardEntryDetailsDTO()) {
			GateInwardEntryDetailsVO gateInwardEntryDetailsVO = new GateInwardEntryDetailsVO();
			gateInwardEntryDetailsVO.setItemName(gateInwardEntryDetailsDTO.getItemName());
			gateInwardEntryDetailsVO.setItemDesc(gateInwardEntryDetailsDTO.getItemDesc());
			gateInwardEntryDetailsVO.setUom(gateInwardEntryDetailsDTO.getUom());
			gateInwardEntryDetailsVO.setPoQty(gateInwardEntryDetailsDTO.getPoQty());
			gateInwardEntryDetailsVO.setInvoiceQty(gateInwardEntryDetailsDTO.getInvoiceQty());
			gateInwardEntryDetailsVO.setInwardQty(gateInwardEntryDetailsDTO.getInwardQty());
			gateInwardEntryDetailsVO.setBalanceQty(gateInwardEntryDetailsDTO.getBalanceQty());
			gateInwardEntryDetailsVO.setExcessQty(gateInwardEntryDetailsDTO.getExcessQty());

			gateInwardEntryDetailsVO.setGateInwardEntryVO(gateInwardEntryVO); // Set the reference in child entity
			gateInwardEntryDetailsVOs.add(gateInwardEntryDetailsVO);
		}
		gateInwardEntryVO.setGateInwardEntryDetailsVO(gateInwardEntryDetailsVOs);

	}
	
	
	@Override
	public String getGateInwardEntryDocId(Long orgId) {
		String ScreenCode = "GIE";
		String result = gateInwardEntryRepo.getGateInwardEntryDocId(orgId, ScreenCode);
		return result;
	}
	
	//GateOutWard
	
	@Override
	public List<GateOutwardEntryVO> getGateOutwardEntryByOrgId(Long orgId) {
		List<GateOutwardEntryVO> gateOutwardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received GateInwardEntry BY OrgId : {}", orgId);
			gateOutwardEntryVO = gateOutwardEntryRepo.findGateOutwardEntryByOrgId(orgId);
		}
		return gateOutwardEntryVO;
	}
	
	@Override
	public List<GateOutwardEntryVO> getGateOutwardEntryById(Long id) {
		List<GateOutwardEntryVO> gateOutwardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received GateInwardEntry BY OrgId : {}", id);
			gateOutwardEntryVO = gateOutwardEntryRepo.findGateOutwardEntryById(id);
		}
		return gateOutwardEntryVO;
	}
	
	@Override
	public Map<String, Object> updateCreateGateOutwardEntry(@Valid GateOutwardEntryDTO gateOutwardEntryDTO) throws ApplicationException {
		String message;
        String screenCode="GOE";
		GateOutwardEntryVO gateOutwardEntryVO = new GateOutwardEntryVO();

		if (gateOutwardEntryDTO.getId() != null) {
			// Fetch existing ItemVO for update
			gateOutwardEntryVO = gateOutwardEntryRepo.findById(gateOutwardEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("GateOutwardEntry not found"));
			gateOutwardEntryVO.setUpdatedBy(gateOutwardEntryDTO.getCreatedBy());
			createUpdateGateOutwardEntryVOByGateOutwardEntryDTO(gateOutwardEntryDTO, gateOutwardEntryVO);
			message = "GateOutwardEntry Updated Successfully";

//			List<GateOutwardEntryDetailsVO> gateOutwardEntryDetailsVOs = gateOutwardEntryDetailsRepo.findByGateOutwardEntryVO(gateOutwardEntryVO);
//			gateOutwardEntryDetailsRepo.deleteAll(gateOutwardEntryDetailsVOs);

			
		} else {
			
			// Create new ItemVO
			gateOutwardEntryVO.setCreatedBy(gateOutwardEntryDTO.getCreatedBy());
			gateOutwardEntryVO.setUpdatedBy(gateOutwardEntryDTO.getCreatedBy());
			createUpdateGateOutwardEntryVOByGateOutwardEntryDTO(gateOutwardEntryDTO, gateOutwardEntryVO);
			message = "GateOutwardEntry Created Successfully";
		}

		// Save the ItemVO
		gateOutwardEntryRepo.save(gateOutwardEntryVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("gateOutwardEntryVO", gateOutwardEntryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateGateOutwardEntryVOByGateOutwardEntryDTO(@Valid GateOutwardEntryDTO gateOutwardEntryDTO, GateOutwardEntryVO gateOutwardEntryVO) {
		gateOutwardEntryVO.setCustomerNo(gateOutwardEntryDTO.getCustomerNo());
		gateOutwardEntryVO.setType(gateOutwardEntryDTO.getType());
		gateOutwardEntryVO.setDeliveryChallanNo(gateOutwardEntryDTO.getDeliveryChallanNo());
		gateOutwardEntryVO.setDeliveryChallanDate(gateOutwardEntryDTO.getDeliveryChallanDate());
		gateOutwardEntryVO.setInvoiceNo(gateOutwardEntryDTO.getInvoiceNo());
		gateOutwardEntryVO.setInvoiceDate(gateOutwardEntryDTO.getInvoiceDate());
		gateOutwardEntryVO.setModeOfShipment(gateOutwardEntryDTO.getModeOfShipment());
		gateOutwardEntryVO.setVehicleNo(gateOutwardEntryDTO.getVehicleNo());
		gateOutwardEntryVO.setNarration(gateOutwardEntryDTO.getNarration());
		gateOutwardEntryVO.setOrgId(gateOutwardEntryDTO.getOrgId());

		if (gateOutwardEntryDTO.getId() != null) {
			List<GateOutwardEntryDetailsVO> entryDetailsVOs = gateOutwardEntryDetailsRepo
					.findByGateOutwardEntryVO(gateOutwardEntryVO);
			gateOutwardEntryDetailsRepo.deleteAll(entryDetailsVOs);
		}
		
		List<GateOutwardEntryDetailsVO> gateOutwardEntryDetailsVOs = new ArrayList<>();
		for (GateOutwardEntryDetailsDTO gateOutwardEntryDetailsDTO : gateOutwardEntryDTO.getGateOutwardEntryDetailsDTO()) {
			GateOutwardEntryDetailsVO gateOutwardEntryDetailsVO = new GateOutwardEntryDetailsVO();
			gateOutwardEntryDetailsVO.setItem(gateOutwardEntryDetailsDTO.getItem());
			gateOutwardEntryDetailsVO.setItemDesc(gateOutwardEntryDetailsDTO.getItemDesc());
			gateOutwardEntryDetailsVO.setUom(gateOutwardEntryDetailsDTO.getUom());
			gateOutwardEntryDetailsVO.setQty(gateOutwardEntryDetailsDTO.getQty());

			gateOutwardEntryDetailsVO.setGateOutwardEntryVO(gateOutwardEntryVO); // Set the reference in child entity
			gateOutwardEntryDetailsVOs.add(gateOutwardEntryDetailsVO);
		}
		gateOutwardEntryVO.setGateOutwardEntryDetailsVO(gateOutwardEntryDetailsVOs);

	}

}
