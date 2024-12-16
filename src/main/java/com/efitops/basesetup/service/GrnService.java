package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDTO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.entity.ThirdPartyAttachmentVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface GrnService {

	List<GrnVO> getGrnByOrgId(Long orgId);

	List<GrnVO> getGrnById(Long id);

	Map<String, Object> updateCreateGrn(GrnDTO grndto) throws ApplicationException;

	String getGrnDocId(Long orgId);

	List<Map<String, Object>> getInwardNoForGRN(Long orgId);

	List<Map<String, Object>> getItemForGRN(Long orgId, String InwardNo);

	List<Map<String, Object>> getSupplierAddressForGRN(Long orgId, String supplierName);

	List<Map<String, Object>> getSGSTandCGSTForGRN(Long orgId, String taxType, String gstType);

	List<Map<String, Object>> getIGSTForGRN(Long orgId, String taxType, String gstType);

	// third party inspection
	List<ThirdPartyInspectionVO> getThirdPartyInspByOrgId(Long orgId);

	List<ThirdPartyInspectionVO> getThirdPartyInspById(Long id);

	List<Map<String, Object>> getGRNForThirdPartyInsp(Long orgId);

	List<Map<String, Object>> getThirdPartyDetailsForThirdPartyInsp(Long orgId);

	Map<String, Object> updateCreateThirdPartyInsp(ThirdPartyInspectionDTO thirdPartyInspectionDTO)
			throws ApplicationException;

	String getThirdPartyInspectionDocId(Long orgId);

	ThirdPartyAttachmentVO uploadFileForThirdPartyInspection(MultipartFile file, Long id) throws IOException;

	// Purchase Order

	List<PurchaseOrderVO> getPurchaseOrderByOrgId(Long orgId);

	List<PurchaseOrderVO> getPurchaseOrderById(Long id);

	Map<String, Object> updateCreatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) throws ApplicationException;

	// Map<String, Object> updateCreateThirdPartyInspection(ThirdPartyInspectionDTO
	// thirdPartyInspectionDTO);

	
	String getPurchaseOrderDocId(Long orgId);
	
	List<Map<String, Object>> getSupplierAddressForPurchaseOrder (Long orgId, String supplierName);

	List<Map<String, Object>> getPurchaseIndentForPurchaseOrder (Long orgId, String customerCode, String workorderno,String basedOn);
	
	List<Map<String, Object>> getQuotationForPurchaseOrder (Long orgId, String customerCode, String workorderno,String basedOn);
	
	List<Map<String, Object>> getItemForPurchaseOrder (Long orgId,String purchaseIndentNo,String quotationNo);

}
