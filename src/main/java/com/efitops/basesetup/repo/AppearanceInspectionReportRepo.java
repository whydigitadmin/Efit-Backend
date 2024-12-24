package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.AppearanceInspectionReportVO;
import com.efitops.basesetup.entity.FinalInspectionReportVO;

@Repository
public interface AppearanceInspectionReportRepo extends JpaRepository<AppearanceInspectionReportVO, Long> {

	List<AppearanceInspectionReportVO> findByFinalInspectionReportVO(FinalInspectionReportVO finalInspectionReportVO);

}
