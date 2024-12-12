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

 
	
	
	@Query (nativeQuery = true, value ="select a.itemname,a.itemdesc,a.inwardqty,a.invoiceqty,a.poqty,uom,hsncode,inspection,needqcapproval,price,taxslab from efit_ops.t_gateinwardentrydetails a\r\n"
			+ "			join efit_ops.t_gateinwardentry b on a.gateinwardentryid = b.gateinwardentryid\r\n"
			+ "			join efit_ops.m_item c on a.itemname = c.itemname\r\n"
			+ "			join efit_ops.itempriceslab d on c.itemid = d.itemid\r\n"
			+ "            join efit_ops.m_itemtaxslab e on e.itemid = c.itemid\r\n"
			+ "            where b.docid=:?2 and b.orgid =?1\r\n"
			+ "            and taxeffectivefrom= (SELECT MAX(priceeffectivefrom) FROM efit_ops.itempriceslab WHERE itemid = d.itemid) \r\n"
			+ "            and priceeffectivefrom= (SELECT MAX(taxeffectivefrom) FROM efit_ops.m_itemtaxslab  WHERE itemid = e.itemid)")
	Set<Object[]> findItemForGRNDetails(Long orgId, String inwardNo);

	
	
	@Query(nativeQuery = true, value =("SELECT \r\n"
			+ "    CONCAT(\r\n"
			+ "        COALESCE(a.addressline1, ''), ', ', \r\n"
			+ "        COALESCE(a.addressline2, ''), ', ', \r\n"
			+ "        COALESCE(a.addressline3, ''), ', ', \r\n"
			+ "        COALESCE(a.city, ''), ', ', \r\n"
			+ "        COALESCE(a.pincode, ''), ', ', \r\n"
			+ "        COALESCE(a.state, '')\r\n"
			+ "    ) AS full_address,stategstin,taxtype,a.state,a.pincodea.city \r\n"
			+ "FROM  efit_ops.partyaddress JOIN efit_ops.partymaster b  ON a.partymasterid = b.partymasterid WHERE b.cancel = 0  AND b.active = 1  and partytype = 'SUPPLIER' AND a.orgid = ?1 \r\n"
			+ "    AND b.partyname = ?2"))
	Set<Object[]> findSupplierAddressDetails(Long orgId, String supplierName);

	
	
	@Query(nativeQuery = true, value =("SELECT sgstpercentage FROM efit_ops.m_gst where 'INTER' = ?3 and gstslab=?2 AND a.orgid = ?1"))
	Set<Object[]> findgetSGSTandCGSTForGRN(Long orgId, String taxType, String gstType);

	@Query(nativeQuery = true, value =("SELECT igstpercentage FROM efit_ops.m_gst where 'INTRA' =  ?3 and gstslab=?2 AND a.orgid = ?1"))
	Set<Object[]> findgetIGSTForGRN(Long orgId, String taxType, String gstType);

	

}
