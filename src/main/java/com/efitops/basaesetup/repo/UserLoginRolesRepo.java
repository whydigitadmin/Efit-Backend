package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.UserLoginRolesVO;
import com.efitops.basaesetup.entity.UserVO;


public interface UserLoginRolesRepo extends JpaRepository<UserLoginRolesVO, Long> {

	List<UserLoginRolesVO> findByUserVO(UserVO userVO);

}