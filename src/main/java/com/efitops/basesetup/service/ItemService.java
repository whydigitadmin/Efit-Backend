package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ItemDTO;
import com.efitops.basesetup.dto.MeasuringInstrumentsDTO;
import com.efitops.basesetup.dto.ItemWiseProcessMasterDTO;
import com.efitops.basesetup.entity.ItemVO;
import com.efitops.basesetup.entity.MeasuringInstrumentsVO;
import com.efitops.basesetup.entity.ItemWiseProcessMasterVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface ItemService {

	List<ItemVO> getItemByOrgId(Long orgId);

	List<ItemVO> getItemById(Long id);

	Map<String, Object> updateCreateItemMaster(ItemDTO itemDTO) throws ApplicationException;

	//MeasuringInstruments
	
	List<MeasuringInstrumentsVO> getMeasuringInstrumentByOrgId(Long orgId);

	List<MeasuringInstrumentsVO> getMeasuringInstrumentById(Long id);

	Map<String, Object> updateCreateMeasuringInstruments(MeasuringInstrumentsDTO measuringInstrumentsDTO)throws ApplicationException;

	String getMeasuringInstrumentsDocId(Long orgId);

	List<Map<String, Object>> getInstrumentNameFromItemMaster(Long orgId);

	//ProcessMaster
	List<ItemWiseProcessMasterVO> getItemWiseProcessMasterByOrgId(Long orgId);

	List<ItemWiseProcessMasterVO> getItemWiseProcessMasterById(Long id);

	Map<String, Object> updateCreateItemWiseProcessMaster(ItemWiseProcessMasterDTO itemWiseProcessMasterDTO) throws ApplicationException;


	List<Map<String, Object>> getItemAndItemDescforItemWiseProcess(Long orgId);



}
