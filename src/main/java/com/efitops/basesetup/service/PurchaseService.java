package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.PurchaseEnquiryDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseQuotationDTO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseQuotationAttachmentVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface PurchaseService {

	Map<String, Object> updateCreatePurchaseIndent(@Valid PurchaseIndentDTO purchaseIndentDTO) throws ApplicationException;

	List<PurchaseIndentVO> getAllPurchaseIndentByOrgId(Long orgId);

	Optional<PurchaseIndentVO> getPurchaseIndentById(Long id);

	List<Map<String, Object>> getCustomerNameForPurchaseIndent(Long orgId);

	List<Map<String, Object>> getIndentType(Long orgId);

	List<Map<String, Object>> getDepartmentForPurchase(Long orgId);

	List<Map<String, Object>> getRequestedByForPurchase(Long orgId); 

	List<Map<String, Object>> getBomItemDetailsForPurchase(Long orgId, String fgPart);
	
	String getpurchaseIndentDocId(Long orgId);
	
	List<Map<String, Object>> getWorkOrderNoForPurchaseIndent(Long orgId, String customerCode);

	List<Map<String, Object>> getWorkOrderDetailsForPurchaseIndent(Long orgId, String workOrderNo);

	List<Map<String, Object>> getVerifiedByForPurchase(Long orgId);

	
	//PurchaseEnquiry

	Map<String, Object> updateCreatePurchaseEnquiry(@Valid PurchaseEnquiryDTO purchaseEnquiryDTO) throws ApplicationException;

	List<PurchaseEnquiryVO> getAllPurchaseEnquiryByOrgId(Long orgId);

	Optional<PurchaseEnquiryVO> getAllPurchaseEnquiryById(Long id);

	String getPurchaseEnquiryDocId(Long orgId, String finYear, String screenCode);

	List<Map<String, Object>> getSupplierNameForPurchaseEnquiry(Long orgId);

	List<Map<String, Object>> getContactPersonDetailsForPurchaseEnquiry(Long orgId, String supplierCode);

	List<Map<String, Object>> getPurchaseIndentNoForPurchaseEnquiry(Long orgId, String customerCode, String workOrderNo);

	List<Map<String, Object>> getItemDetailsForPurchaseEnquiry(Long orgId, String purchaseIndentNo, String fgItem);

	List<Map<String, Object>> getWorkOrderNoForPurchaseEnquiry(Long orgId, String customerCode);
	
	List<Map<String, Object>> getWorkOrderDetailsForPurchaseEnquiry(Long orgId, String workOrderNo);


	//PurchaseQuotation
	
	List<PurchaseQuotationVO> getAllPurchaseQuotationByOrgId(Long orgId);

	Optional<PurchaseQuotationVO> getPurchaseQuotationById(Long id);

	Map<String, Object> updateCreatePurchaseQuotation(@Valid PurchaseQuotationDTO purchaseQuotationDTO) throws ApplicationException;

	String getpurchaseQuotationDocId(Long orgId);

	List<Map<String, Object>> getPurchaseEnquiryNoForPurchaseQuotation(Long orgId, String customerCode,String workOrderNo);

	List<Map<String, Object>> getItemDetailsForPurchaseQuotation(Long orgId, String purchaseEnquiryNo);

	PurchaseQuotationAttachmentVO uploadPurchaseQuatationAttachementsInBloob(MultipartFile file, Long id) throws IOException;

	List<Map<String, Object>> getWorkOrderNoForPurchaseQuotation(Long orgId, String customerCode);

	List<Map<String, Object>> getWorkOrderDetailsForPurchaseQuotation(Long orgId, String workOrderNo);





	
}
