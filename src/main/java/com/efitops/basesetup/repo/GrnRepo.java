package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GrnVO;
@Repository
public interface GrnRepo extends JpaRepository<GrnVO, Long> {

	@Query(nativeQuery = true,value="select*from grn where orgid=?1")
	List<GrnVO> findGrnByOrgId(Long orgId);

	@Query(nativeQuery = true,value="select*from grn where grnid=?1")
	List<GrnVO> getGrnById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getGrnDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "SELECT docid,ponumber,suppliername,vehicleno,invoiceno,invoicedate,b.currency,b.gstin FROM efit_ops.gateinwardentry a1 \r\n"
			+ "join efit_ops.partymaster b on a1.suppliername = b.partyname\r\n"
			+ "where upper(partytype) ='SUPPLIER' and a1.orgid =?1")
	Set<Object[]> findInwardNoForGRNDetails(Long orgId);

 
	
	
@Query(nativeQuery = true, value="select a1.itemname,a1.itemdesc,a1.inwardqty,a1.invoiceqty,a1.poqty,uom,hsncode,inspection,needqcapproval,price,taxslab  \r\n"
		+ " from efit_ops.t_gateinwardentrydetails a1   \r\n"
		+ "				join efit_ops.t_gateinwardentry b on a1.gateinwardentryid = b.gateinwardentryid  \r\n"
		+ "					join efit_ops.item c on a1.itemname = c.itemname\r\n"
		+ "				join efit_ops.m_itempriceslab d on c.itemid = d.itemid\r\n"
		+ "			          join efit_ops.m_itemtaxslab e on e.itemid = c.itemid\r\n"
		+ "			          where b.docid=?2 and b.orgid =?1\r\n"
		+ "			         and taxeffectivefrom= (SELECT MAX(priceeffectivefrom) FROM efit_ops.itempriceslab WHERE itemid = d.itemid) \r\n"
		+ "			         and priceeffectivefrom= (SELECT MAX(taxeffectivefrom) FROM efit_ops.itemtaxslab  WHERE itemid = e.itemid)")
	Set<Object[]> findItemForGRNDetails(Long orgId, String inwardNo);

	
	
	@Query (nativeQuery = true, value ="SELECT CONCAT(\r\n"
			+ "        COALESCE(a.addressline1, ''), ', ',\r\n"
			+ "        COALESCE(a.addressline2, ''), ', ',\r\n"
			+ "        COALESCE(a.addressline3, ''), ', ',\r\n"
			+ "        COALESCE(a.city, ''), ', ',\r\n"
			+ "        COALESCE(a.pincode, ''), ', ',\r\n"
			+ "        COALESCE(a.state, '')\r\n"
			+ "    ) AS full_address,\r\n"
			+ "    a.stategstin,\r\n"
			+ "    a.taxtype,\r\n"
			+ "    a.state,\r\n"
			+ "    a.pincode,\r\n"
			+ "    a.city\r\n"
			+ "FROM  efit_ops.partyaddress a JOIN efit_ops.partymaster b  ON a.partymasterid = b.partymasterid\r\n"
			+ "    WHERE b.cancel = 0  AND b.active = 1  and partyname = ?2 and b.orgid =?1 and partytype = 'SUPPLIER'")
	Set<Object[]> findSupplierAddressDetails(Long orgId, String supplierName);

	
	
	@Query(nativeQuery = true, value =("SELECT sgstpercentage  FROM efit_ops.gst where 'INTRA' = ?3 and gstslab=?2 AND orgid = ?1"))
	Set<Object[]> findgetSGSTandCGSTForGRN(Long orgId, String gstType , String  taxType);

	@Query(nativeQuery = true, value =("SELECT igstpercentage  FROM efit_ops.gst where 'INTER' =  ?3 and gstslab=?2 AND orgid = ?1"))
	Set<Object[]> findgetIGSTForGRN(Long orgId,  String gstType , String  taxType);

	

}
