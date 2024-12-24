package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolIssueEntryVO;

@Repository
public interface ToolIssueEntryRepo extends JpaRepository<ToolIssueEntryVO, Long> {

	@Query(nativeQuery = true, value = "select*from t_toolissueentry where orgid=?1")
	List<ToolIssueEntryVO> findToolIssueEntryByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select*from t_toolissueentry where toolissueentryid=?1")
	List<ToolIssueEntryVO> getToolIssueEntryById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getGrnDocId(Long orgId, String screenCode);
}
