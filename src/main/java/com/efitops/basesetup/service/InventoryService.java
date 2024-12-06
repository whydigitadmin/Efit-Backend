package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ItemIssueToProductionDTO;
import com.efitops.basesetup.dto.PickListDTO;
import com.efitops.basesetup.dto.PutawayDTO;
import com.efitops.basesetup.dto.RouteCardEntryDTO;
import com.efitops.basesetup.entity.ItemIssueToProductionVO;
import com.efitops.basesetup.entity.PickListVO;
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
	
	List<Map<String, Object>> getGrnDetailsForPutaway(Long orgId);
	
	List<Map<String, Object>> getLocationCodeForPutaway(Long orgId);

	List<Map<String, Object>> getFillGridForPutaway(Long orgId, String grnNo);

	List<Map<String, Object>> getRackNoForPutaway(Long orgId);

	//routeCardEntry

	Map<String, Object> updateCreateRouteCardEntry(@Valid RouteCardEntryDTO routeCardEntryDTO) throws ApplicationException;

	List<RouteCardEntryVO> getRouteCardEntryByOrgId(Long orgId);

	List<RouteCardEntryVO> getRouteCardEntryById(Long id);

	String getRouteCardEntryDocId(Long orgId);

	//PickList
	Map<String, Object> updateCreatePickList(@Valid PickListDTO pickListDTO) throws ApplicationException;

	List<PickListVO> getPickListByOrgId(Long orgId);

	List<PickListVO> getPickListById(Long id);

	String getPickListDocId(Long orgId);
	
	//ItemIssueToProduction

	Map<String, Object> updateCreateItemIssToProd(@Valid ItemIssueToProductionDTO itemIssueToProductionDTO) throws ApplicationException;

	List<ItemIssueToProductionVO> getItemIssToProdByOrgId(Long id);

	List<ItemIssueToProductionVO> getItemIssToProdById(Long id);

	String getItemIssueToProductionDocId(Long orgId);

	


}
