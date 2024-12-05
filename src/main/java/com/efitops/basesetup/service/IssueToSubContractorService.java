package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DcForSubContractDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDTO;
import com.efitops.basesetup.dto.SubContractInvoiceDTO;
import com.efitops.basesetup.dto.SubContractQuotationDTO;
import com.efitops.basesetup.entity.DcForSubContractVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.entity.SubContractEnquiryVO;
import com.efitops.basesetup.entity.SubContractInvoiceVO;
import com.efitops.basesetup.entity.SubContractQuotationVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface IssueToSubContractorService {
	
	//IssueToSubContractor
	Map<String, Object> createUpdateIssueToSubContractor(IssueToSubContractorDTO  issueToSubContractorDTO) throws ApplicationException;

	List<IssueToSubContractorVO> getAllIssueToSubContractorByOrgId(Long orgId);

	List<IssueToSubContractorVO> getIssueToSubContractorById(Long id);

	String getIssueToSubContractorDocId(Long orgId);
		
	List<Map<String, Object>> getRouteCardNoAndItemNo(Long orgId);
	
	List<Map<String, Object>> getDepartmentName(Long orgId);

	List<Map<String, Object>> getProcessNameFormItemWiseProcess(Long orgId,String item);
	
	//DcForSubContracto
	
	List<DcForSubContractVO> getDcforSCByOrgId(Long orgId);

	List<DcForSubContractVO> getDcforSCById(Long id);


	Map<String, Object> updateCreateDcForSubContract(DcForSubContractDTO dcForSubContractDTO)
			throws ApplicationException;

	String getDcForSubContractDocId(Long orgId);
	
	//SubContractEnquiry
	
	Map<String, Object> createUpdateSubContractEnquiry(SubContractEnquiryDTO  subContractEnquiryDTO) throws ApplicationException;

	List<SubContractEnquiryVO> getAllSubContractEnquiryByOrgId(Long orgId);

	List<SubContractEnquiryVO> getSubContractEnquiryById(Long id);

	String getSubContractEnquiryDocId(Long orgId);
	
	
	
	//SubContractQuotation
	
	Map<String, Object> createUpdateSubContractQuotation(SubContractQuotationDTO  subContractQuotationDTO) throws ApplicationException;

	List<SubContractQuotationVO> getAllSubContractQuotationByOrgId(Long orgId);

	List<SubContractQuotationVO> getSubContractQuotationById(Long id);

	String getSubContractQuotationDocId(Long orgId);
	
	
	//SubContractInvoice
	
	Map<String, Object> createUpdateSubContractInvoice(SubContractInvoiceDTO  subContractInvoiceDTO) throws ApplicationException;

	List<SubContractInvoiceVO> getAllSubContractInvoiceByOrgId(Long orgId);

	List<SubContractInvoiceVO> getSubContractInvoiceById(Long id);

	String getSubContractInvoiceDocId(Long orgId);

}
