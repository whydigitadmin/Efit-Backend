package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyMasterVO;
import com.efitops.basaesetup.entity.PartyTdsExemptedVO;

@Repository
public interface PartyTdsExemptedRepo extends JpaRepository<PartyTdsExemptedVO, Long> {

	List<PartyTdsExemptedVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
