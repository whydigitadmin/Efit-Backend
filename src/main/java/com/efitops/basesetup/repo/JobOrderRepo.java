package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobOrderVO;

@Repository
public interface JobOrderRepo extends JpaRepository<JobOrderVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM jobcard WHERE jobcardid=?1")
	List<JobOrderVO> getAllJobOrderById(Long id);

	@Query(nativeQuery = true, value = "SELECT * FROM jobcard WHERE orgid=?1")
	List<JobOrderVO> getAllJobOrderByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getJobOrderDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT A.shiftcode FROM m_shiftmast A WHERE A.orgid=?1")
	Set<Object[]> getShift(Long orgId);

	@Query(nativeQuery = true, value = "select B.operationdesc from t_routecardentry A, t_routecardentrydetails B WHERE A.routecardentryid=B.routecardentryid AND orgid=?1")
	Set<Object[]> getOperationName(Long orgId);

	@Query(nativeQuery = true, value = "select employee from employee where orgid=?1;")
	Set<Object[]> getOperatorName(Long orgId);

	@Query(nativeQuery = true, value = "SELECT timing FROM m_shiftmast A WHERE A.orgid=?1 AND A.shiftcode=?2")
	Set<Object[]> getTimings(Long orgId, String shiftCode);

}
