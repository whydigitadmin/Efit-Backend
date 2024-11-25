package com.efitops.basaesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyMasterVO;
import com.efitops.basaesetup.entity.PartyVendorEvaluationVO;

@Repository
public interface PartyVendorEvaluationRepo extends JpaRepository<PartyVendorEvaluationVO, Long> {

	PartyVendorEvaluationVO findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
