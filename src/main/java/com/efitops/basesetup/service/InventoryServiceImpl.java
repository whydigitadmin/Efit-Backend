package com.efitops.basesetup.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PutawayDTO;
import com.efitops.basesetup.dto.PutawayDetailsDTO;
import com.efitops.basesetup.dto.RouteCardClosureDTO;
import com.efitops.basesetup.dto.RouteCardEngDeptDTO;
import com.efitops.basesetup.dto.RouteCardEntryDTO;
import com.efitops.basesetup.dto.RouteCardEntryDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.PutawayDetailsVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.entity.RouteCardClosureVO;
import com.efitops.basesetup.entity.RouteCardEngDeptVO;
import com.efitops.basesetup.entity.RouteCardEntryDetailsVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.PutawayDetailsRepo;
import com.efitops.basesetup.repo.PutawayRepo;
import com.efitops.basesetup.repo.RouteCardClosureRepo;
import com.efitops.basesetup.repo.RouteCardEngDeptRepo;
import com.efitops.basesetup.repo.RouteCardEntryDetailsRepo;
import com.efitops.basesetup.repo.RouteCardEntryRepo;

@Service
public class InventoryServiceImpl implements InventoryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	PutawayRepo putawayRepo;

	@Autowired
	PutawayDetailsRepo putawayDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	RouteCardEntryRepo routeCardEntryRepo;

	@Autowired
	RouteCardClosureRepo routeCardClosureRepo;

	@Autowired
	RouteCardEngDeptRepo routeCardEngDeptRepo;

	@Autowired
	RouteCardEntryDetailsRepo routeCardEntryDetailsRepo;

	// Putaway

	@Override
	public List<PutawayVO> getPutawayById(Long id) {
		List<PutawayVO> putawayVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Item BY Id : {}", id);
			putawayVO = putawayRepo.findPutawayById(id);
		}
		return putawayVO;
	}

	@Override
	public List<PutawayVO> getPutawayByOrgId(Long orgId) {
		List<PutawayVO> putawayVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			putawayVO = putawayRepo.findPutawayByOrgId(orgId);
		}
		return putawayVO;
	}

	@Override
	public Map<String, Object> updateCreatePutaway(@Valid PutawayDTO putawayDTO) throws ApplicationException {
		String message;
		String screenCode = "PUT";

		PutawayVO putawayVO = new PutawayVO();

		if (putawayDTO.getId() != null) {
			// Fetch existing ItemVO for update
			putawayVO = putawayRepo.findById(putawayDTO.getId())
					.orElseThrow(() -> new ApplicationException("Putaway not found"));
			putawayVO.setUpdatedBy(putawayDTO.getCreatedBy());
			createUpdatePutawayVOByPutawayDTO(putawayDTO, putawayVO);
			message = "Putaway Updated Successfully";

			List<PutawayDetailsVO> putawayDetailsVOs = putawayDetailsRepo.findByPutawayVO(putawayVO);
			putawayDetailsRepo.deleteAll(putawayDetailsVOs);

		} else {

			// GETDOCID API
			String docId = putawayRepo.getPutawayDocId(putawayDTO.getOrgId(), screenCode);

			putawayVO.setDocId(docId);

//			        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(putawayDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			// Create new ItemVO
			putawayVO.setCreatedBy(putawayDTO.getCreatedBy());
			putawayVO.setUpdatedBy(putawayDTO.getCreatedBy());
			createUpdatePutawayVOByPutawayDTO(putawayDTO, putawayVO);
			message = "Putaway Created Successfully";
		}

		// Save the ItemVO
		putawayRepo.save(putawayVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("putawayVO", putawayVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatePutawayVOByPutawayDTO(@Valid PutawayDTO putawayDTO, PutawayVO putawayVO) {
		putawayVO.setGrnNo(putawayDTO.getGrnNo());
		putawayVO.setGrnDate(putawayDTO.getGrnDate());
		putawayVO.setSupplier(putawayDTO.getSupplier());
		putawayVO.setVehicleNo(putawayDTO.getVehicleNo());
		putawayVO.setFromLocation(putawayDTO.getFromLocation());
		putawayVO.setToLocation(putawayDTO.getToLocation());
		putawayVO.setGoodsType(putawayDTO.getGoodsType());
		putawayVO.setDcNo(putawayDTO.getDcNo());
		putawayVO.setNarration(putawayDTO.getNarration());
		putawayVO.setOrgId(putawayDTO.getOrgId());

		// Handling ItemInventoryVO
		List<PutawayDetailsVO> putawayDetailsVOs = new ArrayList<>();
		for (PutawayDetailsDTO putawayDetailsDTO : putawayDTO.getPutawayDetailsDTO()) {
			PutawayDetailsVO putawayDetailsVO = new PutawayDetailsVO();
			putawayDetailsVO.setItem(putawayDetailsDTO.getItem());
			putawayDetailsVO.setItemDesc(putawayDetailsDTO.getItemDesc());
			putawayDetailsVO.setUnit(putawayDetailsDTO.getUnit());
			putawayDetailsVO.setRecQty(putawayDetailsDTO.getRecQty());
			putawayDetailsVO.setPutawayQty(putawayDetailsDTO.getPutawayQty());
			putawayDetailsVO.setRackNo(putawayDetailsDTO.getRackNo());

			putawayDetailsVO.setPutawayVO(putawayVO); // Set the reference in child entity
			putawayDetailsVOs.add(putawayDetailsVO);
		}
		putawayVO.setPutawayDetailsVO(putawayDetailsVOs);

	}

	@Override
	public String getPutawayDocId(Long orgId) {
		String ScreenCode = "PUT";
		String result = putawayRepo.getPutawayDocId(orgId, ScreenCode);
		return result;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getGrnDetailsForPutaway(Long orgId) {

		Set<Object[]> result = putawayRepo.findGrnDetailsForPutaway(orgId);
		return getGrnDetailsForPutaway(result);
	}

	private List<Map<String, Object>> getGrnDetailsForPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("grnDate", fs[1] != null ? fs[1].toString() : "");
			part.put("supplierName", fs[2] != null ? fs[2].toString() : "");
			part.put("invDcNo", fs[3] != null ? fs[3].toString() : "");
			part.put("invoiceNo", fs[4] != null ? fs[4].toString() : "");
			part.put("vehicleNo", fs[5] != null ? fs[5].toString() : "");
//			part.put("id",fs[6]!=null ? Integer.parseInt(fs[6].toString()):0);
			
			details1.add(part);
		}
		return details1;
	}

	
	
	@Override
	@Transactional
	public List<Map<String, Object>> getLocationCodeForPutaway(Long orgId) {

		Set<Object[]> result = putawayRepo.findLocationCodeForPutaway(orgId);
		return getLocationCodeForPutaway(result);
	}

	private List<Map<String, Object>> getLocationCodeForPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("locationCode", fs[0] != null ? fs[0].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getFillGridForPutaway(Long orgId , String grnNo) {

		Set<Object[]> result = putawayRepo.findFillGridForPutaway(orgId,grnNo);
		return getFillGridForPutaway(result);
	}

	private List<Map<String, Object>> getFillGridForPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("item", fs[0] != null ? fs[0].toString() : "");
			part.put("itemDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("primaryUnit", fs[2] != null ? fs[2].toString() : "");
			part.put("receivedQty", fs[3] != null ? fs[3].toString() : "");
			part.put("id",fs[4]!=null ? Integer.parseInt(fs[4].toString()):0);

			details1.add(part);
		}
		return details1;
	}

	// Routecardentry

	@Override
	public List<RouteCardEntryVO> getRouteCardEntryById(Long id) {
		List<RouteCardEntryVO> routeCardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received RouteCardEntry BY Id : {}", id);
			routeCardEntryVO = routeCardEntryRepo.findRouteCardEntryById(id);
		}
		return routeCardEntryVO;
	}

	@Override
	public List<RouteCardEntryVO> getRouteCardEntryByOrgId(Long orgId) {
		List<RouteCardEntryVO> routeCardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received RouteCardEntry BY OrgId : {}", orgId);
			routeCardEntryVO = routeCardEntryRepo.findRouteCardEntryByOrgId(orgId);
		}
		return routeCardEntryVO;
	}

	@Override
	public Map<String, Object> updateCreateRouteCardEntry(@Valid RouteCardEntryDTO routeCardEntryDTO)
			throws ApplicationException {
		String message;
		String screenCode = "RCE";

		RouteCardEntryVO routeCardEntryVO = new RouteCardEntryVO();

		if (routeCardEntryDTO.getId() != null) {
			routeCardEntryVO = routeCardEntryRepo.findById(routeCardEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("RouteCardEntry not found"));
			routeCardEntryVO.setUpdatedBy(routeCardEntryDTO.getCreatedBy());
			createUpdateRouteCardEntryVOByRouteCardEntryDTO(routeCardEntryDTO, routeCardEntryVO);
			message = "RouteCardEntry Updated Successfully";

			List<RouteCardEntryDetailsVO> routeCardEntryDetailsVOs = routeCardEntryDetailsRepo.findByRouteCardEntryVO(routeCardEntryVO);
			routeCardEntryDetailsRepo.deleteAll(routeCardEntryDetailsVOs);

			List<RouteCardEngDeptVO> routeCardEngDeptVOs = routeCardEngDeptRepo.findByRouteCardEntryVO(routeCardEntryVO);
			routeCardEngDeptRepo.deleteAll(routeCardEngDeptVOs);
			
			List<RouteCardClosureVO> routeCardClosureVOs = routeCardClosureRepo.findByRouteCardEntryVO(routeCardEntryVO);
			routeCardClosureRepo.deleteAll(routeCardClosureVOs);

			
		} else {

			// GETDOCID API
			String docId = routeCardEntryRepo.getRouteCardEntryDocId(routeCardEntryDTO.getOrgId(), screenCode);

			routeCardEntryVO.setDocId(docId);

//			        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(routeCardEntryDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			routeCardEntryVO.setCreatedBy(routeCardEntryDTO.getCreatedBy());
			routeCardEntryVO.setUpdatedBy(routeCardEntryDTO.getCreatedBy());
			createUpdateRouteCardEntryVOByRouteCardEntryDTO(routeCardEntryDTO, routeCardEntryVO);
			message = "RouteCardEntry Created Successfully";
		}

		routeCardEntryRepo.save(routeCardEntryVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("routeCardEntryVO", routeCardEntryVO);
		response.put("message", message);
		return response;
	}
	

	private void createUpdateRouteCardEntryVOByRouteCardEntryDTO(@Valid RouteCardEntryDTO routeCardEntryDTO, RouteCardEntryVO routeCardEntryVO) {
		routeCardEntryVO.setCustomerName(routeCardEntryDTO.getCustomerName());
		routeCardEntryVO.setWoNo(routeCardEntryDTO.getWoNo());
		routeCardEntryVO.setFgPartName(routeCardEntryDTO.getFgPartName());
		routeCardEntryVO.setFgPartDesc(routeCardEntryDTO.getFgPartDesc());
		routeCardEntryVO.setFgQty(routeCardEntryDTO.getFgQty());
		routeCardEntryVO.setBatchQty(routeCardEntryDTO.getBatchQty());
		routeCardEntryVO.setRmType(routeCardEntryDTO.getRmType());
		routeCardEntryVO.setRmSize(routeCardEntryDTO.getRmSize());
		routeCardEntryVO.setRmbatchNo(routeCardEntryDTO.getRmbatchNo());
		routeCardEntryVO.setRmQty(routeCardEntryDTO.getRmQty());
		routeCardEntryVO.setNarration(routeCardEntryDTO.getNarration());
		routeCardEntryVO.setOrgId(routeCardEntryDTO.getOrgId());
		routeCardEntryVO.setInvoice(routeCardEntryDTO.getInvoice());
		routeCardEntryVO.setInvoiceDate(routeCardEntryDTO.getInvoiceDate());
		routeCardEntryVO.setQty(routeCardEntryDTO.getQty());
		routeCardEntryVO.setStockQty(routeCardEntryDTO.getStockQty());
		routeCardEntryVO.setStatus(routeCardEntryDTO.getStatus());


		//RouteCardEntryDetails
		List<RouteCardEntryDetailsVO> routeCardEntryDetailsVOs = new ArrayList<>();
		for (RouteCardEntryDetailsDTO routeCardEntryDetailsDTO : routeCardEntryDTO.getRouteCardEntryDetailsDTO()) {
			RouteCardEntryDetailsVO routeCardEntryDetailsVO = new RouteCardEntryDetailsVO();
			routeCardEntryDetailsVO.setOperationDesc(routeCardEntryDetailsDTO.getOperationDesc());
			routeCardEntryDetailsVO.setMachineCenter(routeCardEntryDetailsDTO.getMachineCenter());
			routeCardEntryDetailsVO.setProcessStart(routeCardEntryDetailsDTO.getProcessStart());
			routeCardEntryDetailsVO.setProcessEnd(routeCardEntryDetailsDTO.getProcessEnd());
			routeCardEntryDetailsVO.setAcceptedQty(routeCardEntryDetailsDTO.getAcceptedQty());
			routeCardEntryDetailsVO.setQtyRework(routeCardEntryDetailsDTO.getQtyRework());
			routeCardEntryDetailsVO.setReject(routeCardEntryDetailsDTO.getReject());
			routeCardEntryDetailsVO.setOptr(routeCardEntryDetailsDTO.getOptr());
			routeCardEntryDetailsVO.setRemarks(routeCardEntryDetailsDTO.getRemarks());

			routeCardEntryDetailsVO.setRouteCardEntryVO(routeCardEntryVO); // Set the reference in child entity
			routeCardEntryDetailsVOs.add(routeCardEntryDetailsVO);
		}
		routeCardEntryVO.setRouteCardEntryDetailsVO(routeCardEntryDetailsVOs);

		
		//RouteCardEntryClosure
		List<RouteCardClosureVO> routeCardClosureVOs = new ArrayList<>();
		for (RouteCardClosureDTO routeCardClosureDTO : routeCardEntryDTO.getRouteCardClosureDTO()) {
			RouteCardClosureVO routeCardClosureVO = new RouteCardClosureVO();
			routeCardClosureVO.setQaManagerSign(routeCardClosureDTO.getQaManagerSign());
	        routeCardClosureVO.setQaManagerSignDate(routeCardClosureDTO.getQaManagerSignDate()); 
	        routeCardClosureVO.setPlantManagerSign(routeCardClosureDTO.getPlantManagerSign()); 
	        routeCardClosureVO.setPlantManagerSignDate(routeCardClosureDTO.getPlantManagerSignDate()); 

			

			routeCardClosureVO.setRouteCardEntryVO(routeCardEntryVO); // Set the reference in child entity
			routeCardClosureVOs.add(routeCardClosureVO);
		}
		routeCardEntryVO.setRouteCardClosureVO(routeCardClosureVOs);

		//RouteCardEngDept
		List<RouteCardEngDeptVO> routeCardEngDeptVOs = new ArrayList<>();
		for (RouteCardEngDeptDTO routeCardEngDeptDTO : routeCardEntryDTO.getRouteCardEngDeptDTO()) {
			RouteCardEngDeptVO routeCardEngDeptVO = new RouteCardEngDeptVO();
			routeCardEngDeptVO.setPreparedBy(routeCardEngDeptDTO.getPreparedBy());
			routeCardEngDeptVO.setPreparedDate(routeCardEngDeptDTO.getPreparedDate()); 
			routeCardEngDeptVO.setApprovedBy(routeCardEngDeptDTO.getApprovedBy()); 
			routeCardEngDeptVO.setApprovedDate(routeCardEngDeptDTO.getApprovedDate()); 

			routeCardEngDeptVO.setRouteCardEntryVO(routeCardEntryVO); // Set the reference in child entity
			routeCardEngDeptVOs.add(routeCardEngDeptVO);
		}
		routeCardEntryVO.setRouteCardEngDeptVO(routeCardEngDeptVOs);

	}
	
	@Override
	public String getRouteCardEntryDocId(Long orgId) {
		String ScreenCode = "RCE";
		String result = routeCardEntryRepo.getRouteCardEntryDocId(orgId, ScreenCode);
		return result;
	}


}
