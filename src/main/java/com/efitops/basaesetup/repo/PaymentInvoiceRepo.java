package com.efitops.basaesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.PaymentInvoiceVO;

public interface PaymentInvoiceRepo extends JpaRepository<PaymentInvoiceVO, Long> {

}
