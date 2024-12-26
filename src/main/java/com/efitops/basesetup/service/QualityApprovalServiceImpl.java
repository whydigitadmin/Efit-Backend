package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.SampleApprovalDTO;
import com.efitops.basesetup.dto.SampleApprovalDetailsDTO;
import com.efitops.basesetup.dto.SettingApprovalDTO;
import com.efitops.basesetup.dto.SettingApprovalDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.SampleApprovalDetailsVO;
import com.efitops.basesetup.entity.SampleApprovalVO;
import com.efitops.basesetup.entity.SettingApprovalDetailsVO;
import com.efitops.basesetup.entity.SettingApprovalVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.SampleApprovalDetailsRepo;
import com.efitops.basesetup.repo.SampleApprovalRepo;
import com.efitops.basesetup.repo.SettingApprovalDetailsRepo;
import com.efitops.basesetup.repo.SettingApprovalRepo;

@Service
public class QualityApprovalServiceImpl  implements QualityApprovalServive{

	@Autowired
	SettingApprovalRepo settingApprovalRepo;
	
	@Autowired
	SettingApprovalDetailsRepo settingApprovalDetailsRepo;
	
	@Autowired
	SampleApprovalRepo sampleApprovalRepo;
	
	@Autowired
	SampleApprovalDetailsRepo sampleApprovalDetailsRepo;
	
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
	
	@Override
	public List<Map<String, Object>> getDrawingNoForSetingApproval(Long orgId,String partNo) {
		Set<Object[]> drawingNo = settingApprovalRepo.findDrawingNoForSetingApproval(orgId,partNo);
		return getDrawingNoForSetingApproval(drawingNo);
	}

	private List<Map<String, Object>> getDrawingNoForSetingApproval(Set<Object[]> drawingNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : drawingNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingNo", ch[0] != null ? ch[0].toString() : ""); 

			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public List<Map<String, Object>> getMachineNoForSetingApproval(Long orgId) {
		Set<Object[]> machineNo = settingApprovalRepo.findMachineNoForSetingApproval(orgId);
		return getMachineNoForSetingApproval(machineNo);
	}

	private List<Map<String, Object>> getMachineNoForSetingApproval(Set<Object[]> machineNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : machineNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("machineNo", ch[0] != null ? ch[0].toString() : ""); 
			map.put("machineName", ch[0] != null ? ch[0].toString() : ""); 

			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public List<Map<String, Object>> getOperatorNameForSetingApproval(Long orgId) {
		Set<Object[]> employeeName = settingApprovalRepo.findOperatorNameForSetingApproval(orgId);
		return getOperatorNameForSetingApproval(employeeName);
	}

	private List<Map<String, Object>> getOperatorNameForSetingApproval(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("operatorName", ch[0] != null ? ch[0].toString() : "");			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getSetterNameForSetingApproval(Long orgId) {
		Set<Object[]> employeeName = settingApprovalRepo.findSetterNameForSetingApproval(orgId);
		return getSetterNameForSetingApproval(employeeName);
	}

	private List<Map<String, Object>> getSetterNameForSetingApproval(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("setterName", ch[0] != null ? ch[0].toString() : "");			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getQualityNameForSetingApproval(Long orgId) {
		Set<Object[]> employeeName = settingApprovalRepo.findQualityNameForSetingApproval(orgId);
		return getQualityNameForSetingApproval(employeeName);
	}

	private List<Map<String, Object>> getQualityNameForSetingApproval(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("qualityName", ch[0] != null ? ch[0].toString() : "");			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getShiftInChargeForSetingApproval(Long orgId) {
		Set<Object[]> employeeName = settingApprovalRepo.findShiftInChargeForSetingApproval(orgId);
		return getShiftInChargeForSetingApproval(employeeName);
	}

	private List<Map<String, Object>> getShiftInChargeForSetingApproval(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("qualityName", ch[0] != null ? ch[0].toString() : "");			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<SampleApprovalVO> getAllSampleApprovalByOrgId(Long orgId) {

		return sampleApprovalRepo.getAllSampleApprovalByOrgId(orgId);
	}

	@Override
	public SampleApprovalVO getSampleApprovalById(Long id) {

		return sampleApprovalRepo.getSampleApprovalById(id);
	}
	
	@Override
	public String getSampleApprovalDocId(Long orgId) {
		String ScreenCode = "SAP";
		String result = sampleApprovalRepo.getSampleApprovalDocId(orgId, ScreenCode);
		return result;
	}
	
	@Override
	public Map<String, Object> createUpdateSampleApproval(@Valid SampleApprovalDTO sampleApprovalDTO) throws ApplicationException {
		String message;
        String screenCode="SAP";
        SampleApprovalVO sampleApprovalVO = new SampleApprovalVO();

		if (sampleApprovalDTO.getId() != null) {
			// Fetch existing ItemVO for update
			sampleApprovalVO = sampleApprovalRepo.findById(sampleApprovalDTO.getId())
					.orElseThrow(() -> new ApplicationException("SampleApproval not found"));
			sampleApprovalVO.setUpdatedBy(sampleApprovalDTO.getCreatedBy());
			createUpdateSampleApprovalVOBySampleApprovalDTO(sampleApprovalDTO, sampleApprovalVO);
			message = "SampleApproval Updated Successfully";

			List<SampleApprovalDetailsVO> sampleApprovalDetailsVOs = sampleApprovalDetailsRepo
					.findBySampleApprovalVO(sampleApprovalVO);
			sampleApprovalDetailsRepo.deleteAll(sampleApprovalDetailsVOs);
	
		} else {
			
			// GETDOCID API
			String docId = sampleApprovalRepo.getSampleApprovalDocId(sampleApprovalDTO.getOrgId(),
					screenCode);
			sampleApprovalVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(sampleApprovalDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			
			// Create new ItemVO
			sampleApprovalVO.setCreatedBy(sampleApprovalDTO.getCreatedBy());
			sampleApprovalVO.setUpdatedBy(sampleApprovalDTO.getCreatedBy());
			createUpdateSampleApprovalVOBySampleApprovalDTO(sampleApprovalDTO, sampleApprovalVO);
			message = "SampleApproval Created Successfully";
		}

		// Save the ItemVO
		sampleApprovalRepo.save(sampleApprovalVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("sampleApprovalVO", sampleApprovalVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateSampleApprovalVOBySampleApprovalDTO(@Valid SampleApprovalDTO sampleApprovalDTO, SampleApprovalVO sampleApprovalVO) {
		sampleApprovalVO.setRouteCardNo(sampleApprovalDTO.getRouteCardNo());
		sampleApprovalVO.setPartName(sampleApprovalDTO.getPartName());
		sampleApprovalVO.setPartNo(sampleApprovalDTO.getPartNo());
		sampleApprovalVO.setDrgNo(sampleApprovalDTO.getDrgNo());
		sampleApprovalVO.setOperation(sampleApprovalDTO.getOperation());
		sampleApprovalVO.setCycleTime(sampleApprovalDTO.getCycleTime());
		sampleApprovalVO.setMachineNo(sampleApprovalDTO.getMachineNo());
		sampleApprovalVO.setMachineName(sampleApprovalDTO.getMachineName());
		sampleApprovalVO.setSampleQty(sampleApprovalDTO.getSampleQty());
		sampleApprovalVO.setJobOrderNo(sampleApprovalDTO.getJobOrderNo());
		sampleApprovalVO.setDocFormatNo(sampleApprovalDTO.getDocFormatNo());
		sampleApprovalVO.setGeneralRemarks(sampleApprovalDTO.getGeneralRemarks());
		sampleApprovalVO.setOperatorName(sampleApprovalDTO.getOperatorName());
		sampleApprovalVO.setShift(sampleApprovalDTO.getShift());
		sampleApprovalVO.setShiftDate(sampleApprovalDTO.getShiftDate());
		sampleApprovalVO.setShiftTime(sampleApprovalDTO.getShiftTime());
		sampleApprovalVO.setShiftInCharge(sampleApprovalDTO.getShiftInCharge());
		sampleApprovalVO.setQualityName(sampleApprovalDTO.getQualityName());
		sampleApprovalVO.setNarration(sampleApprovalDTO.getNarration());
		sampleApprovalVO.setOrgId(sampleApprovalDTO.getOrgId());
		
		
		List<SampleApprovalDetailsVO> sampleApprovalDetailsVOs = new ArrayList<>();
		for (SampleApprovalDetailsDTO sampleApprovalDetailsDTO : sampleApprovalDTO.getSampleApprovalDetailsDTO()) {
			SampleApprovalDetailsVO sampleApprovalDetailsVO = new SampleApprovalDetailsVO();
			sampleApprovalDetailsVO.setCharacteristics(sampleApprovalDetailsDTO.getCharacteristics());
			sampleApprovalDetailsVO.setSpecification(sampleApprovalDetailsDTO.getSpecification());
			sampleApprovalDetailsVO.setMethodOfInspection(sampleApprovalDetailsDTO.getMethodOfInspection());
			sampleApprovalDetailsVO.setLsl(sampleApprovalDetailsDTO.getLsl());
			sampleApprovalDetailsVO.setUsl(sampleApprovalDetailsDTO.getUsl());
			sampleApprovalDetailsVO.setSimple1(sampleApprovalDetailsDTO.getSimple1());
			sampleApprovalDetailsVO.setSimple2(sampleApprovalDetailsDTO.getSimple2());
			sampleApprovalDetailsVO.setSimple3(sampleApprovalDetailsDTO.getSimple3());
			sampleApprovalDetailsVO.setSimple4(sampleApprovalDetailsDTO.getSimple4());
			sampleApprovalDetailsVO.setSimple5(sampleApprovalDetailsDTO.getSimple5());
			sampleApprovalDetailsVO.setOperator1(sampleApprovalDetailsDTO.getOperator1());
			sampleApprovalDetailsVO.setOperator2(sampleApprovalDetailsDTO.getOperator2());
			sampleApprovalDetailsVO.setOperator3(sampleApprovalDetailsDTO.getOperator3());
			sampleApprovalDetailsVO.setOperator4(sampleApprovalDetailsDTO.getOperator4());
			sampleApprovalDetailsVO.setOperator5(sampleApprovalDetailsDTO.getOperator5());
			sampleApprovalDetailsVO.setStatus(sampleApprovalDetailsDTO.getStatus());


			sampleApprovalDetailsVO.setSampleApprovalVO(sampleApprovalVO); // Set the reference in child entity
			sampleApprovalDetailsVOs.add(sampleApprovalDetailsVO);
		}
		sampleApprovalVO.setSampleApprovalDetailsVO(sampleApprovalDetailsVOs);

	}

	
	@Override
	public List<Map<String, Object>> getRouteCardDetailsForSampleApproval(Long orgId) {
		Set<Object[]> routeCardDetails = sampleApprovalRepo.findRouteCardDetailsForSampleApproval(orgId);
		return getRouteCardDetailsForSampleApproval(routeCardDetails);
	}

	private List<Map<String, Object>> getRouteCardDetailsForSampleApproval(Set<Object[]> routeCardDetails) {
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
	
	@Override
	public List<Map<String, Object>> getDrawingMasterNoForSampleApproval(Long orgId,String partNo) {
		Set<Object[]> drawingMasterNo = sampleApprovalRepo.findDrawingMasterNoForSampleApproval(orgId,partNo);
		return DrawingMasterNoForSampleApproval(drawingMasterNo);
	}

	private List<Map<String, Object>> DrawingMasterNoForSampleApproval(Set<Object[]> drawingMasterNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : drawingMasterNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingMasterNo", ch[0] != null ? ch[0].toString() : ""); 
			
			List1.add(map);
		}
		return List1;

	}
	
	
	@Override
	public List<Map<String, Object>> getMachineNoForSampleApproval(Long orgId) {
		Set<Object[]> machineNo = sampleApprovalRepo.findMachineNoForSampleApproval(orgId);
		return getMachineNoForSampleApproval(machineNo);
	}

	private List<Map<String, Object>> getMachineNoForSampleApproval(Set<Object[]> machineNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : machineNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("machineNo", ch[0] != null ? ch[0].toString() : ""); 
			map.put("machineName", ch[0] != null ? ch[0].toString() : ""); 

			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public List<Map<String, Object>> getJobOrderNoForSampleApproval(Long orgId,String routeCardNo) {
		Set<Object[]> jobOrderNo = sampleApprovalRepo.findJobOrderNoForSampleApproval(orgId,routeCardNo);
		return getJobOrderNoForSampleApproval(jobOrderNo);
	}

	private List<Map<String, Object>> getJobOrderNoForSampleApproval(Set<Object[]> jobOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : jobOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobOrderNo", ch[0] != null ? ch[0].toString() : ""); 
			List1.add(map);
		}
		return List1;

	}

	
	
}
