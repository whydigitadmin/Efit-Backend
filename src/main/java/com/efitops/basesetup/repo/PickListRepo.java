package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PickListVO;

@Repository
public interface PickListRepo extends JpaRepository<PickListVO, Long>{

	@Query(nativeQuery = true, value = "select * from t_picklist where picklistid=?1")
	List<PickListVO> findPickListById(Long id);

	@Query(nativeQuery = true, value = "select * from t_picklist where orgid=?1")
	List<PickListVO> findPickListByOrgId(Long orgId);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPickListDocId(Long orgId, String screenCode);

}
