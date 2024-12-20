package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ProcessDoneVO;

public interface ProcessDoneRepo extends JpaRepository<ProcessDoneVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM processdone WHERE orgid=?1")
	List<ProcessDoneVO> getAllProcessDoneByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM processdone WHERE processdoneid=?1")
	List<ProcessDoneVO> getAllProcessDoneById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getProcessDoneDocId(Long orgId, String finYear, String branchCode, String screenCode);

}
