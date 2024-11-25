package com.efitops.basaesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basaesetup.dto.AdjustmentJournalDTO;
import com.efitops.basaesetup.dto.ArApAdjustmentOffSetDTO;
import com.efitops.basaesetup.dto.BankingDepositDTO;
import com.efitops.basaesetup.dto.BankingWithdrawalDTO;
import com.efitops.basaesetup.dto.BrsOpeningDTO;
import com.efitops.basaesetup.dto.ChartCostCenterDTO;
import com.efitops.basaesetup.dto.ContraVoucherDTO;
import com.efitops.basaesetup.dto.DailyMonthlyExRatesDTO;
import com.efitops.basaesetup.dto.DebitNoteDTO;
import com.efitops.basaesetup.dto.FundTransferDTO;
import com.efitops.basaesetup.dto.GeneralJournalDTO;
import com.efitops.basaesetup.dto.GlOpeningBalanceDTO;
import com.efitops.basaesetup.dto.GstSalesVoucherDTO;
import com.efitops.basaesetup.dto.PaymentReversalDTO;
import com.efitops.basaesetup.dto.PaymentVoucherDTO;
import com.efitops.basaesetup.dto.ReceiptReversalDTO;
import com.efitops.basaesetup.dto.ReconcileBankDTO;
import com.efitops.basaesetup.dto.ReconcileCashDTO;
import com.efitops.basaesetup.dto.ReconcileCorpBankDTO;
import com.efitops.basaesetup.dto.TmsJobCardDTO;
import com.efitops.basaesetup.entity.AdjustmentJournalVO;
import com.efitops.basaesetup.entity.ArApAdjustmentOffSetVO;
import com.efitops.basaesetup.entity.BankingDepositVO;
import com.efitops.basaesetup.entity.BankingWithdrawalVO;
import com.efitops.basaesetup.entity.BrsExcelUploadVO;
import com.efitops.basaesetup.entity.BrsOpeningVO;
import com.efitops.basaesetup.entity.ChartCostCenterVO;
import com.efitops.basaesetup.entity.ContraVoucherVO;
import com.efitops.basaesetup.entity.DailyMonthlyExRatesVO;
import com.efitops.basaesetup.entity.DebitNoteVO;
import com.efitops.basaesetup.entity.FundTransferVO;
import com.efitops.basaesetup.entity.GeneralJournalVO;
import com.efitops.basaesetup.entity.GlOpeningBalanceVO;
import com.efitops.basaesetup.entity.GstSalesVoucherVO;
import com.efitops.basaesetup.entity.PaymentReversalVO;
import com.efitops.basaesetup.entity.PaymentVoucherVO;
import com.efitops.basaesetup.entity.ReceiptReversalVO;
import com.efitops.basaesetup.entity.ReconcileBankVO;
import com.efitops.basaesetup.entity.ReconcileCashVO;
import com.efitops.basaesetup.entity.ReconcileCorpBankVO;
import com.efitops.basaesetup.entity.TmsJobCardVO;
import com.efitops.basaesetup.exception.ApplicationException;

@Service
public interface TransactionService {

//	DailyMonthlyExRatesVO
	List<DailyMonthlyExRatesVO> getAllDailyMonthlyExRatesByOrgId(Long orgId);

	DailyMonthlyExRatesVO updateCreateDailyMonthlyExRates(@Valid DailyMonthlyExRatesDTO irnCreditDTO)
			throws ApplicationException;

	List<DailyMonthlyExRatesVO> getAllDailyMonthlyExRatesById(Long id);

	List<DailyMonthlyExRatesVO> getDailyMonthlyExRatesByActive();

//	BrsOpening
	List<BrsOpeningVO> getAllBrsOpeningByOrgId(Long orgId);

	Map<String, Object> updateCreateBrsOpening(@Valid BrsOpeningDTO brsOpeningDTO) throws ApplicationException;

	List<BrsOpeningVO> getAllBrsOpeningById(Long id);

	List<BrsOpeningVO> getBrsOpeningByActive();

	List<Map<String, Object>> getBranchForBrsOpening(Long orgId);

	void ExcelUploadForBrs(MultipartFile[] files, Long orgId, String createdBy, String branch, String branchCode)
			throws ApplicationException, EncryptedDocumentException, IOException;

	List<BrsExcelUploadVO> getAllBrsExcelByOrgId(Long orgId);

	int getTotalRows();

	int getSuccessfulUploads();

//	ChartCostCenter
	List<ChartCostCenterVO> getAllChartCostCenterByOrgId(Long orgId);

	List<Map<String, Object>> updateCreateChartCostCenterList(@Valid List<ChartCostCenterDTO> chartCostCenterDTOList)
			throws ApplicationException;


	
	List<ChartCostCenterVO> getChartCostCenterById(Long id);

	List<ChartCostCenterVO> getChartCostCenterByActive();

//	FundTransfer
	List<FundTransferVO> getAllFundTransferByOrgId(Long orgId);

	Map<String, Object> updateCreateFundTransfer(@Valid FundTransferDTO fundTransferDTO) throws ApplicationException;

	List<FundTransferVO> getAllFundTransferById(Long id);

	List<FundTransferVO> getFundTransferByActive();

// GeneralJournal
	List<GeneralJournalVO> getAllGeneralJournalByOrgId(Long orgId);

	Map<String, Object> updateCreateGeneralJournal(@Valid GeneralJournalDTO generalJournalDTO)
			throws ApplicationException;

	List<GeneralJournalVO> getGeneralJournalById(Long id);

	List<GeneralJournalVO> getGeneralJournalByActive();
	
	List<Map<String, Object>> getAccountNameFromGroup( Long orgId);

// DebitNote
	List<DebitNoteVO> getAllDebitNoteByOrgId(Long orgId);

	DebitNoteVO updateCreateDebitNote(@Valid DebitNoteDTO debitNoteDTO) throws ApplicationException;

	List<DebitNoteVO> getAllDebitNoteById(Long id);

	List<DebitNoteVO> getDebitNoteByActive();

// GstSalesVoucher
	List<GstSalesVoucherVO> getAllGstSalesVoucherByOrgId(Long orgId);

	GstSalesVoucherVO updateCreateGstSalesVoucher(@Valid GstSalesVoucherDTO gstSalesVoucherDTO)
			throws ApplicationException;

	List<GstSalesVoucherVO> getAllGstSalesVoucherById(Long id);

	List<GstSalesVoucherVO> getGstSalesVoucherByActive();

// PaymentVoucher
	List<PaymentVoucherVO> getAllPaymentVoucherByOrgId(Long orgId);

	Map<String, Object> updateCreatePaymentVoucher(@Valid PaymentVoucherDTO paymentVoucherDTO)
			throws ApplicationException;

	List<PaymentVoucherVO> getPaymentVoucherById(Long id);

	List<PaymentVoucherVO> getPaymentVoucherByActive();

	PaymentVoucherVO getpaymentVoucherByDocId(Long orgId, String docId);

	String getpaymentVoucherDocId(Long orgId, String finYear, String branch, String branchCode);

//	ReceiptReversal
	List<ReceiptReversalVO> getAllReceiptReversalByOrgId(Long orgId);

	ReceiptReversalVO updateCreateReceiptReversal(@Valid ReceiptReversalDTO receiptReversalDTO)
			throws ApplicationException;

	List<ReceiptReversalVO> getAllReceiptReversalById(Long id);

	List<ReceiptReversalVO> getReceiptReversalByActive();

//	PaymentReversal
	List<PaymentReversalVO> getAllPaymentReversalByOrgId(Long orgId);

	PaymentReversalVO updateCreatePaymentReversal(@Valid PaymentReversalDTO paymentReversalDTO)
			throws ApplicationException;

	List<PaymentReversalVO> getAllPaymentReversalById(Long id);

	List<PaymentReversalVO> getPaymentReversalByActive();

//	ArApAdjustmentOffSet
	List<ArApAdjustmentOffSetVO> getAllArApAdjustmentOffSetByOrgId(Long orgId);

	ArApAdjustmentOffSetVO updateCreateArApAdjustmentOffSet(@Valid ArApAdjustmentOffSetDTO arApAdjustmentOffSetDTO)
			throws ApplicationException;

	List<ArApAdjustmentOffSetVO> getAllArApAdjustmentOffSetById(Long id);

	List<ArApAdjustmentOffSetVO> getArApAdjustmentOffSetByActive();

	// GlOpeningBalance
	List<GlOpeningBalanceVO> getAllGlOpeningBalanceByOrgId(Long orgId);

	Map<String, Object> updateCreateGlOpeningBalance(@Valid GlOpeningBalanceDTO glOpeningBalanceDTO)
			throws ApplicationException;

	List<GlOpeningBalanceVO> getAllGlOpeningBalanceById(Long id);

	List<GlOpeningBalanceVO> getGlOpeningBalanceByActive();

	String getGlOpeningBalanceDocId(Long orgId, String finYear, String branch, String branchCode);

	GlOpeningBalanceVO getGlOpeningBalanceByDocId(Long orgId, String docId);

	// ReconciliationSummary

//	List<ReconciliationSummaryVO> getAllReconciliationSummaryById(Long id);
//
//	List<ReconciliationSummaryVO> getAllReconciliationSummaryByOrgId(Long orgId);
//
//	ReconciliationSummaryVO updateCreateReconciliationSummary(@Valid ReconciliationSummaryDTO reconciliationSummaryDTO);

	// ReconcileBank
	List<ReconcileBankVO> getAllReconcileBankByOrgId(Long orgId);

	Map<String, Object> updateCreateReconcileBank(@Valid ReconcileBankDTO reconcileBankDTO) throws ApplicationException;

	List<ReconcileBankVO> getAllReconcileBankById(Long id);

	String getReconcileBankDocId(Long orgId, String finYear, String branch, String branchCode);

	String getGeneralJournalDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> getBankNameForGroupLedger(Long orgId);

	// ReconcileCorpBank
	List<ReconcileCorpBankVO> getAllReconcileCorpBankByOrgId(Long orgId);

	Map<String, Object> updateCreateReconcileCorpBank(@Valid ReconcileCorpBankDTO reconcileCorpBankDTO)
			throws ApplicationException;

	List<ReconcileCorpBankVO> getAllReconcileCorpBankById(Long id);

	String getReconcileCorpBankDocId(Long orgId, String finYear, String branch, String branchCode);


	// ReconcileCash

	List<ReconcileCashVO> getAllReconcileCashByOrgId(Long orgId);

	Map<String, Object> updateCreateReconcileCash(@Valid ReconcileCashDTO reconcileCashDTO) throws ApplicationException;

	List<ReconcileCashVO> getAllReconcileCashById(Long id);

	FundTransferVO getFundTranferByDocId(Long orgId, String docId);

	String getFundTranferDocId(Long orgId, String finYear, String branch, String branchCode);

	String getReconcileCashDocId(Long orgId, String finYear, String branch, String branchCode);

	String getChartCostCenterDocId(Long orgId, String finYear, String branch, String branchCode);


	/// TMS-TT-JobCard

	List<TmsJobCardVO> getAllTmsJobCardByOrgId(Long orgId);

	Map<String, Object> updateCreateTmsJobCard(@Valid TmsJobCardDTO tmsJobCardDTO) throws ApplicationException;

	List<TmsJobCardVO> getAllTmsJobCardById(Long id);

	List<Map<String, Object>> getSalesPersonFromPartyMaster(Long orgId, String partyName);

	List<Map<String, Object>> getAllCustomersFromPartyMaster(Long orgId);

	String getTmsJobCardDocId(Long orgId, String finYear, String branch, String branchCode);

	// AdjustmentJournal

	List<AdjustmentJournalVO> getAllAdjustmentJournalByOrgId(Long orgId);

	List<AdjustmentJournalVO> getAdjustmentJournalById(Long id);

	Map<String, Object> updateCreateAdjustmentJournal(@Valid AdjustmentJournalDTO adjustmentJournalDTO)
			throws ApplicationException;

	String getAdjustmentJournalDocId(Long orgId, String finYear, String branch, String branchCode);

	//BankingDeposit
	List<BankingDepositVO> getAllBankingDepositByOrgId(Long orgId);

	List<BankingDepositVO> getBankingDepositById(Long id);

	Map<String, Object> updateCreateBankingDeposit(@Valid BankingDepositDTO bankingDepositDTO)
			throws ApplicationException;

	String getBankingDepositDocId(Long orgId, String finYear, String branch, String branchCode);
 
	List<Map<String, Object>> getBankNameFromGroupforBankingDeposit(Long orgId);

	//BankingWithdrawal
	List<BankingWithdrawalVO> getAllBankingWithdrawalByOrgId(Long orgId);

	List<BankingWithdrawalVO> getBankingWithdrawalById(Long id);

	Map<String, Object> updateCreateBankingWithdrawal(@Valid BankingWithdrawalDTO bankingWithdrawalDTO) throws ApplicationException;

	String getBankingWithdrawalDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> getCurrencyAndExrates(Long orgId);

	//contraVoucher
	
	List<ContraVoucherVO> getAllContraVoucherByOrgId(Long orgId);

	List<ContraVoucherVO> getContraVoucherById(Long id);

	String getContraVoucherDocId(Long orgId, String finYear, String branch, String branchCode);

	Map<String, Object> updateCreateContraVoucher(@Valid ContraVoucherDTO contraVoucherDTO) throws ApplicationException;


	
}