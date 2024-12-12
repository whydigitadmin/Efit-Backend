package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;

@Repository
public interface IncomingMaterialInspectionRepo extends JpaRepository<IncomingMaterialInspectionVO, Long> {

	@Query(nativeQuery = true, value = "select * from  t_incomingmaterialinspection where orgid=?1")
	List<IncomingMaterialInspectionVO> getAllIncomingMaterialInspectionByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_incomingmaterialinspection  where incomingmaterialinspectionid=?1")
	IncomingMaterialInspectionVO getIncomingMaterialInspectionById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getIncomingMaterialInspectionDocId(Long orgId, String screenCode);

}
