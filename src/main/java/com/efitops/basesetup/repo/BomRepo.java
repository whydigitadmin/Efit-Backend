package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BomVO;

@Repository
public interface BomRepo extends JpaRepository<BomVO, Long>{
	
	@Query(nativeQuery = true,value="select * from  t_bom where orgid=?1")
	List<BomVO> getAllBomByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from t_bom where bomid=?1")
	List<BomVO> getBomById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getBomDocId(Long orgId, String screenCode);

}
