package com.efitops.basesetup.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DrawingMaster1VO;
import com.efitops.basesetup.entity.DrawingMasterVO;

public interface DrawingMaster1Repo extends JpaRepository<DrawingMaster1VO, Long>{

	List<DrawingMaster1VO> findByDrawingMasterVO(DrawingMasterVO drawingMasterVO);

}
