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
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.MachineMasterDTO1;
import com.efitops.basesetup.dto.MachineMasterDTO2;
import com.efitops.basesetup.dto.StockLocationDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DrawingMaster1VO;
import com.efitops.basesetup.entity.DrawingMaster2VO;
import com.efitops.basesetup.entity.DrawingMasterVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineMasterVO1;
import com.efitops.basesetup.entity.MachineMasterVO2;
import com.efitops.basesetup.entity.MachineMasterVO3;
import com.efitops.basesetup.entity.StockLocationRepo;
import com.efitops.basesetup.entity.StockLocationVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.CompanyRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.DrawingMaster1Repo;
import com.efitops.basesetup.repo.DrawingMaster2Repo;
import com.efitops.basesetup.repo.DrawingMasterRepo;
import com.efitops.basesetup.repo.MachineMasterRepo;
import com.efitops.basesetup.repo.MachineMasterRepo1;
import com.efitops.basesetup.repo.MachineMasterRepo2;
import com.efitops.basesetup.repo.MachineMasterRepo3;

@Service
public class MachineMasterServiceImpl implements MachineMasterService {

	@Autowired
	MachineMasterRepo machineMasterRepo;

	@Autowired
	MachineMasterRepo1 machineMasterRepo1;

	@Autowired
	MachineMasterRepo2 machineMasterRepo2;

	@Autowired
	MachineMasterRepo3 machineMasterRepo3;

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

			List<MachineMasterVO1> machineMasterVO1s = machineMasterRepo1.findByMachineMasterVO(machineMasterVO);
			machineMasterRepo1.deleteAll(machineMasterVO1s);

			List<MachineMasterVO2> machineMasterVO2s = machineMasterRepo2.findByMachineMasterVO(machineMasterVO);
			machineMasterRepo2.deleteAll(machineMasterVO2s);

//			List<MachineMasterVO3> machineMasterVO3s = machineMasterRepo3.findByMachineMasterVO(machineMasterVO);
//			machineMasterRepo3.deleteAll(machineMasterVO3s);

		}

		List<MachineMasterVO1> MachineMasterVO1s = new ArrayList<>();
		for (MachineMasterDTO1 machineMasterDTO1 : machineMasterDTO.getMachineMasterDTO1()) {
			MachineMasterVO1 machineMasterVO1 = new MachineMasterVO1();

			machineMasterVO1.setInstallationDate(machineMasterDTO1.getInstallationDate());
			machineMasterVO1.setPowerConsumption(machineMasterDTO1.getPowerConsumption());
			machineMasterVO1.setConsumption(machineMasterDTO1.getConsumption());
			machineMasterVO1.setPowerProduced(machineMasterDTO1.getPowerProduced());
			machineMasterVO1.setCapacity(machineMasterDTO1.getCapacity());
			machineMasterVO1.setUnit(machineMasterDTO1.getUnit());
			machineMasterVO1.setBedSize(machineMasterDTO1.getBedSize());
			machineMasterVO1.setCurrentInAmps(machineMasterDTO1.getCurrentInAmps());
			machineMasterVO1.setVoltage(machineMasterDTO1.getVoltage());
			machineMasterVO1.setCushionTonnage(machineMasterDTO1.getCushionTonnage());
			machineMasterVO1.setMachineType(machineMasterDTO1.getMachineType());
			machineMasterVO1.setHourlyRate(machineMasterDTO1.getHourlyRate());
			machineMasterVO1.setInstrumentWt(machineMasterDTO1.getInstrumentWt());
			machineMasterVO1.setUom(machineMasterDTO1.getUom());
			machineMasterVO1.setWarrantyStDate(machineMasterDTO1.getWarrantyStDate());
			machineMasterVO1.setWarrantyEndDate(machineMasterDTO1.getWarrantyEndDate());
			machineMasterVO1.setLastCalibratedDate(machineMasterDTO1.getLastCalibratedDate());
			machineMasterVO1.setNextDueDate(machineMasterDTO1.getNextDueDate());
			machineMasterVO1.setLifeCycle(machineMasterDTO1.getLifeCycle());
			machineMasterVO1.setRangeInfo(machineMasterDTO1.getRangeInfo());
			machineMasterVO1.setErrorAllowed(machineMasterDTO1.getErrorAllowed());
			machineMasterVO1.setFrequenceOfCalibration(machineMasterDTO1.getFrequenceOfCalibration());
			machineMasterVO1.setMaintenanceDate(machineMasterDTO1.getMaintenanceDate());

			machineMasterVO1.setMachineMasterVO(machineMasterVO);
			MachineMasterVO1s.add(machineMasterVO1);
		}
		machineMasterVO.setMachineMasterVO1(MachineMasterVO1s);

		List<MachineMasterVO2> MachineMasterVO2s = new ArrayList<>();
		for (MachineMasterDTO2 machineMasterDTO2 : machineMasterDTO.getMachineMasterDTO2()) {
			MachineMasterVO2 machineMasterVO2 = new MachineMasterVO2();

			machineMasterVO2.setItemDescription(machineMasterDTO2.getItemDescription());
			machineMasterVO2.setCycleTime(machineMasterDTO2.getCycleTime());
			machineMasterVO2.setProdQtyHr(machineMasterDTO2.getProdQtyHr());
			machineMasterVO2.setOperationName(machineMasterDTO2.getOperationName());
			machineMasterVO2.setRemarks(machineMasterDTO2.getRemarks());

			machineMasterVO2.setMachineMasterVO(machineMasterVO);
			MachineMasterVO2s.add(machineMasterVO2);
		}
		machineMasterVO.setMachineMasterVO2(MachineMasterVO2s);

//		List<MachineMasterVO3> MachineMasterVO3s = new ArrayList<>();
//		for (MachineMasterDTO3 machineMasterDTO3 : machineMasterDTO.getMachineMasterDTO3()) {
//			MachineMasterVO3 machineMasterVO3 = new MachineMasterVO3();
//
//			machineMasterVO3.setInstrumentName(machineMasterDTO3.getInstrumentName());
//		//	machineMasterVO3.setAttachments(machineMasterDTO3.getAttachments());
//			machineMasterVO3.setFilePath(machineMasterDTO3.getFilePath());
//
//			machineMasterVO3.setMachineMasterVO(machineMasterVO);
//			MachineMasterVO3s.add(machineMasterVO3);
//		}
//		machineMasterVO.setMachineMasterVO3(MachineMasterVO3s);

		return machineMasterVO;

	}
	
	@Override
	public String getMachineMasterDocId(Long orgId) {
		String screenCode = "MM";
		String result = machineMasterRepo.getMachineMasterByDocId(orgId,screenCode);
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
	public MachineMasterVO3 uploadMachineAttachementsInBloob(MultipartFile file, Long id) throws IOException {
		MachineMasterVO3 machineMasterVO3 = machineMasterRepo3.findById(id).get();
		machineMasterVO3.setAttachements(file.getBytes());
		return machineMasterRepo3.save(machineMasterVO3);
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
		
		DrawingMasterVO drawingMasterVO;

		String message = null;

		if (ObjectUtils.isEmpty(drawingMasterDTO.getId())) {

			drawingMasterVO = new DrawingMasterVO();

			drawingMasterVO.setCreatedBy(drawingMasterDTO.getCreatedBy());

			drawingMasterVO.setUpdatedBy(drawingMasterDTO.getCreatedBy());

			message = "Drawing Master Created succesfull";

		}

		else {

			drawingMasterVO = drawingMasterRepo.findById(drawingMasterDTO.getId()).orElseThrow(
					() -> new ApplicationException("Drawing Master Not Found with id: " + drawingMasterDTO.getId()));
			drawingMasterVO.setUpdatedBy(drawingMasterDTO.getCreatedBy());

			message = "Stock Location Updation Successfully";

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

	@Override
	public DrawingMaster1VO uploadAttachementsInBloob(MultipartFile file, Long id) throws IOException {
		DrawingMaster1VO drawingMaster1VO = drawingMaster1Repo.findById(id).get();
		drawingMaster1VO.setAttachements(file.getBytes());
		return drawingMaster1Repo.save(drawingMaster1VO);
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
			map.put("companyName", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public MachineMasterVO uploadImagesInMachineMaster(MultipartFile file, Long id) throws IOException {
		MachineMasterVO machineMasterVO = machineMasterRepo.findById(id).get();
		machineMasterVO.setAttachements(file.getBytes());
		return machineMasterRepo.save(machineMasterVO);
	}

	
	

	
}

