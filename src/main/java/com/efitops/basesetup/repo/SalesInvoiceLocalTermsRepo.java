package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SalesInvoiceLocalTermsVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalVO;

public interface SalesInvoiceLocalTermsRepo extends JpaRepository<SalesInvoiceLocalTermsVO, Long> {

	List<SalesInvoiceLocalTermsVO> findBySalesInvoiceLocalVO(SalesInvoiceLocalVO salesInvoiceLocalVO);

}
