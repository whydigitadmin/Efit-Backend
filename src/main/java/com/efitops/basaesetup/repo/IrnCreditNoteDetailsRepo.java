package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.IrnCreditNoteDetailsVO;
import com.efitops.basaesetup.entity.IrnCreditNoteVO;

@Repository
public interface IrnCreditNoteDetailsRepo extends JpaRepository<IrnCreditNoteDetailsVO, Long>{

	List<IrnCreditNoteDetailsVO> findByIrnCreditNoteVO(IrnCreditNoteVO irnCreditVO);


}
