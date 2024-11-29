package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PutawayVO;

@Repository
public interface PutawayRepo extends JpaRepository<PutawayVO, Long>{

}
