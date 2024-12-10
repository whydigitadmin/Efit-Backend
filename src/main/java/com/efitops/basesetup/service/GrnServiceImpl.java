package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.GrnDetailsDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.GrnDetailsVO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionDetailsVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.GrnDetailsRepo;
import com.efitops.basesetup.repo.GrnRepo;
import com.efitops.basesetup.repo.ThirdPartyInspectionDetailsRepo;
import com.efitops.basesetup.repo.ThirdPartyInspectionRepo;

@Service
public class GrnServiceImpl implements GrnService
{
	public static final Logger LOGGER = LoggerFactory.getLogger(GrnServiceImpl.class);

	
	@Autowired
	GrnRepo grnRepo;
	
	@Autowired
	GrnDetailsRepo grnDetailsRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	ThirdPartyInspectionRepo thirdPartyInspectionRepo;
	
	@Autowired
	ThirdPartyInspectionDetailsRepo thirdPartyInspectionDetailsRepo;

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
		GrnVO grnVO = new GrnVO();
		String message;
		String screenCode = "GRN";
		if (ObjectUtils.isNotEmpty(grndto.getId())) {
			grnVO = grnRepo.findById(grndto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Grn details"));
			message = "Enquiry Updated Successfully";
			grnVO.setUpdatedBy(grndto.getCreatedBy());

		} else {

			String docId = grnRepo.getGrnDocId(grndto.getOrgId(), screenCode);
			grnVO.setGrnNo(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(grndto.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			grnVO.setCreatedBy(grndto.getCreatedBy());
			grnVO.setUpdatedBy(grndto.getCreatedBy());

			message = "Enquiry Created Successfully";
		}
		createUpdateGrnVOByGrnDTO(grndto,grnVO);
		grnRepo.save(grnVO);
		Map<String, Object> response = new HashMap<>();
		response.put("grnVO", grnVO);
		response.put("message", message);
		return response;

	}
	

	private void createUpdateGrnVOByGrnDTO(@Valid GrnDTO grndto, GrnVO grnVO) throws ApplicationException {
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
		grnVO.setSupplierName(grndto.getSupplierName());

		BigDecimal grossAmount=BigDecimal.ZERO;
		BigDecimal netAmount=BigDecimal.ZERO;
		BigDecimal totalTaxAmount=BigDecimal.ZERO;
		
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
			grnDetailsVO.setAcceptQty(grnDetailsDTO.getAcceptQty());
			grnDetailsVO.setPoRate(grnDetailsDTO.getPoRate());
			grnDetailsVO.setChallanQty(grnDetailsDTO.getChallanQty());
			grnDetailsVO.setExcessQty(grnDetailsDTO.getExcessQty());
			grnDetailsVO.setRecievedQty(grnDetailsDTO.getRecievedQty());
			grnDetailsVO.setRejectQty(grnDetailsDTO.getRejectQty());
			grnDetailsVO.setIgst(grnDetailsDTO.getIgst());
			grnDetailsVO.setInspectionable(grnDetailsDTO.getInspectionable());
			grnDetailsVO.setSgst(grnDetailsDTO.getSgst());
			grnDetailsVO.setStock(grnDetailsDTO.getStock());
			grnDetailsVO.setHsnSacCode(grnDetailsDTO.getHsnSacCode());
			grnDetailsVO.setCgst(grnDetailsDTO.getCgst());
			grnDetailsVO.setOrderQty(grnDetailsDTO.getOrderQty());
			
			BigDecimal taxAmount=BigDecimal.ZERO;
			BigDecimal landedValues=BigDecimal.ZERO;
			
			
		BigDecimal amountSet =grnDetailsDTO.getPoRate().multiply(grnDetailsDTO.getAcceptQty());
			grnDetailsVO.setAmount(amountSet);
			grossAmount=grossAmount.add(grnDetailsVO.getAmount());
			grnDetailsVO.setPendingQty(grnDetailsDTO.getOrderQty().subtract(grnDetailsDTO.getChallanQty()));
			    
			     BigDecimal sgstamount = grnDetailsDTO.getSgst().multiply(grnDetailsVO.getAmount()).divide(BigDecimal.valueOf(100));
			     BigDecimal cgstamount = grnDetailsDTO.getCgst().multiply(grnDetailsVO.getAmount()).divide(BigDecimal.valueOf(100));
			     BigDecimal igstamount = grnDetailsDTO.getIgst().multiply(grnDetailsVO.getAmount()).divide(BigDecimal.valueOf(100));
			     
			     taxAmount = taxAmount.add(cgstamount).add(sgstamount).add(igstamount);
			     grnDetailsVO.setTaxValue(taxAmount);
			     totalTaxAmount=totalTaxAmount.add(grnDetailsVO.getTaxValue());
			     
			     landedValues  = grnDetailsVO.getAmount().add(grnDetailsVO.getTaxValue());
			     grnDetailsVO.setLandedValue(landedValues);
			  netAmount=netAmount.add(grnDetailsVO.getLandedValue());

			grnDetailsVO.setGrnVO(grnVO); // Set the reference in child entity
			grnDetailsVOs.add(grnDetailsVO);
		}
		
		grnVO.setGrossAmount(grossAmount);
		grnVO.setNetAmount(netAmount);
		grnVO.setTotalAmountTax(totalTaxAmount);
		
		grnVO.setGrnDetailsVO(grnDetailsVOs);
	
	}
	
	@Override
	public String getGrnDocId(Long orgId) {
		String screenCode = "GRN";
		String result = grnRepo.getGrnDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getInwardNoForGRN(Long orgId) {
		Set<Object[]> address = grnRepo.findInwardNoForGRNDetails(orgId);
		return getInwardNoForGRN(address);
	}

		private List<Map<String, Object>> getInwardNoForGRN(Set<Object[]> chCode) {
			List<Map<String, Object>> inward = new ArrayList<>();
			for (Object[] ch : chCode) {
				Map<String, Object> map = new HashMap<>();
				map.put("docid", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
				map.put("ponumber", ch[1] != null ? ch[1].toString() : "");
				map.put("suppliername", ch[2] != null ? ch[2].toString() : "");
				map.put("vehicleno", ch[3] != null ? ch[3].toString() : "");
				map.put("invoiceno", ch[4] != null ? ch[4].toString() : "");
				map.put("invoicedate", ch[5] != null ? ch[5].toString() : "");
				map.put("currency", ch[6] != null ? ch[6].toString() : "");
				map.put("gstin", ch[7] != null ? ch[7].toString() : "");
				
				inward.add(map);
			}
			return inward;
		}

		@Override
		public List<Map<String, Object>> getItemForGRN(Long orgId,String InwardNo) {
			Set<Object[]> grnitem = grnRepo.findItemForGRNDetails(orgId,InwardNo);
			return getItemForGRN(grnitem);
		}

			private List<Map<String, Object>> getItemForGRN(Set<Object[]> chCode) {
				List<Map<String, Object>>itemgrn = new ArrayList<>();
				for (Object[] ch : chCode) {
					Map<String, Object> map = new HashMap<>();
					map.put("itemname", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
					map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
					map.put("inwardqty", ch[2] != null ? ch[2].toString() : "");
					map.put("invoiceqty", ch[3] != null ? ch[3].toString() : "");
					map.put("poqty", ch[4] != null ? ch[4].toString() : "");
					map.put("uom", ch[5] != null ? ch[5].toString() : "");
					map.put("hsncode", ch[6] != null ? ch[6].toString() : "");
					map.put("inspection", ch[7] != null ? ch[7].toString() : "");
					map.put("needqcapproval", ch[8] != null ? ch[8].toString() : "");
					map.put("price", ch[9] != null ? ch[9].toString() : "");
					
					
					itemgrn.add(map);
				}
				return itemgrn;
			}

			@Override
			public List<ThirdPartyInspectionVO> getThirdPartyInspByOrgId(Long orgId) {
				List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
				if (ObjectUtils.isNotEmpty(orgId)) {
					LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
					thirdPartyInspectionVO = thirdPartyInspectionRepo.findThirdPartyInspectionOrgId(orgId);
				}
				return thirdPartyInspectionVO;
			}

			@Override
			public List<ThirdPartyInspectionVO> getThirdPartyInspById(Long id) {
				List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
				if (ObjectUtils.isNotEmpty(id)) {
					LOGGER.info("Successfully Received Shift BY Id : {}", id);
					thirdPartyInspectionVO = thirdPartyInspectionRepo.getThirdPartyInspectionById(id);
				}
				return thirdPartyInspectionVO;
			}

			@Override
			public Map<String, Object> updateCreateThirdPartyInsp(ThirdPartyInspectionDTO thirdPartyInspectionDTO)
					throws ApplicationException {
				ThirdPartyInspectionVO thirdPartyInspectionVO = new ThirdPartyInspectionVO();
				String message;
				String screenCode = "TPI";
				if (ObjectUtils.isNotEmpty(thirdPartyInspectionDTO.getId())) {
					thirdPartyInspectionVO = thirdPartyInspectionRepo.findById(thirdPartyInspectionDTO.getId())
							.orElseThrow(() -> new ApplicationException("Invalid Thirdparty Inspection  details"));
					message = " Thirdparty Inspection Updated Successfully";
					thirdPartyInspectionVO.setUpdatedBy(thirdPartyInspectionDTO.getCreatedBy());

				} else {

					String docId = thirdPartyInspectionRepo.getThirdPartyInspectionDocId(thirdPartyInspectionDTO.getOrgId(), screenCode);
					thirdPartyInspectionVO.setDocId(docId);

					// GETDOCID LASTNO +1
					DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
							.findByOrgIdAndScreenCode(thirdPartyInspectionDTO.getOrgId(), screenCode);
					documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
					documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

					thirdPartyInspectionVO.setCreatedBy(thirdPartyInspectionDTO.getCreatedBy());
					thirdPartyInspectionVO.setUpdatedBy(thirdPartyInspectionDTO.getCreatedBy());

					message = " Thirdparty Inspection Created Successfully";
				}
				createUpdateThirdPartyInspectionVOByThirdPartyInspectionDTO(thirdPartyInspectionDTO,thirdPartyInspectionVO);
				thirdPartyInspectionRepo.save(thirdPartyInspectionVO);
				Map<String, Object> response = new HashMap<>();
				response.put("thirdPartyInspectionVO", thirdPartyInspectionVO);
				response.put("message", message);
				return response;

			}
			

			private void createUpdateThirdPartyInspectionVOByThirdPartyInspectionDTO(@Valid ThirdPartyInspectionDTO thirdPartyInspectionDTO, ThirdPartyInspectionVO thirdPartyInspectionVO) throws ApplicationException {
				thirdPartyInspectionVO.setId(thirdPartyInspectionDTO.getId());
				thirdPartyInspectionVO.setGrnNo(thirdPartyInspectionDTO.getGrnNo());
				thirdPartyInspectionVO.setWorkOutNo(thirdPartyInspectionDTO.getWorkOutNo());
				thirdPartyInspectionVO.setPoNo(thirdPartyInspectionDTO.getPoNo());
				thirdPartyInspectionVO.setCustomerName(thirdPartyInspectionDTO.getCustomerName());
				thirdPartyInspectionVO.setSupplierName(thirdPartyInspectionDTO.getSupplierName());
				thirdPartyInspectionVO.setThirdPartyDetails(thirdPartyInspectionDTO.getThirdPartyDetails());
				thirdPartyInspectionVO.setThirdPartyAddress(thirdPartyInspectionDTO.getThirdPartyAddress());

				
				if(ObjectUtils.isNotEmpty(thirdPartyInspectionVO.getId())) {	
			List<ThirdPartyInspectionDetailsVO>thirdPartyInspectionDetailsVO1= thirdPartyInspectionDetailsRepo.findByThirdPartyInspectionVO(thirdPartyInspectionVO);
			thirdPartyInspectionDetailsRepo.deleteAll(thirdPartyInspectionDetailsVO1);
				}
				
				
				List<ThirdPartyInspectionDetailsVO> thirdPartyInspectionDetailsVOs = new ArrayList<>();
				for (ThirdPartyInspectionDetailsDTO thirdPartyInspectionDetailsDTO : thirdPartyInspectionDTO.getThirdPartyInspectionDetailsDTO()) {
					ThirdPartyInspectionDetailsVO thirdPartyInspectionDetailsVO = new ThirdPartyInspectionDetailsVO();
					thirdPartyInspectionDetailsVO.setItemId(thirdPartyInspectionDetailsDTO.getItemId());
					thirdPartyInspectionDetailsVO.setInspectionType(thirdPartyInspectionDetailsDTO.getInspectionType());
					thirdPartyInspectionDetailsVO.setCertificateNo(thirdPartyInspectionDetailsDTO.getCertificateNo());
					thirdPartyInspectionDetailsVO.setRemarks(thirdPartyInspectionDetailsDTO.getRemarks());
					thirdPartyInspectionDetailsVO.setStatus(thirdPartyInspectionDetailsDTO.getStatus());
					thirdPartyInspectionDetailsVO.setThirdPartyInspectionVO(thirdPartyInspectionVO);
					thirdPartyInspectionDetailsVOs.add(thirdPartyInspectionDetailsVO);
				}
				thirdPartyInspectionVO.setThirdPartyInspectionDetailsVO(thirdPartyInspectionDetailsVOs);
			
			}

			@Override
			public List<Map<String, Object>> getGRNDetForThirdPartyInsp(Long orgId) {
				Set<Object[]> grndet = thirdPartyInspectionRepo.findGRNDetForThirdPartyInsp(orgId);
				return getGRNDetForThirdPartyInsp(grndet);
			}

				private List<Map<String, Object>> getGRNDetForThirdPartyInsp(Set<Object[]> chCode) {
					List<Map<String, Object>>itemgrn = new ArrayList<>();
					for (Object[] ch : chCode) {
						Map<String, Object> map = new HashMap<>();
						map.put("grnno", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
						map.put("inwardno", ch[1] != null ? ch[1].toString() : "");
						map.put("pono", ch[2] != null ? ch[2].toString() : "");
						map.put("customer", ch[3] != null ? ch[3].toString() : "");
						map.put("suppliername", ch[4] != null ? ch[4].toString() : "");
									
						itemgrn.add(map);
					}
					return itemgrn;
				}

				@Override
				public String getThirdPartyInspectionDocId(Long orgId) {
					String screenCode = "TPI";
					String result = thirdPartyInspectionRepo.getThirdPartyInspectionDocId(orgId, screenCode);
					return result;
				}

				

}
