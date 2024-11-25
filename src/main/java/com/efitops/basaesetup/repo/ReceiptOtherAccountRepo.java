package com.efitops.basaesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.ReceiptOtherAccountVO;

@Repository
public interface ReceiptOtherAccountRepo extends JpaRepository<ReceiptOtherAccountVO, Long> {

}
