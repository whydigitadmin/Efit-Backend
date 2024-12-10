package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractInvoiceVO;

@Repository
public interface SubContractInvoiceRepo extends JpaRepository<SubContractInvoiceVO, Long> {
	@Query(nativeQuery = true, value = "select * from  t_subcontractinvoice where orgid=?1")
	List<SubContractInvoiceVO> getAllSubContractInvoiceByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_subcontractinvoice where subcontractinvoiceid=?1")
	List<SubContractInvoiceVO> getSubContractInvoiceById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSubContractInvoiceDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.jobworkorderno,a.dcno,a.jobworkorderdate,a.dispatchedthrough,a.routecardno,a.contractorcode,a.contractorname,a.destination from t_jobworkout a where \r\n"
			+ "a.orgid=?1 and a.active=1 order by a.jobworkorderno")
	Set<Object[]> getJobWorkOutOrderNo(Long orgId);    

	@Query(nativeQuery = true, value = "select a.part,a.partdesc,a.process,a.quantitynos,a.rate,a.grossamt,a.cgst,a.sgst,a.amount,a1.totalgrossamt,a1.totaltax,a1.totalamt,a1.amtinwords from\r\n"
			+ "			   t_jobworkout a1,t_jobworkoutdetails a where a1.orgid=?1 and a1.jobworkorderno=?2 and\r\n"
			+ "               a.jobworkoutid=a1.jobworkoutid and a1.active=true  group by\r\n"
			+ "			   a.part,a.partdesc,a.process,a.quantitynos,a.rate,a.grossamt,a.cgst,a.sgst,a.amount,a1.totalgrossamt,a1.totaltax,a1.totalamt,a1.amtinwords order by a.part")
	Set<Object[]> getJobWorkOutOrderFromPartNoAndDesc(Long orgId, String docId);
}
