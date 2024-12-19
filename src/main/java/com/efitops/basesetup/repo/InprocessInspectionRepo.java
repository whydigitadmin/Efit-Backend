package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InprocessInspectionVO;

@Repository
public interface InprocessInspectionRepo extends JpaRepository<InprocessInspectionVO, Long> {

	@Query(nativeQuery = true, value = "select * from  t_inprocessinspection where orgid=?1")
	List<InprocessInspectionVO> getAllInprocessInspectionByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_inprocessinspection  where inprocessinspectionid=?1")
	InprocessInspectionVO getInprocessInspectionById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getInprocessInspectionDocId(Long orgId, String screenCode);

}
