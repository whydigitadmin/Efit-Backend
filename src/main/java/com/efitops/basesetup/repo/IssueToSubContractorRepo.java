package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IssueToSubContractorVO;

@Repository
public interface IssueToSubContractorRepo extends JpaRepository<IssueToSubContractorVO, Long> {

	@Query(nativeQuery = true, value = "select * from  t_issuetosubcontractor  where orgid=?1")
	List<IssueToSubContractorVO> getAllIssueToSubContractorByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_issuetosubcontractor where issuetosubcontractorid=?1")
	List<IssueToSubContractorVO> getIssueToSubContractorById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getIssueToSubContractorDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.customername,a.fgpartname,a.fgpartdesc,a.fgqty,a.wono from t_routecardentry a where a.orgid=?1  and a.status in('PENDING','IN-COMPLETE') group by \r\n"
			+ "			a.docid,a.customername,a.fgpartname,a.fgpartdesc,a.fgqty,a.wono\r\n"
			+ "			 order by  a.docid")
	Set<Object[]> getRouteCardNoAndItemNo(Long orgId);

	@Query(nativeQuery = true, value = "select departmentname from m_department where orgid=?1 and active=true group by departmentname")
	Set<Object[]> getDepartmentName(Long orgId);

	@Query(nativeQuery = true, value = "select a1.processname from m_itemwiseprocess a,m_itemwiseprocessdetails a1 where a.orgid=?1 and a.item=?2\r\n"
			+ "		 and a.processtype='SUB-CONTRACT' and \r\n"
			+ "		a.itemwiseprocessid=a1.itemwiseprocessid group by a1.processname order by a1.processname")
	Set<Object[]> getProcessNameFormItemWiseProcess(Long orgId, String item);

}
