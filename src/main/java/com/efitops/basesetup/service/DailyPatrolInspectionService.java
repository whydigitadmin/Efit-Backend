package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Repository;

import com.efitops.basesetup.dto.DailyPatrolInspectionDTO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Repository
public interface DailyPatrolInspectionService {

	Map<String, Object> updateCreateDailyPatrolInspection(@Valid DailyPatrolInspectionDTO dailyPatrolInspectionDTO) throws ApplicationException;

	String getDailyPatrolInspectionDocId(Long orgId);

	Optional<DailyPatrolInspectionVO> getDailyPatrolInspectionById(Long id);

	List<DailyPatrolInspectionVO> getAllDailyPatrolInspection(Long orgId);

	List<Map<String, Object>> getRouteCardNo(Long orgId);

	List<Map<String, Object>> getMachineDetail(Long orgId);

	List<Map<String, Object>> getShiftDetails();

	List<Map<String, Object>> getJobOrderNo();


}
