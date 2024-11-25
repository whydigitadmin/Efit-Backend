package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.CostDebitNoteGstVO;
import com.efitops.basaesetup.entity.CostDebitNoteVO;
@Repository
public interface CostDebitNoteGstRepo extends JpaRepository<CostDebitNoteGstVO, Long>{

	List<CostDebitNoteGstVO> findByCostDebitNoteVO(CostDebitNoteVO costDebitNoteVO);

}
