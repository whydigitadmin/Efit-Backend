package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseInvoiceVO;

@Repository
public interface PurchaseInvoiceRepo extends JpaRepository<PurchaseInvoiceVO, Long> {

	@Query(nativeQuery = true, value = "select * from  purchaseinvoice where orgid=?1")
	List<PurchaseInvoiceVO> getAllPurchaseInvoiceByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from purchaseinvoice where purchaseinvoiceid=?1")
	PurchaseInvoiceVO getPurchaseInvoiceById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPurchaseInvoiceDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid from purchaseorder a where a.orgid=?1 and a.suppliercode=?2 and a.active group by a.docid order by a.docid")
	Set<Object[]> getPurchaseOrderPoNumber(Long orgId, String supplierCode);

	@Query(nativeQuery = true, value = "select a.grnno,a.grndate,a.location,a.inwardno,a1.partycode,a.gstno,a.address,\r\n"
			+ "				 a.currency,a.exchanderate,a.grncleartime,a.invdcno,a.invdcdate,a.gsttype,a.customer from \r\n"
			+ "	 grn a,partymaster a1 where a.orgid=?1 and a.pono=?2 and\r\n"
			+ "     a.suppliername = a1.partyname group by\r\n"
			+ "     a.grnno,a.grndate,a.location,a.inwardno,a1.partycode,a.gstno,a.address,\r\n"
			+ "	a.currency,a.exchanderate,a.grncleartime,a.invdcno,a.invdcdate,a.gsttype,a.customer order by a.grnno")
	Set<Object[]> getGrnNoAndGrnDateFromGrnDetails(Long orgId, String poNo);

	@Query(nativeQuery = true, value = "select a.itemcode,a.itemdesc,a.hsnsaccode,a.taxtype,a.primaryunit,\r\n"
			+ "	 a.porate,a.recievedqty,a.acceptqty from \r\n"
			+ "	 grn a1,grndetails a where a1.orgid=?1 and a1.grnno=?2 and a.grnid=a1.grnid \r\n"
			+ "     group by a.itemcode,a.itemdesc,a.hsnsaccode,a.taxtype,a.primaryunit, \r\n"
			+ "	a.porate,a.recievedqty,a.acceptqty order by  a.itemcode")
	Set<Object[]> getItemCodeAndItemDescFromGrn(Long orgId, String grnNo);

	@Query(nativeQuery = true, value = "select a1.purchaseorderdetailsid from purchaseorder a, purchaseorderdetails a1 where a.purchaseorderid=a1.purchaseorderid and \r\n"
			+ "			a.docid=?1 and a1.itemdesc=?2 and a.orgid=?3 group by a1.purchaseorderdetailsid")
	Set<Object[]> getPoDetailsId(Long docId, String itemDesc, Long orgId);

}
