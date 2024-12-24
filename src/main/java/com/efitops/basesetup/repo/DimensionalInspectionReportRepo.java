package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DimensionalInspectionReportVO;
import com.efitops.basesetup.entity.FinalInspectionReportVO;

@Repository
public interface DimensionalInspectionReportRepo extends JpaRepository<DimensionalInspectionReportVO, Long> {

	List<DimensionalInspectionReportVO> findByFinalInspectionReportVO(FinalInspectionReportVO finalInspectionReportVO);

}
