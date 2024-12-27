package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SampleApprovalVO;

@Repository
public interface SampleApprovalRepo extends JpaRepository<SampleApprovalVO, Long>{

	@Query(nativeQuery = true, value = "select * from  settingapproval where orgid=?1")
	List<SampleApprovalVO> getAllSampleApprovalByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from  settingapproval where sampleapprovalid=?1")
	SampleApprovalVO getSampleApprovalById(Long id);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSampleApprovalDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.fgpartname,a.fgpartdesc from routecardentry a where a.orgid=?1 and a.status='PENDING' and active=1 order by 1")
	Set<Object[]> findRouteCardDetailsForSampleApproval(Long orgId);

	@Query(nativeQuery = true, value = "select a.drawingno from drawingmaster a where a.orgid=?1 and a.fgpartno=?2 and active=1 order by 1")
	Set<Object[]> findDrawingMasterNoForSampleApproval(Long orgId, String partNo);

	@Query(nativeQuery = true, value = "select a.machineno,a.machinename from machinemaster a where a.orgid=?1  and active=1 order by 1")
	Set<Object[]> findMachineNoForSampleApproval(Long orgId);

	@Query(nativeQuery = true, value = "select a.docid from joborder a where a.orgid=?1 and a.routecardno=?2 and active=1 order by 1")
	Set<Object[]> findJobOrderNoForSampleApproval(Long orgId, String routeCardNo);


}
