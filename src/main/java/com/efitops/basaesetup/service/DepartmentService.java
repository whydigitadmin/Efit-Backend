package com.efitops.basaesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basaesetup.dto.DepartmentDTO;
import com.efitops.basaesetup.dto.GstDTO;
import com.efitops.basaesetup.dto.MaterialTypeDTO;
import com.efitops.basaesetup.dto.ProcessMasterDTO;
import com.efitops.basaesetup.entity.DepartmentVO;
import com.efitops.basaesetup.entity.GstVO;
import com.efitops.basaesetup.entity.MaterialTypeVO;
import com.efitops.basaesetup.entity.ProcessMasterVO;
import com.efitops.basaesetup.exception.ApplicationException;

@Service
public interface DepartmentService {
	
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
}
