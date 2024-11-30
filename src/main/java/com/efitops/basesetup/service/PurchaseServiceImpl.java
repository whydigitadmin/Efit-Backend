package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.dto.PurchaseEnquiryDTO;
import com.efitops.basesetup.dto.PurchaseEnquiryDetailsDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO1;
import com.efitops.basesetup.dto.PurchaseIndentDTO2;
import com.efitops.basesetup.entity.PurchaseEnquiryDetailsVO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseIndentVO1;
import com.efitops.basesetup.entity.PurchaseIndentVO2;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DepartmentRepo;
import com.efitops.basesetup.repo.EmployeeRepo;
import com.efitops.basesetup.repo.ItemRepo;
import com.efitops.basesetup.repo.PartyMasterRepo;
import com.efitops.basesetup.repo.PurchaseEnquiryDetailsRepo;
import com.efitops.basesetup.repo.PurchaseEnquiryRepo;
import com.efitops.basesetup.repo.PurchaseIndentRepo;
import com.efitops.basesetup.repo.PurchaseIndentRepo1;
import com.efitops.basesetup.repo.PurchaseIndentRepo2;

@Repository
public class PurchaseServiceImpl implements PurchaseService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImpl.class);

	@Autowired
	PurchaseIndentRepo purchaseIndentRepo;

	@Autowired
	PurchaseIndentRepo1 purchaseIndentRepo1;

	@Autowired
	PurchaseIndentRepo2 purchaseIndentRepo2;

	@Autowired
	PartyMasterRepo partyMasterRepo;

	@Autowired
	ItemRepo itemRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	PurchaseEnquiryRepo purchaseEnquiryRepo;

	@Autowired
	PurchaseEnquiryDetailsRepo purchaseEnquiryDetailsRepo;

	@Override
	public Map<String, Object> updateCreatePurchaseIndent(@Valid PurchaseIndentDTO purchaseIndentDTO)
			throws ApplicationException {

		PurchaseIndentVO purchaseIndentVO;
		String message = null;

		if (ObjectUtils.isEmpty(purchaseIndentDTO.getId())) {

			purchaseIndentVO = new PurchaseIndentVO();
			purchaseIndentVO.setCreatedBy(purchaseIndentDTO.getCreatedBy());
			purchaseIndentVO.setUpdatedBy(purchaseIndentDTO.getCreatedBy());

			message = "PurchaseIndent Creation SuccessFully";
		} else {

			purchaseIndentVO = purchaseIndentRepo.findById(purchaseIndentDTO.getId()).orElseThrow(
					() -> new ApplicationException("PurchaseIndent  Not Found with id: " + purchaseIndentDTO.getId()));
			purchaseIndentVO.setUpdatedBy(purchaseIndentDTO.getCreatedBy());

			message = "PurchaseIndent Updation Successfully";
		}

		purchaseIndentVO = getPurchaseIndentVOFromPurchaseIndentDTO(purchaseIndentVO, purchaseIndentDTO);
		purchaseIndentRepo.save(purchaseIndentVO);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("purchaseIndentVO", purchaseIndentVO);
		return response;

	}

	private PurchaseIndentVO getPurchaseIndentVOFromPurchaseIndentDTO(PurchaseIndentVO purchaseIndentVO,
			@Valid PurchaseIndentDTO purchaseIndentDTO) {

		purchaseIndentVO.setIndentType(purchaseIndentDTO.getIndentType());
		purchaseIndentVO.setCustomerName(purchaseIndentDTO.getCustomerName());
		purchaseIndentVO.setWorkOrderNo(purchaseIndentDTO.getWorkOrderNo());
		purchaseIndentVO.setDepartment(purchaseIndentDTO.getDepartment());
		purchaseIndentVO.setFgPart(purchaseIndentDTO.getFgPart());
		purchaseIndentVO.setFgPartDesc(purchaseIndentDTO.getFgPartDesc());
		purchaseIndentVO.setFgQty(purchaseIndentDTO.getFgQty());
		purchaseIndentVO.setRequestedBy(purchaseIndentDTO.getRequestedBy());
		purchaseIndentVO.setCustomerPoNo(purchaseIndentDTO.getCustomerPoNo());
		purchaseIndentVO.setOrgId(purchaseIndentDTO.getOrgId());
		purchaseIndentVO.setFinYear(purchaseIndentDTO.getFinYear());

		if (purchaseIndentDTO.getId() != null) {

			List<PurchaseIndentVO1> purchaseIndentVO1s = purchaseIndentRepo1.findByPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentRepo1.deleteAll(purchaseIndentVO1s);

			List<PurchaseIndentVO2> purchaseIndentVO2s = purchaseIndentRepo2.findByPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentRepo2.deleteAll(purchaseIndentVO2s);

		}

		List<PurchaseIndentVO1> purchaseIndentVO1s = new ArrayList<PurchaseIndentVO1>();
		for (PurchaseIndentDTO1 purchaseIndentDTO1 : purchaseIndentDTO.getPurchaseIndentDTO1()) {

			PurchaseIndentVO1 purchaseIndentVO1 = new PurchaseIndentVO1();
			purchaseIndentVO1.setItem(purchaseIndentDTO1.getItem());
			purchaseIndentVO1.setItemDescription(purchaseIndentDTO1.getItemDescription());
			purchaseIndentVO1.setUom(purchaseIndentDTO1.getUom());
			purchaseIndentVO1.setReqQty(purchaseIndentDTO1.getReqQty());
			purchaseIndentVO1.setAvlStock(purchaseIndentDTO1.getAvlStock());
			purchaseIndentVO1.setIndentQty(purchaseIndentDTO1.getIndentQty());

			purchaseIndentVO1.setPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentVO1s.add(purchaseIndentVO1);
		}

		purchaseIndentVO.setPurchaseIndentVO1(purchaseIndentVO1s);

		List<PurchaseIndentVO2> purchaseIndentVO2s = new ArrayList<PurchaseIndentVO2>();
		for (PurchaseIndentDTO2 purchaseIndentDTO2 : purchaseIndentDTO.getPurchaseIndentDTO2()) {

			PurchaseIndentVO2 purchaseIndentVO2 = new PurchaseIndentVO2();

			purchaseIndentVO2.setVerifiedBy(purchaseIndentDTO2.getVerifiedBy());
			purchaseIndentVO2.setCancelRemarks(purchaseIndentDTO2.getCancelRemarks());

			purchaseIndentVO2.setPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentVO2s.add(purchaseIndentVO2);
		}

		purchaseIndentVO.setPurchaseIndentVO2(purchaseIndentVO2s);
		return purchaseIndentVO;
	}

	@Override
	public List<PurchaseIndentVO> getAllPurchaseIndentByOrgId(Long orgId) {
		return purchaseIndentRepo.getAllPurchaseIndentByOrgId(orgId);
	}

	@Override
	public Optional<PurchaseIndentVO> getPurchaseIndentById(Long id) {
		return purchaseIndentRepo.getPurchaseIndentById(id);
	}

	@Override
	public List<Map<String, Object>> getCustomerNameForPurchaseIndent(Long orgId) {
		Set<Object[]> cstname = partyMasterRepo.findCustomerDetails(orgId);
		return getCustomerDetails(cstname);
	}

	private List<Map<String, Object>> getCustomerDetails(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerCode", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("customerName", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getIndentType() {
		Set<Object[]> itemDetails = itemRepo.findItemDetails();
		return getItem(itemDetails);
	}

	private List<Map<String, Object>> getItem(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemType", ch[0] != null ? ch[0].toString() : ""); // Empty string if null

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getDepartmentForPurchase() {
		Set<Object[]> departmentDetails = departmentRepo.getDepartmentDetails();
		return getDepart(departmentDetails);
	}

	private List<Map<String, Object>> getDepart(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("departmentId", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("departmentName", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getRequestedByForPurchase() {
		Set<Object[]> requestedByDetails = employeeRepo.getRequestedByDetails();
		return getRequested(requestedByDetails);
	}

	private List<Map<String, Object>> getRequested(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeId", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("employeeName", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getItemDetailsForPurchase(String itemName) {
		Set<Object[]> item = itemRepo.getItemDetails(itemName);
		return getItem1(item);
	}

	private List<Map<String, Object>> getItem1(Set<Object[]> it) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : it) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryUnit", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	// PurchaseEnquiry

	@Override
	public Map<String, Object> updateCreatePurchaseEnquiry(@Valid PurchaseEnquiryDTO purchaseEnquiryDTO)
			throws ApplicationException {

		PurchaseEnquiryVO purchaseEnquiryVO;
		String message = null;

		if (ObjectUtils.isEmpty(purchaseEnquiryDTO.getId())) {

			purchaseEnquiryVO = new PurchaseEnquiryVO();
			purchaseEnquiryVO.setCreatedBy(purchaseEnquiryDTO.getCreatedBy());
			purchaseEnquiryVO.setUpdatedBy(purchaseEnquiryDTO.getCreatedBy());

			message = "PurchaseEnquiry Created SuccessFully";
		} else {

			purchaseEnquiryVO = purchaseEnquiryRepo.findById(purchaseEnquiryDTO.getId())
					.orElseThrow(() -> new ApplicationException(
							"purchaseIndentD  Not Found with id: " + purchaseEnquiryDTO.getId()));
			purchaseEnquiryVO.setUpdatedBy(purchaseEnquiryDTO.getCreatedBy());

			message = "PurchaseEnquiry Updation Successfully";

		}

		purchaseEnquiryVO = getPurchaseEnquiryVOFromPurchaseEnquiryDTO(purchaseEnquiryVO, purchaseEnquiryDTO);
		purchaseEnquiryRepo.save(purchaseEnquiryVO);

		Map<String, Object> reponse = new HashMap<String, Object>();
		reponse.put("message", message);
		reponse.put("purchaseEnquiryVO", purchaseEnquiryVO);
		return reponse;

	}

	private PurchaseEnquiryVO getPurchaseEnquiryVOFromPurchaseEnquiryDTO(PurchaseEnquiryVO purchaseEnquiryVO,
			@Valid PurchaseEnquiryDTO purchaseEnquiryDTO) {

		purchaseEnquiryVO.setPurchaseEnquiryNo(purchaseEnquiryDTO.getPurchaseEnquiryNo());
		purchaseEnquiryVO.setPurchaseEnquiryDate(purchaseEnquiryDTO.getPurchaseEnquiryDate());
		purchaseEnquiryVO.setCustomerName(purchaseEnquiryDTO.getCustomerName());
		purchaseEnquiryVO.setWorkOrderNo(purchaseEnquiryDTO.getWorkOrderNo());
		purchaseEnquiryVO.setPurchaseIndentNo(purchaseEnquiryDTO.getPurchaseIndentNo());
		purchaseEnquiryVO.setCustomerPoNo(purchaseEnquiryDTO.getCustomerPoNo());
		purchaseEnquiryVO.setFgPartName(purchaseEnquiryDTO.getFgPartName());
		purchaseEnquiryVO.setFgPartDesc(purchaseEnquiryDTO.getFgPartDesc());
		purchaseEnquiryVO.setSupplierName(purchaseEnquiryDTO.getSupplierName());
		purchaseEnquiryVO.setContactPerson(purchaseEnquiryDTO.getContactPerson());
		purchaseEnquiryVO.setContactNo(purchaseEnquiryDTO.getContactNo());
		purchaseEnquiryVO.setEnquiryType(purchaseEnquiryDTO.getEnquiryType());
		purchaseEnquiryVO.setEnquiryDueDate(purchaseEnquiryDTO.getEnquiryDueDate());
		purchaseEnquiryVO.setExpectedDeliveryDate(purchaseEnquiryDTO.getExpectedDeliveryDate());
		purchaseEnquiryVO.setSummary(purchaseEnquiryDTO.getSummary());
		purchaseEnquiryVO.setBranch(purchaseEnquiryDTO.getBranch());
		purchaseEnquiryVO.setBranchCode(purchaseEnquiryDTO.getBranchCode());
		purchaseEnquiryVO.setCreatedBy(purchaseEnquiryDTO.getCreatedBy());
		purchaseEnquiryVO.setCancelRemarks(purchaseEnquiryDTO.getCancelRemarks());
		purchaseEnquiryVO.setFinYear(purchaseEnquiryDTO.getFinYear());
		purchaseEnquiryVO.setOrgId(purchaseEnquiryDTO.getOrgId());

		if (purchaseEnquiryDTO.getId() != null) {

			List<PurchaseEnquiryDetailsVO> detailsVOs = purchaseEnquiryDetailsRepo
					.findByPurchaseEnquiryVO(purchaseEnquiryVO);
			purchaseEnquiryDetailsRepo.deleteAll(detailsVOs);

		}

		List<PurchaseEnquiryDetailsVO> purchaseEnquiryDetailsVOs = new ArrayList<PurchaseEnquiryDetailsVO>();

		for (PurchaseEnquiryDetailsDTO purchaseEnquiryDetailsDTO : purchaseEnquiryDTO.getPurchaseEnquiryDetailsDTO()) {

			PurchaseEnquiryDetailsVO purchaseEnquiryDetailsVO = new PurchaseEnquiryDetailsVO();

			purchaseEnquiryDetailsVO.setItem(purchaseEnquiryDetailsDTO.getItem());
			purchaseEnquiryDetailsVO.setItemDesc(purchaseEnquiryDetailsDTO.getItemDesc());
			purchaseEnquiryDetailsVO.setUnit(purchaseEnquiryDetailsDTO.getUnit());
			purchaseEnquiryDetailsVO.setQtyRequired(purchaseEnquiryDetailsDTO.getQtyRequired());
			purchaseEnquiryDetailsVO.setRemarks(purchaseEnquiryDetailsDTO.getRemarks());

			purchaseEnquiryDetailsVO.setPurchaseEnquiryVO(purchaseEnquiryVO);
			purchaseEnquiryDetailsVOs.add(purchaseEnquiryDetailsVO);

		}

		purchaseEnquiryVO.setPurchaseEnquiryDetailsVO(purchaseEnquiryDetailsVOs);

		return purchaseEnquiryVO;
	}

	@Override
	public List<PurchaseEnquiryVO> getAllPurchaseEnquiryByOrgId(Long orgId) {
		return purchaseEnquiryRepo.getPurchaseEnquiry(orgId);
	}

	@Override
	public Optional<PurchaseEnquiryVO> getAllPurchaseEnquiryById(Long id) {
		return purchaseEnquiryRepo.getPurchaseEnquiryById(id);
	}
}
