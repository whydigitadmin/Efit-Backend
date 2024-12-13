package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseQuotation1VO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;

@Repository
public interface PurchaseQuotation1Repo extends JpaRepository<PurchaseQuotation1VO, Long>{

	List<PurchaseQuotation1VO> findByPurchaseQuotationVO(PurchaseQuotationVO purchaseQuotationVO);


}
