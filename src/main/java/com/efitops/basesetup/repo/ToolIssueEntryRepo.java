package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolIssueEntryVO;

@Repository
public interface ToolIssueEntryRepo extends JpaRepository<ToolIssueEntryVO, Long> {

	@Query(nativeQuery = true, value = "select*from toolissueentry where orgid=?1")
	List<ToolIssueEntryVO> findToolIssueEntryByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select*from toolissueentry where toolissueentryid=?1")
	List<ToolIssueEntryVO> getToolIssueEntryById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getGrnDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value ="select itemname,itemdesc,instrumentseqcode from efit_ops.item where upper(itemtype)='INSTRUMENT' and orgid=?1")
	Set<Object[]> findInstrumentforTollIssueForEntry(Long orgId);

	@Query(nativeQuery = true, value ="select count(*) as lastcount from efit_ops.toolissueentry where instrumentname='Pressure Gauge'")
	Set<Object[]> getlastcountforTollIssueForEntry(Long orgId);

	
	
}
