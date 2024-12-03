package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IssueToSubContractorVO;
@Repository
public interface IssueToSubContractorRepo extends JpaRepository<IssueToSubContractorVO, Long> {

	@Query(nativeQuery = true,value="select * from  t_issuetosubcontractor  where orgid=?1")
	List<IssueToSubContractorVO> getAllIssueToSubContractorByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from t_issuetosubcontractor where issuetosubcontractorid=?1")
	List<IssueToSubContractorVO> getIssueToSubContractorById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getIssueToSubContractorDocId(Long orgId, String screenCode);
	
	
}
