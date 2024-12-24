package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SampleApprovalDetailsVO;

@Repository
public interface SampleApprovalDetailsRepo extends JpaRepository<SampleApprovalDetailsVO, Long>{

}
