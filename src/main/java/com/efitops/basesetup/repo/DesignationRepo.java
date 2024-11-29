package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DesignationVO;


@Repository
public interface DesignationRepo  extends JpaRepository<DesignationVO, Long>
{

	@Query(nativeQuery = true, value = "select * from m_designation where orgid=?1")
	List<DesignationVO> getDesignationByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from m_designation where designationid=?1")
	List<DesignationVO> getDesignationById(Long id);
}