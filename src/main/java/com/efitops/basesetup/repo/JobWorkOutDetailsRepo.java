package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobWorkOutDetailsVO;

@Repository
public interface JobWorkOutDetailsRepo extends JpaRepository<JobWorkOutDetailsVO, Long> {

}
