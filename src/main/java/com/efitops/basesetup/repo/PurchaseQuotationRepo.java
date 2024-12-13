package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseQuotationVO;

@Repository
public interface PurchaseQuotationRepo extends JpaRepository<PurchaseQuotationVO, Long>{

	@Query(nativeQuery = true, value = "SELECT * FROM t_purchasequotation where orgid=?1")
	List<PurchaseQuotationVO> getAllPurchaseQuotationByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM t_purchasequotationdetails where purchasequotationid=?1")
	Optional<PurchaseQuotationVO> getAllPurchaseQuotationById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getPurchaseQuotationByDocId(Long orgId, String screenCode);


}
