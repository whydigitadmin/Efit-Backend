package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseQuotationVO1;

@Repository
public interface PurchaseQuotation1Repo extends JpaRepository<PurchaseQuotationVO1, Long>{


}
