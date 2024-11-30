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

}
