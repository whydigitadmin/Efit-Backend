package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemWiseProcessMasterVO;

@Repository
public interface ItemWiseProcessMasterRepo extends JpaRepository<ItemWiseProcessMasterVO, Long>{

	@Query(nativeQuery = true, value = "select * from m_itemwiseprocess where orgid=?1")
	List<ItemWiseProcessMasterVO> findItemWiseProcessMasterByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from m_itemwiseprocess where itemwiseprocessid=?1")
	List<ItemWiseProcessMasterVO> findItemWiseProcessMasterById(Long id);

	@Query(nativeQuery = true, value = "select itemname, itemdesc , ROW_NUMBER() OVER (ORDER BY itemname) AS id from m_item where orgid=?1 and itemtype NOT IN ('RAW MATERIAL') group by itemname ,itemdesc order by itemname")
	Set<Object[]> findItemAndItemDescforItemWiseProcess(Long orgId);

	@Query(nativeQuery = true, value = "select processname from m_processmaster where orgid=?1 ")
	Set<Object[]> findProcessNameFromItemWiseProcess(Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getItemWiseProcessMasterDocId(Long orgId, String screenCode);



}
