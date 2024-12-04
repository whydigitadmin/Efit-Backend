package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobWorkOutVO;

@Repository
public interface JobWorkOutRepo extends JpaRepository<JobWorkOutRepo, Long>{

	static List<JobWorkOutVO> findJobWorkOutByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	List<JobWorkOutVO> getJobWorkOutById(Long id);

}
