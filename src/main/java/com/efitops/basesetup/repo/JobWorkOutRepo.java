package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobWorkOutVO;

@Repository
public interface JobWorkOutRepo extends JpaRepository<JobWorkOutVO, Long>{

	
}
