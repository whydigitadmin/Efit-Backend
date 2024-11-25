package com.efitops.basaesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.UserLoginBranchAccessibleVO;


public interface UserLoginBranchAccessibleRepo extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

}
