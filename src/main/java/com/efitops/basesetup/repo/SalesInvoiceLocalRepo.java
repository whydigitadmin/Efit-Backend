package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesInvoiceLocalVO;

@Repository
public interface SalesInvoiceLocalRepo extends JpaRepository<SalesInvoiceLocalVO, Long> {

	@Query(nativeQuery = true, value = "select * from  salesinvoicelocal where orgid=?1")
	List<SalesInvoiceLocalVO> getAllSalesInvoiceLocalByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from salesinvoicelocal  where salesinvoicelocalid=?1")
	SalesInvoiceLocalVO getSalesInvoiceLocalById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSalesInvoiceLocalDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.partyname,a1.stategstin,a1.taxtype from partymaster a,partyaddress a1 where \r\n"
			+ "	a.orgid='' and a.partymasterid=a1.partyaddressid and upper(a1.addresstype='BILLING')  and \r\n"
			+ "	upper(a.suppliertype='LOCAL') and a.active = 1 group by\r\n"
			+ "	 a.partyname,a1.stategstin,a1.taxtype order by a.partyname")
	Set<Object[]> getCustomerNameFromPartyMaster(Long orgId);

}
