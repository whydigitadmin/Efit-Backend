package com.efitops.basesetup.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PurchaseQuotationVO;

public interface PurchaseQuotationService extends JpaRepository<PurchaseQuotationVO, Long>{

}
