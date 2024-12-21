package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.SettingApprovalDTO;
import com.efitops.basesetup.entity.SettingApprovalVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface QualityApprovalServive {

	List<SettingApprovalVO> getAllSettingApprovalByOrgId(Long orgId);

	SettingApprovalVO getSettingApprovalById(Long id);

	String getSettingApprovalDocId(Long orgId);

	Map<String, Object> createUpdateSettingApproval(SettingApprovalDTO settingApprovalDTO) throws ApplicationException;

	List<Map<String, Object>> getRouteCardDetailsForSetingApproval(Long orgId);

	List<Map<String, Object>> getDrawingNoForSetingApproval(Long orgId, String partNo);

	List<Map<String, Object>> getMachineNoForSetingApproval(Long orgId);

	List<Map<String, Object>> getOperatorNameForSetingApproval(Long orgId);

	List<Map<String, Object>> getSetterNameForSetingApproval(Long orgId);

	List<Map<String, Object>> getQualityNameForSetingApproval(Long orgId);

	List<Map<String, Object>> getShiftInChargeForSetingApproval(Long orgId);

	

}
