package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EnquiryVO;
@Repository
public interface EnquiryRepo extends JpaRepository<EnquiryVO, Long> {

	@Query(nativeQuery = true,value="select * from  enquiry where orgid=?1")
	List<EnquiryVO> getAllEnquiryByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from enquiry where enquiryid=?1")
	List<EnquiryVO> getEnquiryById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getEnquiryDocId(Long orgId, String screenCode);

}
