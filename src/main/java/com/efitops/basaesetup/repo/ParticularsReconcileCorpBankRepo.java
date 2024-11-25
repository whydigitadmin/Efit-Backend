package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.ParticularsReconcileCorpBankVO;
import com.efitops.basaesetup.entity.ReconcileCorpBankVO;
@Repository
public interface ParticularsReconcileCorpBankRepo extends JpaRepository<ParticularsReconcileCorpBankVO,Long>{

	List<ParticularsReconcileCorpBankVO> findByReconcileCorpBankVO(ReconcileCorpBankVO reconcileCorpBankVO);

}
