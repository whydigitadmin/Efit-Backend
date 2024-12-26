package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DetailsSubmissionToBankVO;

@Repository
public interface DetailsSubmissionToBankRepo extends JpaRepository<DetailsSubmissionToBankVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM detailssubmissiontobank WHERE orgid=?1")
	List<DetailsSubmissionToBankVO> getAllDetailsSubmissionToBankByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM detailssubmissiontobank WHERE detailssubmissiontobankid=?1")
	List<DetailsSubmissionToBankVO> getAllDetailsSubmissionToBankById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getBankDetailsDocId(Long orgId, String finYear, String branchCode, String screenCode);

}