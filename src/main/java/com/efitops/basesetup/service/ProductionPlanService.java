package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ProductionPlanDTO;
import com.efitops.basesetup.entity.ProductionPlanVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface ProductionPlanService {

	List<ProductionPlanVO> getAllProductionPlanByOrgId(Long orgId);

	List<ProductionPlanVO> getProductionPlanById(Long productionPlanId);

	Map<String, Object> createUpdateProductionPlan(ProductionPlanDTO productionPlanDTO) throws ApplicationException;

	String getProductionPlanDocId(Long orgId, String finYear, String branchCode, String screenCode);
}
