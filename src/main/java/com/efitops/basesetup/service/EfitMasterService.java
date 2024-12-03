package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DepartmentDTO;
import com.efitops.basesetup.dto.DesignationDTO;
import com.efitops.basesetup.dto.GstDTO;
import com.efitops.basesetup.dto.ItemDTO;
import com.efitops.basesetup.dto.ItemWiseProcessMasterDTO;
import com.efitops.basesetup.dto.MaterialTypeDTO;
import com.efitops.basesetup.dto.MeasuringInstrumentsDTO;
import com.efitops.basesetup.dto.ProcessMasterDTO;
import com.efitops.basesetup.dto.RackMasterDTO;
import com.efitops.basesetup.dto.ShiftDTO;
import com.efitops.basesetup.dto.UomDTO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DesignationVO;
import com.efitops.basesetup.entity.GstVO;
import com.efitops.basesetup.entity.ItemVO;
import com.efitops.basesetup.entity.ItemWiseProcessMasterVO;
import com.efitops.basesetup.entity.MaterialTypeVO;
import com.efitops.basesetup.entity.MeasuringInstrumentsVO;
import com.efitops.basesetup.entity.ProcessMasterVO;
import com.efitops.basesetup.entity.RackMasterVO;
import com.efitops.basesetup.entity.ShiftVO;
import com.efitops.basesetup.entity.UomVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface EfitMasterService {

	List<ItemVO> getItemByOrgId(Long orgId);

	List<ItemVO> getItemById(Long id);

	Map<String, Object> updateCreateItemMaster(ItemDTO itemDTO) throws ApplicationException;
	
	List<Map<String, Object>> getPrimaryCodeFromUomMaster(Long orgId);
	
	List<Map<String, Object>> getStockLocationForItemMaster(Long orgId);

	List<Map<String, Object>> getTaxSlabFromGst(Long orgId);
	
	List<Map<String, Object>> getMaterialGroupFromMaterialType(Long orgId, String materialType);
	
	List<Map<String, Object>> getMaterialSubGroupFromMaterialType(Long orgId, String materialType, String materialGroup);

	List<Map<String, Object>> getMaterialTypeForItemMaster(Long orgId);

	//MeasuringInstruments
	
	List<MeasuringInstrumentsVO> getMeasuringInstrumentByOrgId(Long orgId);

	List<MeasuringInstrumentsVO> getMeasuringInstrumentById(Long id);

	Map<String, Object> updateCreateMeasuringInstruments(MeasuringInstrumentsDTO measuringInstrumentsDTO)throws ApplicationException;

	String getMeasuringInstrumentsDocId(Long orgId);

	List<Map<String, Object>> getInstrumentNameFromItemMaster(Long orgId);

	//ItemwiseProcessMaster
	List<ItemWiseProcessMasterVO> getItemWiseProcessMasterByOrgId(Long orgId);

	List<ItemWiseProcessMasterVO> getItemWiseProcessMasterById(Long id);

	Map<String, Object> updateCreateItemWiseProcessMaster(ItemWiseProcessMasterDTO itemWiseProcessMasterDTO) throws ApplicationException;


	List<Map<String, Object>> getItemAndItemDescforItemWiseProcess(Long orgId);
	
	List<Map<String, Object>> getProcessNameFromItemWiseProcess(Long orgId);
	


	//Department
	
		Map<String, Object> createUpdateDepartment(DepartmentDTO departmentDTO) throws ApplicationException;

		List<DepartmentVO> getAllDepartmentByOrgId(Long orgId);

		List<DepartmentVO> getDepartmentById(Long id); 
		
		String getDepartmentDocId(Long orgId);
		
		//GST

		Map<String, Object> createUpdateGst(GstDTO gstDTO) throws ApplicationException;

		List<GstVO> getAllGstByOrgId(Long orgId);

		List<GstVO> getGstById(Long id); 
		
		//ProcessMaster
		
		Map<String, Object> createUpdateProcessMaster(ProcessMasterDTO processMasterDTO) throws ApplicationException;
		
		List<ProcessMasterVO> getAllProcessMasterByOrgId(Long orgId);

		List<ProcessMasterVO> getProcessMasterById(Long id); 
		
		String getProcessMasterDocId(Long orgId);
		
		//Material Type
		
	    Map<String, Object> createUpdateMaterialType(MaterialTypeDTO materialTypeDTO) throws ApplicationException;
		
		List<MaterialTypeVO> getAllMaterialTypeByOrgId(Long orgId);

		List<MaterialTypeVO> getMaterialTypeById(Long id);
		
		//Designation
		List<DesignationVO> getDesignationByOrgId(Long orgId);

		List<DesignationVO> getDesignationById(Long id);

		Map<String, Object> updateCreateDesignation(DesignationDTO designationdto) throws ApplicationException;

		//UOM
		
		List<UomVO> getUomByOrgId(Long orgId);

		List<UomVO> getUomById(Long id);

		Map<String, Object> updateCreateUom(@Valid UomDTO uomDTO) throws ApplicationException;

		//SHIFT
		
		List<ShiftVO> getShiftByOrgId(Long orgId);

		Map<String, Object> updateCreateShift(ShiftDTO shiftdto) throws ApplicationException;

		List<ShiftVO> getShiftById(Long id);

		//RackMaster
		
		List<RackMasterVO> getRackMasterByOrgId(Long orgId);

		List<RackMasterVO> getRackMasterById(Long id);

		Map<String, Object> updateCreateRackMaster(RackMasterDTO rackMasterDTO) throws ApplicationException;





		

	

}
