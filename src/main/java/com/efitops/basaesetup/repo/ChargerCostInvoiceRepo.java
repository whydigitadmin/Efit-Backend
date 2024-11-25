package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.ChargerCostInvoiceVO;
import com.efitops.basaesetup.entity.CostInvoiceVO;

public interface ChargerCostInvoiceRepo extends JpaRepository<ChargerCostInvoiceVO, Long> {

	List<ChargerCostInvoiceVO> findByCostInvoiceVO(CostInvoiceVO costInvoiceVO);

}
