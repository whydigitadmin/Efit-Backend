package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SettingApprovalVO;

@Repository
public interface SettingApprovalRepo extends JpaRepository<SettingApprovalVO, Long>{

	@Query(nativeQuery = true, value = "select * from  settingapproval where orgid=?1")
	List<SettingApprovalVO> getAllSettingApprovalByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from  settingapproval where settingapprovalid=?1")
	SettingApprovalVO getSettingApprovalById(Long id);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSettingApprovalDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.fgpartname,a.fgpartdesc from routecardentry a where a.orgid=?1 and a.status='PENDING' and active=1 order by 1")
	Set<Object[]> findRouteCardDetailsForSetingApproval(Long orgId);

	@Query(nativeQuery = true, value = "select a.drawingno from drawingmaster a where a.orgid=?1 and a.fgpartno=?2 and active=1 order by 1")
	Set<Object[]> findDrawingNoForSetingApproval(Long orgId, String partNo);
	
	@Query(nativeQuery = true, value = "select a.machineno,a.machinename from machinemaster a where a.orgid=?1  and active=1 order by 1")
	Set<Object[]> findMachineNoForSetingApproval(Long orgId);

	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='OPERATOR' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> findOperatorNameForSetingApproval(Long orgId);

	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='OPERATOR' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> findSetterNameForSetingApproval(Long orgId);

	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='SHIFTINCHARGE' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> findQualityNameForSetingApproval(Long orgId);

	@Query(nativeQuery = true,value = "select  a.employee from employee a where a.orgid=?1 and designation='QUALITY' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> findShiftInChargeForSetingApproval(Long orgId);

	

}
