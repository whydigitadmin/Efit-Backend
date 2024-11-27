package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DrawingMasterVO;
@Repository
public interface DrawingMasterRepo extends JpaRepository<DrawingMasterVO, Long>{

	@Query(nativeQuery =true,value ="select * from drawingMaster where orgid=?1")
	List<DrawingMasterVO> getDrawingMasterByOrgId(Long orgId);
	
	@Query(nativeQuery =true,value ="select * from drawingMaster where drawingMasterid=?1")
	Optional<DrawingMasterVO> getDrawingMasterById(Long id);

}