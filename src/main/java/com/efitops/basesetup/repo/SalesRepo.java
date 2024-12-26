package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesVO;

@Repository
public interface SalesRepo extends JpaRepository<SalesVO, Long>{

}
