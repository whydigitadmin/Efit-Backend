package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PurchaseEnquiryDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface PurchaseService {

	Map<String, Object> updateCreatePurchaseIndent(@Valid PurchaseIndentDTO purchaseIndentDTO) throws ApplicationException;

	List<PurchaseIndentVO> getAllPurchaseIndentByOrgId(Long orgId);

	Optional<PurchaseIndentVO> getPurchaseIndentById(Long id);

	List<Map<String, Object>> getCustomerNameForPurchaseIndent(Long orgId);

	List<Map<String, Object>> getIndentType();

	List<Map<String, Object>> getDepartmentForPurchase();

	List<Map<String, Object>> getRequestedByForPurchase(); 

	List<Map<String, Object>> getItemDetailsForPurchase(String itemName);
	
	String getpurchaseIndentDocId(Long orgId, String finYear, String screenCode);
	
	
	//PurchaseEnquiry

	Map<String, Object> updateCreatePurchaseEnquiry(@Valid PurchaseEnquiryDTO purchaseIndentDTO) throws ApplicationException;

	List<PurchaseEnquiryVO> getAllPurchaseEnquiryByOrgId(Long orgId);

	Optional<PurchaseEnquiryVO> getAllPurchaseEnquiryById(Long id);

	String getPurchaseEnquiryDocId(Long orgId, String finYear, String screenCode);

	

	
	
}
