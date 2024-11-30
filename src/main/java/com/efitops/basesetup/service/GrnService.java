package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface GrnService {

	List<GrnVO> getGrnByOrgId(Long orgId);


	List<GrnVO> getGrnById(Long id);

	Map<String, Object> updateCreateGrn(GrnDTO grndto) throws ApplicationException;



	}
