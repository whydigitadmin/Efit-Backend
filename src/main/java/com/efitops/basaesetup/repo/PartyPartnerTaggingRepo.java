package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyMasterVO;
import com.efitops.basaesetup.entity.PartyPartnerTaggingVO;

@Repository
public interface PartyPartnerTaggingRepo extends JpaRepository<PartyPartnerTaggingVO, Long> {

	List<PartyPartnerTaggingVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
