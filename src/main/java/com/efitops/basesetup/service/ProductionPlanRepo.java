package com.efitops.basesetup.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ProductionPlanVO;

public interface ProductionPlanRepo extends JpaRepository<ProductionPlanVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM productionplan WHERE id=?1")
	List<ProductionPlanVO> getAllProductionPlanByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM productionplan WHERE productionplanid=?1")
	List<ProductionPlanVO> getAllProductionPlanById(Long id);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getProductionPlanDocId(Long orgId, String finYear, String branchCode, String screenCode);

}
