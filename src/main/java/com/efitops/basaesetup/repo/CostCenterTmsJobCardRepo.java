package com.efitops.basaesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.CostCenterTmsJobCardVO;
import com.efitops.basaesetup.entity.TmsJobCardVO;
@Repository
public interface CostCenterTmsJobCardRepo extends JpaRepository<CostCenterTmsJobCardVO, Long> {

	List<CostCenterTmsJobCardVO> findByTmsJobCardVO(TmsJobCardVO tmsJobCardVO);

}
