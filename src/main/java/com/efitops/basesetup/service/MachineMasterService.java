package com.efitops.basesetup.service;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.DrawingMasterDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.StockLocationDTO;
import com.efitops.basesetup.entity.DrawingMaster1VO;
import com.efitops.basesetup.entity.DrawingMaster2VO;
import com.efitops.basesetup.entity.DrawingMasterVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.StockLocationVO;
import com.efitops.basesetup.exception.ApplicationException;
@Service
public interface MachineMasterService {

	Map<String, Object> updateCreateMachineMaster(@Valid MachineMasterDTO machineMasterDTO) throws ApplicationException;

	List<MachineMasterVO> getAllMachineMasterByOrgId(Long orgId);

	Optional<MachineMasterVO> getAllMachineMasterById(Long id);
	
	MachineMasterVO3 uploadMachineAttachementsInBloob(MultipartFile file, Long id) throws IOException;
	
	String getMachineMasterDocId(Long orgId);

	MachineMasterVO getMachineMasterByDocId(Long orgId, String docId);
	
	MachineMasterVO uploadImagesInMachineMaster(MultipartFile file, Long id) throws IOException;
	
	//STOCKLOCATION

	Map<String, Object> updateCreateStockLocation(@Valid StockLocationDTO stockLocationDTO) throws ApplicationException;

	List<StockLocationVO> getAllStockLocationByOrgId(Long orgId);

	Optional<StockLocationVO> getAllStockLocationById(Long id);
	
	List<Map<String, Object>> getCompanyForStockLocation(Long orgId);
	
	//DRAWING MASTER

	Map<String, Object> updateDrawingMaster(@Valid DrawingMasterDTO drawingMasterDTO) throws ApplicationException;

	List<DrawingMasterVO> getAllDrawingMasterByOrgId(Long orgId);

	Optional<DrawingMasterVO> getAllDrawingMasterById(Long id);

//	List<DrawingMaster1VO> uploadAttachementsInBloob(MultipartFile file, Long id) throws IOException;

	DrawingMaster2VO uploadAttachementsInBloob1(MultipartFile file, Long id)throws IOException;

	

	List<DrawingMaster1VO> uploadAttachmentsInBloob(List<MultipartFile> files, List<Long> id) throws IOException;

	List<DrawingMaster2VO> uploadAttachmentsInBloob1(List<MultipartFile> files, List<Long> id) throws IOException;






	

	

	

	

}

