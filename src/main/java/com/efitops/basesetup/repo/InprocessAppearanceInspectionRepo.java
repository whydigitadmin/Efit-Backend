package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InprocessAppearanceInspectionVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;

@Repository
public interface InprocessAppearanceInspectionRepo extends JpaRepository<InprocessAppearanceInspectionVO, Long> {

	List<InprocessAppearanceInspectionVO> findByInprocessInspectionVO(InprocessInspectionVO inprocessInspectionVO);

}
