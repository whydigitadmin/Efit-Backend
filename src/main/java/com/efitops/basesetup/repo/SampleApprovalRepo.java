package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SampleApprovalVO;

@Repository
public interface SampleApprovalRepo extends JpaRepository<SampleApprovalVO, Long>{

}
