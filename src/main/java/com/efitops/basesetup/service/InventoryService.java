package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PutawayDTO;
import com.efitops.basesetup.dto.RouteCardEntryDTO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface InventoryService {

	//Putaway
	Map<String, Object> updateCreatePutaway(@Valid PutawayDTO putawayDTO) throws ApplicationException;

	List<PutawayVO> getPutawayByOrgId(Long orgId);

	List<PutawayVO> getPutawayById(Long id);

	String getPutawayDocId(Long orgId);
	
	//routeCardEntry

	Map<String, Object> updateCreateRouteCardEntry(@Valid RouteCardEntryDTO routeCardEntryDTO) throws ApplicationException;

	List<RouteCardEntryVO> getRouteCardEntryByOrgId(Long orgId);

	List<RouteCardEntryVO> getRouteCardEntryById(Long id);

	String getRouteCardEntryDocId(Long orgId);

}