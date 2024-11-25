package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.PartyMasterVO;
import com.efitops.basaesetup.entity.PartySalesPersonTaggingVO;

@Repository
public interface PartySalesPersonTaggingRepo extends JpaRepository<PartySalesPersonTaggingVO, Long> {

	List<PartySalesPersonTaggingVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
