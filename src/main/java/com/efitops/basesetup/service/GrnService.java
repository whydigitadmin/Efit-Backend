package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDTO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
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

	List<Map<String, Object>> getSupplierAddressForGRN(Long orgId, String supplierName);
	
	List<Map<String, Object>> getSGSTandCGSTForGRN(Long orgId,String taxType,String gstType );
	
	List<Map<String, Object>> getIGSTForGRN(Long orgId,String taxType,String gstType );
	
	//third party inspection
	List<ThirdPartyInspectionVO> getThirdPartyInspByOrgId(Long orgId);

	List<ThirdPartyInspectionVO> getThirdPartyInspById(Long id);
	
	List<Map<String,Object>> getGRNForThirdPartyInsp(Long orgId);
	
	List<Map<String,Object>> getThirdPartyDetailsForThirdPartyInsp(Long orgId);
	

	
	
	Map<String, Object> updateCreateThirdPartyInsp (ThirdPartyInspectionDTO thirdPartyInspectionDTO) throws ApplicationException;
	

	String getThirdPartyInspectionDocId(Long orgId);
	
	ThirdPartyInspectionVO uploadFileForThirdPartyInspection(MultipartFile file, Long id) throws IOException;



	//Map<String, Object> updateCreateThirdPartyInspection(ThirdPartyInspectionDTO thirdPartyInspectionDTO);

	}
