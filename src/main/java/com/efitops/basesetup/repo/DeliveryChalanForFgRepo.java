package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChalanForFgVO;

@Repository
public interface DeliveryChalanForFgRepo extends JpaRepository<DeliveryChalanForFgVO, Long> {

	@Query(nativeQuery = true, value = "select * from  deliverychalanforfg where orgid=?1")
	List<DeliveryChalanForFgVO> getAllDeliveryChalanForFgByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from deliverychalanforfg  where deliverychalanforfgid=?1")
	DeliveryChalanForFgVO getDeliveryChalanForFgById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getDeliveryChalanForFgDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.partyname,concat(a1.addressline1,',',a1.addressline2,',',a1.addressline3,a1.state,',',a1.pincode)as address\r\n"
			+ " from partymaster a,partyaddress a1 where a.partymasterid=a1.partymasterid and \r\n"
			+ " a.orgid=?1 and a.partytype='CUSTOMER' and a1.addresstype='BILLING' and a.active = 1 group by a.partyname,concat(a1.addressline1,',',a1.addressline2,',',a1.addressline3,a1.state,',',a1.pincode)\r\n"
			+ " order by a.partyname")
	Set<Object[]> getCustomerNameFromPartyMaster(Long orgId);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate,a1.duedate from saleorder a,saleorderdetails a1 where\r\n"
			+ " a.saleorderid=a1.saleorderid and a.orgid=?1 and a.customername=?2 and a.active = 1 group by\r\n"
			+ " a.docid,a.docdate,a1.duedate order by a.docid")
	Set<Object[]> getSoNoFromSaleOrder(Long orgId, String customerName);

	@Query(nativeQuery = true, value = " select  a1.itemname,a1.itemdesc,a2.qtyoffered,a1.primaryunit from\r\n"
			+ " a saleorder,saleorderdetails a2,item a1 where a1.itemname=a2.partno and a1.itemType='FG' and \r\n"
			+ " a.customername=?1 and a.active = 1 and a.saleorderid=a2.saleorderdetails  group by \r\n"
			+ " a1.itemname,a1.itemdesc,a2.qtyoffered,a1.primaryunit order by a1.itemname")
	Set<Object[]> getItemNameFromSaleOrder(String customerName);

}
