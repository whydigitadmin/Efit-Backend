package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractEnquiryVO;

@Repository
public interface SubContractEnquiryRepo extends JpaRepository<SubContractEnquiryVO, Long> {
	@Query(nativeQuery = true, value = "select * from  t_subcontractenquiry  where orgid=?1")
	List<SubContractEnquiryVO> getAllSubContractEnquiryByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_subcontractenquiry where subcontractenquiryid=?1")
	List<SubContractEnquiryVO> getSubContractEnquiryById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSubContractEnquiryDocId(Long orgId, String screenCode);

}
