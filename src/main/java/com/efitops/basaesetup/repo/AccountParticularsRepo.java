package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.AccountParticularsVO;
import com.efitops.basaesetup.entity.AdjustmentJournalVO;

@Repository
public interface AccountParticularsRepo extends JpaRepository<AccountParticularsVO, Long>{

	List<AccountParticularsVO> findByAdjustmentJournalVO(AdjustmentJournalVO adjustmentJournalVO);

	
}
