package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DispatchPlanVO;

@Repository
public interface DispatchPlanRepo extends JpaRepository<DispatchPlanVO, Long>{

	@Query(nativeQuery = true, value = "select * from t_dispatchplan where orgid=?1")
	List<DispatchPlanVO> getDispatchPlanByOrgId(Long orgId);
	 

	@Query(nativeQuery = true, value = "select * from t_dispatchplan where dispatchplanid=?1")
	DispatchPlanVO getDispatchPlanById(Long id);


	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getDispatchPlanByDocId(Long orgId, String screenCode);

}
