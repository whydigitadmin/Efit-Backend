package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemParticularsVO;
import com.efitops.basesetup.entity.WorkOrderVO;
@Repository
public interface ItemParticularsRepo extends JpaRepository<ItemParticularsVO, Long> {

	List<ItemParticularsVO> findByWorkOrderVO(WorkOrderVO workOrderVO);

}
