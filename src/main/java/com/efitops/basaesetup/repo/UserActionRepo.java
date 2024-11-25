package com.efitops.basaesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.UserActionVO;



@Repository
public interface UserActionRepo extends JpaRepository<UserActionVO, Long>{

}
