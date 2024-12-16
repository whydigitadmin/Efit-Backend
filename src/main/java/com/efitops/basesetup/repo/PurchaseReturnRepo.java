package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseReturnVO;

@Repository
public interface PurchaseReturnRepo extends JpaRepository<PurchaseReturnVO, Long> {

	@Query(nativeQuery = true, value = "select * from  t_purchasereturn where orgid=?1")
	List<PurchaseReturnVO> getAllPurchaseReturnByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_purchasereturn  where purchasereturnid=?1")
	PurchaseReturnVO getPurchaseReturnById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPurchaseReturnDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate,a.grncleartime,a.pono,a.gst_no,a.gststate,\r\n"
			+ " a.address,a.inwardno,a.currency,a.exchangerate,a.invdcno,a.invdcdate,a.gsttype,a.customername from t_purchaseinvoice a where\r\n"
			+ " a.orgid=?1 and a.docid=?2 and a.suppliercode=?3 and  a.active group by a.docid,a.docdate,a.grncleartime,a.pono,a.gst_no,a.gststate,\r\n"
			+ " a.address,a.inwardno,a.currency,a.exchangerate,a.invdcno,a.invdcdate,a.gsttype,a.customername order by a.docid")
	Set<Object[]> getPurchaseInvoiceNumberFromPurchaseInvoice(Long orgId, String purchaseInvoiceNo,
			String supplierCode);

	@Query(nativeQuery = true, value = "select a.locationcode from m_stocklocation a where a.orgid=?1 group by a.locationcode order by a.locationcode")
	Set<Object[]> getLocationFromStockLocation(Long orgId);

	@Query(nativeQuery = true, value = " select a.itemcode,a.itemname,a.hsnsaccode,a.taxtype,a.primaryunit,\r\n"
			+ " a.porate,a.unitprice from\r\n"
			+ " t_purchaseinvoice a1,t_purchaseinvoiceitem a where a1.orgid=?1 and a1.docid=?2 and a.purchaseinvoiceid=a1.purchaseinvoiceid and \r\n"
			+ " a1.active group by a.itemcode,a.itemname,a.hsnsaccode,a.taxtype,a.primaryunit,\r\n"
			+ " a.porate,a.unitprice order by  a.itemcode")
	Set<Object[]> getItemCodeAndItemDescFromPurchsaeInvoice(Long orgId, String purchaseInvoiceNo);

}
