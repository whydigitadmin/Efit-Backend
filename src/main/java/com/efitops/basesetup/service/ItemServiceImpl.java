package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ItemDTO;
import com.efitops.basesetup.dto.ItemInventoryDTO;
import com.efitops.basesetup.dto.ItemPriceSlabDTO;
import com.efitops.basesetup.dto.ItemTaxSlabDTO;
import com.efitops.basesetup.dto.ItemWiseProcessDetailsDTO;
import com.efitops.basesetup.dto.ItemWiseProcessMasterDTO;
import com.efitops.basesetup.dto.MeasuringInstrumentsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ItemInventoryVO;
import com.efitops.basesetup.entity.ItemPriceSlabVO;
import com.efitops.basesetup.entity.ItemTaxSlabVO;
import com.efitops.basesetup.entity.ItemVO;
import com.efitops.basesetup.entity.ItemWiseProcessDetailsVO;
import com.efitops.basesetup.entity.ItemWiseProcessMasterVO;
import com.efitops.basesetup.entity.MeasuringInstrumentsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ItemInventoryRepo;
import com.efitops.basesetup.repo.ItemPriceSlabRepo;
import com.efitops.basesetup.repo.ItemRepo;
import com.efitops.basesetup.repo.ItemTaxSlabRepo;
import com.efitops.basesetup.repo.ItemWiseProcessDetailsRepo;
import com.efitops.basesetup.repo.ItemWiseProcessMasterRepo;
import com.efitops.basesetup.repo.MeasuringInstrumentsRepo;

@Service
public class ItemServiceImpl implements ItemService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	ItemRepo itemRepo;
	@Autowired
	ItemInventoryRepo itemInventoryRepo;
	@Autowired
	ItemPriceSlabRepo itemPriceSlabRepo;
	@Autowired
	ItemTaxSlabRepo itemTaxSlabRepo;

	@Autowired
	MeasuringInstrumentsRepo measuringInstrumentsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	ItemWiseProcessMasterRepo itemWiseProcessMasterRepo;
	
	@Autowired
	ItemWiseProcessDetailsRepo itemWiseProcessDetailsRepo;

	@Override
	public List<ItemVO> getItemByOrgId(Long orgId) {
		List<ItemVO> itemVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			itemVO = itemRepo.findItemByOrgId(orgId);
		}
		return itemVO;
	}

	@Override
	public List<ItemVO> getItemById(Long id) {
		List<ItemVO> itemVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Item BY Id : {}", id);
			itemVO = itemRepo.findItemById(id);
		}
		return itemVO;
	}

	@Override
	public Map<String, Object> updateCreateItemMaster(@Valid ItemDTO itemDTO) throws ApplicationException {
		String message;

		ItemVO itemVO = new ItemVO();

		if (itemDTO.getId() != null) {
			// Fetch existing ItemVO for update
			itemVO = itemRepo.findById(itemDTO.getId())
					.orElseThrow(() -> new ApplicationException("Item master not found"));
			itemVO.setUpdatedBy(itemDTO.getCreatedBy());
			createUpdateItemMasterVOByItemMasterDTO(itemDTO, itemVO);
			message = "Item Master Updated Successfully";
			
			
				List<ItemTaxSlabVO> itemTaxSlabVOs = itemTaxSlabRepo
						.findByItemVO(itemVO);
				itemTaxSlabRepo.deleteAll(itemTaxSlabVOs);
				
				List<ItemInventoryVO> itemInventoryVOs = itemInventoryRepo
						.findByItemVO(itemVO);
				itemInventoryRepo.deleteAll(itemInventoryVOs);
				
				List<ItemPriceSlabVO> itemPriceSlabVOs = itemPriceSlabRepo
						.findByItemVO(itemVO);
				itemPriceSlabRepo.deleteAll(itemPriceSlabVOs);
			
				
		} else {
			// Create new ItemVO
			itemVO.setCreatedBy(itemDTO.getCreatedBy());
			itemVO.setUpdatedBy(itemDTO.getCreatedBy());
			createUpdateItemMasterVOByItemMasterDTO(itemDTO, itemVO);
			message = "Item Master Created Successfully";
		}

		// Save the ItemVO
		itemRepo.save(itemVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("itemVO", itemVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateItemMasterVOByItemMasterDTO(@Valid ItemDTO itemDTO, ItemVO itemVO) {
		itemVO.setItemName(itemDTO.getItemName());
		itemVO.setMaterialGroup(itemDTO.getMaterialGroup());
		itemVO.setMaterialType(itemDTO.getMaterialType());
		itemVO.setItemType(itemDTO.getItemType());
		itemVO.setMaterialSubGroup(itemDTO.getMaterialSubGroup());
		itemVO.setItemDesc(itemDTO.getItemDesc());
		itemVO.setPrimaryUnit(itemDTO.getPrimaryUnit());
		itemVO.setHsnCode(itemDTO.getHsnCode());
		itemVO.setNeedqcapproval(itemDTO.getNeedQcApproval());
		itemVO.setInspection(itemDTO.getInspection());
		itemVO.setInstrumentSeqCode(itemDTO.getInstrumentSeqCode());
		itemVO.setOrgId(itemDTO.getOrgId());
		itemVO.setActive(itemDTO.isActive());


		// Handling ItemInventoryVO
		List<ItemInventoryVO> itemInventoryVOs = new ArrayList<>();
		for (ItemInventoryDTO itemInventoryDTO : itemDTO.getItemInventoryDTO()) {
			ItemInventoryVO itemInventoryVO = new ItemInventoryVO();
			itemInventoryVO.setImportLocal(itemInventoryDTO.getImportLocal());
			itemInventoryVO.setStockLocation(itemInventoryDTO.getStockLocation());
			itemInventoryVO.setMinOrderQuantity(itemInventoryDTO.getMinOrderQuantity());
			itemInventoryVO.setReOrderLevel(itemInventoryDTO.getReOrderLevel());
			itemInventoryVO.setItemVO(itemVO); // Set the reference in child entity
			itemInventoryVOs.add(itemInventoryVO);
		}
		itemVO.setItemInventoryVO(itemInventoryVOs);

		// Handling ItemPriceSlabVO
		List<ItemPriceSlabVO> itemPriceSlabVOs = new ArrayList<>();
		for (ItemPriceSlabDTO itemPriceSlabDTO : itemDTO.getItemPriceSlabDTO()) {
			ItemPriceSlabVO itemPriceSlabVO = new ItemPriceSlabVO();
			itemPriceSlabVO.setPrice(itemPriceSlabDTO.getPrice());
			itemPriceSlabVO.setPriceEffectiveFrom(itemPriceSlabDTO.getPriceEffectiveFrom());
			itemPriceSlabVO.setItemVO(itemVO); // Set the reference in child entity
			itemPriceSlabVOs.add(itemPriceSlabVO);
		}
		itemVO.setItemPriceSlabVO(itemPriceSlabVOs);


		// Handling ItemTaxSlabVO
		List<ItemTaxSlabVO> itemTaxSlabVOs = new ArrayList<>();
		for (ItemTaxSlabDTO itemTaxSlabDTO : itemDTO.getItemTaxSlabDTO()) {
			ItemTaxSlabVO itemTaxSlabVO = new ItemTaxSlabVO();
			itemTaxSlabVO.setTaxSlab(itemTaxSlabDTO.getTaxSlab());
			itemTaxSlabVO.setTaxEffectiveFrom(itemTaxSlabDTO.getTaxEffectiveFrom());
			itemTaxSlabVO.setItemVO(itemVO); // Set the reference in child entity
			itemTaxSlabVOs.add(itemTaxSlabVO);
		}
		itemVO.setItemTaxSlabVO(itemTaxSlabVOs);
	}

	// MeasuringInstrument

	@Override
	public List<MeasuringInstrumentsVO> getMeasuringInstrumentByOrgId(Long orgId) {
		List<MeasuringInstrumentsVO> measuringInstrumentsVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received MeasuringInstrument BY OrgId : {}", orgId);
			measuringInstrumentsVO = measuringInstrumentsRepo.findMeasuringInstrumentsByOrgId(orgId);
		}
		return measuringInstrumentsVO;
	}

	@Override
	public List<MeasuringInstrumentsVO> getMeasuringInstrumentById(Long id) {
		List<MeasuringInstrumentsVO> measuringInstrumentsVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received MeasuringInstrument BY Id : {}", id);
			measuringInstrumentsVO = measuringInstrumentsRepo.findMeasuringInstrumentsById(id);
		}
		return measuringInstrumentsVO;
	}

	@Override
	public Map<String, Object> updateCreateMeasuringInstruments(@Valid MeasuringInstrumentsDTO measuringInstrumentsDTO)
			throws ApplicationException {
		String message;
		MeasuringInstrumentsVO measuringInstrumentsVO = new MeasuringInstrumentsVO();
		String screenCode = "MI";

		if (measuringInstrumentsDTO.getId() != null) {
			// Fetch existing ItemVO for update
			measuringInstrumentsVO = measuringInstrumentsRepo.findById(measuringInstrumentsDTO.getId())
					.orElseThrow(() -> new ApplicationException("MeasuringInstrument not found"));

			if (!measuringInstrumentsVO.getInstrumentName()
					.equalsIgnoreCase(measuringInstrumentsDTO.getInstrumentName())) {
				if (measuringInstrumentsRepo.existsByInstrumentNameAndOrgId(measuringInstrumentsDTO.getInstrumentName(),
						measuringInstrumentsDTO.getOrgId())) {
					String errorMessage = String.format("The InstrumentName: %s  already exists This Organization.",
							measuringInstrumentsDTO.getInstrumentName());
					throw new ApplicationException(errorMessage);
				}
			}

			if (!measuringInstrumentsVO.getInstrumentCode()
					.equalsIgnoreCase(measuringInstrumentsDTO.getInstrumentCode())) {
				if (measuringInstrumentsRepo.existsByInstrumentCodeAndOrgId(measuringInstrumentsDTO.getInstrumentCode(),
						measuringInstrumentsDTO.getOrgId())) {
					String errorMessage = String.format("The InstrumentCode: %s  already exists This Organization.",
							measuringInstrumentsDTO.getInstrumentCode());
					throw new ApplicationException(errorMessage);
				}
			}

			measuringInstrumentsVO.setUpdatedBy(measuringInstrumentsDTO.getCreatedBy());
			createUpdateMeasuringInstrumentVOByMeasuringInstrumentDTO(measuringInstrumentsDTO, measuringInstrumentsVO);
			message = "MeasuringInstrument Updated Successfully";
		} else {

			// GETDOCID API
			String docId = measuringInstrumentsRepo.getMeasuringInstrumentByDocId(measuringInstrumentsDTO.getOrgId(),
					screenCode);

			measuringInstrumentsVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(measuringInstrumentsDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			if (measuringInstrumentsRepo.existsByInstrumentNameAndOrgId(measuringInstrumentsDTO.getInstrumentName(),
					measuringInstrumentsDTO.getOrgId())) {
				String errorMessage = String.format("The InstrumentName: %s  already exists This Organization.",
						measuringInstrumentsDTO.getInstrumentName());
				throw new ApplicationException(errorMessage);
			}

			if (measuringInstrumentsRepo.existsByInstrumentCodeAndOrgId(measuringInstrumentsDTO.getInstrumentCode(),
					measuringInstrumentsDTO.getOrgId())) {
				String errorMessage = String.format("The InstrumentCode: %s  already exists This Organization.",
						measuringInstrumentsDTO.getInstrumentCode());
				throw new ApplicationException(errorMessage);
			}
			// Create new ItemVO
			measuringInstrumentsVO.setCreatedBy(measuringInstrumentsDTO.getCreatedBy());
			measuringInstrumentsVO.setUpdatedBy(measuringInstrumentsDTO.getCreatedBy());
			createUpdateMeasuringInstrumentVOByMeasuringInstrumentDTO(measuringInstrumentsDTO, measuringInstrumentsVO);
			message = "MeasuringInstrument Created Successfully";
		}

		// Save the ItemVO
		measuringInstrumentsRepo.save(measuringInstrumentsVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("measuringInstrumentsVO", measuringInstrumentsVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateMeasuringInstrumentVOByMeasuringInstrumentDTO(
			@Valid MeasuringInstrumentsDTO measuringInstrumentsDTO, MeasuringInstrumentsVO measuringInstrumentsVO) {
		measuringInstrumentsVO.setInstrumentName(measuringInstrumentsDTO.getInstrumentName());
		measuringInstrumentsVO.setInstrumentCode(measuringInstrumentsDTO.getInstrumentCode());
		measuringInstrumentsVO.setLeastCount(measuringInstrumentsDTO.getLeastCount());
		measuringInstrumentsVO.setCalibrationFrequence(measuringInstrumentsDTO.getCalibrationFrequence());
		measuringInstrumentsVO.setColourCode(measuringInstrumentsDTO.getColourCode());
		measuringInstrumentsVO.setRanges(measuringInstrumentsDTO.getRanges());
		measuringInstrumentsVO.setRemarks(measuringInstrumentsDTO.getRemarks());
		measuringInstrumentsVO.setOrgId(measuringInstrumentsDTO.getOrgId());
	}

	@Override
	public String getMeasuringInstrumentsDocId(Long orgId) {
		String ScreenCode = "MI";
		String result = measuringInstrumentsRepo.getMeasuringInstrumentsDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getInstrumentNameFromItemMaster(Long orgId) {

		Set<Object[]> result = measuringInstrumentsRepo.findInstrumentNameFromItemMaster(orgId);
		return getInstrumentNameFromItemMaster(result);
	}

	private List<Map<String, Object>> getInstrumentNameFromItemMaster(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("item", fs[0] != null ? fs[0].toString() : "");
			part.put("itemDesc", fs[1] != null ? fs[1].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	// ProcessMaster

	@Override
	public List<ItemWiseProcessMasterVO> getItemWiseProcessMasterByOrgId(Long orgId) {
		List<ItemWiseProcessMasterVO> itemWiseProcessMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received ItemWiseProcessMaster BY OrgId : {}", orgId);
			itemWiseProcessMasterVO = itemWiseProcessMasterRepo.findItemWiseProcessMasterByOrgId(orgId);
		}
		return itemWiseProcessMasterVO;
	}

	@Override
	public List<ItemWiseProcessMasterVO> getItemWiseProcessMasterById(Long id) {
		List<ItemWiseProcessMasterVO> itemWiseProcessMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ItemWiseProcessMaster BY Id : {}", id);
			itemWiseProcessMasterVO = itemWiseProcessMasterRepo.findItemWiseProcessMasterById(id);
		}
		return itemWiseProcessMasterVO;
	}

	@Override
	public Map<String, Object> updateCreateItemWiseProcessMaster(@Valid ItemWiseProcessMasterDTO itemWiseProcessMasterDTO)
			throws ApplicationException {
		String message;
		ItemWiseProcessMasterVO itemWiseProcessMasterVO = new ItemWiseProcessMasterVO();
		String screenCode = "PM";

		if (itemWiseProcessMasterDTO.getId() != null) {
			// Fetch existing ItemVO for update
			itemWiseProcessMasterVO = itemWiseProcessMasterRepo.findById(itemWiseProcessMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("ItemWiseProcessMaster not found"));

			itemWiseProcessMasterVO.setUpdatedBy(itemWiseProcessMasterDTO.getCreatedBy());
			createUpdateProcessMasterVOByProcessMasterDTO(itemWiseProcessMasterDTO, itemWiseProcessMasterVO);
			message = "ItemWiseProcessMaster Updated Successfully";
		} else {

			// Create new ItemVO
			itemWiseProcessMasterVO.setCreatedBy(itemWiseProcessMasterDTO.getCreatedBy());
			itemWiseProcessMasterVO.setUpdatedBy(itemWiseProcessMasterDTO.getCreatedBy());
			createUpdateProcessMasterVOByProcessMasterDTO(itemWiseProcessMasterDTO, itemWiseProcessMasterVO);
			message = "ItemWiseProcessMaster Created Successfully";
		}

		// Save the ItemVO
		itemWiseProcessMasterRepo.save(itemWiseProcessMasterVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("ItemWiseProcessMasterVO", itemWiseProcessMasterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateProcessMasterVOByProcessMasterDTO(@Valid ItemWiseProcessMasterDTO itemWiseProcessMasterDTO,
			ItemWiseProcessMasterVO itemWiseProcessMasterVO) {
		itemWiseProcessMasterVO.setProcessType(itemWiseProcessMasterDTO.getProcessType());
		itemWiseProcessMasterVO.setItem(itemWiseProcessMasterDTO.getItem());
		itemWiseProcessMasterVO.setItemDesc(itemWiseProcessMasterDTO.getItemDesc());
		itemWiseProcessMasterVO.setOrgId(itemWiseProcessMasterDTO.getOrgId());

		if (itemWiseProcessMasterDTO.getId() != null) {
			List<ItemWiseProcessDetailsVO> itemWiseProcessDetailsVOs = itemWiseProcessDetailsRepo
					.findByItemWiseProcessMasterVO(itemWiseProcessMasterVO);
			itemWiseProcessDetailsRepo.deleteAll(itemWiseProcessDetailsVOs);
		}
		// Handling ItemPriceSlabVO
		List<ItemWiseProcessDetailsVO> itemWiseProcessDetailsVOs = new ArrayList<>();
		for (ItemWiseProcessDetailsDTO itemWiseProcessDetailsDTO : itemWiseProcessMasterDTO.getItemWiseProcessDetailsDTO()) {
			ItemWiseProcessDetailsVO itemWiseProcessDetailsVO = new ItemWiseProcessDetailsVO();
			itemWiseProcessDetailsVO.setProcessName(itemWiseProcessDetailsDTO.getProcessName());
			itemWiseProcessDetailsVO.setItemWiseProcessMasterVO(itemWiseProcessMasterVO); // Set the reference in child entity
			itemWiseProcessDetailsVOs.add(itemWiseProcessDetailsVO);
		}
		itemWiseProcessMasterVO.setItemWiseProcessDetailsVO(itemWiseProcessDetailsVOs);

	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getItemAndItemDescforItemWiseProcess(Long orgId) {

		Set<Object[]> result = itemWiseProcessMasterRepo.findItemAndItemDescforItemWiseProcess(orgId);
		return getItemAndItemDescforItemWiseProcess(result);
	}

	private List<Map<String, Object>> getItemAndItemDescforItemWiseProcess(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("itemName", fs[0] != null ? fs[0].toString() : "");
			part.put("itemDesc", fs[1] != null ? fs[1].toString() : "");

			details1.add(part);
		}
		return details1;
	}

}
