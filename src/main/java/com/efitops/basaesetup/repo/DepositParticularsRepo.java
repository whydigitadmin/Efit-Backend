package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.BankingDepositVO;
import com.efitops.basaesetup.entity.DepositParticularsVO;

@Repository
public interface DepositParticularsRepo extends JpaRepository<DepositParticularsVO, Long> {

	List<DepositParticularsVO> findByBankingDepositVO(BankingDepositVO bankingDepositVO);

}
