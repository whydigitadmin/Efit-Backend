package com.efitops.basaesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.ArApOffSetInvoiceDetailsVO;
@Repository
public interface ArApOffSetInvoiceDetailsRepo extends JpaRepository<ArApOffSetInvoiceDetailsVO, Long>{

}
