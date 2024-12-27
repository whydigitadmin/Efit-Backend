package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.CostDebitNoteDTO;
import com.efitops.basesetup.entity.CostDebitNoteVO;
import com.efitops.basesetup.entity.CostInvoiceVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface CostDebitNoteService {

	Map<String, Object> updateCreateCostDebitNote(@Valid CostDebitNoteDTO costDebitNoteDTO) throws ApplicationException;

	List<CostDebitNoteVO> getCostDebitNoteByOrgId(Long orgId);

	List<CostDebitNoteVO> getCostDebitNoteById(Long id);

	List<CostDebitNoteVO> getActiveCostDebitNote();

	CostDebitNoteVO getAllCostDebitNoteByDocId(Long orgId, String docId);


	String getCostDebitNoteDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> partyDetailsForCostDebitNote(Long orgId, String branch, String finYear);

	List<Map<String, Object>> chargeTypeDetailsForCostDebitNote(Long orgId);

	List<Map<String, Object>> getAllDocIdForCostInvoice(Long orgId);

	List<Map<String, Object>> getCurrencyAndExrates(Long orgId);

	List<CostInvoiceVO> getOrginBillNoByParty(Long orgId, String party, String branchCode);

}
