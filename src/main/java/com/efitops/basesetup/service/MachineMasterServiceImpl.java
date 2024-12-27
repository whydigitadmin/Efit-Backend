package com.efitops.basesetup.service;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.DrawingMaster1DTO;
import com.efitops.basesetup.dto.DrawingMaster2DTO;
import com.efitops.basesetup.dto.DrawingMasterDTO;
import com.efitops.basesetup.dto.MachineCapacityDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.MachineTechnicalInfoDTO;
import com.efitops.basesetup.dto.StockLocationDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DrawingMaster1VO;
import com.efitops.basesetup.entity.DrawingMaster2VO;
import com.efitops.basesetup.entity.DrawingMasterVO;
import com.efitops.basesetup.entity.MachineCapacityVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineTechnicalInfoVO;
import com.efitops.basesetup.entity.StockLocationRepo;
import com.efitops.basesetup.entity.StockLocationVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.CompanyRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.DrawingMaster1Repo;
import com.efitops.basesetup.repo.DrawingMaster2Repo;
import com.efitops.basesetup.repo.DrawingMasterRepo;
import com.efitops.basesetup.repo.MachineCapacityRepo;
import com.efitops.basesetup.repo.MachineMasterRepo;
import com.efitops.basesetup.repo.MachineTechnicalInfoRepo;

@Service
public class MachineMasterServiceImpl implements MachineMasterService {

	@Autowired
	MachineMasterRepo machineMasterRepo;

	@Autowired
	MachineTechnicalInfoRepo machineTechnicalInfoRepo;

	@Autowired
	MachineCapacityRepo machineCapacityRepo;

	@Autowired
	StockLocationRepo stockLocationRepo;
	
	@Autowired
	DrawingMasterRepo drawingMasterRepo;
	
	@Autowired
	DrawingMaster1Repo drawingMaster1Repo;
	
	@Autowired
	DrawingMaster2Repo drawingMaster2Repo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	CompanyRepo companyRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(MachineMasterServiceImpl.class);

	@Override
	public Map<String, Object> updateCreateMachineMaster(@Valid MachineMasterDTO machineMasterDTO)
			throws ApplicationException {

		MachineMasterVO machineMasterVO;
        String screenCode="MM";
		String message = null;

		if (ObjectUtils.isEmpty(machineMasterDTO.getId())) {

			machineMasterVO = new MachineMasterVO();
			
			machineMasterVO.setCreatedBy(machineMasterDTO.getCreatedBy());
			machineMasterVO.setUpdatedBy(machineMasterDTO.getCreatedBy());
			
			String docId = machineMasterRepo.getMachineMasterByDocId(machineMasterDTO.getOrgId(),
					screenCode);

			machineMasterVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(machineMasterDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "MachineMaster Creation Success";
		}

		else {
			machineMasterVO = machineMasterRepo.findById(machineMasterDTO.getId()).orElseThrow(
					() -> new ApplicationException("Machine Master Not Found with id: " + machineMasterDTO.getId()));
			machineMasterVO.setUpdatedBy(machineMasterDTO.getCreatedBy());

			message = "MachineMaster Updation Successfully";
		}

		machineMasterVO = getMachineMasterVOFrommachineMasterDTO(machineMasterVO, machineMasterDTO);
		machineMasterRepo.save(machineMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("machineMasterVO", machineMasterVO);
		return response;

	}

	private MachineMasterVO getMachineMasterVOFrommachineMasterDTO(MachineMasterVO machineMasterVO,
			@Valid MachineMasterDTO machineMasterDTO) {

		machineMasterVO.setDepartment(machineMasterDTO.getDepartment());
		machineMasterVO.setType(machineMasterDTO.getType());
		machineMasterVO.setMachineNo(machineMasterDTO.getMachineNo());
		machineMasterVO.setMachineName(machineMasterDTO.getMachineName());
		machineMasterVO.setCalibrationRequired(machineMasterDTO.isCalibrationRequired());
		machineMasterVO.setLocation(machineMasterDTO.getLocation());
		machineMasterVO.setProcessNo(machineMasterDTO.getProcessNo());
		machineMasterVO.setMachineCategory(machineMasterDTO.getMachineCategory());
		machineMasterVO.setSection(machineMasterDTO.getSection());
		machineMasterVO.setModel(machineMasterDTO.getModel());
		machineMasterVO.setSerialNo(machineMasterDTO.getSerialNo());
		machineMasterVO.setStatus(machineMasterDTO.getStatus());
		machineMasterVO.setManufacturedBy(machineMasterDTO.getManufacturedBy());
		machineMasterVO.setMadeIn(machineMasterDTO.getMadeIn());
		machineMasterVO.setPurchasedFrom(machineMasterDTO.getPurchasedFrom());
		machineMasterVO.setModeOfPurchase(machineMasterDTO.getModeOfPurchase());
		machineMasterVO.setMachineIncharge(machineMasterDTO.getMachineIncharge());
		machineMasterVO.setMachineUsedFor(machineMasterDTO.getMachineUsedFor());
		machineMasterVO.setPmCheckListNo(machineMasterDTO.getPmCheckListNo());
		machineMasterVO.setRemarks(machineMasterDTO.getRemarks());
		machineMasterVO.setActive(machineMasterDTO.isActive());
		machineMasterVO.setOrgId(machineMasterDTO.getOrgId());
		machineMasterVO.setInstrumentName(machineMasterDTO.getInstrumentName());
		machineMasterVO.setFilePath(machineMasterDTO.getFilePath());

		if (machineMasterDTO.getId() != null) {

			List<MachineTechnicalInfoVO> machineTechnicalInfoVOs = machineTechnicalInfoRepo.findByMachineMasterVO(machineMasterVO);
			machineTechnicalInfoRepo.deleteAll(machineTechnicalInfoVOs);

			List<MachineCapacityVO> machineCapacityVOs = machineCapacityRepo.findByMachineMasterVO(machineMasterVO);
			machineCapacityRepo.deleteAll(machineCapacityVOs);

//			List<MachineMasterVO3> machineMasterVO3s = machineMasterRepo3.findByMachineMasterVO(machineMasterVO);
//			machineMasterRepo3.deleteAll(machineMasterVO3s);

		}

		List<MachineTechnicalInfoVO> MachineTechnicalInfoVOs = new ArrayList<>();
		for (MachineTechnicalInfoDTO machineTechnicalInfoDTO : machineMasterDTO.getMachineTechnicalInfoDTO()) {
			MachineTechnicalInfoVO machineTechnicalInfoVO = new MachineTechnicalInfoVO();

			machineTechnicalInfoVO.setInstallationDate(machineTechnicalInfoDTO.getInstallationDate());
			machineTechnicalInfoVO.setPowerConsumption(machineTechnicalInfoDTO.getPowerConsumption());
			machineTechnicalInfoVO.setConsumption(machineTechnicalInfoDTO.getConsumption());
			machineTechnicalInfoVO.setPowerProduced(machineTechnicalInfoDTO.getPowerProduced());
			machineTechnicalInfoVO.setCapacity(machineTechnicalInfoDTO.getCapacity());
			machineTechnicalInfoVO.setUnit(machineTechnicalInfoDTO.getUnit());
			machineTechnicalInfoVO.setBedSize(machineTechnicalInfoDTO.getBedSize());
			machineTechnicalInfoVO.setCurrentInAmps(machineTechnicalInfoDTO.getCurrentInAmps());
			machineTechnicalInfoVO.setVoltage(machineTechnicalInfoDTO.getVoltage());
			machineTechnicalInfoVO.setCushionTonnage(machineTechnicalInfoDTO.getCushionTonnage());
			machineTechnicalInfoVO.setMachineType(machineTechnicalInfoDTO.getMachineType());
			machineTechnicalInfoVO.setHourlyRate(machineTechnicalInfoDTO.getHourlyRate());
			machineTechnicalInfoVO.setInstrumentWt(machineTechnicalInfoDTO.getInstrumentWt());
			machineTechnicalInfoVO.setUom(machineTechnicalInfoDTO.getUom());
			machineTechnicalInfoVO.setWarrantyStDate(machineTechnicalInfoDTO.getWarrantyStDate());
			machineTechnicalInfoVO.setWarrantyEndDate(machineTechnicalInfoDTO.getWarrantyEndDate());
			machineTechnicalInfoVO.setLastCalibratedDate(machineTechnicalInfoDTO.getLastCalibratedDate());
			machineTechnicalInfoVO.setNextDueDate(machineTechnicalInfoDTO.getNextDueDate());
			machineTechnicalInfoVO.setLifeCycle(machineTechnicalInfoDTO.getLifeCycle());
			machineTechnicalInfoVO.setRangeInfo(machineTechnicalInfoDTO.getRangeInfo());
			machineTechnicalInfoVO.setErrorAllowed(machineTechnicalInfoDTO.getErrorAllowed());
			machineTechnicalInfoVO.setFrequenceOfCalibration(machineTechnicalInfoDTO.getFrequenceOfCalibration());
			machineTechnicalInfoVO.setMaintenanceDate(machineTechnicalInfoDTO.getMaintenanceDate());

			machineTechnicalInfoVO.setMachineMasterVO(machineMasterVO);
			MachineTechnicalInfoVOs.add(machineTechnicalInfoVO);
		}
		machineMasterVO.setMachineTechnicalInfoVO(MachineTechnicalInfoVOs);

		List<MachineCapacityVO> MachineCapacityVOs = new ArrayList<>();
		for (MachineCapacityDTO machineCapacityDTO : machineMasterDTO.getMachineCapacityDTO()) {
			MachineCapacityVO machineCapacityVO = new MachineCapacityVO();

			machineCapacityVO.setItemDescription(machineCapacityDTO.getItemDescription());
			machineCapacityVO.setCycleTime(machineCapacityDTO.getCycleTime());
			machineCapacityVO.setProdQtyHr(machineCapacityDTO.getProdQtyHr());
			machineCapacityVO.setOperationName(machineCapacityDTO.getOperationName());
			machineCapacityVO.setItemId(machineCapacityDTO.getItemId());

			machineCapacityVO.setRemarks(machineCapacityDTO.getRemarks());

			machineCapacityVO.setMachineMasterVO(machineMasterVO);
			MachineCapacityVOs.add(machineCapacityVO);
		}
		machineMasterVO.setMachineCapacityVO(MachineCapacityVOs);

		return machineMasterVO;

	}
	
	@Override
	public String getMachineMasterDocId(Long orgId) {
		String ScreenCode = "MM";
		String result = machineMasterRepo.getMachineMasterByDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<MachineMasterVO> getAllMachineMasterByOrgId(Long orgId) {

		return machineMasterRepo.getMachineMasterByOrgId(orgId);
	}
	
	@Override
	public MachineMasterVO getMachineMasterByDocId(Long orgId, String docId) {
		
		return machineMasterRepo.findALLMachineMasterByDocId(orgId,docId);
	}


	@Override
	public Optional<MachineMasterVO> getAllMachineMasterById(Long id) {

		return machineMasterRepo.getMachineMasterById(id);
	}

	@Override
	public MachineMasterVO uploadMachineAttachementsInBloob(MultipartFile file, Long id) throws IOException {
		MachineMasterVO machineMasterVO = machineMasterRepo.findById(id).get();
		machineMasterVO.setAttachements(file.getBytes());
		return machineMasterRepo.save(machineMasterVO);
	}

	
	
	//STOCK LOCATION
	
	@Override
	public Map<String, Object> updateCreateStockLocation(@Valid StockLocationDTO stockLocationDTO)
			throws ApplicationException {

		StockLocationVO stockLocationVO;

		String message = null;

		if (ObjectUtils.isEmpty(stockLocationDTO.getId())) {

			stockLocationVO = new StockLocationVO();

			stockLocationVO.setCreatedBy(stockLocationDTO.getCreatedBy());

			stockLocationVO.setUpdatedBy(stockLocationDTO.getCreatedBy());

			message = "Stock Location Created succesfull";

		}

		else {

			stockLocationVO = stockLocationRepo.findById(stockLocationDTO.getId()).orElseThrow(
					() -> new ApplicationException("Stock Location Not Found with id: " + stockLocationDTO.getId()));
			stockLocationVO.setUpdatedBy(stockLocationDTO.getCreatedBy());

			message = "Stock Location Updation Successfully";

		}

		stockLocationVO = getStockLocationVOFromStockLocationDTO(stockLocationVO, stockLocationDTO);
		stockLocationRepo.save(stockLocationVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("stockLocationVO", stockLocationVO);
		return response;
		
	}

	private StockLocationVO getStockLocationVOFromStockLocationDTO(StockLocationVO stockLocationVO,
			@Valid StockLocationDTO stockLocationDTO) {
		
		stockLocationVO.setLocationCode(stockLocationDTO.getLocationCode());
		stockLocationVO.setLocationName(stockLocationDTO.getLocationName());
		stockLocationVO.setCompany(stockLocationDTO.getCompany());
		stockLocationVO.setBranch(stockLocationDTO.getBranch() );
		stockLocationVO.setStartDate(stockLocationDTO.getStartDate());
		stockLocationVO.setClosedDate(stockLocationDTO.getClosedDate());
		stockLocationVO.setActive(stockLocationDTO.isActive());
		stockLocationVO.setOrgId(stockLocationDTO.getOrgId());
		
		return stockLocationVO;
	}

	@Override
	public List<StockLocationVO> getAllStockLocationByOrgId(Long orgId) {
		return stockLocationRepo.getStockLocationByOrgId(orgId);
	}

	@Override
	public Optional<StockLocationVO> getAllStockLocationById(Long id) {
		return stockLocationRepo.getStockLocationById(id);
	}

	@Override
	public Map<String, Object> updateDrawingMaster(@Valid DrawingMasterDTO drawingMasterDTO) throws ApplicationException {
		
		DrawingMasterVO drawingMasterVO = new DrawingMasterVO();
		String screenCode ="DM";
		String message = null;

		if (ObjectUtils.isEmpty(drawingMasterDTO.getId())) {

			drawingMasterVO = new DrawingMasterVO();
			

			// GETDOCID API
						String docId = drawingMasterRepo.getDrawingMasterDocId(drawingMasterDTO.getOrgId(), screenCode);

						drawingMasterVO.setDocId(docId);

//						        							// GETDOCID LASTNO +1
						DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
								.findByOrgIdAndScreenCode(drawingMasterDTO.getOrgId(), screenCode);
						documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
						documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			drawingMasterVO.setCreatedBy(drawingMasterDTO.getCreatedBy());

			drawingMasterVO.setUpdatedBy(drawingMasterDTO.getCreatedBy());

			message = "Drawing Master Created succesfull";

		}

		else {


			drawingMasterVO = drawingMasterRepo.findById(drawingMasterDTO.getId()).orElseThrow(
					() -> new ApplicationException("Drawing Master Not Found with id: " + drawingMasterDTO.getId()));
			drawingMasterVO.setUpdatedBy(drawingMasterDTO.getCreatedBy());

			message = "Drawing Master Updation Successfully";

		}

		drawingMasterVO = getDrawingMasterVOFromDrawingMasterDTO(drawingMasterVO, drawingMasterDTO);
		drawingMasterRepo.save(drawingMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("drawingMasterVO", drawingMasterVO);
		return response;
		
	}

	private DrawingMasterVO getDrawingMasterVOFromDrawingMasterDTO(DrawingMasterVO drawingMasterVO,
			@Valid DrawingMasterDTO drawingMasterDTO) {
		drawingMasterVO.setPartNo(drawingMasterDTO.getPartNo());
	    drawingMasterVO.setDrawingNo(drawingMasterDTO.getDrawingNo());
	    drawingMasterVO.setDrawingRevNo(drawingMasterDTO.getDrawingRevNo());
	    drawingMasterVO.setEffDate(drawingMasterDTO.getEffDate());
	    drawingMasterVO.setFgPartNo(drawingMasterDTO.getFgPartNo());
	    drawingMasterVO.setFgPartName(drawingMasterDTO.getFgPartName());
	    drawingMasterVO.setCreatedBy(drawingMasterDTO.getCreatedBy());
	    drawingMasterVO.setCancelRemarks(drawingMasterDTO.getCancelRemarks());
	    drawingMasterVO.setOrgId(drawingMasterDTO.getOrgId());
	    drawingMasterVO.setActive(drawingMasterDTO.isActive());
	    
	    if(drawingMasterDTO.getId() !=null) {
	    	
	    	List<DrawingMaster1VO> drawingMaster1VOs = drawingMaster1Repo.findByDrawingMasterVO(drawingMasterVO);
	    	drawingMaster1Repo.deleteAll(drawingMaster1VOs);
	    	
	    	List<DrawingMaster2VO> drawingMaster2VOs = drawingMaster2Repo.findByDrawingMasterVO(drawingMasterVO);
	    	drawingMaster2Repo.deleteAll(drawingMaster2VOs);
	    	
	    }
	    
	    List<DrawingMaster1VO> drawingMaster1VOs=new ArrayList<>();
	    for(DrawingMaster1DTO drawingMaster1DTO:drawingMasterDTO.getDrawingMaster1DTO()) {
	    	
	    	DrawingMaster1VO drawingMaster1VO=new DrawingMaster1VO();
	    	//drawingMaster1VO.setAttachements(drawingMaster1DTO.getAttachements());
	    	drawingMaster1VO.setFileName(drawingMaster1DTO.getFileName());
	    	
	    	drawingMaster1VO.setDrawingMasterVO(drawingMasterVO);
	    	drawingMaster1VOs.add(drawingMaster1VO);
	    }
	    
	    drawingMasterVO.setDrawingMaster1VO(drawingMaster1VOs);
	    
	    
	    List<DrawingMaster2VO> drawingMaster2VOs=new ArrayList<>();
	    for(DrawingMaster2DTO drawingMaster2DTO:drawingMasterDTO.getDrawingMaster2DTO()) {
	    	
	    	DrawingMaster2VO drawingMaster2VO=new DrawingMaster2VO();
	 //   	drawingMaster2VO.setAttachements(drawingMaster2DTO.getAttachements());
	    	drawingMaster2VO.setFileName(drawingMaster2DTO.getFileName());
	    	
	    	drawingMaster2VO.setDrawingMasterVO(drawingMasterVO);
	    	drawingMaster2VOs.add(drawingMaster2VO);
	    }
	    
	    drawingMasterVO.setDrawingMaster2VO(drawingMaster2VOs);
	    
	    
	    return drawingMasterVO;
	}

	@Override
	public List<DrawingMasterVO> getAllDrawingMasterByOrgId(Long orgId) {
		return drawingMasterRepo.getDrawingMasterByOrgId(orgId);
	}

	@Override
	public Optional<DrawingMasterVO> getAllDrawingMasterById(Long id) {
		return drawingMasterRepo.getDrawingMasterById(id);
	}

//	@Override
//	public DrawingMaster1VO uploadAttachementsInBloob(MultipartFile file, Long id) throws IOException {
//		DrawingMaster1VO drawingMaster1VO = drawingMaster1Repo.findById(id).get();
//		drawingMaster1VO.setAttachements(file.getBytes());
//		return drawingMaster1Repo.save(drawingMaster1VO);
//	}
	
	
	@Override
	public List<DrawingMaster1VO> uploadAttachmentsInBloob(List<MultipartFile> files, List<Long> ids) throws IOException {
	    List<DrawingMaster1VO> updatedDrawingMasterList = new ArrayList<>();

	    for (int i = 0; i < ids.size(); i++) {
	        Long id = ids.get(i);
	        MultipartFile file = files.get(i);

	        // Find the entity by its ID
	        DrawingMaster1VO drawingMaster1VO = drawingMaster1Repo.findById(id)
	                .orElseThrow(() -> new RuntimeException("DrawingMaster1VO not found with ID: " + id));

	        // Process the file and set it as the attachment for this entity
	        drawingMaster1VO.setAttachements(file.getBytes());

	        // Save the updated entity to the database
	        DrawingMaster1VO updatedDrawingMaster = drawingMaster1Repo.save(drawingMaster1VO);

	        // Add the updated entity to the list
	        updatedDrawingMasterList.add(updatedDrawingMaster);
	    }

	    // Return the list of updated entities
	    return updatedDrawingMasterList;
	}

	@Override
	public List<DrawingMaster2VO> uploadAttachmentsInBloob1(List<MultipartFile> files, List<Long> ids) throws IOException {
	    List<DrawingMaster2VO> updatedDrawingMaster2List = new ArrayList<>();

	    for (int i = 0; i < ids.size(); i++) {
	        Long id = ids.get(i);
	        MultipartFile file = files.get(i);

	        // Find the entity by its ID
	        DrawingMaster2VO drawingMaster2VO = drawingMaster2Repo.findById(id)
	                .orElseThrow(() -> new RuntimeException("DrawingMaster2VO not found with ID: " + id));

	        // Process the file and set it as the attachment for this entity
	        drawingMaster2VO.setAttachements(file.getBytes());

	        // Save the updated entity to the database
	        DrawingMaster2VO updatedDrawingMaster2 = drawingMaster2Repo.save(drawingMaster2VO);

	        // Add the updated entity to the list
	        updatedDrawingMaster2List.add(updatedDrawingMaster2);
	    }

	    // Return the list of updated entities
	    return updatedDrawingMaster2List;
	}

	
	@Override
	public DrawingMaster2VO uploadAttachementsInBloob1(MultipartFile file, Long id) throws IOException {
		DrawingMaster2VO drawingMaster2VO = drawingMaster2Repo.findById(id).get();
		drawingMaster2VO.setAttachements(file.getBytes());
		return drawingMaster2Repo.save(drawingMaster2VO);
	}

	@Override
	public List<Map<String, Object>> getCompanyForStockLocation(Long orgId) {
		
		Set<Object[]> getCompany = companyRepo.findCompanyForStockLocation(orgId);
		return getCompanyDetails(getCompany);
	}

	private List<Map<String, Object>> getCompanyDetails(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("companyCode", ch[0] != null ? ch[0].toString() : "");
			map.put("companyName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public List<Map<String, Object>> getFGSFGPartDetailsForDrawingMaster(Long orgId) {
		Set<Object[]> drawingMasterVO = drawingMasterRepo.findFGSFGPartDetailsForDrawingMaster(orgId);
		return getFGSFGPartDetailsForDrawingMaster(drawingMasterVO);
	}

	private List<Map<String, Object>> getFGSFGPartDetailsForDrawingMaster(Set<Object[]> drawingMasterVO) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : drawingMasterVO) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemName", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryUnit", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public String getDrawingMasterDocId(Long orgId) {
		String screenCode = "DM";
		String result = drawingMasterRepo.getDrawingMasterDocId(orgId, screenCode);
		return result;
	}
	
	

	
}

