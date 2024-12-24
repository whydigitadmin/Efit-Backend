package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.FinalInspectionReportDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.dto.InprocessInspectionDTO;
import com.efitops.basesetup.entity.FinalInspectionReportVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface QualityService {

	// IncomingMaterialInspection

	Map<String, Object> createUpdateIncomingMaterialInspection(
			IncomingMaterialInspectionDTO incomingMaterialInspectionDTO) throws ApplicationException;

	List<IncomingMaterialInspectionVO> getAllIncomingMaterialInspectionByOrgId(Long orgId);

	IncomingMaterialInspectionVO getIncomingMaterialInspectionById(Long id);

	String getIncomingMaterialInspectionDocId(Long orgId);

	List<Map<String, Object>> getGrnNoFromGrnScreen(Long orgId, String grnNo);

	List<Map<String, Object>> getItemNoFromGrn(Long orgId, String grnNo);

	// InprocesInspection

	Map<String, Object> createUpdateInprocessInspection(InprocessInspectionDTO inprocessInspectionDTO)
			throws ApplicationException;

	List<InprocessInspectionVO> getAllInprocessInspectionByOrgId(Long orgId);

	InprocessInspectionVO getInprocessInspectionById(Long id);

	String getInprocessInspectionDocId(Long orgId);

	List<Map<String, Object>> getDocIdFromRouteCardNumber(Long orgId);

	List<Map<String, Object>> getDrawingNumberFromDrawingMaster(Long orgId, String fgPartno);

	List<Map<String, Object>> getEmployeeNameFromEmployeeMaster(Long orgId);

	// FinalInspectionReport

	Map<String, Object> createUpdateFinalInspectionReport(FinalInspectionReportDTO finalInspectionReportDTO)
			throws ApplicationException;

	List<FinalInspectionReportVO> getAllFinalInspectionReportByOrgId(Long orgId);

	FinalInspectionReportVO getFinalInspectionReportById(Long id);

	String getFinalInspectionReportDocId(Long orgId);

}
