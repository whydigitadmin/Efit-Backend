package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MaterialDetailVO;
import com.efitops.basesetup.entity.MaterialTypeVO;

@Repository
public interface MaterialDetailRepo extends JpaRepository<MaterialDetailVO, Long>{

	List<MaterialDetailVO> findByMaterialTypeVO(MaterialTypeVO materialTypeVO);
}