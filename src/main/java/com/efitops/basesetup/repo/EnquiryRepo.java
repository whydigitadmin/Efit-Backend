package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EnquiryVO;
@Repository
public interface EnquiryRepo extends JpaRepository<EnquiryVO, Long> {

	@Query(nativeQuery = true,value="select * from  t_enquiry where orgid=?1")
	List<EnquiryVO> getAllEnquiryByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from t_enquiry where enquiryid=?1")
	List<EnquiryVO> getEnquiryById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getEnquiryDocId(Long orgId, String screenCode);
	
	@Query(nativeQuery = true,value = "select  a.partyname,a.partycode,a.currency from partymaster a where  a.partytype='CUSTOMER' and  a.orgid=?1 and active=1 and cancel=0 group by \r\n"
			+ " a.partyname,a.partycode,a.currency order by  a.partyname")
	Set<Object[]> getCustomerNameAndCode(Long orgId);	
	
	@Query(nativeQuery = true,value = "select a.contactperson,a.contactphoneno from partystate a,partymaster a1 where a.partymasterid=a1.partymasterid and a1.orgid=?1 and a1.partyname=?2 and \r\n"
			+ " a1.partytype='CUSTOMER' and active = 1 and cancel = 0 group by a.contactperson,a.contactphoneno \r\n"
			+ " order by a.contactperson")
	Set<Object[]> getContactNameAndNo(Long orgId,String partyName);	
	
	@Query(nativeQuery = true,value = " select a.itemname,a.itemdesc,a.primaryunit from m_item a where a.orgid=?1 and active=1 and cancel=0 group by a.itemname,a.itemdesc,a.primaryunit  order by \r\n"
			+ " a.itemname")
	Set<Object[]> getPartNoAndDescription(Long orgId);	
	
	@Query(nativeQuery = true,value = "select a.drawingno,a.drawingrevno from m_drawingmaster a where a.partno=?1 and a.orgid=?2 and active=1 and  cancel=0  group by \r\n"
			+ "  a.drawingno,a.drawingrevno order by a.drawingno")
	Set<Object[]> getDrawingNoAndRevNo(Long orgId,String partNo);	
	

}
