package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractInvoiceVO;
import com.efitops.basesetup.entity.SubContractTaxInvoiceDetailsVO;
@Repository
public interface SubContractTaxInvoiceDetailsRepo extends JpaRepository<SubContractTaxInvoiceDetailsVO, Long> {

	List<SubContractTaxInvoiceDetailsVO> findBySubContractInvoiceVO(SubContractInvoiceVO subContractInvoiceVO);

}
