package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseEnquiryVO;

@Repository
public interface PurchaseEnquiryRepo extends JpaRepository<PurchaseEnquiryVO, Long>
{

}
