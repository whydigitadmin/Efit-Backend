package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDTO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface GrnService {

	List<GrnVO> getGrnByOrgId(Long orgId);


	List<GrnVO> getGrnById(Long id);

	Map<String, Object> updateCreateGrn(GrnDTO grndto) throws ApplicationException;

	String getGrnDocId(Long orgId);
	
	List<Map<String, Object>> getInwardNoForGRN(Long orgId);
	
	List<Map<String, Object>> getItemForGRN(Long orgId,String InwardNo);

	List<ThirdPartyInspectionVO> getThirdPartyInspByOrgId(Long orgId);

	List<ThirdPartyInspectionVO> getThirdPartyInspById(Long id);
	
	Map<String, Object> updateCreateThirdPartyInsp (ThirdPartyInspectionDTO thirdPartyInspectionDTO) throws ApplicationException;
	
	List<Map<String, Object>> getGRNDetForThirdPartyInsp (Long orgId);


	String getThirdPartyInspectionDocId(Long orgId);


	//Map<String, Object> updateCreateThirdPartyInspection(ThirdPartyInspectionDTO thirdPartyInspectionDTO);

	}
