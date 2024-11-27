package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ReceiptInvoiceVO;

@Repository
public interface ReceiptInvoiceRepo extends JpaRepository<ReceiptInvoiceVO, Long> {

}
