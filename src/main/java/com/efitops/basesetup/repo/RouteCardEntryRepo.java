package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RouteCardEntryVO;

@Repository
public interface RouteCardEntryRepo extends JpaRepository<RouteCardEntryVO, Long>{

	@Query(nativeQuery = true, value = "select * from t_routecardentry where orgid=?1")
	List<RouteCardEntryVO> findRouteCardEntryByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_routecardentry where routecardentryid=?1")
	List<RouteCardEntryVO> findRouteCardEntryById(Long id);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getRouteCardEntryDocId(Long orgId, String screenCode);

    //Dropdown api
	@Query(nativeQuery = true,value = "select  a.partyname,a.partycode from partymaster a where  a.partytype='CUSTOMER' and  a.orgid=?1 and active=1 and cancel=0 group by \r\n"
			+ " a.partyname,a.partycode order by  a.partyname")
	Set<Object[]> findCustomerNameAndCodeFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true,value = "select  a.docid from t_workorder a where a.orgid=?1 and customer=?2 and active=1 and cancel=0 group by \r\n"
			+ " a.docid order by  a.docid")
	Set<Object[]> findWorkOrderNoFromRouteCardEntry(Long orgId, String customer);

	@Query(nativeQuery = true,value = "select  b.partno,b.partname,b.requiredqty from t_workorder a JOIN t_itemparticulars b ON a.workorderid = b.workorderid where a.orgid=?1 and a.docid=?2 and active=1 and cancel=0 group by \r\n"
			+ " b.partno,b.partname,b.requiredqty order by   b.partno")
	Set<Object[]> findFgPartNameAndDescAndQtyFromRouteCardEntry(Long orgId, String workOrderNo);

	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='MACHINE OPERATOR' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> findOptrSignFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='Team Leader' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> findPreparedByFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='PRODUCTION MANAGER' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> findApprovedByFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='QUALITY MANAGER' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> findQAManagerSignFromRouteCardEntry(Long orgId);
	
	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='PLANT MANAGER' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> findPlantManagerSignFromRouteCardEntry(Long orgId);

}
