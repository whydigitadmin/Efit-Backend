package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	List<Map<String, Object>> getCustomerNameAndCodeFromRouteCardEntry(Long orgId);
	
	List<Map<String, Object>> getWorkOrderNoFromRouteCardEntry(Long orgId, String customerCode);
	
	List<Map<String, Object>> getFgPartNameAndDescAndQtyFromRouteCardEntry(Long orgId, String workOrderNo);
	
	List<Map<String, Object>> getOptrSignFromRouteCardEntry(Long orgId);
	
	List<Map<String, Object>> getPreparedByFromRouteCardEntry(Long orgId);

	List<Map<String, Object>> getApprovedByFromRouteCardEntry(Long orgId);

	List<Map<String, Object>> getQAManagerSignFromRouteCardEntry(Long orgId);

	List<Map<String, Object>> getPlantManagerSignFromRouteCardEntry(Long orgId);

	RouteCardEntryVO uploadFileForRouteCardEntry(MultipartFile file, Long id) throws IOException;

	//PickList
	Map<String, Object> updateCreatePickList(@Valid PickListDTO pickListDTO) throws ApplicationException;

	List<PickListVO> getPickListByOrgId(Long orgId);

	List<PickListVO> getPickListById(Long id);

	String getPickListDocId(Long orgId);
	
	List<Map<String, Object>> getItemIssueToProductionDetailsfromPickList(Long orgId, String itemIssueToProduction);

	
	//ItemIssueToProduction

	Map<String, Object> updateCreateItemIssToProd(@Valid ItemIssueToProductionDTO itemIssueToProductionDTO) throws ApplicationException;

	List<ItemIssueToProductionVO> getItemIssToProdByOrgId(Long id);

	List<ItemIssueToProductionVO> getItemIssToProdById(Long id);

	String getItemIssueToProductionDocId(Long orgId);

	List<Map<String, Object>> getRouteCardEntryNoForItemIssueToProduction(Long orgId, String customerCode);

	List<Map<String, Object>> getRouteCardEntryDetailsForItemIssueToProduction(Long orgId, String routeCardNo);

	List<Map<String, Object>> getItemIssueToProductionDetailsfromBom(Long orgId, String fgItemId);

	List<Map<String, Object>> getItemIssueToProductionNofromPickList(Long orgId, String routeCardEntryNo);



}
