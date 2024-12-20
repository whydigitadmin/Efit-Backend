package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DispatchPlanDTO;
import com.efitops.basesetup.dto.DispatchPlanDetailsDTO;
import com.efitops.basesetup.entity.DispatchPlanDetailsVO;
import com.efitops.basesetup.entity.DispatchPlanVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DispatchPlanDetailsRepo;
import com.efitops.basesetup.repo.DispatchPlanRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;

@Service
public class DispatchPlanServiceImpl implements DispatchPlanService{

	@Autowired
	DispatchPlanRepo dispatchPlanRepo;
	
	@Autowired
	DispatchPlanDetailsRepo dispatchPlanDetailsRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Override
	public List<DispatchPlanVO> getDispatchPlanByOrgId(Long orgId) {
		List<DispatchPlanVO> dispatchPlanVO = new ArrayList<>();
		dispatchPlanVO = dispatchPlanRepo.getDispatchPlanByOrgId(orgId);

		return dispatchPlanVO;
	}

	@Override
	public DispatchPlanVO getDispatchPlanById(Long id) {
		DispatchPlanVO dispatchPlanVO = new DispatchPlanVO();

		dispatchPlanVO = dispatchPlanRepo.getDispatchPlanById(id);

		return dispatchPlanVO;
	}

	@Override
	public Map<String, Object> updateCreateDispatchPlan(@Valid DispatchPlanDTO dispatchPlanDTO) throws ApplicationException {
		String message;
        String screenCode ="DP";
		DispatchPlanVO dispatchPlanVO = new DispatchPlanVO();

		if (dispatchPlanDTO.getId() != null) {
			// Fetch existing ItemVO for update
			dispatchPlanVO = dispatchPlanRepo.findById(dispatchPlanDTO.getId())
					.orElseThrow(() -> new ApplicationException("DispatchPlan master not found"));
			dispatchPlanVO.setUpdatedBy(dispatchPlanDTO.getCreatedBy());
			createUpdateDispatchPlanVOByDispatchPlanDTO(dispatchPlanDTO, dispatchPlanVO);
			message = "DispatchPlan Master Updated Successfully";

			List<DispatchPlanDetailsVO> dispatchPlanDetailsVOs = dispatchPlanDetailsRepo.findByDispatchPlanVO(dispatchPlanVO);
			dispatchPlanDetailsRepo.deleteAll(dispatchPlanDetailsVOs);

		} else {
			

			// GETDOCID API
			String docId = dispatchPlanRepo.getDispatchPlanByDocId(dispatchPlanDTO.getOrgId(),
					screenCode);

			dispatchPlanVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(dispatchPlanDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			
			// Create new ItemVO
			dispatchPlanVO.setCreatedBy(dispatchPlanDTO.getCreatedBy());
			dispatchPlanVO.setUpdatedBy(dispatchPlanDTO.getCreatedBy());
			createUpdateDispatchPlanVOByDispatchPlanDTO(dispatchPlanDTO, dispatchPlanVO);
			message = "DispatchPlan Master Created Successfully";
		}

		// Save the ItemVO
		dispatchPlanRepo.save(dispatchPlanVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("dispatchPlanVO", dispatchPlanVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDispatchPlanVOByDispatchPlanDTO(@Valid DispatchPlanDTO dispatchPlanDTO, DispatchPlanVO dispatchPlanVO) {
		dispatchPlanVO.setRouteCardEntry(dispatchPlanDTO.getRouteCardEntry());
		dispatchPlanVO.setCustomerName(dispatchPlanDTO.getCustomerName());
		dispatchPlanVO.setCustomerCode(dispatchPlanDTO.getCustomerCode());
		dispatchPlanVO.setWorkOrderNo(dispatchPlanDTO.getWorkOrderNo());
		dispatchPlanVO.setScheduleDispatchDate(dispatchPlanDTO.getScheduleDispatchDate());
		dispatchPlanVO.setDispatchDate(dispatchPlanDTO.getDispatchDate());
		dispatchPlanVO.setNarration(dispatchPlanDTO.getNarration());
		dispatchPlanVO.setOrgId(dispatchPlanDTO.getOrgId());

		// Handling ItemInventoryVO
		List<DispatchPlanDetailsVO> dispatchPlanDetailsVOs = new ArrayList<>();
		for (DispatchPlanDetailsDTO dispatchPlanDetailsDTO : dispatchPlanDTO.getDispatchPlanDetailsDTO()) {
			DispatchPlanDetailsVO dispatchPlanDetailsVO = new DispatchPlanDetailsVO();
			dispatchPlanDetailsVO.setItem(dispatchPlanDetailsDTO.getItem());
			dispatchPlanDetailsVO.setItemDesc(dispatchPlanDetailsDTO.getItemDesc());
			dispatchPlanDetailsVO.setUnit(dispatchPlanDetailsDTO.getUnit());
			dispatchPlanDetailsVO.setOrderQty(dispatchPlanDetailsDTO.getOrderQty());
			dispatchPlanDetailsVO.setDeliveryQty(dispatchPlanDetailsDTO.getDeliveryQty());
			dispatchPlanDetailsVO.setRemarks(dispatchPlanDetailsDTO.getRemarks());

			dispatchPlanDetailsVO.setDispatchPlanVO(dispatchPlanVO); // Set the reference in child entity
			dispatchPlanDetailsVOs.add(dispatchPlanDetailsVO);
		}
		dispatchPlanVO.setDispatchPlanDetailsVO(dispatchPlanDetailsVOs);

	
	}
	
	@Override
	public String getDispatchPlanDocId(Long orgId) {
		String screenCode = "DP";
		String result = dispatchPlanRepo.getDispatchPlanByDocId(orgId, screenCode);
		return result;
	}

	

	@Override
	public List<Map<String, Object>> getRouteCardDetailsForDispatchPlan(Long orgId) {
		Set<Object[]> routeCardDetails = dispatchPlanRepo.findRouteCardDetailsForDispatchPlan(orgId);
		return getRouteCardDetailsForDispatchPlan(routeCardDetails);
	}

	private List<Map<String, Object>> getRouteCardDetailsForDispatchPlan(Set<Object[]> routeCardDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : routeCardDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : ""); 
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("workOrderNo", ch[3] != null ? ch[3].toString() : "");

			
			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public List<Map<String, Object>> getItemDetailsForDispatchPlan(Long orgId, String workOrderNo) {
		Set<Object[]> itemDetails = dispatchPlanRepo.findItemDetailsForDispatchPlan(orgId,workOrderNo);
		return getItemDetailsForDispatchPlan(itemDetails);
	}

	private List<Map<String, Object>> getItemDetailsForDispatchPlan(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemCode", ch[0] != null ? ch[0].toString() : ""); 
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			map.put("unit", ch[2] != null ? ch[2].toString() : "");
			map.put("unit", ch[3] != null ? ch[3].toString() : "");
			
			List1.add(map);
		}
		return List1;

	}
	
}
