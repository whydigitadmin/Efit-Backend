package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyChargesExemptionVO;
import com.efitops.basaesetup.entity.PartyMasterVO;

@Repository
public interface PartyChargesExemptionRepo extends JpaRepository<PartyChargesExemptionVO, Long>{

	List<PartyChargesExemptionVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
