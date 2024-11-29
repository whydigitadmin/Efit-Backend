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

import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.GrnDetailsDTO;
import com.efitops.basesetup.entity.GrnDetailsVO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.GrnDetailsRepo;
import com.efitops.basesetup.repo.GrnRepo;

@Service
public class GrnServiceImpl implements GrnService
{
	public static final Logger LOGGER = LoggerFactory.getLogger(GrnServiceImpl.class);

	
	@Autowired
	GrnRepo grnRepo;
	
	@Autowired
	GrnDetailsRepo grnDetailsRepo;

	@Override
	public List<GrnVO> getGrnByOrgId(Long orgId) {
		List<GrnVO> grnVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			grnVO = grnRepo.findGrnByOrgId(orgId);
		}
		return grnVO;
	}
	
	@Override
	public List<GrnVO> getGrnById (Long id) {
		List<GrnVO> grnVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			grnVO = grnRepo.getGrnById(id);
		}
		return grnVO;
	}
	@Override
	public Map<String, Object> updateCreateGrn(GrnDTO grndto) throws ApplicationException {
		String screenCode = "D";
		GrnVO grnVO = new GrnVO();
		String message;
		if (ObjectUtils.isNotEmpty(grndto.getId())) {
			grnVO = grnRepo.findById(grndto.getId()).orElseThrow(() -> new ApplicationException("Uom not found")); 
			message="Grn Updated sucessfully";
		}else {
			
			grnVO.setCreatedBy(grndto.getCreatedBy());
			grnVO.setUpdatedBy(grndto.getCreatedBy());
			message="Grn Created sucessfully";
		}
		createUpdateGrnVOByGrnDTO(grndto,grnVO);
		grnRepo.save(grnVO);
		Map<String, Object> response = new HashMap<>();
		response.put("grnVO", grnVO);
		response.put("message", message);
		return response;

	}
	

	private void createUpdateGrnVOByGrnDTO(@Valid GrnDTO grndto, GrnVO grnVO) throws ApplicationException {
		grnVO.setGrnNo(grndto.getSupplierName());
		grnVO.setInwardNo(grndto.getInwardNo());
		grnVO.setLocation(grndto.getLocation());
		grnVO.setGstNo(grndto.getGstNo());
		grnVO.setPoNo(grndto.getPoNo());
		grnVO.setOrgId(grndto.getOrgId());
		grnVO.setGstType(grndto.getGstType());
		grnVO.setAdress(grndto.getAdress());
		grnVO.setCurrency(grndto.getCurrency());
		grnVO.setExchangeRate(grndto.getExchangeRate());
		grnVO.setGrnClearTime(grndto.getGrnClearTime());
		grnVO.setInvDcNo(grndto.getInvDcNo());
		grnVO.setInvDcDate(grndto.getInvDcDate());
		grnVO.setCustomer(grndto.getCustomer());
		
		if(ObjectUtils.isNotEmpty(grnVO.getId())) {	
	List<GrnDetailsVO>grnDetailsVo1= grnDetailsRepo.findByGrnVO(grnVO);
	grnDetailsRepo.deleteAll(grnDetailsVo1);
		}
		
		
		List<GrnDetailsVO> grnDetailsVOs = new ArrayList<>();
		for (GrnDetailsDTO grnDetailsDTO : grndto.getGrnDetailsDTO()) {
			GrnDetailsVO grnDetailsVO = new GrnDetailsVO();
			grnDetailsVO.setItemCode(grnDetailsDTO.getItemCode());
			grnDetailsVO.setItemDesc(grnDetailsDTO.getItemDesc());
			grnDetailsVO.setHsnSacCode(grnDetailsDTO.getHsnSacCode());
			grnDetailsVO.setTaxType(grnDetailsDTO.getTaxType());
			grnDetailsVO.setPrimaryUnit(grnDetailsDTO.getPrimaryUnit());
			grnDetailsVO.setTaxValue(grnDetailsDTO.getTaxValue());
			grnDetailsVO.setPendingQty(grnDetailsDTO.getPendingQty());
			grnDetailsVO.setAcceptQty(grnDetailsDTO.getAcceptQty());
			grnDetailsVO.setAmount(grnDetailsDTO.getAmount());
			grnDetailsVO.setChallanQty(grnDetailsDTO.getChallanQty());
			grnDetailsVO.setExcessQty(grnDetailsDTO.getExcessQty());
			grnDetailsVO.setRecievedQty(grnDetailsDTO.getRecievedQty());
			grnDetailsVO.setRejectQty(grnDetailsDTO.getRejectQty());
			grnDetailsVO.setPoRate(grnDetailsDTO.getPoRate());
			grnDetailsVO.setIgst(grnDetailsDTO.getIgst());
			grnDetailsVO.setInspectionable(grnDetailsDTO.getInspectionable());
			grnDetailsVO.setSgst(grnDetailsDTO.getSgst());
			grnDetailsVO.setStock(grnDetailsDTO.getStock());
			grnDetailsVO.setHsnSacCode(grnDetailsDTO.getHsnSacCode());
			grnDetailsVO.setCgst(grnDetailsDTO.getCgst());
			grnDetailsVO.setLandedValue(grnDetailsDTO.getLandedValue());

			grnDetailsVO.setGrnVO(grnVO); // Set the reference in child entity
			grnDetailsVOs.add(grnDetailsVO);
		}
		grnVO.setGrnDetailsVO(grnDetailsVOs);
	
	}


}
