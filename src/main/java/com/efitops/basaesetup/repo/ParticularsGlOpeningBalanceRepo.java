package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.GlOpeningBalanceVO;
import com.efitops.basaesetup.entity.ParticularsGlOpeningBalanceVO;
@Repository
public interface ParticularsGlOpeningBalanceRepo extends JpaRepository<ParticularsGlOpeningBalanceVO,Long> {

	List<ParticularsGlOpeningBalanceVO> findByGlOpeningBalanceVO(GlOpeningBalanceVO glOpeningBalanceVO);

}
