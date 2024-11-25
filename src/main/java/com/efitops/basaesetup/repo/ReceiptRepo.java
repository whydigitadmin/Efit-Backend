package com.efitops.basaesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.ReceiptVO;

@Repository
public interface ReceiptRepo extends JpaRepository<ReceiptVO, Long> {

	@Query(nativeQuery = true, value = "select * from receipt where orgid=?1")
	List<ReceiptVO> getAllReceiptReceivableByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from receipt where receiptid=?1")
	List<ReceiptVO> getAllReceiptReceivableById(Long id);

	@Query(nativeQuery = true, value = "select * from receipt where active=1")
	List<ReceiptVO> findReceiptReceivablesByActive();

	@Query(nativeQuery = true, value = "select partyname,partycode from partymaster where orgid=?1 and active=1 and partytype='CUSTOMER'")
	Set<Object[]> getCustomerNameAndCodeForReceipt(Long orgId);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate,a.customername,a.bankcashacc,a.receiptamt,a.bankchargeacc,a.taxamt,a.tdsamt,b.invno,b.invdate,b.refno,b.refdate,a.chequebank,a.chequeutino,b.amount,b.outstanding,b.settled,a.createdon,a.createdby from receipt a, receiptinvdetails b where a.receiptid=b.receiptid and a.orgid=?1 and a.docdate BETWEEN ?2 AND ?3 and a.customername =?4")
	Set<Object[]> findAllReceiptRegister(Long orgId, String fromDate, String toDate, String subLedgerName);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getReceiptDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getArBillBalanceDocId(Long orgId, String finYear, String branchCode, String screenCode);

}
