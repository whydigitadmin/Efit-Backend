package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseIndentVO1;
@Repository
public interface PurchaseIndentRepo1 extends JpaRepository<PurchaseIndentVO1, Long>{

	List<PurchaseIndentVO1> findByPurchaseIndentVO(PurchaseIndentVO purchaseIndentVO);

}
