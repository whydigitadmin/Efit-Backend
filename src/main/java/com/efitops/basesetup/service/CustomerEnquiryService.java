package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.QuotationDTO;
import com.efitops.basesetup.dto.WorkOrderDTO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.QuotationVO;
import com.efitops.basesetup.entity.WorkOrderVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface CustomerEnquiryService {
	
	//Enquiry
	
	Map<String, Object> createUpdateEnquiry(EnquiryDTO enquiryDTO) throws ApplicationException;
	
	List<EnquiryVO> getAllEnquiryByOrgId(Long orgId);

	List<EnquiryVO> getEnquiryById(Long id); 
	
	String getEnquiryDocId(Long orgId);
	
	List<Map<String, Object>> getCustomerNameAndCode(Long orgId);
	
	List<Map<String, Object>> getContactNameAndNo(Long orgId,String partyName);
	
    List<Map<String, Object>> getPartNoAndDescription(Long orgId);
	
	List<Map<String, Object>> getDrawingNoAndRevNo(Long orgId,String partNo);

	//Quotation
	
  Map<String, Object> createUpdateQuotation(QuotationDTO quotationDTO) throws ApplicationException;
	
	List<QuotationVO> getAllQuotationByOrgId(Long orgId);

	List<QuotationVO> getQuotationById(Long id); 
	
	String getQuotationDocId(Long orgId);
	
	//WorkOrder
	
Map<String, Object> createUpdateWorkOrder(WorkOrderDTO workOrderDTO) throws ApplicationException;
	
	List<WorkOrderVO> getAllWorkOrderByOrgId(Long orgId);

	List<WorkOrderVO> getWorkOrderById(Long id); 
	
	String getWorkOrderDocId(Long orgId);
	
}
