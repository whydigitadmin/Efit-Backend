package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GrnVO;
@Repository
public interface GrnRepo extends JpaRepository<GrnVO, Long> {

	@Query(nativeQuery = true,value="select*from t_grn where orgid=?1")
	List<GrnVO> findGrnByOrgId(Long orgId);

	@Query(nativeQuery = true,value="select*from t_grn where grnid=?1")
	List<GrnVO> getGrnById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getGrnDocId(Long orgId, String screenCode);

}
