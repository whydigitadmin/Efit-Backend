package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolsIssueToCalibrationVO;

@Repository
public interface ToolsIssueToCalibrationRepo extends JpaRepository<ToolsIssueToCalibrationVO, Long> {

	@Query(nativeQuery = true, value = "select*from toolissuetocalibration where orgid=?1")
	List<ToolsIssueToCalibrationVO> findToolsIssueToCalibrationByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select*from toolissuetocalibration where toolissuetocalibrationid=?1")
	List<ToolsIssueToCalibrationVO> findToolsIssueToCalibrationById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getToolsIssueToCalibrationDocId(Long orgId, String screenCode);

}
