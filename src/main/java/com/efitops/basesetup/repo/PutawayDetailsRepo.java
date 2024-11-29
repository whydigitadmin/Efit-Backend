package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PutawayDetailsVO;

@Repository
public interface PutawayDetailsRepo extends JpaRepository<PutawayDetailsVO, Long>{

}
