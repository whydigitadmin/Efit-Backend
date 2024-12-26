package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.dto.DetailsSubmissionToBankDTO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankVO;
import com.efitops.basesetup.exception.ApplicationException;

public interface DetailsSubmissionToBankService {

	List<DetailsSubmissionToBankVO> getAllDetailsSubmissionToBankByOrgId(Long orgId);

	List<DetailsSubmissionToBankVO> getDetailsSubmissionToBankById(Long detailsSubmissionToBankId);

	Map<String, Object> createUpdateDetailsSubmissionToBank(DetailsSubmissionToBankDTO detailsSubmissionToBankDTO)
			throws ApplicationException;

	String getDetailsSubmissionToBankDocId(Long orgId, String finYear, String branchCode, String screenCode);

}