package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyMasterVO;
import com.efitops.basaesetup.entity.PartySpecialTDSVO;

@Repository
public interface PartySpecialTDSRepo extends JpaRepository<PartySpecialTDSVO, Long>{

	List<PartySpecialTDSVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
