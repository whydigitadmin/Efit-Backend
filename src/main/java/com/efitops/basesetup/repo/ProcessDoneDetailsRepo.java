package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProcessDoneDetailsVO;

public interface ProcessDoneDetailsRepo extends JpaRepository<ProcessDoneDetailsVO, Long> {

}
