package com.efitops.basaesetup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basaesetup.dto.AccountDTO;
import com.efitops.basaesetup.dto.BranchDTO;
import com.efitops.basaesetup.dto.ChargeTypeRequestDTO;
import com.efitops.basaesetup.dto.ChequeBookDTO;
import com.efitops.basaesetup.dto.CostCenterDTO;
import com.efitops.basaesetup.dto.EmployeeDTO;
import com.efitops.basaesetup.dto.GroupLedgerDTO;
import com.efitops.basaesetup.dto.ListOfValuesDTO;
import com.efitops.basaesetup.dto.PartyMasterDTO;
import com.efitops.basaesetup.dto.SacCodeDTO;
import com.efitops.basaesetup.dto.SetTaxRateDTO;
import com.efitops.basaesetup.dto.SubLedgerAccountDTO;
import com.efitops.basaesetup.dto.TaxMasterDTO;
import com.efitops.basaesetup.dto.TcsMasterDTO;
import com.efitops.basaesetup.dto.TdsMasterDTO;
import com.efitops.basaesetup.entity.AccountVO;
import com.efitops.basaesetup.entity.BranchVO;
import com.efitops.basaesetup.entity.ChargeTypeRequestVO;
import com.efitops.basaesetup.entity.ChequeBookVO;
import com.efitops.basaesetup.entity.CostCenterVO;
import com.efitops.basaesetup.entity.EmployeeVO;
import com.efitops.basaesetup.entity.GroupLedgerVO;
import com.efitops.basaesetup.entity.ListOfValuesVO;
import com.efitops.basaesetup.entity.PartyMasterVO;
import com.efitops.basaesetup.entity.SacCodeVO;
import com.efitops.basaesetup.entity.SetTaxRateVO;
import com.efitops.basaesetup.entity.SubLedgerAccountVO;
import com.efitops.basaesetup.entity.TaxMasterVO;
import com.efitops.basaesetup.entity.TcsMasterVO;
import com.efitops.basaesetup.entity.TdsMasterVO;
import com.efitops.basaesetup.exception.ApplicationException;

@Service
public interface MasterService {

	// Branch

	List<BranchVO> getAllBranch(Long orgid);

	Optional<BranchVO> getBranchById(Long branchid);

	Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception;

	void deleteBranch(Long branchid);

	// employee

	List<EmployeeVO> getAllEmployee();

	List<EmployeeVO> getAllEmployeeByOrgId(Long orgId);

	Optional<EmployeeVO> getEmployeeById(Long employeeid);

	Map<String, Object> createEmployee(EmployeeDTO employeeDTO) throws ApplicationException;

	void deleteEmployee(Long employeeid);

//	SetTaxRateVO
	List<SetTaxRateVO> getAllSetTaxRateByOrgId(Long orgId);

	List<SetTaxRateVO> getAllSetTaxRateById(Long id);

	SetTaxRateVO updateCreateSetTaxRate(@Valid SetTaxRateDTO setTaxRateDTO) throws Exception;

	List<SetTaxRateVO> getSetTaxRateByActive();

//	TaxMasterVO
	TaxMasterVO updateCreateTaxMaster(TaxMasterDTO taxMasterDTO) throws ApplicationException;

	List<TaxMasterVO> getAllTaxMasterByOrgId(Long orgId);

	List<TaxMasterVO> getAllTaxMasterById(Long id);

	List<TaxMasterVO> getTaxMasterByActive();

//	TcsMasterVO 
	List<TcsMasterVO> getAllTcsMasterByOrgId(Long orgId);

	List<TcsMasterVO> getAllTcsMasterById(Long id);

	TcsMasterVO updateCreateTcsMaster(@Valid TcsMasterDTO tcsMasterDTO) throws ApplicationException;

	List<TcsMasterVO> getTcsMasterByActive();

//	TdsMasterVO
	List<TdsMasterVO> getAllTdsMasterByOrgId(Long orgId);

	List<TdsMasterVO> getAllTdsMasterById(Long id);

	TdsMasterVO updateCreateTdsMaster(@Valid TdsMasterDTO tdsMasterDTO) throws ApplicationException;

	List<TdsMasterVO> getTdsMasterByActive();

//	AccountVO
	List<AccountVO> getAllAccountByOrgId(Long orgId);

	AccountVO updateCreateAccount(@Valid AccountDTO accountDTO) throws ApplicationException;

	List<AccountVO> getAllAccountById(Long id);

	List<AccountVO> getAccountByActive();

//	GroupLedgerVO
	List<GroupLedgerVO> getAllGroupLedgerById(Long id);

	List<GroupLedgerVO> getAllGroupLedgerByOrgId(Long orgId);

	List<Map<String, Object>> getGroupName(Long orgId);

	GroupLedgerVO updateCreateGroupLedger(@Valid GroupLedgerDTO groupLedgerDTO) throws ApplicationException;

	List<GroupLedgerVO> getGroupLedgerByActive();

//	SacCode
	List<SacCodeVO> getAllSacCodeById(Long id);

	List<SacCodeVO> getAllSacCodeByOrgId(Long orgId);

	List<SacCodeVO> getAllActiveSacCodeByOrgId(Long orgId);

	SacCodeVO updateCreateSacCode(@Valid SacCodeDTO sacCodeDTO) throws ApplicationException;

//	List<SacCodeVO> getSacCodeByActive();

//	SubLedgerAccount
	List<SubLedgerAccountVO> getAllSubLedgerAccountByOrgId(Long orgId);

	SubLedgerAccountVO updateCreateSubLedgerAccount(@Valid SubLedgerAccountDTO subLedgerAccountDTO)
			throws ApplicationException;

	List<SubLedgerAccountVO> getAllSubLedgerAccountById(Long id);

	List<SubLedgerAccountVO> getSubLedgerAccountByActive();

//	CostCenterVO
	List<CostCenterVO> getAllCostCenterByOrgId(Long orgId);

	CostCenterVO updateCreateCostCenter(@Valid CostCenterDTO costCenterDTO) throws ApplicationException;

	List<CostCenterVO> getAllCostCenterById(Long id);

	List<CostCenterVO> getCostCenterByActive();

//	ChequeBook
	List<ChequeBookVO> getAllChequeBookByOrgId(Long orgId);

	ChequeBookVO updateCreateChequeBook(@Valid ChequeBookDTO chequeBookDTO) throws ApplicationException;

	List<ChequeBookVO> getAllChequeBookById(Long id);

	List<ChequeBookVO> getChequeBookByActive();

//	ChargeTypeRequest
	List<ChargeTypeRequestVO> getAllChargeTypeRequestByOrgId(Long orgId);

	List<Map<String, Object>> getChargeType(Long orgId);

	ChargeTypeRequestVO updateCreateChargeTypeRequest(@Valid ChargeTypeRequestDTO chargeTypeRequestDTO)
			throws ApplicationException;

	List<ChargeTypeRequestVO> getAllChargeTypeRequestById(Long id);

	List<ChargeTypeRequestVO> getChargeTypeRequestByActive();

	List<Map<String, Object>> getSalesAccountFromGroup(Long orgId);

	List<Map<String, Object>> getPaymentAccountFromGroup(Long orgId);
//	ListOfValues

	List<ListOfValuesVO> getListOfValuesById(Long id);

	List<ListOfValuesVO> getListOfValuesByOrgId(Long orgid);

	ListOfValuesVO updateCreateListOfValues(@Valid ListOfValuesDTO listOfValuesDTO) throws ApplicationException;

	// PartyMaster

	List<PartyMasterVO> getPartyMasterByOrgId(Long orgid);

	List<PartyMasterVO> getPartyMasterById(Long id);

	PartyMasterVO updateCreatePartyMaster(@Valid PartyMasterDTO partyMasterDTO) throws ApplicationException;

	String getPartyMasterDocId(Long orgId, String finYear, String branch, String branchCode);

}
