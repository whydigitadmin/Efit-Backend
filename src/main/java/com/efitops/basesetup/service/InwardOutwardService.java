package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.GateOutwardEntryDTO;
import com.efitops.basesetup.entity.GateInwardEntryVO;
import com.efitops.basesetup.entity.GateOutwardEntryVO;
import com.efitops.basesetup.exception.ApplicationException;
@Service
public interface InwardOutwardService {

	//GateInwardEntry
	List<GateInwardEntryVO> getGateInwardEntryByOrgId(Long orgId);

	List<GateInwardEntryVO> getGateInwardEntryById(Long id);

	Map<String, Object> updateCreateGateInwardEntry(GateInwardEntryDTO gateInwardEntryDTO) throws ApplicationException;

	String getGateInwardEntryDocId(Long orgId);

	//GateOutwardEntry
	List<GateOutwardEntryVO> getGateOutwardEntryByOrgId(Long orgId);

	List<GateOutwardEntryVO> getGateOutwardEntryById(Long id);

	Map<String, Object> updateCreateGateOutwardEntry(GateOutwardEntryDTO gateOutwardEntryDTO) throws ApplicationException;

}
