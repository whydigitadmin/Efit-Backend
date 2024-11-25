package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyAddressVO;
import com.efitops.basaesetup.entity.PartyMasterVO;

@Repository
public interface PartyAddressRepo extends JpaRepository<PartyAddressVO, Long>{

	List<PartyAddressVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
