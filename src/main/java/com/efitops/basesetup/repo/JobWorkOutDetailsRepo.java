package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobWorkOutDetailsRepo extends JpaRepository<JobWorkOutDetailsRepo, Long> {

}
