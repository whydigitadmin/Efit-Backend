package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractEnquiryVO;

@Repository
public interface SubContractEnquiryRepo extends JpaRepository<SubContractEnquiryVO, Long> {
	@Query(nativeQuery = true, value = "select * from  t_subcontractenquiry  where orgid=?1")
	List<SubContractEnquiryVO> getAllSubContractEnquiryByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_subcontractenquiry where subcontractenquiryid=?1")
	List<SubContractEnquiryVO> getSubContractEnquiryById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSubContractEnquiryDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true,value = "select  a.partyname,a.partycode,a.gstin from partymaster a where  a.partytype='SUB-CONTRACTOR' and  a.orgid=?1 \r\n"
			+ "and a.active=1 group by \r\n"
			+ " a.partyname,a.partycode,a.gstin order by  a.partyname")
	Set<Object[]> getSubContractCustomerNameAndCode(Long orgId);	
	
	@Query(nativeQuery = true,value = "select a.contactperson,a.contactphoneno from partystate a,partymaster a1 where\r\n"
			+ " a.partymasterid=a1.partymasterid and a1.orgid=?1 and a1.partycode=?2 and a1.partytype='SUB-CONTRACTOR' and\r\n"
			+ " a1.active = 1 group by a.contactperson,a.contactphoneno order by a.contactperson")
	Set<Object[]> getSubContractContactNameAndNo(Long orgId,String partyCode);	
	
	@Query(nativeQuery = true,value = "  select a.item,a.itemdescription,a.process,a.quantity from\r\n"
			+ " t_issueitemdetails a,t_issuetosubcontractor a1  where a1.orgid=?1 and a.issuetosubcontractorid=a1.issuetosubcontractorid and \r\n"
			+ " a1.docid=?2 and a1.active  group by a.item,a.itemdescription,a.process,a.quantity order by a.item")
	Set<Object[]> getSubContractPartNoAndDescription(Long orgId,String scIssueNo);	
	
	@Query(nativeQuery = true,value = "select docid from t_routecardentry where orgid=?1 and active=true group by docid")
	Set<Object[]> getSubRouteCardNo(Long orgId);	
	
	@Query(nativeQuery = true,value = "select a.docid from t_issuetosubcontractor a where a.orgid=?1 and a.routecardno=?2 and  a.active=true group by docid")
	Set<Object[]> getScIssueNoFormSubContract(Long orgId,String routeCardNo);	
}
