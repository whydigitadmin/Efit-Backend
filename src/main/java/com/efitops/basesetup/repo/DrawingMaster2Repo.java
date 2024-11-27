package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DrawingMaster2VO;
import com.efitops.basesetup.entity.DrawingMasterVO;

public interface DrawingMaster2Repo extends JpaRepository<DrawingMaster2VO, Long>{

	List<DrawingMaster2VO> findByDrawingMasterVO(DrawingMasterVO drawingMasterVO);

}