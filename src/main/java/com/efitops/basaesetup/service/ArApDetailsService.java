package com.efitops.basaesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basaesetup.dto.ArapDetailsDTO;
import com.efitops.basaesetup.entity.ArapDetailsVO;
import com.efitops.basaesetup.exception.ApplicationException;

@Service
public interface ArApDetailsService {

//	ArapDetails
	List<ArapDetailsVO> getAllArapDetailsByOrgId(Long orgId);

	Map<String, Object> createupdateArapDetails(@Valid ArapDetailsDTO arapDetailsDTO) throws ApplicationException;

	List<ArapDetailsVO> getAllArapDetailsById(Long id);

	List<ArapDetailsVO> getArapDetailsByActive();

	ArapDetailsVO getArapDetailsByDocId(Long orgId, String docId);

	String getArapDetailsDocId(Long orgId, String finYear, String branch, String branchCode);

}
