package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DispatchPlanVO;

@Repository
public interface DispatchPlanRepo extends JpaRepository<DispatchPlanVO, Long>{

	@Query(nativeQuery = true, value = "select * from dispatchplan where orgid=?1")
	List<DispatchPlanVO> getDispatchPlanByOrgId(Long orgId);
	 

	@Query(nativeQuery = true, value = "select * from dispatchplan where dispatchplanid=?1")
	DispatchPlanVO getDispatchPlanById(Long id);


	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getDispatchPlanByDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.customercode,a.customername,a.wono from routecardentry a where a.orgid=?1 and a.status='PENDING' and active=1 order by 1")
	Set<Object[]> findRouteCardDetailsForDispatchPlan(Long orgId);

	@Query(nativeQuery = true, value = "select b.partno,b.partname,b.uom,b.ordqty from workorder a join workorderdetails b ON a.workorderid=b.workorderid where a.orgid=?1 and a.docid=?2  and active=1 order by 1")
	Set<Object[]> findItemDetailsForDispatchPlan(Long orgId, String routeCardNo);

}
