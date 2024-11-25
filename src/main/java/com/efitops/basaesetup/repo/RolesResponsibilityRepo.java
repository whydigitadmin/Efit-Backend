package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.RolesResponsibilityVO;
import com.efitops.basaesetup.entity.RolesVO;


public interface RolesResponsibilityRepo extends JpaRepository<RolesResponsibilityVO, Long>{

	List<RolesResponsibilityVO> findByRolesVO(RolesVO rolesVO);

}
