package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProcessDoneVO;

public interface ProcessDoneRepo extends JpaRepository<ProcessDoneVO, Long>{

}
