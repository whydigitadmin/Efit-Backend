package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GrnVO;
@Repository
public interface GrnRepo extends JpaRepository<GrnVO, Long> {

	@Query(nativeQuery = true,value="select*from t_grn where orgid=?1")
	List<GrnVO> findGrnByOrgId(Long orgId);

	@Query(nativeQuery = true,value="select*from t_grn where grnid=?1")
	List<GrnVO> getGrnById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getGrnDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "SELECT docid,ponumber,suppliername,vehicleno,invoiceno,invoicedate,b.currency,b.gstin FROM efit_ops.t_gateinwardentry a\r\n"
			+ "join efit_ops.partymaster b on a.suppliername = b.partyname\r\n"
			+ "where upper(partytype) ='SUPPLIER' and a.orgid =?1")
	Set<Object[]> findInwardNoForGRNDetails(Long orgId);


	
	
	@Query(nativeQuery = true, value ="select a.itemname,a.itemdesc,a.inwardqty,a.invoiceqty,a.poqty,uom,hsncode,inspection,needqcapproval,price from efit_ops.t_gateinwardentrydetails a\r\n"
			+ "	join efit_ops.t_gateinwardentry b on a.gateinwardentryid = b.gateinwardentryid\r\n"
			+ "	join efit_ops.item c on a.itemname = c.itemname\r\n"
			+ "	join efit_ops.itempriceslab d on c.itemid = d.itemid\r\n"
			+ "	where b.docid=:?2 and b.orgid =?1 " )
	Set<Object[]> findItemForGRNDetails(Long orgId, String inwardNo);

}
