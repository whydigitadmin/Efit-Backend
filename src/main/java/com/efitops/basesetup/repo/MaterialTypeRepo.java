package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MaterialTypeVO;
@Repository
public interface MaterialTypeRepo extends JpaRepository<MaterialTypeVO, Long> {

	boolean existsByItemGroupAndOrgId(String itemGroup, Long orgId);

	@Query(nativeQuery = true,value = "select * from  m_material where orgid=?1")
	List<MaterialTypeVO> getAllMaterialTypeByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value = "select * from  m_material where materialid=?1")
	List<MaterialTypeVO> getMaterialTypeById(Long id);
}