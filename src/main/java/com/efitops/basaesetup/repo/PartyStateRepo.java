package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyMasterVO;
import com.efitops.basaesetup.entity.PartyStateVO;

@Repository
public interface PartyStateRepo extends JpaRepository<PartyStateVO, Long>{


	List<PartyStateVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
