package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.CostDebitChargesVO;
import com.efitops.basaesetup.entity.CostDebitNoteVO;
@Repository
public interface CostDebitChargesRepo extends JpaRepository<CostDebitChargesVO, Long>{

	List<CostDebitChargesVO> findByCostDebitNoteVO(CostDebitNoteVO costDebitNoteVO);

}
