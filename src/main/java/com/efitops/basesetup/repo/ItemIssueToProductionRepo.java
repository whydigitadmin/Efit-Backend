package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemIssueToProductionVO;

@Repository
public interface ItemIssueToProductionRepo extends JpaRepository<ItemIssueToProductionVO, Long>{

	List<ItemIssueToProductionVO> findItemIssueToProductionById(Long id);

	List<ItemIssueToProductionVO> findItemIssueToProductionByOrgId(Long orgId);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getItemIssueToProductionDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true,value = "select  a.docid from routecardentry a where a.orgid=?1 and a.customercode=?2 upper(status)='PENDING' and active=1 and cancel=0 group by \r\n"
			+ " a.docid order by  a.docid")
	Set<Object[]> findRouteCardEntryNoForItemIssueToProduction(Long orgId, String customerCode);

	
	@Query(nativeQuery = true,value = "select  a.wono,a.fgpartname,a.fgpartdesc,a.fgqty from routecardentry a where a.orgid=?1 and a.docid=?2 and active=1 and cancel=0 group by a.wono,a.fgpartname,a.fgpartdesc,a.fgqty order by  a.wono")
	Set<Object[]> findRouteCardEntryDetailsForItemIssueToProduction(Long orgId, String routeCardNo);

	@Query(nativeQuery = true,value = "select  b.itemcode,b.itemdesc,b.uom,b.qty  from bom a join bomdetails b where a.bomid=b.bomid and a.orgid=?1 and a.productcode=?2 and active=1 and cancel=0 group by  b.itemcode,b.itemdesc,b.uom,b.qty  order by  b.itemcode")
	Set<Object[]> findItemIssueToProductionDetailsfromBom(Long orgId, String fgItemId);

}
