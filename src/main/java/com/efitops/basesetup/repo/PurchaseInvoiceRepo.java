package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseInvoiceVO;

@Repository
public interface PurchaseInvoiceRepo extends JpaRepository<PurchaseInvoiceVO, Long> {

	@Query(nativeQuery = true, value = "select * from  t_purchaseinvoice where orgid=?1")
	List<PurchaseInvoiceVO> getAllPurchaseInvoiceByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_purchaseinvoice where purchaseinvoiceid=?1")
	PurchaseInvoiceVO getPurchaseInvoiceById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPurchaseInvoiceDocId(Long orgId, String screenCode);

}
