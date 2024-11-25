package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.CostInvoiceVO;
import com.efitops.basaesetup.entity.TdsCostInvoiceVO;

public interface TdsCostInvoiceRepo extends JpaRepository<TdsCostInvoiceVO,Long> {

	List<TdsCostInvoiceVO> findByCostInvoiceVO(CostInvoiceVO costInvoiceVO);

}
