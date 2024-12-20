package com.efitops.basesetup.repo;

import java.util.List;

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

}
