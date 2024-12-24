package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DailyPatrolInspectionDTO;
import com.efitops.basesetup.dto.DailyPatrolInspectionDetails1DTO;
import com.efitops.basesetup.dto.DailyPatrolInspectionFinalDTO;
import com.efitops.basesetup.entity.DailyPatrolInspectionDetails1VO;
import com.efitops.basesetup.entity.DailyPatrolInspectionFinalVO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DailyPatrolInspectionDetails1Repo;
import com.efitops.basesetup.repo.DailyPatrolInspectionFinalRepo;
import com.efitops.basesetup.repo.DailyPatrolInspectionRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.MachineMasterRepo;

@Service
public class DailyPatrolInspectionServiceImpl implements DailyPatrolInspectionService {

	@Autowired
	DailyPatrolInspectionRepo dailyPatrolInspectionRepo;

	@Autowired
	DailyPatrolInspectionDetails1Repo dailyPatrolInspectionDetails1Repo;

	@Autowired
	DailyPatrolInspectionFinalRepo dailyPatrolInspectionFinalRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	MachineMasterRepo machineMasterRepo;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DailyPatrolInspectionServiceImpl.class);

	@Override
	public Map<String, Object> updateCreateDailyPatrolInspection(
			@Valid DailyPatrolInspectionDTO dailyPatrolInspectionDTO) throws ApplicationException {

		DailyPatrolInspectionVO dailyPatrolInspectionVO = null;
        String screenCode="DPI";
		String message = null;

		if (ObjectUtils.isEmpty(dailyPatrolInspectionDTO.getId())) {

			dailyPatrolInspectionVO = new DailyPatrolInspectionVO();
			
			
			String docId = dailyPatrolInspectionRepo.getDailyPatrolInspectionDocId(dailyPatrolInspectionDTO.getOrgId(), screenCode);

			dailyPatrolInspectionVO.setDocId(docId);

//			        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(dailyPatrolInspectionDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			dailyPatrolInspectionVO.setCreatedBy(dailyPatrolInspectionDTO.getCreatedBy());
			dailyPatrolInspectionVO.setUpdatedBy(dailyPatrolInspectionDTO.getCreatedBy());

			message = "DailyPatrolInspection Creation Successfull";

		} else {

			dailyPatrolInspectionVO = dailyPatrolInspectionRepo.findById(dailyPatrolInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException(
							"DailyPatrolInspection  Not Found with id: " + dailyPatrolInspectionDTO.getId()));
			dailyPatrolInspectionVO.setUpdatedBy(dailyPatrolInspectionDTO.getCreatedBy());

			message = "DailyPatrolInspection Updation Successfull";

		}

		dailyPatrolInspectionVO = getDailyPatrolInspectionVOFromDailyPatrolInspectionVO(dailyPatrolInspectionVO,
				dailyPatrolInspectionDTO);
		dailyPatrolInspectionRepo.save(dailyPatrolInspectionVO);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO);
		return response;

	}

	private DailyPatrolInspectionVO getDailyPatrolInspectionVOFromDailyPatrolInspectionVO(
			DailyPatrolInspectionVO dailyPatrolInspectionVO, @Valid DailyPatrolInspectionDTO dailyPatrolInspectionDTO) {

		dailyPatrolInspectionVO.setRouteCardNo(dailyPatrolInspectionDTO.getRouteCardNo());
		dailyPatrolInspectionVO.setPartNo(dailyPatrolInspectionDTO.getPartNo());
		dailyPatrolInspectionVO.setPartName(dailyPatrolInspectionDTO.getPartName());
		dailyPatrolInspectionVO.setDrgNo(dailyPatrolInspectionDTO.getDrgNo());
		dailyPatrolInspectionVO.setShift(dailyPatrolInspectionDTO.getShift());
		dailyPatrolInspectionVO.setMachineNo(dailyPatrolInspectionDTO.getMachineNo());
		dailyPatrolInspectionVO.setMachineName(dailyPatrolInspectionDTO.getMachineName());
		dailyPatrolInspectionVO.setTime(dailyPatrolInspectionDTO.getTime());
		dailyPatrolInspectionVO.setJobOrderNo(dailyPatrolInspectionDTO.getJobOrderNo());
		dailyPatrolInspectionVO.setDocumentFormatNo(dailyPatrolInspectionDTO.getDocumentFormatNo());
		dailyPatrolInspectionVO.setNarration(dailyPatrolInspectionDTO.getNarration());
		dailyPatrolInspectionVO.setCreatedBy(dailyPatrolInspectionDTO.getCreatedBy());
		dailyPatrolInspectionVO.setActive(dailyPatrolInspectionDTO.isActive());
		dailyPatrolInspectionVO.setOrgId(dailyPatrolInspectionDTO.getOrgId());
		
		if (dailyPatrolInspectionDTO.getId() != null) {

			List<DailyPatrolInspectionDetails1VO> dailyPatrolInspectionDetails1VOs = dailyPatrolInspectionDetails1Repo
					.findByDailyPatrolInspectionVO(dailyPatrolInspectionVO);
			dailyPatrolInspectionDetails1Repo.deleteAll(dailyPatrolInspectionDetails1VOs);

			List<DailyPatrolInspectionFinalVO> dailyPatrolInspectionFinalVOs = dailyPatrolInspectionFinalRepo
					.findByDailyPatrolInspectionVO(dailyPatrolInspectionVO);
			dailyPatrolInspectionFinalRepo.deleteAll(dailyPatrolInspectionFinalVOs);

		}

		List<DailyPatrolInspectionDetails1VO> dailyPatrolInspectionDetails1VOs = new ArrayList<DailyPatrolInspectionDetails1VO>();
		for (DailyPatrolInspectionDetails1DTO dailyPatrolInspectionDetails1DTO : dailyPatrolInspectionDTO
				.getDailyPatrolInspectionDetails1DTO()) {

			DailyPatrolInspectionDetails1VO dailyPatrolInspectionDetails1VO = new DailyPatrolInspectionDetails1VO();

			dailyPatrolInspectionDetails1VO.setCharacteristic(dailyPatrolInspectionDetails1DTO.getCharacteristic());
			dailyPatrolInspectionDetails1VO
					.setMethodOfInspection(dailyPatrolInspectionDetails1DTO.getMethodOfInspection());
			dailyPatrolInspectionDetails1VO.setLsl(dailyPatrolInspectionDetails1DTO.getLsl());
			dailyPatrolInspectionDetails1VO.setUsl(dailyPatrolInspectionDetails1DTO.getUsl());
			dailyPatrolInspectionDetails1VO.setSample1(dailyPatrolInspectionDetails1DTO.getSample1());
			dailyPatrolInspectionDetails1VO.setSample2(dailyPatrolInspectionDetails1DTO.getSample2());
			dailyPatrolInspectionDetails1VO.setSample3(dailyPatrolInspectionDetails1DTO.getSample3());
			dailyPatrolInspectionDetails1VO.setSample4(dailyPatrolInspectionDetails1DTO.getSample4());
			dailyPatrolInspectionDetails1VO.setSample5(dailyPatrolInspectionDetails1DTO.getSample5());
			dailyPatrolInspectionDetails1VO.setSample6(dailyPatrolInspectionDetails1DTO.getSample6());
			dailyPatrolInspectionDetails1VO.setSample7(dailyPatrolInspectionDetails1DTO.getSample7());
			dailyPatrolInspectionDetails1VO.setSample8(dailyPatrolInspectionDetails1DTO.getSample8());
			dailyPatrolInspectionDetails1VO.setSample9(dailyPatrolInspectionDetails1DTO.getSample9());
			dailyPatrolInspectionDetails1VO.setSample10(dailyPatrolInspectionDetails1DTO.getSample10());
			dailyPatrolInspectionDetails1VO.setStatus(dailyPatrolInspectionDetails1DTO.getStatus());
			dailyPatrolInspectionDetails1VO.setRemarks(dailyPatrolInspectionDetails1DTO.getRemarks());

			dailyPatrolInspectionDetails1VO.setDailyPatrolInspectionVO(dailyPatrolInspectionVO);
			dailyPatrolInspectionDetails1VOs.add(dailyPatrolInspectionDetails1VO);
		}

		dailyPatrolInspectionVO.setDailyPatrolInspectionDetails1VO(dailyPatrolInspectionDetails1VOs);

		// DailyPatrolInspectionFinalDTO

		List<DailyPatrolInspectionFinalVO> dailyPatrolInspectionFinalVOs = new ArrayList<DailyPatrolInspectionFinalVO>();
		for (DailyPatrolInspectionFinalDTO dailyPatrolInspectionFinaDTO : dailyPatrolInspectionDTO
				.getDailyPatrolInspectionFinalDTO()) {

			DailyPatrolInspectionFinalVO dailyPatrolInspectionFinalVO = new DailyPatrolInspectionFinalVO();

			dailyPatrolInspectionFinaDTO.setInCharge(dailyPatrolInspectionFinaDTO.getInCharge());
			dailyPatrolInspectionFinaDTO.setInspectionBy(dailyPatrolInspectionFinaDTO.getInspectionBy());
			dailyPatrolInspectionFinalVO.setRemarks(dailyPatrolInspectionFinaDTO.getRemarks());

			dailyPatrolInspectionFinalVO.setDailyPatrolInspectionVO(dailyPatrolInspectionVO);
			dailyPatrolInspectionFinalVOs.add(dailyPatrolInspectionFinalVO);
		}

		dailyPatrolInspectionVO.setDailyPatrolInspectionFinalVO(dailyPatrolInspectionFinalVOs);

		return dailyPatrolInspectionVO;
	}
	
	@Override
	public String getDailyPatrolInspectionDocId(Long orgId) {
		String ScreenCode = "DPI";
		String result = dailyPatrolInspectionRepo.getDailyPatrolInspectionDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public Optional<DailyPatrolInspectionVO> getDailyPatrolInspectionById(Long id) {
		return dailyPatrolInspectionRepo.getDailyPatrolInspectionById(id);
	}

	@Override
	public List<DailyPatrolInspectionVO> getAllDailyPatrolInspection(Long orgId) {
		
		return dailyPatrolInspectionRepo.getAllDPI(orgId);
	}

	@Override
	public List<Map<String, Object>> getRouteCardNo(Long orgId) {
	
		Set<Object[]> getRoute = dailyPatrolInspectionRepo.getRouteCardNo(orgId);
		return getRouteDetails(getRoute);
	}

	private List<Map<String, Object>> getRouteDetails(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[1] != null ? ch[1].toString() : "");
			map.put("partName", ch[1] != null ? ch[1].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getMachineDetail(Long orgId) {
		Set<Object[]> getRoute = machineMasterRepo.getMachineDetail1(orgId);
		return getMachine(getRoute);
	}

	private List<Map<String, Object>> getMachine(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("machineNo", ch[0] != null ? ch[0].toString() : "");
			map.put("machineName", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getShiftDetails() {
		Set<Object[]> getShift = machineMasterRepo.getShiftDetails1();
		return getShift1(getShift);
	}

	private List<Map<String, Object>> getShift1(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("shiftId", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("shiftName", ch[1] != null ? ch[1].toString() : "");
			map.put("timings", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getJobOrderNo() {
		Set<Object[]> getJobOrder = machineMasterRepo.getJobOrderNo1();
		return getJob(getJobOrder);
	}

	private List<Map<String, Object>> getJob(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("shiftId", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("shiftName", ch[1] != null ? ch[1].toString() : "");
			map.put("timings", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;

	}
	
}
