package com.efitops.basaesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.ReceiptInvoiceVO;

@Repository
public interface ReceiptInvoiceRepo extends JpaRepository<ReceiptInvoiceVO, Long> {

}
