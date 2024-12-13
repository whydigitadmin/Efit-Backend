package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseIndentVO;

@Repository
public interface PurchaseIndentRepo extends JpaRepository<PurchaseIndentVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM t_purchaseindent where orgid=?1")
	List<PurchaseIndentVO> getAllPurchaseIndentByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM t_purchaseindent where purchaseindentid=?1")
	Optional<PurchaseIndentVO> getPurchaseIndentById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getPurchaseIndentByDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "SELECT docid FROM t_purchaseindent where orgid=?1 and screencode=?2")
	String getpurchaseIndentDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "SELECT materialtype FROM m_material where orgid=?1 and active=1")
	Set<Object[]> findIndentType(Long orgId);

	@Query(nativeQuery = true, value = "select distinct partyname,partycode from partymaster where partytype = 'customer' and orgid=?1 and active=1 order by 1")
	Set<Object[]> findCustomerDetails(Long orgId);
	
	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='MACHINE OPERATOR' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> getRequestedByDetails(Long orgId);
	
	@Query(nativeQuery=true,value ="select departmentname from department where orgid=?1 and  active=1 order by 1" )
	Set<Object[]> getDepartmentDetails(Long orgId);

	@Query(nativeQuery=true,value ="select b.itemcode,b.itemdesc,b.uom,b.qty from m_bom a join m_bomdetails b where a.bomid = b.bomid and a.orgid=?1 and a.productcode=?2 and active=1 order by 1" )
	Set<Object[]> findBomItemDetailsForPurchase(Long orgId, String fgPart);

	@Query(nativeQuery=true,value ="SELECT a.docid, b.partno, b.partname, b.requiredqty, a.customerpono FROM t_workorder a JOIN t_itemparticulars b ON a.workorderid = b.workorderid WHERE a.orgid = ?1 AND a.customercode = ?2 AND a.active = 1 AND a.docid NOT IN (SELECT c.workorderno FROM t_purchaseindent c WHERE c.orgid = ?1) ORDER BY a.docid" )
	Set<Object[]> findWorkOrderNoForPurchaseIndent(Long orgId, String customerCode);

	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='PRODUCTION MANAGER' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> getVerifiedByForPurchase(Long orgId);




}
