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

import com.efitops.basesetup.dto.BomDTO;
import com.efitops.basesetup.dto.BomDetailsDTO;
import com.efitops.basesetup.dto.DepartmentDTO;
import com.efitops.basesetup.dto.DesignationDTO;
import com.efitops.basesetup.dto.GstDTO;
import com.efitops.basesetup.dto.ItemDTO;
import com.efitops.basesetup.dto.ItemInventoryDTO;
import com.efitops.basesetup.dto.ItemPriceSlabDTO;
import com.efitops.basesetup.dto.ItemTaxSlabDTO;
import com.efitops.basesetup.dto.ItemWiseProcessDetailsDTO;
import com.efitops.basesetup.dto.ItemWiseProcessMasterDTO;
import com.efitops.basesetup.dto.JobWorkOutDTO;
import com.efitops.basesetup.dto.JobWorkOutDetailsDTO;
import com.efitops.basesetup.dto.MaterialDetailDTO;
import com.efitops.basesetup.dto.MaterialTypeDTO;
import com.efitops.basesetup.dto.MeasuringInstrumentsDTO;
import com.efitops.basesetup.dto.ProcessMasterDTO;
import com.efitops.basesetup.dto.RackMasterDTO;
import com.efitops.basesetup.dto.ShiftDTO;
import com.efitops.basesetup.dto.ShiftDetailsDTO;
import com.efitops.basesetup.dto.UomDTO;
import com.efitops.basesetup.entity.BomDetailsVO;
import com.efitops.basesetup.entity.BomVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DesignationVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.GstVO;
import com.efitops.basesetup.entity.ItemInventoryVO;
import com.efitops.basesetup.entity.ItemPriceSlabVO;
import com.efitops.basesetup.entity.ItemTaxSlabVO;
import com.efitops.basesetup.entity.ItemVO;
import com.efitops.basesetup.entity.ItemWiseProcessDetailsVO;
import com.efitops.basesetup.entity.ItemWiseProcessMasterVO;
import com.efitops.basesetup.entity.JobWorkOutDetailsVO;
import com.efitops.basesetup.entity.JobWorkOutVO;
import com.efitops.basesetup.entity.MaterialDetailVO;
import com.efitops.basesetup.entity.MaterialTypeVO;
import com.efitops.basesetup.entity.MeasuringInstrumentsVO;
import com.efitops.basesetup.entity.ProcessMasterVO;
import com.efitops.basesetup.entity.RackMasterVO;
import com.efitops.basesetup.entity.ShiftDetailsVO;
import com.efitops.basesetup.entity.ShiftVO;
import com.efitops.basesetup.entity.UomVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.BomDetailsRepo;
import com.efitops.basesetup.repo.BomRepo;
import com.efitops.basesetup.repo.DepartmentRepo;
import com.efitops.basesetup.repo.DesignationRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.GstRepo;
import com.efitops.basesetup.repo.ItemInventoryRepo;
import com.efitops.basesetup.repo.ItemPriceSlabRepo;
import com.efitops.basesetup.repo.ItemRepo;
import com.efitops.basesetup.repo.ItemTaxSlabRepo;
import com.efitops.basesetup.repo.ItemWiseProcessDetailsRepo;
import com.efitops.basesetup.repo.ItemWiseProcessMasterRepo;
import com.efitops.basesetup.repo.MaterialDetailRepo;
import com.efitops.basesetup.repo.MaterialTypeRepo;
import com.efitops.basesetup.repo.MeasuringInstrumentsRepo;
import com.efitops.basesetup.repo.ProcessMasterRepo;
import com.efitops.basesetup.repo.RackMasterRepo;
import com.efitops.basesetup.repo.ShiftDetailsRepo;
import com.efitops.basesetup.repo.ShiftRepo;
import com.efitops.basesetup.repo.UomRepo;

@Service
public class EfitMasterServiceImpl implements EfitMasterService {

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

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	GstRepo gstRepo;

	@Autowired
	ProcessMasterRepo processMasterRepo;

	@Autowired
	MaterialTypeRepo materialTypeRepo;

	@Autowired
	MaterialDetailRepo materialDetailRepo;

	@Autowired
	DesignationRepo designationrepo;
	
	@Autowired
	UomRepo uomrepo;
	
	@Autowired
	ShiftRepo shiftRepo;
	
	@Autowired
	ShiftDetailsRepo shiftDetailsRepo;
	
	@Autowired
	ShiftRepo shiftrepo;
	
	@Autowired
	RackMasterRepo rackMasterRepo;
	
	@Autowired
	BomRepo bomRepo;
	
	@Autowired
	BomDetailsRepo bomDetailsRepo;

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

			List<ItemTaxSlabVO> itemTaxSlabVOs = itemTaxSlabRepo.findByItemVO(itemVO);
			itemTaxSlabRepo.deleteAll(itemTaxSlabVOs);

			List<ItemInventoryVO> itemInventoryVOs = itemInventoryRepo.findByItemVO(itemVO);
			itemInventoryRepo.deleteAll(itemInventoryVOs);

			List<ItemPriceSlabVO> itemPriceSlabVOs = itemPriceSlabRepo.findByItemVO(itemVO);
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
	
	@Override
	@Transactional
	public List<Map<String, Object>> getPrimaryCodeFromUomMaster(Long orgId) {

		Set<Object[]> result = itemRepo.findPrimaryCodeFromUomMaster(orgId);
		return getPrimaryCodeFromUomMaster(result);
	}

	private List<Map<String, Object>> getPrimaryCodeFromUomMaster(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("primaryUnit", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getStockLocationForItemMaster(Long orgId) {

		Set<Object[]> result = itemRepo.findStockLocationForItemMaster(orgId);
		return getStockLocationForItemMaster(result);
	}

	private List<Map<String, Object>> getStockLocationForItemMaster(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("stockLocation", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getTaxSlabFromGst(Long orgId) {

		Set<Object[]> result = itemRepo.findTaxSlabFromGst(orgId);
		return getTaxSlabFromGst(result);
	}

	private List<Map<String, Object>> getTaxSlabFromGst(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("taxSlab", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getMaterialGroupFromMaterialType(Long orgId,String materialType) {

		Set<Object[]> result = itemRepo.findMaterialGroupFromMaterialType(orgId,materialType);
		return getMaterialGroupFromMaterialType(result);
	}

	private List<Map<String, Object>> getMaterialGroupFromMaterialType(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("materialGroup", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getMaterialSubGroupFromMaterialType(Long orgId,String materialType,String materialGroup) {

		Set<Object[]> result = itemRepo.findMaterialSubGroupFromMaterialType(orgId,materialType,materialGroup);
		return getMaterialSubGroupFromMaterialType(result);
	}

	private List<Map<String, Object>> getMaterialSubGroupFromMaterialType(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("materialSubGroup", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getMaterialTypeForItemMaster(Long orgId) {

		Set<Object[]> result = itemRepo.findMaterialTypeForItemMaster(orgId);
		return getMaterialTypeForItemMaster(result);
	}

	private List<Map<String, Object>> getMaterialTypeForItemMaster(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("materialType", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
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
	public Map<String, Object> updateCreateItemWiseProcessMaster(
			@Valid ItemWiseProcessMasterDTO itemWiseProcessMasterDTO) throws ApplicationException {
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
		for (ItemWiseProcessDetailsDTO itemWiseProcessDetailsDTO : itemWiseProcessMasterDTO
				.getItemWiseProcessDetailsDTO()) {
			ItemWiseProcessDetailsVO itemWiseProcessDetailsVO = new ItemWiseProcessDetailsVO();
			itemWiseProcessDetailsVO.setProcessName(itemWiseProcessDetailsDTO.getProcessName());
			itemWiseProcessDetailsVO.setItemWiseProcessMasterVO(itemWiseProcessMasterVO); // Set the reference in child
																							// entity
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
	
	@Override
	@Transactional
	public List<Map<String, Object>> getProcessNameFromItemWiseProcess(Long orgId) {

		Set<Object[]> result = itemWiseProcessMasterRepo.findProcessNameFromItemWiseProcess(orgId);
		return getProcessNameFromItemWiseProcess(result);
	}

	private List<Map<String, Object>> getProcessNameFromItemWiseProcess(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("processName", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	// Department
	@Override
	public Map<String, Object> createUpdateDepartment(DepartmentDTO departmentDTO) throws ApplicationException {
		DepartmentVO departmentVO = new DepartmentVO();
		String message;
		String screenCode = "DEPT";
		if (ObjectUtils.isNotEmpty(departmentDTO.getId())) {
			departmentVO = departmentRepo.findById(departmentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Department details"));

			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			if (!departmentVO.getDepartmentName().equalsIgnoreCase(departmentDTO.getDepartmentName())) {
				if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("The DepartmentName: %s already exists in This Organization.",
							departmentDTO.getDepartmentName());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setDepartmentName(departmentDTO.getDepartmentName().toUpperCase());
			}

			if (!departmentVO.getDepartmentCode().equalsIgnoreCase(departmentDTO.getDepartmentCode())) {
				if (departmentRepo.existsByDepartmentCodeAndOrgId(departmentDTO.getDepartmentCode(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("The DepartmentCode: %s already exists in This Organization.",
							departmentDTO.getDepartmentCode());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setDepartmentCode(departmentDTO.getDepartmentCode().toUpperCase());
			}
			message = "Department Updated Successfully";
		} else {

			if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
					departmentDTO.getOrgId())) {
				String errorMessage = String.format("The DepartmentName : %s already exists in This Organization.",
						departmentDTO.getDepartmentName());
				throw new ApplicationException(errorMessage);
			}
			if (departmentRepo.existsByDepartmentCodeAndOrgId(departmentDTO.getDepartmentCode(),
					departmentDTO.getOrgId())) {
				String errorMessage = String.format("The DepartmentCode: %s already exists in This Organization.",
						departmentDTO.getDepartmentCode());
				throw new ApplicationException(errorMessage);
			}
			String docId = departmentRepo.getDepartmentDocId(departmentDTO.getOrgId(), screenCode);
			departmentVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(departmentDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			message = "Department Created Successfully";
		}

		createUpdateDepartmentVOByDepartmentDTO(departmentDTO, departmentVO);
		departmentRepo.save(departmentVO);
		Map<String, Object> response = new HashMap<>();
		response.put("departmentVO", departmentVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDepartmentVOByDepartmentDTO(DepartmentDTO departmentDTO, DepartmentVO departmentVO) {
		departmentVO.setDepartmentName(departmentDTO.getDepartmentName().toUpperCase());
		departmentVO.setDepartmentCode(departmentDTO.getDepartmentCode().toUpperCase());
		departmentVO.setOrgId(departmentDTO.getOrgId());
		departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
		departmentVO.setActive(departmentDTO.isActive());

	}

	@Override
	public String getDepartmentDocId(Long orgId) {
		String screenCode = "DEPT";
		String result = departmentRepo.getDepartmentDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<DepartmentVO> getAllDepartmentByOrgId(Long orgId) {
		List<DepartmentVO> departmentVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Department BY OrgId : {}", orgId);
			departmentVO = departmentRepo.getAllDepartmentByOrgId(orgId);
		}
		return departmentVO;
	}

	@Override
	public List<DepartmentVO> getDepartmentById(Long id) {
		List<DepartmentVO> departmentVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  Department BY Id : {}", id);
			departmentVO = departmentRepo.getDepartmentById(id);
		}
		return departmentVO;
	}

	// GST

	@Override
	public Map<String, Object> createUpdateGst(GstDTO gstDTO) throws ApplicationException {
		GstVO gstVO = new GstVO();
		String message;
		if (ObjectUtils.isNotEmpty(gstDTO.getId())) {
			gstVO = gstRepo.findById(gstDTO.getId()).orElseThrow(() -> new ApplicationException("Invalid Gst details"));
			message = "Gst Updated Successfully";
			gstVO.setUpdatedBy(gstDTO.getCreatedBy());
			if (!gstVO.getGstSlab().equalsIgnoreCase(gstDTO.getGstSlab())) {
				if (gstRepo.existsByGstSlabAndOrgId(gstDTO.getGstSlab(), gstDTO.getOrgId())) {
					String errorMessage = String.format("The GstSlab : %s already exists in This Organization.",
							gstDTO.getGstSlab());
					throw new ApplicationException(errorMessage);
				}
				gstVO.setGstSlab(gstDTO.getGstSlab().toUpperCase());
			}

		} else {

			if (gstRepo.existsByGstSlabAndOrgId(gstDTO.getGstSlab(), gstDTO.getOrgId())) {
				String errorMessage = String.format("The GstSlab : %s already exists in This Organization.",
						gstDTO.getGstSlab());
				throw new ApplicationException(errorMessage);
			}
			gstVO.setCreatedBy(gstDTO.getCreatedBy());
			gstVO.setUpdatedBy(gstDTO.getCreatedBy());

			message = "Gst Created Successfully";
		}
		createUpdateGstVOByGstDTO(gstDTO, gstVO);
		gstRepo.save(gstVO);
		Map<String, Object> response = new HashMap<>();
		response.put("gstVO", gstVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateGstVOByGstDTO(GstDTO gstDTO, GstVO gstVO) {
		gstVO.setGstSlab(gstDTO.getGstSlab().toUpperCase());
		gstVO.setGstPercentage(gstDTO.getGstPercentage());
		gstVO.setIgstPercentage(gstDTO.getIgstPercentage());
		gstVO.setCgstPercentage(gstDTO.getCgstPercentage());
		gstVO.setSgstPercentage(gstDTO.getSgstPercentage());
		gstVO.setOrgId(gstDTO.getOrgId());
		gstVO.setCreatedBy(gstDTO.getCreatedBy());
		gstVO.setActive(gstDTO.isActive());
	}

	@Override
	public List<GstVO> getAllGstByOrgId(Long orgId) {
		List<GstVO> gstVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Gst BY OrgId : {}", orgId);
			gstVO = gstRepo.getAllGstByOrgId(orgId);
		}
		return gstVO;
	}

	@Override
	public List<GstVO> getGstById(Long id) {
		List<GstVO> gstVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  Gst BY Id : {}", id);
			gstVO = gstRepo.getGstById(id);
		}
		return gstVO;
	}

	// ProcessMaster

	@Override
	public Map<String, Object> createUpdateProcessMaster(ProcessMasterDTO processMasterDTO)
			throws ApplicationException {
		ProcessMasterVO processMasterVO = new ProcessMasterVO();
		String message;
		String screenCode = "PM";
		if (ObjectUtils.isNotEmpty(processMasterDTO.getId())) {
			processMasterVO = processMasterRepo.findById(processMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid ProcessMaster details"));
			message = "ProcessMaster Updated Successfully";
			processMasterVO.setUpdatedBy(processMasterDTO.getCreatedBy());
			if (!processMasterVO.getProcessName().equalsIgnoreCase(processMasterDTO.getProcessName())) {
				if (processMasterRepo.existsByProcessNameAndOrgId(processMasterDTO.getProcessName(),
						processMasterDTO.getOrgId())) {
					String errorMessage = String.format("The ProcessName : %s already exists in This Organization.",
							processMasterDTO.getProcessName());
					throw new ApplicationException(errorMessage);
				}
				processMasterVO.setProcessName(processMasterDTO.getProcessName().toUpperCase());
			}

		} else {
			if (processMasterRepo.existsByProcessNameAndOrgId(processMasterDTO.getProcessName(),
					processMasterDTO.getOrgId())) {
				String errorMessage = String.format("The ProcessName : %s already exists in This Organization.",
						processMasterDTO.getProcessName());
				throw new ApplicationException(errorMessage);
			}

			String docId = processMasterRepo.getProcessMasterDocId(processMasterDTO.getOrgId(), screenCode);
			processMasterVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(processMasterDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			processMasterVO.setCreatedBy(processMasterDTO.getCreatedBy());
			processMasterVO.setUpdatedBy(processMasterDTO.getCreatedBy());

			message = "ProcessMaster Created Successfully";
		}
		createUpdatedProcessMasterVOFromProcessMasterDTO(processMasterDTO, processMasterVO);
		processMasterRepo.save(processMasterVO);
		Map<String, Object> response = new HashMap<>();
		response.put("processMasterVO", processMasterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedProcessMasterVOFromProcessMasterDTO(ProcessMasterDTO processMasterDTO,
			ProcessMasterVO processMasterVO) {
		processMasterVO.setProcessName(processMasterDTO.getProcessName().toUpperCase());
		processMasterVO.setCreatedBy(processMasterDTO.getCreatedBy());
		processMasterVO.setOrgId(processMasterDTO.getOrgId());
		processMasterVO.setActive(processMasterDTO.isActive());
	}

	@Override
	public String getProcessMasterDocId(Long orgId) {
		String screenCode = "PM";
		String result = processMasterRepo.getProcessMasterDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<ProcessMasterVO> getAllProcessMasterByOrgId(Long orgId) {
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  ProcessMaster BY OrgId : {}", orgId);
			processMasterVO = processMasterRepo.getAllProcessMasterByOrgId(orgId);
		}
		return processMasterVO;
	}

	@Override
	public List<ProcessMasterVO> getProcessMasterById(Long id) {
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  ProcessMaster BY Id : {}", id);
			processMasterVO = processMasterRepo.getProcessMasterById(id);
		}
		return processMasterVO;
	}

	// Material Type

	@Override
	public Map<String, Object> createUpdateMaterialType(MaterialTypeDTO materialTypeDTO) throws ApplicationException {
		MaterialTypeVO materialTypeVO = new MaterialTypeVO();
		String message;
		if (ObjectUtils.isNotEmpty(materialTypeDTO.getId())) {
			materialTypeVO = materialTypeRepo.findById(materialTypeDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid MaterialType Details"));
			materialTypeVO.setUpdatedBy(materialTypeDTO.getCreatedBy());

			if (!materialTypeVO.getItemGroup().equalsIgnoreCase(materialTypeDTO.getItemGroup())) {
				if (materialTypeRepo.existsByItemGroupAndOrgId(materialTypeDTO.getItemGroup(),
						materialTypeDTO.getOrgId())) {
					String errorMessage = String.format("The ItemGroup: %s already exists in this Organization!",
							materialTypeDTO.getItemGroup());
					throw new ApplicationException(errorMessage);
				}
				materialTypeVO.setItemGroup(materialTypeDTO.getItemGroup().toUpperCase());
			}
			message = "MaterialType Updated Successfully";
		} else {
			if (materialTypeRepo.existsByItemGroupAndOrgId(materialTypeDTO.getItemGroup(),
					materialTypeDTO.getOrgId())) {
				String errorMessage = String.format("The ItemGroup: %s already exists in this Organization!",
						materialTypeDTO.getItemGroup());
				throw new ApplicationException(errorMessage);
			}
			materialTypeVO.setCreatedBy(materialTypeDTO.getCreatedBy());
			materialTypeVO.setUpdatedBy(materialTypeDTO.getCreatedBy());
			message = "MaterialType Created Successfully";
		}

		getMaterialTypeVOFromMaterialTypeDTO(materialTypeDTO, materialTypeVO);
		materialTypeRepo.save(materialTypeVO);

		Map<String, Object> response = new HashMap<>();
		response.put("materialTypeVO", materialTypeVO);
		response.put("message", message);
		return response;
	}

	private void getMaterialTypeVOFromMaterialTypeDTO(MaterialTypeDTO materialTypeDTO, MaterialTypeVO materialTypeVO) {
		materialTypeVO.setMaterialType(materialTypeDTO.getMaterialType().toUpperCase());
		materialTypeVO.setItemGroup(materialTypeDTO.getItemGroup().toLowerCase().toUpperCase());
		materialTypeVO.setOrgId(materialTypeDTO.getOrgId());
		materialTypeVO.setCreatedBy(materialTypeDTO.getCreatedBy());
//		materialTypeVO.setActive(materialTypeDTO.isActive());

		if (ObjectUtils.isNotEmpty(materialTypeVO.getId())) {
			List<MaterialDetailVO> materialDetailVO1 = materialDetailRepo.findByMaterialTypeVO(materialTypeVO);
			materialDetailRepo.deleteAll(materialDetailVO1);
		}

		List<MaterialDetailVO> materialDetailVOs = new ArrayList<>();
		for (MaterialDetailDTO materialDetailDTO : materialTypeDTO.getMaterialDetailDTO()) {
			MaterialDetailVO materialDetailVO = new MaterialDetailVO();
			materialDetailVO.setItemSubGroup(materialDetailDTO.getItemSubGroup());
			materialDetailVO.setMaterialTypeVO(materialTypeVO);
			materialDetailVOs.add(materialDetailVO);
		}
		materialTypeVO.setMaterialDetailVO(materialDetailVOs);
	}

	@Override
	public List<MaterialTypeVO> getAllMaterialTypeByOrgId(Long orgId) {
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  MaterialType BY OrgId : {}", orgId);
			materialTypeVO = materialTypeRepo.getAllMaterialTypeByOrgId(orgId);
		}
		return materialTypeVO;
	}

	@Override
	public List<MaterialTypeVO> getMaterialTypeById(Long id) {
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  MaterialType BY Id : {}", id);
			materialTypeVO = materialTypeRepo.getMaterialTypeById(id);
		}
		return materialTypeVO;
	}

	@Override
	public List<DesignationVO> getDesignationByOrgId(Long orgId) {
		List<DesignationVO> designationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received ArapAdjustments BY OrgId : {}", orgId);
			designationVO = designationrepo.getDesignationByOrgId(orgId);
		} 
		return designationVO;
	}


	@Override
	public List<DesignationVO> getDesignationById(Long id) {
		List<DesignationVO> designationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ArapAdjustments BY Id : {}", id);
			designationVO = designationrepo.getDesignationById(id);
		} 
		return designationVO;
	}
	
	@Override
	public Map<String, Object> updateCreateDesignation(@Valid DesignationDTO designationDTO)
			throws ApplicationException {
		String screenCode = "DSG";
		DesignationVO designationVO = new DesignationVO();
		String message;
		if (ObjectUtils.isNotEmpty(designationDTO.getId())) {
			designationVO = designationrepo.findById(designationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Designation not found"));

			designationVO.setUpdatedBy(designationDTO.getCreatedBy());
			createUpdateDesignationVOByDesignationDTO(designationDTO, designationVO);
			message = "Designation  Updated Successfully";
		} else {
			String docId = designationrepo.getDesignationDocId(designationDTO.getOrgId(), screenCode);
			designationVO.setDocid(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(designationDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			
			designationVO.setCreatedBy(designationDTO.getCreatedBy());
			designationVO.setUpdatedBy(designationDTO.getCreatedBy());
			createUpdateDesignationVOByDesignationDTO(designationDTO, designationVO);
			message = "Designation Created Successfully";
		}

		designationrepo.save(designationVO);
		Map<String, Object> response = new HashMap<>();
		response.put("designationVO", designationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDesignationVOByDesignationDTO(@Valid DesignationDTO designationDTO,
			DesignationVO designationVO) throws ApplicationException {
		designationVO.setDesignation(designationDTO.getDesignation());
		designationVO.setOrgId(designationDTO.getOrgId());
		designationVO.setActive(designationDTO.isActive());
		
		
	}
	@Override
	public String getDesignationDocId(Long orgId) {
		String screenCode = "DSG";
		String result = designationrepo.getDesignationDocId(orgId, screenCode);
		return result;
	}
	
	@Override
	public List<UomVO> getUomByOrgId(Long orgId) {
		List<UomVO> uomVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Uom BY OrgId : {}", orgId);
			uomVO = uomrepo.getUomByOrgId(orgId);
		}
		return uomVO;
	}

	@Override
	public List<UomVO> getUomById(Long id) {
		List<UomVO> uomVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Uom BY Id : {}", id);
			uomVO = uomrepo.getUomById(id);
		}
		return uomVO;
	}

	@Override
	public Map<String, Object> updateCreateUom(@Valid UomDTO uomDTO) throws ApplicationException {
		String screenCode = "D";
		UomVO uomVO = new UomVO();
		String message;
		if (ObjectUtils.isNotEmpty(uomDTO.getId())) {
			uomVO = uomrepo.findById(uomDTO.getId()).orElseThrow(() -> new ApplicationException("Uom not found")); 
			
			 if (!uomVO.getUomCode().equalsIgnoreCase(uomDTO.getUomCode())) {
				if (uomrepo.existsByUomCodeAndOrgId(uomDTO.getUomCode(), uomDTO.getOrgId())) {
					String errorMessage = String.format("The UomCode: %s  already exists This Organization.",
							uomDTO.getUomCode());
					throw new ApplicationException(errorMessage);
				}
			}

			uomVO.setUpdatedBy(uomDTO.getCreatedBy());
			createUpdateUomVOByUomDTO(uomDTO, uomVO);
			message = "Uom  Updated Successfully";
		} else {

			if (uomrepo.existsByUomCodeAndOrgId(uomDTO.getUomCode(), uomDTO.getOrgId())) {
				String errorMessage = String.format("The UomCode: %s  already exists This Organization.",
						uomDTO.getUomCode());
				throw new ApplicationException(errorMessage);
			}
			uomVO.setCreatedBy(uomDTO.getCreatedBy());
			uomVO.setUpdatedBy(uomDTO.getCreatedBy());
			createUpdateUomVOByUomDTO(uomDTO, uomVO);
			message = "Uom Created Successfully";
		}

		uomrepo.save(uomVO);
		Map<String, Object> response = new HashMap<>();
		response.put("uomVO", uomVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateUomVOByUomDTO(@Valid UomDTO uomDTO, UomVO uomVO) throws ApplicationException {
		uomVO.setUomCode(uomDTO.getUomCode());
		uomVO.setUomDesc(uomDTO.getUomDesc());
		uomVO.setOrgId(uomDTO.getOrgId());
		uomVO.setActive(uomDTO.isActive());


	}
	//shift master 
	
		@Override
		public List<ShiftVO> getShiftByOrgId(Long orgId) {
			List<ShiftVO> shiftVO = new ArrayList<>();
			if (ObjectUtils.isNotEmpty(orgId)) {
				LOGGER.info("Successfully Received Uom BY OrgId : {}", orgId);
				shiftVO = shiftRepo.getShiftByOrgId(orgId);
			}
			return shiftVO;
	}

	
	@Override
	public List<ShiftVO> getShiftById(Long id) {
		List<ShiftVO> shiftVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			shiftVO = shiftrepo.getShiftById(id);
		}
		return shiftVO;
	}
	@Override
	public Map<String, Object> updateCreateShift(ShiftDTO shiftdto) throws ApplicationException {
		String screenCode = "D";
		ShiftVO shiftVO = new ShiftVO();
		String message;
		if (ObjectUtils.isNotEmpty(shiftdto.getId())) {
			shiftVO = shiftrepo.findById(shiftdto.getId()).orElseThrow(() -> new ApplicationException("Uom not found")); 
			
			 if (!shiftVO.getShiftName().equalsIgnoreCase(shiftdto.getShiftName())) {
				if (shiftrepo.existsByShiftCodeAndOrgId(shiftdto.getShiftCode(), shiftdto.getOrgId())) {
					String errorMessage = String.format("The UomCode: %s  already exists This Organization.",
							shiftdto.getShiftName());
					throw new ApplicationException(errorMessage);
				}
			}

			 List<ShiftDetailsVO> shiftDetailsVOs = shiftDetailsRepo
						.findByShiftVO(shiftVO);
				shiftDetailsRepo.deleteAll(shiftDetailsVOs);
				
			 shiftVO.setUpdatedBy(shiftdto.getCreatedBy());
			createUpdateShiftVOByShiftDTO(shiftdto, shiftVO);
			message = "Uom  Updated Successfully";
		} else {

			if (shiftrepo.existsByShiftNameAndOrgId(shiftdto.getShiftName(), shiftdto.getOrgId())) {
				String errorMessage = String.format("The Shift: %s  already exists This Organization.",
						shiftdto.getShiftName());
				throw new ApplicationException(errorMessage);
			}
			shiftVO.setCreatedBy(shiftdto.getCreatedBy());
			shiftVO.setUpdatedBy(shiftdto.getCreatedBy());   
			createUpdateShiftVOByShiftDTO(shiftdto, shiftVO);
			message = "Shift Created Successfully";
		}

		shiftrepo.save(shiftVO);
		Map<String, Object> response = new HashMap<>();
		response.put("shiftVO", shiftdto);
		response.put("message", message);
		return response;
	}

	private void createUpdateShiftVOByShiftDTO(@Valid ShiftDTO shiftDTO, ShiftVO shiftVO) throws ApplicationException {
		shiftVO.setShiftName(shiftDTO.getShiftName());
		shiftVO.setShiftType(shiftDTO.getShiftType());
		shiftVO.setShiftCode(shiftDTO.getShiftCode());
		shiftVO.setFromHour(shiftDTO.getFromHour());
		shiftVO.setToHour(shiftDTO.getToHour());
		shiftVO.setTiming(shiftDTO.getTiming());
		shiftVO.setOrgId(shiftDTO.getOrgId());
		shiftVO.setActive(shiftDTO.isActive());;
		

		List<ShiftDetailsVO> shiftDetailsVOs = new ArrayList<>();
		for (ShiftDetailsDTO shiftDetailsDTO : shiftDTO.getShiftDetailsDTO()) {
			ShiftDetailsVO shiftDetailsVO = new ShiftDetailsVO();
			shiftDetailsVO.setSno(shiftDetailsDTO.getSno());
			shiftDetailsVO.setTimingInHours(shiftDetailsDTO.getTimingInHours());
			
			shiftDetailsVO.setShiftVO (shiftVO); // Set the reference in child entity
			shiftDetailsVOs.add(shiftDetailsVO);
		}
		shiftVO.setShiftDetailsVO(shiftDetailsVOs);
	
	}
	
	
	@Override
	public List<RackMasterVO> getRackMasterByOrgId(Long orgId) {
		List<RackMasterVO> rackMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received RackMaster BY OrgId : {}", orgId);
			rackMasterVO = rackMasterRepo.getRackMasterByOrgId(orgId);
		} 
		return rackMasterVO;
	}
	
	@Override
	public List<RackMasterVO> getRackMasterById(Long id) {
		List<RackMasterVO> rackMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received RackMaster BY Id : {}", id);
			rackMasterVO = rackMasterRepo.getRackMasterById(id);
		} 
		return rackMasterVO;
	}
	
	
	@Override
	public Map<String, Object> updateCreateRackMaster(@Valid RackMasterDTO rackMasterDTO) throws ApplicationException {
		String message;

		RackMasterVO rackMasterVO = new RackMasterVO();

		if (rackMasterDTO.getId() != null) {
			// Fetch existing ItemVO for update
			rackMasterVO = rackMasterRepo.findById(rackMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("RackMaster master not found"));
			rackMasterVO.setUpdatedBy(rackMasterDTO.getCreatedBy());
			createUpdateRackMasterVOByRackMasterDTO(rackMasterDTO, rackMasterVO);
			message = "RackMaster Updated Successfully";

			
		} else {
			// Create new ItemVO
			rackMasterVO.setCreatedBy(rackMasterDTO.getCreatedBy());
			rackMasterVO.setUpdatedBy(rackMasterDTO.getCreatedBy());
			createUpdateRackMasterVOByRackMasterDTO(rackMasterDTO, rackMasterVO);
			message = "RackMaster Created Successfully";
		}

		// Save the ItemVO
		rackMasterRepo.save(rackMasterVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("rackMasterVO", rackMasterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateRackMasterVOByRackMasterDTO(@Valid RackMasterDTO rackMasterDTO, RackMasterVO rackMasterVO) {
		rackMasterVO.setRackNo(rackMasterDTO.getRackNo());
		rackMasterVO.setRackLocation(rackMasterDTO.getRackLocation());
		rackMasterVO.setOrgId(rackMasterDTO.getOrgId());
		rackMasterVO.setActive(rackMasterDTO.isActive());

	}

	
	//Bom Master
	
	@Override
	public Map<String, Object> createUpdateBom(BomDTO bomDTO) throws ApplicationException {
		BomVO bomVO = new BomVO();
		String message;
		String screenCode = "BOM";
		if (ObjectUtils.isNotEmpty(bomDTO.getId())) {
			bomVO = bomRepo.findById(bomDTO.getId())
					.orElseThrow(() -> new ApplicationException("SubContractEnquiry Enquiry details"));
			message = "jobWorkOut Updated Successfully";
			bomVO.setUpdatedBy(bomDTO.getCreatedBy());

		} else {

			String docId = bomRepo.getBomDocId(bomDTO.getOrgId(),
					screenCode);
			bomVO.setDocid(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(bomDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			bomVO.setCreatedBy(bomDTO.getCreatedBy());
			bomVO.setUpdatedBy(bomDTO.getCreatedBy());

			message = "jobWorkOut Created Successfully";
		}
		createUpdatedBomVOFromBomDTO(bomDTO, bomVO);
		bomRepo.save(bomVO);
		Map<String, Object> response = new HashMap<>();
		response.put("bomVO", bomVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedBomVOFromBomDTO(BomDTO bomDTO,
			BomVO bomVO) {
		bomVO.setProductCode(bomDTO.getProductCode());
		bomVO.setProductName(bomDTO.getProductName());
		bomVO.setProductType(bomDTO.getProductType());
		bomVO.setQty(bomDTO.getQty());
		bomVO.setUom(bomDTO.getUom());
		bomVO.setActive(bomDTO.isActive());
		bomVO.setRevision(bomDTO.isRevision());
		bomVO.setCurrent(bomDTO.isCurrent());


		if (ObjectUtils.isNotEmpty(bomDTO.getId())) {
			List<BomDetailsVO> bomDetailsVO1 = bomDetailsRepo
					.findByBomVO(bomVO);
			bomDetailsRepo.deleteAll(bomDetailsVO1);

		}

		List<BomDetailsVO> bomDetailsVOs = new ArrayList<>();
		for (BomDetailsDTO bomDetailsDTO : bomDTO.getBomDetailsDTO()) {
			BomDetailsVO bomDetailsVO = new BomDetailsVO();
			bomDetailsVO.setItemCode(bomDetailsDTO.getItemCode());
			bomDetailsVO.setItemDesc(bomDetailsDTO.getItemDesc());
			bomDetailsVO.setItemType(bomDetailsDTO.getItemType());
			bomDetailsVO.setQty(bomDetailsDTO.getQty());
			bomDetailsVO.setUom(bomDetailsDTO.getUom());
			
		}
		bomVO.setBomDetailsVO(bomDetailsVOs);

	
	}

	

	@Override
	public String getBomDocId(Long orgId) {
		String ScreenCode = "BOM";
		String result = bomRepo.getBomDocId(orgId, ScreenCode);
		return result;
	}


	@Override
	public List<BomVO> getAllBomOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return bomRepo.getAllBomByOrgId(orgId);
	}

	@Override
	public List<BomVO> getAllBomId(Long id) {
		// TODO Auto-generated method stub
		return bomRepo.getBomById(id);
	}
	
	
}
