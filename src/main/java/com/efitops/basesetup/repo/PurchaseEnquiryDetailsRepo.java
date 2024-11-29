package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseEnquiryDetailsVO;

@Repository
public interface PurchaseEnquiryDetailsRepo extends JpaRepository<PurchaseEnquiryDetailsVO, Long> {

}
