package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesItemParticularsVO;

@Repository
public interface SalesItemParticularsRepo extends JpaRepository<SalesItemParticularsVO, Long>{

}