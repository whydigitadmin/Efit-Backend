package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DrawingMasterVO;
@Repository
public interface DrawingMasterRepo extends JpaRepository<DrawingMasterVO, Long>{

	@Query(nativeQuery =true,value ="select * from m_drawingMaster where orgid=?1")
	List<DrawingMasterVO> getDrawingMasterByOrgId(Long orgId);
	
	@Query(nativeQuery =true,value ="select * from m_drawingMaster where drawingMasterid=?1")
	Optional<DrawingMasterVO> getDrawingMasterById(Long id);

	@Query(nativeQuery = true, value = "SELECT itemname,itemdesc,primaryunit FROM efit_ops.m_item where orgid=?1 and itemtype IN ('FG', 'SFG') and cancel =0 and active = 1")
	Set<Object[]> findFGSFGPartDetailsForDrawingMaster(Long orgId);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getDrawingMasterDocId(Long orgId, String screenCode);

}