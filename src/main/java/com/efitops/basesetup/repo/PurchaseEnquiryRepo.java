package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseEnquiryVO;

@Repository
public interface PurchaseEnquiryRepo extends JpaRepository<PurchaseEnquiryVO, Long>
{
 
	@Query(nativeQuery =true,value = "select * from t_purchaseenquiry where orgid=?1")
	List<PurchaseEnquiryVO> getPurchaseEnquiry(Long orgId);
	
	@Query(nativeQuery =true,value = "select * from t_purchaseenquiry where purchaseenquiryid=?1")
	Optional<PurchaseEnquiryVO> getPurchaseEnquiryById(Long id);
	
	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getPurchaseEnquiryByDocId(Long orgId, String screenCode);

	@Query(nativeQuery =true,value ="SELECT docid FROM t_purchaseenquiry where orgid=?1 and finyear=?2 and screencode=?3")
	String getPurchaseEnquiryDocId(Long orgId, String finYear, String screenCode);

	@Query(nativeQuery = true, value = "select distinct partyname,partycode from partymaster where partytype = 'SUPPLIER' and orgid=?1 and active=1 order by 1")
	Set<Object[]> findSupplierNameForPurchaseEnquiry(Long orgId);

	@Query(nativeQuery = true, value = "select distinct b.contactperson,b.contact,b.taxtype from partymaster a join partyaddress b ON a.partymasterid= b.partymasterid where  a.partytype = 'SUPPLIER' and a.orgid=?1 and a.partycode=?2  and active=1 order by 1")
	Set<Object[]> findContactPersonDetailsForPurchaseEnquiry(Long orgId, String supplierCode);

	
	@Query(nativeQuery=true,value ="SELECT a.docid FROM t_purchaseindent a  WHERE a.orgid = ?1 AND a.customercode = ?2 and workorderno=?3 AND a.active = 1 AND a.docid NOT IN (SELECT c.purchaseindentno FROM t_purchaseenquiry c WHERE c.orgid = ?1) ORDER BY a.docid" )
	Set<Object[]> findPurchaseIndentNoForPurchaseEnquiry(Long orgId, String customerCode, String workOrderNo);

	@Query(nativeQuery=true,value ="select distinct b.item,b.itemdesc,b.uom,b.reqqty from t_purchaseindent a join t_purchaseindent1 b \r\n"
			+ "ON a.purchaseindentid=b.purchaseindentid where  a.orgid=?1 and a.docid=?2  and active=1 \r\n"
			+ "UNION (select d.itemcode,d.itemdesc,d.uom,0 as qty  from m_bom c join m_bomdetails d where c.bomid=d.bomid and c.orgid=?1 and ?2='Null' and productcode=?3 )order by 1" )
	Set<Object[]> findItemDetailsForPurchaseEnquiry(Long orgId, String purchaseIndentNo, String fgItem);

	@Query(nativeQuery=true,value ="SELECT a.docid, b.partno, b.partname, b.requiredqty, a.customerpono FROM t_workorder a JOIN t_itemparticulars b ON a.workorderid = b.workorderid WHERE a.orgid = ?1 AND a.customercode = ?2 AND a.active = 1 AND a.docid ORDER BY a.docid" )
	Set<Object[]> findWorkOrderNoForPurchaseEnquiry(Long orgId, String customerCode);

}
