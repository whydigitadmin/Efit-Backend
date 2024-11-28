package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QuotationVO;
@Repository
public interface QuotationRepo extends JpaRepository<QuotationVO, Long>{
	
	@Query(nativeQuery = true,value="select * from quotation  where  orgid=?1")
	List<QuotationVO> getAllQuotationByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from quotation  where  quotationid=?1")
	List<QuotationVO> getQuotationById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getQuotationDocId(Long orgId, String screenCode);



}
