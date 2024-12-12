package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface QualityService {

	// IncomingMaterialInspection

	Map<String, Object> createUpdateIncomingMaterialInspection(IncomingMaterialInspectionDTO incomingMaterialInspectionDTO)
			throws ApplicationException;

	List<IncomingMaterialInspectionVO> getAllIncomingMaterialInspectionByOrgId(Long orgId);

	IncomingMaterialInspectionVO getIncomingMaterialInspectionById(Long id);

	String getIncomingMaterialInspectionDocId(Long orgId);

}
