package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseIndentVO2;

@Repository
public interface PurchaseIndentRepo2 extends JpaRepository<PurchaseIndentVO2, Long>{

	List<PurchaseIndentVO2> findByPurchaseIndentVO(PurchaseIndentVO purchaseIndentVO);
	
}
