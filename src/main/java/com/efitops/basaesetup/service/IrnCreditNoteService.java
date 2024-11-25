package com.efitops.basaesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basaesetup.dto.IrnCreditNoteDTO;
import com.efitops.basaesetup.entity.IrnCreditNoteVO;
import com.efitops.basaesetup.entity.PartyMasterVO;
import com.efitops.basaesetup.entity.TaxInvoiceVO;
import com.efitops.basaesetup.exception.ApplicationException;

@Service
public interface IrnCreditNoteService {
	
//	IrnCredit
	List<IrnCreditNoteVO> getAllIrnCreditByOrgId(Long orgId);
	
	List<IrnCreditNoteVO> getAllIrnCreditById(Long id);

	Map<String, Object> updateCreateIrnCreditNote(IrnCreditNoteDTO irnCreditDTO) throws ApplicationException;
	
	String getIrnCreditNoteDocId(Long orgId, String finYear, String branch, String branchCode);

	List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType);
	
	List<TaxInvoiceVO>getOriginBillNofromTaxInvoiceByParty(Long orgId,String party,String branchCode);
}
