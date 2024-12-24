package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ProcessDoneVO;

public interface ProcessDoneRepo extends JpaRepository<ProcessDoneVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM processdone WHERE orgid=?1")
	List<ProcessDoneVO> getAllProcessDoneByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM processdone WHERE processdoneid=?1")
	List<ProcessDoneVO> getAllProcessDoneById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getProcessDoneDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT A.docid,A.fgpartname,A.fgpartdesc,A.fgqty FROM routecardentry A WHERE A.orgid=?1 AND A.customername=?2 GROUP BY A.docid,A.fgpartname,A.fgpartdesc,A.fgqty;")
	Set<Object[]> getRouteCardNo(Long orgId, String customerName);

	@Query(nativeQuery = true, value = "select A.docid from joborder where A.orgid=?1 A.routecardno=?2;")
	Set<Object[]> getJobCardNo(Long orgId, String routeCardNo);

	@Query(nativeQuery = true, value = "SELECT  A.departmentname FROM department A WHERE A.orgid =?1 ORDER BY A.departmentname;")
	Set<Object[]> getFrom(Long orgId);

	@Query(nativeQuery = true, value = "SELECT  A.departmentname  FROM department A WHERE A.orgid=?1 ORDER BY A.departmentname;")
	Set<Object[]> getTo(Long orgId);

}
