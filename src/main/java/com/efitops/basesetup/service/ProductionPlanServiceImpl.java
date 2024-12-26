package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ProductionPlanDTO;
import com.efitops.basesetup.dto.ProductionPlanDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ProductionPlanDetailsVO;
import com.efitops.basesetup.entity.ProductionPlanVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ProductionPlanDetailsRepo;

@Service
public class ProductionPlanServiceImpl implements ProductionPlanService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProductionPlanServiceImpl.class);

	@Autowired
	ProductionPlanRepo productionPlanRepo;

	@Autowired
	ProductionPlanDetailsRepo productionPlanDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public List<ProductionPlanVO> getAllProductionPlanByOrgId(Long orgId) {
		List<ProductionPlanVO> productionPlanVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  ProductionPlan BY OrgId : {}", orgId);
			productionPlanVO = productionPlanRepo.getAllProductionPlanByOrgId(orgId);
		}
		return productionPlanVO;
	}

	@Override
	public List<ProductionPlanVO> getProductionPlanById(Long id) {
		List<ProductionPlanVO> productionPlanVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ProductionPlan BY Id : {}", id);
			productionPlanVO = productionPlanRepo.getAllProductionPlanById(id);
		}
		return productionPlanVO;
	}

	@Override
	public Map<String, Object> createUpdateProductionPlan(ProductionPlanDTO productionPlanDTO)
			throws ApplicationException {
		String screenCode = "PP";
		ProductionPlanVO productionPlanVO = new ProductionPlanVO();
		String message;
		if (ObjectUtils.isNotEmpty(productionPlanDTO.getId())) {
			productionPlanVO = productionPlanRepo.findById(productionPlanDTO.getId())
					.orElseThrow(() -> new ApplicationException("Production Plan not found"));

			productionPlanVO.setModifiedBy(productionPlanDTO.getCreatedBy());
			createUpdateProductionPlanVOByProductionPlanDTO(productionPlanDTO, productionPlanVO);
			message = "ProductionPlan Updated Successfully";
		} else {
			// GETDOCID API
			String docId = productionPlanRepo.getProductionPlanDocId(productionPlanVO.getOrgId(),
					productionPlanVO.getFinYear(), productionPlanVO.getBranchCode(), screenCode);
			productionPlanVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(productionPlanVO.getOrgId(),
							productionPlanVO.getFinYear(), productionPlanVO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			productionPlanVO.setCreatedBy(productionPlanDTO.getCreatedBy());
			productionPlanVO.setModifiedBy(productionPlanDTO.getCreatedBy());
			createUpdateProductionPlanVOByProductionPlanDTO(productionPlanDTO, productionPlanVO);
			message = "Tax Invoice Created Successfully";
		}

		productionPlanRepo.save(productionPlanVO);
		Map<String, Object> response = new HashMap<>();
		response.put("productionPlanVO", productionPlanVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateProductionPlanVOByProductionPlanDTO(ProductionPlanDTO productionPlanDTO,
			ProductionPlanVO productionPlanVO) {

		// Map fields from DTO to VO
		productionPlanVO.setOrgId(productionPlanDTO.getOrgId());
		productionPlanVO.setBranch(productionPlanDTO.getBranch());
		productionPlanVO.setBranchCode(productionPlanDTO.getBranchCode());
		productionPlanVO.setFinYear(productionPlanDTO.getFinYear());
		productionPlanVO.setCreatedBy(productionPlanDTO.getCreatedBy());
		productionPlanVO.setRouteCardNo(productionPlanDTO.getRouteCardNo());
		productionPlanVO.setWoSoNo(productionPlanDTO.getWoSoNo());
		productionPlanVO.setWoSoDate(productionPlanDTO.getWoSoDate());
		productionPlanVO.setCustomerName(productionPlanDTO.getCustomerName());
		productionPlanVO.setPart(productionPlanDTO.getPart());
		productionPlanVO.setPartDesc(productionPlanDTO.getPartDesc());
		productionPlanVO.setProductionQty(productionPlanDTO.getProductionQty());
		productionPlanVO.setProductionStartDate(productionPlanDTO.getProductionStartDate());
		productionPlanVO.setProductionEndDate(productionPlanDTO.getProductionEndDate());
		productionPlanVO.setRawMaterial(productionPlanDTO.getRawMaterial());
		productionPlanVO.setRawMaterialDesc(productionPlanDTO.getRawMaterialDesc());
		productionPlanVO.setNarration(productionPlanDTO.getNarration());

		if (ObjectUtils.isNotEmpty(productionPlanVO.getId())) {
			List<ProductionPlanDetailsVO> productionPlanDetailsVO1 = productionPlanDetailsRepo
					.findByProductionPlanVO(productionPlanVO);
			productionPlanDetailsRepo.deleteAll(productionPlanDetailsVO1);
		}

		List<ProductionPlanDetailsVO> productionPlanDetailsVOs = new ArrayList<>();
		for (ProductionPlanDetailsDTO productionPlanDetailsDTO : productionPlanDTO.getProductionPlanDetailsDTO()) {

			ProductionPlanDetailsVO productionPlanDetailsVO = new ProductionPlanDetailsVO();
			productionPlanDetailsVO.setProcess(productionPlanDetailsDTO.getProcess());
			productionPlanDetailsVO.setQty(productionPlanDetailsDTO.getQty());
			productionPlanDetailsVO.setFromDate(productionPlanDetailsDTO.getFromDate());
			productionPlanDetailsVO.setToDate(productionPlanDetailsDTO.getToDate());
			productionPlanDetailsVO.setMachineName(productionPlanDetailsDTO.getMachineName());
			productionPlanDetailsVO.setMachineNo(productionPlanDetailsDTO.getMachineNo());
			productionPlanDetailsVO.setTimeTakenInSec(productionPlanDetailsDTO.getTimeTakenInSec());
			productionPlanDetailsVO.setTotalTimeTaken(productionPlanDetailsDTO.getTotalTimeTaken());
			productionPlanDetailsVO.setTimeTakenInHours(productionPlanDetailsDTO.getTimeTakenInHours());
			productionPlanDetailsVO.setQtyPerHr(productionPlanDetailsDTO.getQtyPerHr());
			productionPlanDetailsVO.setExpMaxProd(productionPlanDetailsDTO.getExpMaxProd());
			productionPlanDetailsVO.setExpMinProd(productionPlanDetailsDTO.getExpMinProd());
			productionPlanDetailsVO.setStatus(productionPlanDetailsDTO.getStatus());
			productionPlanDetailsVO.setProductionPlanVO(productionPlanVO);
		}
		productionPlanVO.setProductionPlanDetailsVO(productionPlanDetailsVOs);
	}

	@Override
	public String getProductionPlanDocId(Long orgId, String finYear, String branchCode, String screenCode) {
		return productionPlanRepo.getProductionPlanDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getRouteCardNoAndDetails(Long orgId) {
		Set<Object[]> routeCard = productionPlanRepo.getRouteCardNo(orgId);
		return getRouteCardNo(routeCard);
	}

	private List<Map<String, Object>> getRouteCardNo(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("woSoNo", ch[1] != null ? ch[1].toString() : "");
			map.put("woSoDate", ch[2] != null ? ch[2].toString() : "");
			map.put("customerName", ch[3] != null ? ch[3].toString() : "");
			map.put("partNo", ch[4] != null ? ch[4].toString() : "");
			map.put("partDesc", ch[5] != null ? ch[5].toString() : "");
			map.put("productionQty", ch[6] != null ? ch[6].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRawMaterialDetails(Long orgId) {
		Set<Object[]> rawMaterial = productionPlanRepo.getRawMaterialDetails(orgId);
		return getRawMaterialDetails(rawMaterial);
	}

	private List<Map<String, Object>> getRawMaterialDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("rawMaterail", ch[0] != null ? ch[0].toString() : "");
			map.put("rawMaterailDesc", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getProcessName(Long orgId, String item) {
		Set<Object[]> process = productionPlanRepo.getProcessName(orgId, item);
		return getProcessDetails(process);
	}

	private List<Map<String, Object>> getProcessDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("processName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getMachineName(Long orgId, String fromDate, Long id, String docId) {
		Set<Object[]> machine = productionPlanRepo.getMachineName(orgId, fromDate, id, docId);
		return getMachineDetails(machine);
	}

	private List<Map<String, Object>> getMachineDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("machineNo", ch[0] != null ? ch[0].toString() : "");
			map.put("machineName", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}
}