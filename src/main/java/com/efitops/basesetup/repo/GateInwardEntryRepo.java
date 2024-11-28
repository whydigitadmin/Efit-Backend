package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GateInwardEntryVO;

@Repository
public interface GateInwardEntryRepo extends JpaRepository<GateInwardEntryVO, Long>{

	@Query(nativeQuery = true, value = "select * from t_gateinwardentry where orgid=?1")
	List<GateInwardEntryVO> findItemByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_gateinwardentry where gateinwardentryid=?1")
	List<GateInwardEntryVO> findItemById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getGateInwardEntryByDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getGateInwardEntryDocId(Long orgId, String screenCode);

}
