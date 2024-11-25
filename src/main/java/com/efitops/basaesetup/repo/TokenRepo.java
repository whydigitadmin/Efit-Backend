package com.efitops.basaesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.TokenVO;

public interface TokenRepo extends JpaRepository<TokenVO, String>{

}
