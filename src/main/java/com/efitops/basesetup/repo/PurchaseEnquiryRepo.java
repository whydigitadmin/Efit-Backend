package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseEnquiryVO;

@Repository
public interface PurchaseEnquiryRepo extends JpaRepository<PurchaseEnquiryVO, Long>
{

	@Query(nativeQuery =true,value = "select * from t_purchaseenquiry where orgid=?1")
	List<PurchaseEnquiryVO> getPurchaseEnquiry(Long orgId);
	
	@Query(nativeQuery =true,value = "select * from t_purchaseenquiry where purchaseenquiryid=?1")
	Optional<PurchaseEnquiryVO> getPurchaseEnquiryById(Long id);
	
	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getPurchaseEnquiryByDocId(Long orgId, String screenCode);

	@Query(nativeQuery =true,value ="SELECT docid FROM t_purchaseenquiry where orgid=?1 and finyear=?2 and screencode=?3")
	String getPurchaseEnquiryDocId(Long orgId, String finYear, String screenCode);

}
