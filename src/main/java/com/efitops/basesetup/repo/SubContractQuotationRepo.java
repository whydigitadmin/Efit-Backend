package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractQuotationVO;
@Repository
public interface SubContractQuotationRepo extends JpaRepository<SubContractQuotationVO, Long> {

	@Query(nativeQuery = true,value="select * from  t_subcontractquotation where orgid=?1")
	List<SubContractQuotationVO> getAllSubContractQuotationByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from t_subcontractquotation  where subcontractquotationid=?1")
	List<SubContractQuotationVO> getSubContractQuotationById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSubContractQuotationDocId(Long orgId, String screenCode);
}
