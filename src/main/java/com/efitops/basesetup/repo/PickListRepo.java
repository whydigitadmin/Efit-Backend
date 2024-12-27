package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemIssueToProductionVO;
import com.efitops.basesetup.entity.PickListVO;

@Repository
public interface PickListRepo extends JpaRepository<PickListVO, Long>{

	@Query(nativeQuery = true, value = "select * from picklist where picklistid=?1")
	List<PickListVO> findPickListById(Long id);

	@Query(nativeQuery = true, value = "select * from picklist where orgid=?1")
	List<PickListVO> findPickListByOrgId(Long orgId);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPickListDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true,value = "select  b.item,b.itemdesc,b.unit  from itemisstoprod a join itemisstoproddtls b where a.itemisstoprodid=b.itemisstoprodid and a.orgid=?1 and a.docid=?2 and active=1 and cancel=0 group by  b.item,b.itemdesc,b.unit  order by  b.item;")
	Set<Object[]> findItemIssueToProductionDetailsfromPickList(Long orgId, String itemIssueToProduction);

	@Query(nativeQuery = true,value = "select  a.docid from itemisstoprod a where   a.orgid=?1 and a.routecardno=?2 and active=1 and cancel=0 group by  a.docid  order by  a.docid;")
	Set<Object[]> findItemIssueToProductionNofromPickList(Long orgId, String routeCardEntryNo);

	

}
