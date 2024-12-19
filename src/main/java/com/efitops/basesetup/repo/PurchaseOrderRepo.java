package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrderVO, Long> {
	
	@Query(nativeQuery = true,value="select * from t_purchaseorder where orgid=?1")
	List<PurchaseOrderVO> findPurchaseOrderByOrgId(Long orgId);

	
	@Query(nativeQuery = true,value="select*from t_purchaseorder where purchaseorderid=?1")
	List<PurchaseOrderVO> getPurchaseOrderById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPurchaseDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPurchaseOrderDocId(Long orgId, String screenCode);

	@Query (nativeQuery = true, value ="SELECT contactperson,contact,CONCAT(\r\n"
			+ "        COALESCE(a.addressline1, ''), \r\n"
			+ "        COALESCE(a.addressline2, ''), \r\n"
			+ "        COALESCE(a.addressline3, ''), \r\n"
			+ "        COALESCE(a.city, ''), \r\n"
			+ "        COALESCE(a.pincode, ''), \r\n"
			+ "        COALESCE(a.state, '')\r\n"
			+ "    ) AS full_address,\r\n"
			+ "    a.stategstin,\r\n"
			+ "    a.taxtype,\r\n"
			+ "    a.state,\r\n"
			+ "    a.pincode,\r\n"
			+ "    a.city\r\n"
			+ "FROM   efit_ops.partyaddress a JOIN efit_ops.partymaster b  ON a.partymasterid = b.partymasterid   \r\n"
			+ "    WHERE b.cancel = 0  AND b.active = 1  and b.partytype = 'SUPPLIER' and b.partyname = ?2 and orgid=?1")
	Set<Object[]> findgetSupplierAddressForPurchaseOrder(Long orgId, String supplierName);


	@Query (nativeQuery = true, value ="select docid  from t_purchaseindent where 'Purchase Indent'=?4 and customercode =?2  and workorderno=?3 and orgid=?1 and active=1 and cancel=0")
	Set<Object[]> findgetPurchaseIndentForPurchaseOrder(Long orgId, String customerCode, String workorderno,String basedOn);


	@Query (nativeQuery = true, value ="select docid from t_purchasequotation where  'Quotation'=?4 and customercode = ?2 and workorderno=?3 and orgid=?1 and active=1 and cancel=0")
	Set<Object[]> findgetQuotationForPurchaseOrder(Long orgId, String customerCode, String workorderno, String basedOn);

	@Query (nativeQuery = true, value ="select a1.item,a1.itemdesc,a1.indentqty,a1.uom,e.taxslab,d.price from efit_ops.t_purchaseindent1 a1 \r\n"
			+ "join efit_ops.m_item c on a1.item =c.itemname\r\n"
			+ "join efit_ops.t_purchaseindent b on a1.purchaseindentid = b.purchaseindentid\r\n"
			+ "join efit_ops.m_itempriceslab d on c.itemid = d.itemid\r\n"
			+ "join efit_ops.m_itemtaxslab e on e.itemid = c.itemid\r\n"
			+ "where b.docid=?2 and b.orgid =?1 \r\n"
			+ "and taxeffectivefrom= (SELECT MAX(taxeffectivefrom) FROM efit_ops.m_itemtaxslab  WHERE itemid = e.itemid) \r\n"
			+ "and priceeffectivefrom =  (SELECT MAX(priceeffectivefrom) FROM efit_ops.m_itempriceslab WHERE itemid = d.itemid)\r\n"
			+ "union all \r\n"
			+ "select a1.item,a1.itemdesc,a1.qty,a1.unit,e.taxslab,a1.unitprice from efit_ops.t_purchasequotationdetails a1 \r\n"
			+ "join t_purchasequotation b on a1.purchasequotationid = b.purchasequotationid\r\n"
			+ "join efit_ops.m_item c on a1.item =c.itemname \r\n"
			+ "join efit_ops.m_itemtaxslab e on e.itemid = c.itemid\r\n"
			+ "where b.docid=?3 and b.orgid =?1 \r\n"
			+ "and taxeffectivefrom= (SELECT MAX(taxeffectivefrom) FROM efit_ops.m_itemtaxslab WHERE itemid = c.itemid)\r\n"
			+ "")
	Set<Object[]> findgetItemForPurchaseOrder(Long orgId, String purchaseIndentNo,String quotationNo);
	

}
