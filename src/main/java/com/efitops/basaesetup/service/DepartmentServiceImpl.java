package com.efitops.basaesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basaesetup.dto.DepartmentDTO;
import com.efitops.basaesetup.dto.GstDTO;
import com.efitops.basaesetup.dto.MaterialDetailDTO;
import com.efitops.basaesetup.dto.MaterialTypeDTO;
import com.efitops.basaesetup.dto.ProcessMasterDTO;
import com.efitops.basaesetup.entity.DepartmentVO;
import com.efitops.basaesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basaesetup.entity.GstVO;
import com.efitops.basaesetup.entity.MaterialDetailVO;
import com.efitops.basaesetup.entity.MaterialTypeVO;
import com.efitops.basaesetup.entity.ProcessMasterVO;
import com.efitops.basaesetup.exception.ApplicationException;
import com.efitops.basaesetup.repo.DepartmentRepo;
import com.efitops.basaesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basaesetup.repo.GstRepo;
import com.efitops.basaesetup.repo.MaterialDetailRepo;
import com.efitops.basaesetup.repo.MaterialTypeRepo;
import com.efitops.basaesetup.repo.ProcessMasterRepo;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	public static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	GstRepo gstRepo;

	@Autowired
	ProcessMasterRepo processMasterRepo;

	@Autowired
	MaterialTypeRepo materialTypeRepo;

	@Autowired
	MaterialDetailRepo materialDetailRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	
	// Department
	@Override
	public Map<String, Object> createUpdateDepartment(DepartmentDTO departmentDTO) throws ApplicationException {
		DepartmentVO departmentVO = new DepartmentVO();
		String message;
		String screenCode="DEPT";
		if (ObjectUtils.isNotEmpty(departmentDTO.getId())) {
			departmentVO = departmentRepo.findById(departmentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Department details"));

			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			if (!departmentVO.getDepartmentName().equalsIgnoreCase(departmentDTO.getDepartmentName())) {
				if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("The DepartmentName: %s already exists in This Organization.",
							departmentDTO.getDepartmentName());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setDepartmentName(departmentDTO.getDepartmentName().toUpperCase());
			}

			if (!departmentVO.getDepartmentCode().equalsIgnoreCase(departmentDTO.getDepartmentCode())) {
				if (departmentRepo.existsByDepartmentCodeAndOrgId(departmentDTO.getDepartmentCode(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("The DepartmentCode: %s already exists in This Organization.",
							departmentDTO.getDepartmentCode());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setDepartmentCode(departmentDTO.getDepartmentCode().toUpperCase());
			}
			message = "Department Updated Successfully";
		} else {

			if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
					departmentDTO.getOrgId())) {
				String errorMessage = String.format("The DepartmentName : %s already exists in This Organization.",
						departmentDTO.getDepartmentName());
				throw new ApplicationException(errorMessage);
			}
			if (departmentRepo.existsByDepartmentCodeAndOrgId(departmentDTO.getDepartmentCode(),
					departmentDTO.getOrgId())) {
				String errorMessage = String.format("The DepartmentCode: %s already exists in This Organization.",
						departmentDTO.getDepartmentCode());
				throw new ApplicationException(errorMessage);
			}
			String docId = departmentRepo.getDepartmentDocId(departmentDTO.getOrgId(),
					screenCode);
			departmentVO.setDepartmentId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(departmentDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			message = "Department Created Successfully";
		}

		createUpdateDepartmentVOByDepartmentDTO(departmentDTO, departmentVO);
		departmentRepo.save(departmentVO);
		Map<String, Object> response = new HashMap<>();
		response.put("departmentVO", departmentVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDepartmentVOByDepartmentDTO(DepartmentDTO departmentDTO, DepartmentVO departmentVO) {
		departmentVO.setDepartmentName(departmentDTO.getDepartmentName().toUpperCase());
		departmentVO.setDepartmentCode(departmentDTO.getDepartmentCode().toUpperCase());
		departmentVO.setOrgId(departmentDTO.getOrgId());
		departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
		departmentVO.setActive(departmentDTO.isActive());


	}
	
	@Override
	public String getDepartmentDocId(Long orgId) {
		  String screenCode="DEPT";
		String result=  departmentRepo.getDepartmentDocId(orgId, screenCode);  
		return result;
	}


	@Override
	public List<DepartmentVO> getAllDepartmentByOrgId(Long orgId) {
		List<DepartmentVO> departmentVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Department BY OrgId : {}", orgId);
			departmentVO = departmentRepo.getAllDepartmentByOrgId(orgId);
		}
		return departmentVO;
	}

	@Override
	public List<DepartmentVO> getDepartmentById(Long id) {
		List<DepartmentVO> departmentVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  Department BY Id : {}", id);
			departmentVO = departmentRepo.getDepartmentById(id);
		}
		return departmentVO;
	}

	// GST

	@Override
	public Map<String, Object> createUpdateGst(GstDTO gstDTO) throws ApplicationException {
		GstVO gstVO = new GstVO();
		String message;
		if (ObjectUtils.isNotEmpty(gstDTO.getId())) {
			gstVO = gstRepo.findById(gstDTO.getId()).orElseThrow(() -> new ApplicationException("Invalid Gst details"));
			message = "Gst Updated Successfully";
			gstVO.setUpdatedBy(gstDTO.getCreatedBy());
			if (!gstVO.getGstSlab().equalsIgnoreCase(gstDTO.getGstSlab())) {
				if (gstRepo.existsByGstSlabAndOrgId(gstDTO.getGstSlab(), gstDTO.getOrgId())) {
					String errorMessage = String.format("The GstSlab : %s already exists in This Organization.",
							gstDTO.getGstSlab());
					throw new ApplicationException(errorMessage);
				}
				gstVO.setGstSlab(gstDTO.getGstSlab().toUpperCase());
			}

		} else {

			if (gstRepo.existsByGstSlabAndOrgId(gstDTO.getGstSlab(), gstDTO.getOrgId())) {
				String errorMessage = String.format("The GstSlab : %s already exists in This Organization.",
						gstDTO.getGstSlab());
				throw new ApplicationException(errorMessage);
			}
			gstVO.setCreatedBy(gstDTO.getCreatedBy());
			gstVO.setUpdatedBy(gstDTO.getCreatedBy());

			message = "Gst Created Successfully";
		}
		createUpdateGstVOByGstDTO(gstDTO, gstVO);
		gstRepo.save(gstVO);
		Map<String, Object> response = new HashMap<>();
		response.put("gstVO", gstVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateGstVOByGstDTO(GstDTO gstDTO, GstVO gstVO) {
		gstVO.setGstSlab(gstDTO.getGstSlab().toUpperCase());
		gstVO.setGstPercentage(gstDTO.getGstPercentage());
		gstVO.setIgstPercentage(gstDTO.getIgstPercentage());
		gstVO.setCgstPercentage(gstDTO.getCgstPercentage());
		gstVO.setSgstPercentage(gstDTO.getSgstPercentage());
		gstVO.setOrgId(gstDTO.getOrgId());
		gstVO.setCreatedBy(gstDTO.getCreatedBy());
		gstVO.setActive(gstDTO.isActive());
	}

	@Override
	public List<GstVO> getAllGstByOrgId(Long orgId) {
		List<GstVO> gstVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Gst BY OrgId : {}", orgId);
			gstVO = gstRepo.getAllGstByOrgId(orgId);
		}
		return gstVO;
	}

	@Override
	public List<GstVO> getGstById(Long id) {
		List<GstVO> gstVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  Gst BY Id : {}", id);
			gstVO = gstRepo.getGstById(id);
		}
		return gstVO;
	}

	// ProcessMaster

	@Override
	public Map<String, Object> createUpdateProcessMaster(ProcessMasterDTO processMasterDTO)
			throws ApplicationException {
		ProcessMasterVO processMasterVO = new ProcessMasterVO();
		String message;
		String screenCode="PM";
		if (ObjectUtils.isNotEmpty(processMasterDTO.getId())) {
			processMasterVO = processMasterRepo.findById(processMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid ProcessMaster details"));
			message = "ProcessMaster Updated Successfully";
			processMasterVO.setUpdatedBy(processMasterDTO.getCreatedBy());
			if (!processMasterVO.getProcessName().equalsIgnoreCase(processMasterDTO.getProcessName())) {
				if (processMasterRepo.existsByProcessNameAndOrgId(processMasterDTO.getProcessName(),
						processMasterDTO.getOrgId())) {
					String errorMessage = String.format("The ProcessName : %s already exists in This Organization.",
							processMasterDTO.getProcessName());
					throw new ApplicationException(errorMessage);
				}
				processMasterVO.setProcessName(processMasterDTO.getProcessName().toUpperCase());
			}

		} else {
			if (processMasterRepo.existsByProcessNameAndOrgId(processMasterDTO.getProcessName(),
					processMasterDTO.getOrgId())) {
				String errorMessage = String.format("The ProcessName : %s already exists in This Organization.",
						processMasterDTO.getProcessName());
				throw new ApplicationException(errorMessage);
			}
			
			String docId = processMasterRepo.getProcessMasterDocId(processMasterDTO.getOrgId(),
					screenCode);
			processMasterVO.setProcessId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(processMasterDTO.getOrgId(),screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			
			processMasterVO.setCreatedBy(processMasterDTO.getCreatedBy());
			processMasterVO.setUpdatedBy(processMasterDTO.getCreatedBy());

			message = "ProcessMaster Created Successfully";
		}
		createUpdatedProcessMasterVOFromProcessMasterDTO(processMasterDTO, processMasterVO);
		processMasterRepo.save(processMasterVO);
		Map<String, Object> response = new HashMap<>();
		response.put("processMasterVO", processMasterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedProcessMasterVOFromProcessMasterDTO(ProcessMasterDTO processMasterDTO,
			ProcessMasterVO processMasterVO) {
		processMasterVO.setProcessName(processMasterDTO.getProcessName().toUpperCase());
		processMasterVO.setCreatedBy(processMasterDTO.getCreatedBy());
		processMasterVO.setOrgId(processMasterDTO.getOrgId());
		processMasterVO.setActive(processMasterDTO.isActive());
	}
	
	@Override
	public String getProcessMasterDocId(Long orgId) {
		 String screenCode="PM";
			String result=  processMasterRepo.getProcessMasterDocId(orgId, screenCode);  
		return result;
	}


	@Override
	public List<ProcessMasterVO> getAllProcessMasterByOrgId(Long orgId) {
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  ProcessMaster BY OrgId : {}", orgId);
			processMasterVO = processMasterRepo.getAllProcessMasterByOrgId(orgId);
		}
		return processMasterVO;
	}

	@Override
	public List<ProcessMasterVO> getProcessMasterById(Long id) {
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  ProcessMaster BY Id : {}", id);
			processMasterVO = processMasterRepo.getProcessMasterById(id);
		}
		return processMasterVO;
	}

	// Material Type

	@Override
	public Map<String, Object> createUpdateMaterialType(MaterialTypeDTO materialTypeDTO) throws ApplicationException {
		MaterialTypeVO materialTypeVO = new MaterialTypeVO();
		String message;
		if (ObjectUtils.isNotEmpty(materialTypeDTO.getId())) {
			materialTypeVO = materialTypeRepo.findById(materialTypeDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid MaterialType Details"));
			materialTypeVO.setUpdatedBy(materialTypeDTO.getCreatedBy());

			if (!materialTypeVO.getItemGroup().equalsIgnoreCase(materialTypeDTO.getItemGroup())) {
				if (materialTypeRepo.existsByItemGroupAndOrgId(materialTypeDTO.getItemGroup(),
						materialTypeDTO.getOrgId())) {
					String errorMessage = String.format("The ItemGroup: %s already exists in this Organization!",
							materialTypeDTO.getItemGroup());
					throw new ApplicationException(errorMessage);
				}
				materialTypeVO.setItemGroup(materialTypeDTO.getItemGroup().toUpperCase());
			}
			message = "MaterialType Updated Successfully";
		} else {
			if (materialTypeRepo.existsByItemGroupAndOrgId(materialTypeDTO.getItemGroup(),
					materialTypeDTO.getOrgId())) {
				String errorMessage = String.format("The ItemGroup: %s already exists in this Organization!",
						materialTypeDTO.getItemGroup());
				throw new ApplicationException(errorMessage);
			}
			materialTypeVO.setCreatedBy(materialTypeDTO.getCreatedBy());
			materialTypeVO.setUpdatedBy(materialTypeDTO.getCreatedBy());
			message = "MaterialType Created Successfully";
		}

		getMaterialTypeVOFromMaterialTypeDTO(materialTypeDTO, materialTypeVO);
		materialTypeRepo.save(materialTypeVO);

		Map<String, Object> response = new HashMap<>();
		response.put("materialTypeVO", materialTypeVO);
		response.put("message", message);
		return response;
	}

	private void getMaterialTypeVOFromMaterialTypeDTO(MaterialTypeDTO materialTypeDTO, MaterialTypeVO materialTypeVO) {
		materialTypeVO.setMaterialType(materialTypeDTO.getMaterialType().toUpperCase());
		materialTypeVO.setItemGroup(materialTypeDTO.getItemGroup().toLowerCase().toUpperCase());
		materialTypeVO.setOrgId(materialTypeDTO.getOrgId());
		materialTypeVO.setCreatedBy(materialTypeDTO.getCreatedBy());
		materialTypeVO.setActive(materialTypeDTO.isActive());

		if (ObjectUtils.isNotEmpty(materialTypeVO.getId())) {
			List<MaterialDetailVO> materialDetailVO1 = materialDetailRepo.findByMaterialTypeVO(materialTypeVO);
			materialDetailRepo.deleteAll(materialDetailVO1);
		}

		List<MaterialDetailVO> materialDetailVOs = new ArrayList<>();
		for (MaterialDetailDTO materialDetailDTO : materialTypeDTO.getMaterialDetailDTO()) {
			MaterialDetailVO materialDetailVO = new MaterialDetailVO();
			materialDetailVO.setItemSubGroup(materialDetailDTO.getItemSubGroup());
			materialDetailVO.setMaterialTypeVO(materialTypeVO);
			materialDetailVOs.add(materialDetailVO);
		}
		materialTypeVO.setMaterialDetailVO(materialDetailVOs);
	}

	@Override
	public List<MaterialTypeVO> getAllMaterialTypeByOrgId(Long orgId) {
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  MaterialType BY OrgId : {}", orgId);
			materialTypeVO = materialTypeRepo.getAllMaterialTypeByOrgId(orgId);
		}
		return materialTypeVO;
	}

	@Override
	public List<MaterialTypeVO> getMaterialTypeById(Long id) {
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  MaterialType BY Id : {}", id);
			materialTypeVO = materialTypeRepo.getMaterialTypeById(id);
		}
		return materialTypeVO;
	}

	
	
}
