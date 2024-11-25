package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.ReceiptInvDetailsVO;
import com.efitops.basaesetup.entity.ReceiptVO;

@Repository
public interface ReceiptInvDetailsRepo extends JpaRepository<ReceiptInvDetailsVO, Long> {

	List<ReceiptInvDetailsVO> findByReceiptVO(ReceiptVO receiptVO);

}
