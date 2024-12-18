package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionPlanDetailsVO;
import com.efitops.basesetup.entity.ProductionPlanVO;

@Repository
public interface ProductionPlanDetailsRepo extends JpaRepository<ProductionPlanDetailsVO, Long> {

	List<ProductionPlanDetailsVO> findByProductionPlanVO(ProductionPlanVO productionPlanVO);

}
