package com.efitops.basesetup.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProductionPlanVO;

public interface ProductionPlanRepo extends JpaRepository<ProductionPlanVO, Long> {

	List<ProductionPlanVO> getAllProductionPlanByOrgId(Long orgId);

	List<ProductionPlanVO> getAllProductionPlanById(Long id);

	String getProductionPlanDocId(Long orgId, String finYear, String branchCode, String screenCode);

}
