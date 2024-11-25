package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.GeneralJournalVO;
import com.efitops.basaesetup.entity.ParticularsJournalVO;

public interface ParticularsJournalRepo extends JpaRepository<ParticularsJournalVO, Long>{

	List<ParticularsJournalVO> findByGeneralJournalVO(GeneralJournalVO generalJournalVO);


}
