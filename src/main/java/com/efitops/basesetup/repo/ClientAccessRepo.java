package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.UserLoginClientAccessVO;
import com.efitops.basesetup.entity.UserVO;

@Repository
public interface ClientAccessRepo extends JpaRepository<UserLoginClientAccessVO, Long>{

	List<UserLoginClientAccessVO> findByUserVO(UserVO userVO);

}
