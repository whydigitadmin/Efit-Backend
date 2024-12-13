package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrderVO, Long> {
	
	@Query(nativeQuery = true,value="select * from t_purchaseorder where orgid=?1")
	List<PurchaseOrderVO> findPurchaseOrderByOrgId(Long orgId);

	
	@Query(nativeQuery = true,value="select*from t_purchaseorder where purchaseorderid=?1")
	List<PurchaseOrderVO> getPurchaseOrderById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPurchaseDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPurchaseOrderDocId(Long orgId, String screenCode);
	

}
