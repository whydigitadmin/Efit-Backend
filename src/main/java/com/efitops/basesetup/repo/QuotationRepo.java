package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QuotationVO;

@Repository
public interface QuotationRepo extends JpaRepository<QuotationVO, Long> {

	@Query(nativeQuery = true, value = "select * from t_quotation  where  orgid=?1")
	List<QuotationVO> getAllQuotationByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_quotation  where  quotationid=?1")
	List<QuotationVO> getQuotationById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getQuotationDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate,a.contactname,a.contactno from t_enquiry a where a.orgid=?1 and a.customer=?2 and active=true group by\r\n"
			+ "a.docid,a.docdate,a.contactname,a.contactno order by a.docid")
	Set<Object[]> getEnquiryNoAndDate(Long orgId, String customer);

	@Query(nativeQuery = true, value = "select a.employee from employee a where a.orgid=?1 and active=1 order by a.employee")
	Set<Object[]> getProductionManager(Long orgId);


}
