package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.ParticularsPaymentVoucherVO;
import com.efitops.basaesetup.entity.PaymentVoucherVO;

public interface ParticularsPaymentVoucherRepo extends JpaRepository<ParticularsPaymentVoucherVO, Long> {

	List<ParticularsPaymentVoucherVO> findByPaymentVoucherVO(PaymentVoucherVO paymentVoucherVO);

}
