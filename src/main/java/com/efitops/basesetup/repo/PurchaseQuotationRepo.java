package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseQuotationVO;

@Repository
public interface PurchaseQuotationRepo extends JpaRepository<PurchaseQuotationVO, Long>{

	@Query(nativeQuery = true, value = "SELECT * FROM purchasequotation where orgid=?1")
	List<PurchaseQuotationVO> getAllPurchaseQuotationByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM purchasequotation where purchasequotationid=?1")
	Optional<PurchaseQuotationVO> getAllPurchaseQuotationById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getPurchaseQuotationByDocId(Long orgId, String screenCode);

	@Query(nativeQuery=true,value ="SELECT a.docid,a.docdate,a.suppliername,a.suppliercode FROM purchaseenquiry a  WHERE a.orgid = ?1 AND a.customercode = ?2 and a.workorderno=?3 AND a.active = 1 AND a.docid NOT IN (SELECT c.enquiryno FROM purchasequotation c WHERE c.orgid = ?1) ORDER BY a.docid" )
	Set<Object[]> findPurchaseEnquiryNoForPurchaseQuotation(Long orgId, String customerCode, String workOrderNo);

	@Query(nativeQuery=true,value ="select distinct b.item,b.itemdesc,b.unit from purchaseenquiry a join purchaseenquirydetails b \r\n"
			+ "ON a.purchaseenquiryid=b.purchaseenquiryid where  a.orgid=?1 and a.docid=?2  and active=1 order by 1" )
	Set<Object[]> findItemDetailsForPurchaseQuotation(Long orgId, String purchaseEnquiryNo);

	@Query(nativeQuery=true,value ="SELECT DISTINCT a.docid FROM workorder a  WHERE a.orgid = ?1 AND a.customercode = ?2 AND a.active = 1 ORDER BY a.docid" )
	Set<Object[]> findWorkOrderNoForPurchaseQuotation(Long orgId, String customerCode);

	@Query(nativeQuery=true,value ="SELECT DISTINCT b.partno, b.partname, b.requiredqty, a.customerpono FROM workorder a JOIN itemparticulars b ON a.workorderid = b.workorderid WHERE a.orgid = ?1 AND a.docid = ?2 AND a.active = 1 ORDER BY b.partno" )
	Set<Object[]> findWorkOrderDetailsForPurchaseQuotation(Long orgId, String customerCode);


}
