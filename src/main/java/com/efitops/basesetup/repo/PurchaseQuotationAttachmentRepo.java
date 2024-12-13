package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseQuotationAttachmentVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;

@Repository
public interface PurchaseQuotationAttachmentRepo extends JpaRepository<PurchaseQuotationAttachmentVO, Long>{

	List<PurchaseQuotationAttachmentVO> findByPurchaseQuotationVO(PurchaseQuotationVO purchaseQuotationVO);

}






