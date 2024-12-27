package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.TdsMasterVO;

@Repository

public interface TdsMasterRepo extends JpaRepository<TdsMasterVO, Long> {

	@Query(value = "Select * from tdsmaster where orgid=?1", nativeQuery = true)
	List<TdsMasterVO> getAllTdsMasterByOrgId(Long orgId);

	@Query(value = "Select * from tdsmaster where tdsmasterid=?1", nativeQuery = true)
	List<TdsMasterVO> getAllTdsMasterById(Long id);

	@Query(nativeQuery = true, value = "select * from tdsmaster where active=1")
	List<TdsMasterVO> findTdsMasterByActive();

	boolean existsBySectionNameAndOrgId(String sectionName, Long orgId);

	boolean existsBySectionAndOrgId(String section, Long orgId);

	boolean existsBySectionAndOrgIdAndId(String section, Long orgId, Long id);

	boolean existsBySectionNameAndOrgIdAndId(String sectionName, Long orgId, Long id);

}
