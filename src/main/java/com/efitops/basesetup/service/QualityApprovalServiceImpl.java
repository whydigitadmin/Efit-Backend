package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.SettingApprovalDTO;
import com.efitops.basesetup.dto.SettingApprovalDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.SettingApprovalDetailsVO;
import com.efitops.basesetup.entity.SettingApprovalVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.SettingApprovalDetailsRepo;
import com.efitops.basesetup.repo.SettingApprovalRepo;

@Service
public class QualityApprovalServiceImpl  implements QualityApprovalServive{

	@Autowired
	SettingApprovalRepo settingApprovalRepo;
	
	@Autowired
	SettingApprovalDetailsRepo settingApprovalDetailsRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Override
	public List<SettingApprovalVO> getAllSettingApprovalByOrgId(Long orgId) {

		return settingApprovalRepo.getAllSettingApprovalByOrgId(orgId);
	}

	@Override
	public SettingApprovalVO getSettingApprovalById(Long id) {

		return settingApprovalRepo.getSettingApprovalById(id);
	}
	
	@Override
	public String getSettingApprovalDocId(Long orgId) {
		String ScreenCode = "SA";
		String result = settingApprovalRepo.getSettingApprovalDocId(orgId, ScreenCode);
		return result;
	}
	
	
	@Override
	public Map<String, Object> createUpdateSettingApproval(@Valid SettingApprovalDTO settingApprovalDTO) throws ApplicationException {
		String message;
        String screenCode="SA";
        SettingApprovalVO settingApprovalVO = new SettingApprovalVO();

		if (settingApprovalDTO.getId() != null) {
			// Fetch existing ItemVO for update
			settingApprovalVO = settingApprovalRepo.findById(settingApprovalDTO.getId())
					.orElseThrow(() -> new ApplicationException("SettingApproval not found"));
			settingApprovalVO.setUpdatedBy(settingApprovalDTO.getCreatedBy());
			createUpdateSettingApprovalVOBySettingApprovalDTO(settingApprovalDTO, settingApprovalVO);
			message = "SettingApproval Updated Successfully";

			List<SettingApprovalDetailsVO> settingApprovalDetailsVOs = settingApprovalDetailsRepo
					.findBySettingApprovalVO(settingApprovalVO);
			settingApprovalDetailsRepo.deleteAll(settingApprovalDetailsVOs);
	
		} else {
			
			// GETDOCID API
			String docId = settingApprovalRepo.getSettingApprovalDocId(settingApprovalDTO.getOrgId(),
					screenCode);
			settingApprovalVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(settingApprovalDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			
			// Create new ItemVO
			settingApprovalVO.setCreatedBy(settingApprovalDTO.getCreatedBy());
			settingApprovalVO.setUpdatedBy(settingApprovalDTO.getCreatedBy());
			createUpdateSettingApprovalVOBySettingApprovalDTO(settingApprovalDTO, settingApprovalVO);
			message = "SettingApproval Created Successfully";
		}

		// Save the ItemVO
		settingApprovalRepo.save(settingApprovalVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("settingApprovalVO", settingApprovalVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateSettingApprovalVOBySettingApprovalDTO(@Valid SettingApprovalDTO settingApprovalDTO, SettingApprovalVO settingApprovalVO) {
		settingApprovalVO.setRouteCardNo(settingApprovalDTO.getRouteCardNo());
		settingApprovalVO.setPartName(settingApprovalDTO.getPartName());
		settingApprovalVO.setPartNo(settingApprovalDTO.getPartNo());
		settingApprovalVO.setDrgNo(settingApprovalDTO.getDrgNo());
		settingApprovalVO.setOperation(settingApprovalDTO.getOperation());
		settingApprovalVO.setCycleTime(settingApprovalDTO.getCycleTime());
		settingApprovalVO.setMachineNo(settingApprovalDTO.getMachineNo());
		settingApprovalVO.setMachineName(settingApprovalDTO.getMachineName());
		settingApprovalVO.setSampleQty(settingApprovalDTO.getSampleQty());
		settingApprovalVO.setGrnClearTime(settingApprovalDTO.getGrnClearTime());
		settingApprovalVO.setDocFormatNo(settingApprovalDTO.getDocFormatNo());
		settingApprovalVO.setGeneralRemarks(settingApprovalDTO.getGeneralRemarks());
		settingApprovalVO.setOperatorName(settingApprovalDTO.getOperatorName());
		settingApprovalVO.setSetterName(settingApprovalDTO.getSetterName());
		settingApprovalVO.setShiftInCharge(settingApprovalDTO.getShiftInCharge());
		settingApprovalVO.setQualityName(settingApprovalDTO.getQualityName());
		settingApprovalVO.setNarration(settingApprovalDTO.getNarration());
		settingApprovalVO.setOrgId(settingApprovalDTO.getOrgId());
		
		
		List<SettingApprovalDetailsVO> settingApprovalDetailsVOs = new ArrayList<>();
		for (SettingApprovalDetailsDTO settingApprovalDetailsDTO : settingApprovalDTO.getSettingApprovalDetailsDTO()) {
			SettingApprovalDetailsVO settingApprovalDetailsVO = new SettingApprovalDetailsVO();
			settingApprovalDetailsVO.setCharacteristics(settingApprovalDetailsDTO.getCharacteristics());
			settingApprovalDetailsVO.setSpecification(settingApprovalDetailsDTO.getSpecification());
			settingApprovalDetailsVO.setMethodOfInspection(settingApprovalDetailsDTO.getMethodOfInspection());
			settingApprovalDetailsVO.setLsl(settingApprovalDetailsDTO.getLsl());
			settingApprovalDetailsVO.setUsl(settingApprovalDetailsDTO.getUsl());
			settingApprovalDetailsVO.setSetter1(settingApprovalDetailsDTO.getSetter1());
			settingApprovalDetailsVO.setSetter2(settingApprovalDetailsDTO.getSetter2());
			settingApprovalDetailsVO.setSetter3(settingApprovalDetailsDTO.getSetter3());
			settingApprovalDetailsVO.setSetter4(settingApprovalDetailsDTO.getSetter4());
			settingApprovalDetailsVO.setSetter5(settingApprovalDetailsDTO.getSetter5());
			settingApprovalDetailsVO.setQulity1(settingApprovalDetailsDTO.getQuality1());
			settingApprovalDetailsVO.setQulity2(settingApprovalDetailsDTO.getQuality2());
			settingApprovalDetailsVO.setQulity3(settingApprovalDetailsDTO.getQuality3());
			settingApprovalDetailsVO.setQulity4(settingApprovalDetailsDTO.getQuality4());
			settingApprovalDetailsVO.setQulity5(settingApprovalDetailsDTO.getQuality5());
			settingApprovalDetailsVO.setRemarks(settingApprovalDetailsDTO.getRemarks());


			settingApprovalDetailsVO.setSettingApprovalVO(settingApprovalVO); // Set the reference in child entity
			settingApprovalDetailsVOs.add(settingApprovalDetailsVO);
		}
		settingApprovalVO.setSettingApprovalDetailsVO(settingApprovalDetailsVOs);

	}

	@Override
	public List<Map<String, Object>> getRouteCardDetailsForSetingApproval(Long orgId) {
		Set<Object[]> routeCardDetails = settingApprovalRepo.findRouteCardDetailsForSetingApproval(orgId);
		return getRouteCardDetailsForSetingApproval(routeCardDetails);
	}

	private List<Map<String, Object>> getRouteCardDetailsForSetingApproval(Set<Object[]> routeCardDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : routeCardDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : ""); 
			map.put("partNo", ch[1] != null ? ch[1].toString() : "");
			map.put("partName", ch[2] != null ? ch[2].toString() : "");

			
			List1.add(map);
		}
		return List1;

	}
	

}
