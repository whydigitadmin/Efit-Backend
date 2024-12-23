package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FinalInspectionReportVO;

@Repository
public interface FinalInspectionReportRepo extends JpaRepository<FinalInspectionReportVO, Long> {

	@Query(nativeQuery = true, value = "select * from  t_finalinspectionreport where orgid=?1")
	List<FinalInspectionReportVO> getAllFinalInspectionReportByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_finalinspectionreport  where finalinspectionreportid=?1")
	FinalInspectionReportVO getFinalInspectionReportById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getFinalInspectionReportDocId(Long orgId, String screenCode);

}
