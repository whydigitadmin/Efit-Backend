package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.PaymentVoucherVO;

public interface PaymentVoucherRepo extends JpaRepository<PaymentVoucherVO, Long> {

	@Query(nativeQuery = true, value = "select * from paymentvoucher where orgid=?1")
	List<PaymentVoucherVO> getAllPaymentVoucherByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from paymentvoucher where paymentvoucherid=?1")
	List<PaymentVoucherVO> getPaymentVoucherById(Long id);

	@Query(nativeQuery = true, value = "select * from paymentvoucher where active=1")
	List<PaymentVoucherVO> findPaymentVoucherByActive();

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	int findFinyr();

	
	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getpaymentVoucherDocId(Long orgId, String finyear, String branchCode, String screenCode);

	@Query(value = "select * from paymentVoucher a where a.orgid=?1 and docid=?2",nativeQuery =true)
	PaymentVoucherVO findAllPaymentVoucherByDocId(Long orgId, String docId);

	@Query(nativeQuery = true,value="select currency,currencydescripition,buyingexrate,sellingexrate from vw_exrates where orgid=?1")
	Set<Object[]> getCurrencyAndExrateDetails(Long orgId);
}
