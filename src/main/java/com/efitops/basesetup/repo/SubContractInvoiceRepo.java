package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractInvoiceVO;
@Repository
public interface SubContractInvoiceRepo extends JpaRepository<SubContractInvoiceVO, Long> {
	@Query(nativeQuery = true,value="select * from  t_subcontractinvoice where orgid=?1")
	List<SubContractInvoiceVO> getAllSubContractInvoiceByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from t_subcontractinvoice where subcontractinvoiceid=?1")
	List<SubContractInvoiceVO> getSubContractInvoiceById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSubContractInvoiceDocId(Long orgId, String screenCode);
	

}
