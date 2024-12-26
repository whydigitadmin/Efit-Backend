package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GateInwardEntryVO;

@Repository
public interface GateInwardEntryRepo extends JpaRepository<GateInwardEntryVO, Long>{

	@Query(nativeQuery = true, value = "select * from gateinwardentry where orgid=?1")
	List<GateInwardEntryVO> findgetGateInwardEntryByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from gateinwardentry where gateinwardentryid=?1")
	List<GateInwardEntryVO> findgetGateInwardEntryById(Long id);


	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getGateInwardEntryDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select docid from purchaseorder where orgid=?1 and suppliercode=?2 and active =1 order by 1")
	Set<Object[]> findPurchaseOrderNoForGateInward(Long orgId, String purchaseIndentNo);

	@Query(nativeQuery = true, value = "select b.item,b.itemdesc,b.uom,b.qty from purchaseorder a join purchaseorderdetails b ON a.purchaseorderid = b.purchaseorderid where a.orgid=?1 and a.suppliercode=?2 and active =1 order by 1")
	Set<Object[]> findItemDetailsForGateInwardEntry(Long orgId, String purchaseOrderNo);


}
