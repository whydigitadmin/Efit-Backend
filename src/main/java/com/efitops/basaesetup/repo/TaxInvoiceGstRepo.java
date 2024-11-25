package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.TaxInvoiceGstVO;
import com.efitops.basaesetup.entity.TaxInvoiceVO;

public interface TaxInvoiceGstRepo extends JpaRepository<TaxInvoiceGstVO, Long>{

	List<TaxInvoiceGstVO> findByTaxInvoiceVO(TaxInvoiceVO taxVO);
	

}
