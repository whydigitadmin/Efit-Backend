package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.WorkOrderVO;

@Repository
public interface WorkOrderRepo extends JpaRepository<WorkOrderVO, Long>{
	
	
	@Query(nativeQuery = true,value="select * from t_workorder  where  orgid=?1")
	List<WorkOrderVO> getAllWorkOrderByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from t_workorder  where  workorderid=?1")
	List<WorkOrderVO> getWorkOrderById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getWorkOrderDocId(Long orgId, String screenCode);


}
