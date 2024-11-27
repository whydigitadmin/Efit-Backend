package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;

@Repository
public interface DocumentTypeMappingDetailsRepo extends JpaRepository<DocumentTypeMappingDetailsVO, Long>{

	@Query(nativeQuery = true, value = "select * from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	DocumentTypeMappingDetailsVO findByOrgIdAndFinYearAndBranchCodeAndScreenCode(Long orgId, String finYear,
			String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select * from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	DocumentTypeMappingDetailsVO findByOrgIdAndScreenCode(Long orgId, String screenCode);



}
                          