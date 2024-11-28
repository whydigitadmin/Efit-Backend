package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.TermsAndConditionsVO;
import com.efitops.basesetup.entity.WorkOrderVO;
@Repository
public interface TermsAndConditionsRepo extends JpaRepository<TermsAndConditionsVO, Long> {

	List<TermsAndConditionsVO> findByWorkOrderVO(WorkOrderVO workOrderVO);

}
