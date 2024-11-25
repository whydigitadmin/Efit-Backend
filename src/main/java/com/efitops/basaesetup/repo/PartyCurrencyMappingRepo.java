package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyCurrencyMappingVO;
import com.efitops.basaesetup.entity.PartyMasterVO;

@Repository
public interface PartyCurrencyMappingRepo extends JpaRepository<PartyCurrencyMappingVO, Long>{

	List<PartyCurrencyMappingVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}