package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseQuotationAttachmentVO;

@Repository
public interface PurchaseQuotationAttachmentRepo extends JpaRepository<PurchaseQuotationAttachmentVO, Long>{

}






