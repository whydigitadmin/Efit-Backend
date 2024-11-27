package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.UomVO;

@Repository
public interface UomRepo extends JpaRepository<UomVO, Long>{

	@Query(nativeQuery = true, value = "select * from designation where orgid=?1")
	List<UomVO> getUomByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from designation where uommastid=?1")
	List<UomVO> getUomById(Long id);

	//boolean existsByUomCodeAndOrgId(String uomCode, Long orgId);


	boolean existsByUomCodeAndOrgId(String uomCode, Long orgId);
}
