package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.ItemIssueToProductionDTO;
import com.efitops.basesetup.dto.ItemIssueToProductionDetailsDTO;
import com.efitops.basesetup.dto.PickListDTO;
import com.efitops.basesetup.dto.PickListDetailsDTO;
import com.efitops.basesetup.dto.PutawayDTO;
import com.efitops.basesetup.dto.PutawayDetailsDTO;
import com.efitops.basesetup.dto.RouteCardClosureDTO;
import com.efitops.basesetup.dto.RouteCardEngDeptDTO;
import com.efitops.basesetup.dto.RouteCardEntryDTO;
import com.efitops.basesetup.dto.RouteCardEntryDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DrawingMaster2VO;
import com.efitops.basesetup.entity.ItemIssueToProductionDetailsVO;
import com.efitops.basesetup.entity.ItemIssueToProductionVO;
import com.efitops.basesetup.entity.PickListDetailsVO;
import com.efitops.basesetup.entity.PickListVO;
import com.efitops.basesetup.entity.PutawayDetailsVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.entity.RouteCardClosureVO;
import com.efitops.basesetup.entity.RouteCardEngDeptVO;
import com.efitops.basesetup.entity.RouteCardEntryDetailsVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ItemIssueToProductionDetailsRepo;
import com.efitops.basesetup.repo.ItemIssueToProductionRepo;
import com.efitops.basesetup.repo.PickListDetailsRepo;
import com.efitops.basesetup.repo.PickListRepo;
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
	
	@Autowired
	PickListRepo pickListRepo;
	
	@Autowired
	PickListDetailsRepo pickListDetailsRepo;
	
	@Autowired
	ItemIssueToProductionRepo itemIssueToProductionRepo;
	
	@Autowired
	ItemIssueToProductionDetailsRepo itemIssueToProductionDetailsRepo;

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

			putawayVO.setCreatedBy(putawayDTO.getCreatedBy());
			putawayVO.setUpdatedBy(putawayDTO.getCreatedBy());
			createUpdatePutawayVOByPutawayDTO(putawayDTO, putawayVO);
			message = "Putaway Created Successfully";
		}

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
	public List<Map<String, Object>> getRackNoForPutaway(Long orgId) {

		Set<Object[]> result = putawayRepo.findRackNoForPutaway(orgId);
		return getRackNoForPutaway(result);
	}

	private List<Map<String, Object>> getRackNoForPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("rackNo", fs[0] != null ? fs[0].toString() : "");
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
	
	@Override
	public List<Map<String, Object>> getCustomerNameAndCodeFromRouteCardEntry(Long orgId) {
		Set<Object[]> customerDetails = routeCardEntryRepo.findCustomerNameAndCodeFromRouteCardEntry(orgId);
		return getCustomerNameAndCodeFromRouteCardEntry(customerDetails);
	}

	private List<Map<String, Object>> getCustomerNameAndCodeFromRouteCardEntry(Set<Object[]> customerDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : customerDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("customer", ch[0] != null ? ch[0].toString() : "");
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");
			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getOptrSignFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findOptrSignFromRouteCardEntry(orgId);
		return getOptrSignFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> getOptrSignFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("optrSign", ch[0] != null ? ch[0].toString() : "");			
			List1.add(map);
		}
		return List1;
	}
	
	
	@Override
	public List<Map<String, Object>> getPreparedByFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findPreparedByFromRouteCardEntry(orgId);
		return getOptrSignFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> getPreparedByFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("preparedBy", ch[0] != null ? ch[0].toString() : "");			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getApprovedByFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findApprovedByFromRouteCardEntry(orgId);
		return getApprovedByFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> getApprovedByFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("approvedBy", ch[0] != null ? ch[0].toString() : "");			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getQAManagerSignFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findQAManagerSignFromRouteCardEntry(orgId);
		return getQAManagerSignFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> getQAManagerSignFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("qaManagerSign", ch[0] != null ? ch[0].toString() : "");			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getPlantManagerSignFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findPlantManagerSignFromRouteCardEntry(orgId);
		return getPlantManagerSignFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> getPlantManagerSignFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("plantManagerSign", ch[0] != null ? ch[0].toString() : "");			
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getWorkOrderNoFromRouteCardEntry(Long orgId,String customer) {
		Set<Object[]> workOrderNo = routeCardEntryRepo.findWorkOrderNoFromRouteCardEntry(orgId,customer);
		return getWorkOrderNoFromRouteCardEntry(workOrderNo);
	}

	private List<Map<String, Object>> getWorkOrderNoFromRouteCardEntry(Set<Object[]> workOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getFgPartNameAndDescAndQtyFromRouteCardEntry(Long orgId,String workOrderNo) {
		Set<Object[]> fgDetails = routeCardEntryRepo.findFgPartNameAndDescAndQtyFromRouteCardEntry(orgId,workOrderNo);
		return getFgPartNameAndDescAndQtyFromRouteCardEntry(fgDetails);
	}

	private List<Map<String, Object>> getFgPartNameAndDescAndQtyFromRouteCardEntry(Set<Object[]> fgDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : fgDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("fgPartName", ch[0] != null ? ch[0].toString() : "");
			map.put("fgPartDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("fgQt", ch[2] != null ? ch[2].toString() : "");

			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public RouteCardEntryVO uploadFileForRouteCardEntry(MultipartFile file, Long id) throws IOException {
		RouteCardEntryVO routeCardEntryVO = routeCardEntryRepo.findById(id).get();
		routeCardEntryVO.setAttachements(file.getBytes());
		return routeCardEntryRepo.save(routeCardEntryVO);
	}
	
	//PickList

	@Override
	public List<PickListVO> getPickListById(Long id) {
		List<PickListVO> pickListVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received PickList BY Id : {}", id);
			pickListVO = pickListRepo.findPickListById(id);
		}
		return pickListVO;
	}
	
	@Override
	public List<PickListVO> getPickListByOrgId(Long orgId) {
		List<PickListVO> pickListVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received PickList BY OrgId : {}", orgId);
			pickListVO = pickListRepo.findPickListByOrgId(orgId);
		}
		return pickListVO;
	}
	
	
	@Override
	public Map<String, Object> updateCreatePickList(@Valid PickListDTO pickListDTO) throws ApplicationException {
		String message;
		String screenCode = "PL";

		PickListVO pickListVO = new PickListVO();

		if (pickListDTO.getId() != null) {
			pickListVO = pickListRepo.findById(pickListDTO.getId())
					.orElseThrow(() -> new ApplicationException("PickList not found"));
			pickListVO.setUpdatedBy(pickListDTO.getCreatedBy());
			createUpdatePickListVOByPickListDTO(pickListDTO, pickListVO);
			message = "PickList Updated Successfully";

			List<PickListDetailsVO> pickListDetailsVOs = pickListDetailsRepo.findByPickListVO(pickListVO);
			pickListDetailsRepo.deleteAll(pickListDetailsVOs);

		} else {

			// GETDOCID API
			String docId = pickListRepo.getPickListDocId(pickListDTO.getOrgId(), screenCode);

			pickListVO.setDocId(docId);

//			        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(pickListDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			pickListVO.setCreatedBy(pickListDTO.getCreatedBy());
			pickListVO.setUpdatedBy(pickListDTO.getCreatedBy());
			createUpdatePickListVOByPickListDTO(pickListDTO, pickListVO);
			message = "PickList Created Successfully";
		}

		pickListRepo.save(pickListVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("pickListVO", pickListVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatePickListVOByPickListDTO(@Valid PickListDTO pickListDTO, PickListVO pickListVO) {
		pickListVO.setCustomerName(pickListDTO.getCustomerName());
		pickListVO.setRouteCardNo(pickListDTO.getRouteCardNo());
		pickListVO.setWorkOrderNo(pickListDTO.getWorkOrderNo());
		pickListVO.setItemIssueToProductionNo(pickListDTO.getItemIssueToProductionNo());
		pickListVO.setDepartment(pickListDTO.getDepartment());
		pickListVO.setLocation(pickListDTO.getLocation());
		pickListVO.setShift(pickListDTO.getShift());
		pickListVO.setPickedBy(pickListDTO.getPickedBy());
		pickListVO.setFgPartNo(pickListDTO.getFgPartNo());
		pickListVO.setOrgId(pickListDTO.getOrgId());

		List<PickListDetailsVO> pickListDetailsVOs = new ArrayList<>();
		for (PickListDetailsDTO pickListDetailsDTO : pickListDTO.getPickListDetailsDTO()) {
			PickListDetailsVO pickListDetailsVO = new PickListDetailsVO();
			pickListDetailsVO.setItem(pickListDetailsDTO.getItem());
			pickListDetailsVO.setItemName(pickListDetailsDTO.getItemName());
			pickListDetailsVO.setUnit(pickListDetailsDTO.getUnit());
			pickListDetailsVO.setRackNo(pickListDetailsDTO.getRackNo());
			pickListDetailsVO.setRackQty(pickListDetailsDTO.getRackQty());
			pickListDetailsVO.setIssuedQty(pickListDetailsDTO.getIssuedQty());	
			pickListDetailsVO.setPickedQty(pickListDetailsDTO.getPickedQty());
			pickListDetailsVO.setRemainingQty(pickListDetailsDTO.getRemainingQty());
			pickListDetailsVO.setActualQty(pickListDetailsDTO.getActualQty());
			pickListDetailsVO.setFlag(pickListDetailsDTO.isFlag());


			pickListDetailsVO.setPickListVO(pickListVO); // Set the reference in child entity
			pickListDetailsVOs.add(pickListDetailsVO);
		}
		pickListVO.setPickListDetailsVO(pickListDetailsVOs);

	}

	@Override
	public String getPickListDocId(Long orgId) {
		String ScreenCode = "PL";
		String result = pickListRepo .getPickListDocId(orgId, ScreenCode);
		return result;
	}
	
	//ItemissueToProduction
	
	@Override
	public List<ItemIssueToProductionVO> getItemIssToProdById(Long id) {
		List<ItemIssueToProductionVO> itemIssueToProductionVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ItemIssueToProduction BY Id : {}", id);
			itemIssueToProductionVO = itemIssueToProductionRepo.findItemIssueToProductionById(id);
		}
		return itemIssueToProductionVO;
	}

	@Override
	public List<ItemIssueToProductionVO> getItemIssToProdByOrgId(Long orgId) {
		List<ItemIssueToProductionVO> itemIssueToProductionVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received ItemIssueToProduction BY Id : {}", orgId);
			itemIssueToProductionVO = itemIssueToProductionRepo.findItemIssueToProductionByOrgId(orgId);
		}
		return itemIssueToProductionVO;
	}
	
	@Override
	public Map<String, Object> updateCreateItemIssToProd(@Valid ItemIssueToProductionDTO itemIssueToProductionDTO) throws ApplicationException {
		String message;
		String screenCode = "IITP";

		ItemIssueToProductionVO itemIssueToProductionVO = new ItemIssueToProductionVO();

		if (itemIssueToProductionDTO.getId() != null) {
			itemIssueToProductionVO = itemIssueToProductionRepo.findById(itemIssueToProductionDTO.getId())
					.orElseThrow(() -> new ApplicationException("ItemIssueToProduction not found"));
			itemIssueToProductionVO.setUpdatedBy(itemIssueToProductionDTO.getCreatedBy());
			createUpdateItemIssueToProductionVOByItemIssueToProductionDTO(itemIssueToProductionDTO, itemIssueToProductionVO);
			message = "ItemIssueToProduction Updated Successfully";

			List<ItemIssueToProductionDetailsVO> itemIssueToProductionDetailsVOs = itemIssueToProductionDetailsRepo.findByItemIssueToProductionVO(itemIssueToProductionVO);
			itemIssueToProductionDetailsRepo.deleteAll(itemIssueToProductionDetailsVOs);

		} else {

			// GETDOCID API
			String docId = itemIssueToProductionRepo.getItemIssueToProductionDocId(itemIssueToProductionDTO.getOrgId(), screenCode);

			itemIssueToProductionVO.setDocId(docId);

//			        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(itemIssueToProductionDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			itemIssueToProductionVO.setCreatedBy(itemIssueToProductionDTO.getCreatedBy());
			itemIssueToProductionVO.setUpdatedBy(itemIssueToProductionDTO.getCreatedBy());
			createUpdateItemIssueToProductionVOByItemIssueToProductionDTO(itemIssueToProductionDTO, itemIssueToProductionVO);
			message = "ItemIssueToProduction Created Successfully";
		}

		itemIssueToProductionRepo.save(itemIssueToProductionVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("itemIssToProdVO", itemIssueToProductionVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateItemIssueToProductionVOByItemIssueToProductionDTO(@Valid ItemIssueToProductionDTO itemIssueToProductionDTO, ItemIssueToProductionVO itemIssueToProductionVO) {
		itemIssueToProductionVO.setRouteCardNo(itemIssueToProductionDTO.getRouteCardNo());
		itemIssueToProductionVO.setWorkorder(itemIssueToProductionDTO.getWorkorder());
		itemIssueToProductionVO.setFgItemId(itemIssueToProductionDTO.getFgItemId());
		itemIssueToProductionVO.setFgItemDesc(itemIssueToProductionDTO.getFgItemDesc());
		itemIssueToProductionVO.setFgQty(itemIssueToProductionDTO.getFgQty());
		itemIssueToProductionVO.setFromLocation(itemIssueToProductionDTO.getFromLocation());
		itemIssueToProductionVO.setRemarks(itemIssueToProductionDTO.getRemarks());
		itemIssueToProductionVO.setPreparedBy(itemIssueToProductionDTO.getPreparedBy());
		itemIssueToProductionVO.setOrgId(itemIssueToProductionDTO.getOrgId());

		List<ItemIssueToProductionDetailsVO> itemIssueToProductionDetailsVOs = new ArrayList<>();
		for (ItemIssueToProductionDetailsDTO itemIssueToProductionDetailsDTO : itemIssueToProductionDTO.getItemIssueToProductionDetailsDTO()) {
			ItemIssueToProductionDetailsVO itemIssueToProductionDetailsVO = new ItemIssueToProductionDetailsVO();
			itemIssueToProductionDetailsVO.setItem(itemIssueToProductionDetailsDTO.getItem());
			itemIssueToProductionDetailsVO.setItemDesc(itemIssueToProductionDetailsDTO.getItemDesc());
			itemIssueToProductionDetailsVO.setUnit(itemIssueToProductionDetailsDTO.getUnit());
			itemIssueToProductionDetailsVO.setHoldQty(itemIssueToProductionDetailsDTO.getHoldQty());
			itemIssueToProductionDetailsVO.setReqQty(itemIssueToProductionDetailsDTO.getReqQty());
//			itemIssueToProductionDetailsVO.setAvgQty(itemIssueToProductionDetailsDTO.getAvgQty());	
			itemIssueToProductionDetailsVO.setIssueQty(itemIssueToProductionDetailsDTO.getIssueQty());
			itemIssueToProductionDetailsVO.setPendingQty(itemIssueToProductionDetailsDTO.getReqQty() - itemIssueToProductionDetailsDTO.getIssueQty());
			itemIssueToProductionDetailsVO.setPickQty(itemIssueToProductionDetailsDTO.getPickQty());
			itemIssueToProductionDetailsVO.setAvgQty(itemIssueToProductionDetailsDTO.getAvgQty());


			itemIssueToProductionDetailsVO.setItemIssueToProductionVO(itemIssueToProductionVO); // Set the reference in child entity
			itemIssueToProductionDetailsVOs.add(itemIssueToProductionDetailsVO);
		}
		itemIssueToProductionVO.setItemIssueToProductionDetailsVO(itemIssueToProductionDetailsVOs);

	}
	
	
	@Override
	public String getItemIssueToProductionDocId(Long orgId) {
		String ScreenCode = "IITP";
		String result = itemIssueToProductionRepo .getItemIssueToProductionDocId(orgId, ScreenCode);
		return result;
	}
	
	
	@Override
	public List<Map<String, Object>> getRouteCardEntryNoForItemIssueToProduction(Long orgId) {
		Set<Object[]> itemIssueToProduction = itemIssueToProductionRepo.findRouteCardEntryNoForItemIssueToProduction(orgId);
		return getRouteCardEntryNoForItemIssueToProduction(itemIssueToProduction);
	}

	private List<Map<String, Object>> getRouteCardEntryNoForItemIssueToProduction(Set<Object[]> itemIssueToProduction) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssueToProduction) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardEntryNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}
	
	
	@Override
	public List<Map<String, Object>> getRouteCardEntryDetailsForItemIssueToProduction(Long orgId,String routeCardNo) {
		Set<Object[]> itemIssueToProduction = itemIssueToProductionRepo.findRouteCardEntryDetailsForItemIssueToProduction(orgId,routeCardNo);
		return getRouteCardEntryDetailsForItemIssueToProduction(itemIssueToProduction);
	}

	private List<Map<String, Object>> getRouteCardEntryDetailsForItemIssueToProduction(Set<Object[]> itemIssueToProduction) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssueToProduction) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("fgItemId", ch[1] != null ? ch[1].toString() : "");
			map.put("fgItemDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("fgQty", ch[3] != null ? ch[3].toString() : "");


			List1.add(map);
		}
		return List1;
	}
	
	
	@Override
	public List<Map<String, Object>> getItemIssueToProductionDetailsfromBom(Long orgId,String fgItemId) {
		Set<Object[]> itemIssueToProduction = itemIssueToProductionRepo.findItemIssueToProductionDetailsfromBom(orgId,fgItemId);
		return getItemIssueToProductionDetailsfromBom(itemIssueToProduction);
	}

	private List<Map<String, Object>> getItemIssueToProductionDetailsfromBom(Set<Object[]> itemIssueToProduction) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssueToProduction) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("unit", ch[2] != null ? ch[2].toString() : "");
			map.put("bomQty", ch[3] != null ? ch[3].toString() : "");


			List1.add(map);
		}
		return List1;
	}
	

}
