package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyDetailsOfDirectorsVO;
import com.efitops.basaesetup.entity.PartyMasterVO;

@Repository
public interface PartyDetailsOfDirectorsRepo extends JpaRepository<PartyDetailsOfDirectorsVO, Long>{

	List<PartyDetailsOfDirectorsVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
