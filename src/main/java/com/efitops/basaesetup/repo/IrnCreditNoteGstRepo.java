package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.IrnCreditNoteGstVO;
import com.efitops.basaesetup.entity.IrnCreditNoteVO;
@Repository
public interface IrnCreditNoteGstRepo extends JpaRepository<IrnCreditNoteGstVO,Long>{


	List<IrnCreditNoteGstVO> findByIrnCreditNoteVO(IrnCreditNoteVO irnCreditNoteVO);

}
