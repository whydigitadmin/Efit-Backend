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

import com.efitops.basesetup.dto.DeliveryChalanForFgDTO;
import com.efitops.basesetup.dto.DeliveryChallanForFgDetailsDTO;
import com.efitops.basesetup.dto.SalesInvoiceLocalDTO;
import com.efitops.basesetup.dto.SalesInvoiceLocalDetailsDTO;
import com.efitops.basesetup.dto.SalesInvoiceLocalTermsDTO;
import com.efitops.basesetup.entity.DeliveryChalanForFgVO;
import com.efitops.basesetup.entity.DeliveryChallanForFgDetailsVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalDetailsVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalTermsVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DeliveryChalanForFgRepo;
import com.efitops.basesetup.repo.DeliveryChallanForFgDetailsRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.SalesInvoiceLocalDetailsRepo;
import com.efitops.basesetup.repo.SalesInvoiceLocalRepo;
import com.efitops.basesetup.repo.SalesInvoiceLocalTermsRepo;

@Service
public class SalesServiceImpl implements SalesService {

	public static final Logger LOGGER = LoggerFactory.getLogger(SalesServiceImpl.class);

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	DeliveryChalanForFgRepo deliveryChalanForFgRepo;

	@Autowired
	DeliveryChallanForFgDetailsRepo deliveryChallanForFgDetailsRepo;

	@Autowired
	SalesInvoiceLocalRepo salesInvoiceLocalRepo;

	@Autowired
	SalesInvoiceLocalDetailsRepo salesInvoiceLocalDetailsRepo;

	@Autowired
	SalesInvoiceLocalTermsRepo salesInvoiceLocalTermsRepo;

	// DeliveryChalanForFg

	@Override
	public Map<String, Object> createUpdateDeliveryChalanForFg(DeliveryChalanForFgDTO deliveryChalanForFgDTO)
			throws ApplicationException {
		DeliveryChalanForFgVO deliveryChalanForFgVO = new DeliveryChalanForFgVO();
		String message;
		String screenCode = "DCF";
		if (ObjectUtils.isNotEmpty(deliveryChalanForFgDTO.getId())) {
			deliveryChalanForFgVO = deliveryChalanForFgRepo.findById(deliveryChalanForFgDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid DeliveryChalanForFg details"));
			message = "DeliveryChalanForFg Updated Successfully";
			deliveryChalanForFgVO.setUpdatedBy(deliveryChalanForFgDTO.getCreatedBy());

		} else {

			String docId = deliveryChalanForFgRepo.getDeliveryChalanForFgDocId(deliveryChalanForFgDTO.getOrgId(),
					screenCode);
			deliveryChalanForFgVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(deliveryChalanForFgDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			deliveryChalanForFgVO.setCreatedBy(deliveryChalanForFgDTO.getCreatedBy());
			deliveryChalanForFgVO.setUpdatedBy(deliveryChalanForFgDTO.getCreatedBy());

			message = "DeliveryChalanForFg Created Successfully";
		}
		createUpdatedDeliveryChalanForFgVOFromDeliveryChalanForFgDTO(deliveryChalanForFgDTO, deliveryChalanForFgVO);
		deliveryChalanForFgRepo.save(deliveryChalanForFgVO);
		Map<String, Object> response = new HashMap<>();
		response.put("deliveryChalanForFgVO", deliveryChalanForFgVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedDeliveryChalanForFgVOFromDeliveryChalanForFgDTO(
			DeliveryChalanForFgDTO deliveryChalanForFgDTO, DeliveryChalanForFgVO deliveryChalanForFgVO) {
		deliveryChalanForFgVO.setCustomerName(deliveryChalanForFgDTO.getCustomerName());
		deliveryChalanForFgVO.setCustomerAddress(deliveryChalanForFgDTO.getCustomerAddress());
		deliveryChalanForFgVO.setSoNo(deliveryChalanForFgDTO.getSoNo());
		deliveryChalanForFgVO.setSoDate(deliveryChalanForFgDTO.getSoDate());
		deliveryChalanForFgVO.setDuDate(deliveryChalanForFgDTO.getDuDate());
		deliveryChalanForFgVO.setVehicleType(deliveryChalanForFgDTO.getVehicleType());
		deliveryChalanForFgVO.setVehicleNo(deliveryChalanForFgDTO.getVehicleNo());

		// Summary
		deliveryChalanForFgVO.setNaration(deliveryChalanForFgDTO.getNaration());

		deliveryChalanForFgVO.setOrgId(deliveryChalanForFgDTO.getOrgId());
		deliveryChalanForFgVO.setActive(deliveryChalanForFgDTO.isActive());
		deliveryChalanForFgVO.setCreatedBy(deliveryChalanForFgDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(deliveryChalanForFgDTO.getId())) {
			List<DeliveryChallanForFgDetailsVO> deliveryChallanForFgDetailsVO1 = deliveryChallanForFgDetailsRepo
					.findByDeliveryChalanForFgVO(deliveryChalanForFgVO);
			deliveryChallanForFgDetailsRepo.deleteAll(deliveryChallanForFgDetailsVO1);

		}

		List<DeliveryChallanForFgDetailsVO> deliveryChallanForFgDetailsVOs = new ArrayList<>();
		for (DeliveryChallanForFgDetailsDTO deliveryChallanForFgDetailsDTO : deliveryChalanForFgDTO
				.getDeliveryChallanForFgDetailsDTO()) {
			DeliveryChallanForFgDetailsVO deliveryChallanForFgDetailsVO = new DeliveryChallanForFgDetailsVO();
			deliveryChallanForFgDetailsVO.setItemNo(deliveryChallanForFgDetailsDTO.getItemNo());
			deliveryChallanForFgDetailsVO.setItemDescription(deliveryChallanForFgDetailsDTO.getItemDescription());
			deliveryChallanForFgDetailsVO.setQuantity(deliveryChallanForFgDetailsDTO.getQuantity());
			deliveryChallanForFgDetailsVO.setUnit(deliveryChallanForFgDetailsDTO.getUnit());
			deliveryChallanForFgDetailsVO.setWeight(deliveryChallanForFgDetailsDTO.getWeight());
			deliveryChallanForFgDetailsVO.setRemarks(deliveryChallanForFgDetailsDTO.getRemarks());
			deliveryChallanForFgDetailsVO.setDeliveryChalanForFgVO(deliveryChalanForFgVO);
			deliveryChallanForFgDetailsVOs.add(deliveryChallanForFgDetailsVO);
		}
		deliveryChalanForFgVO.setDeliveryChallanForFgDetailsVO(deliveryChallanForFgDetailsVOs);
	}

	@Override
	public List<DeliveryChalanForFgVO> getAllDeliveryChalanForFgByOrgId(Long orgId) {

		return deliveryChalanForFgRepo.getAllDeliveryChalanForFgByOrgId(orgId);
	}

	@Override
	public DeliveryChalanForFgVO getDeliveryChalanForFgById(Long id) {

		return deliveryChalanForFgRepo.getDeliveryChalanForFgById(id);
	}

	@Override
	public String getDeliveryChalanForFgDocId(Long orgId) {
		String ScreenCode = "DCF";
		String result = deliveryChalanForFgRepo.getDeliveryChalanForFgDocId(orgId, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getCustomerNameFromPartyMaster(Long orgId) {
		Set<Object[]> chType = deliveryChalanForFgRepo.getCustomerNameFromPartyMaster(orgId);
		return getgetCustomerName(chType);
	}

	private List<Map<String, Object>> getgetCustomerName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : "");
			map.put("customerAddress", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSoNoFromSaleOrder(Long orgId, String customerName) {
		Set<Object[]> chType = deliveryChalanForFgRepo.getSoNoFromSaleOrder(orgId, customerName);
		return getSoNo(chType);
	}

	private List<Map<String, Object>> getSoNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("soNo", ch[0] != null ? ch[0].toString() : "");
			map.put("soDate", ch[1] != null ? ch[1].toString() : "");
			map.put("dueDate", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemNameFromSaleOrder(String customerName) {
		Set<Object[]> chType = deliveryChalanForFgRepo.getItemNameFromSaleOrder(customerName);
		return getItemName(chType);
	}

	private List<Map<String, Object>> getItemName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemName", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("quantity", ch[2] != null ? ch[2].toString() : "");
			map.put("unit", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// SalesInvoiceLocal

	@Override
	public Map<String, Object> createUpdateSalesInvoiceLocal(SalesInvoiceLocalDTO salesInvoiceLocalDTO)
			throws ApplicationException {
		SalesInvoiceLocalVO salesInvoiceLocalVO = new SalesInvoiceLocalVO();
		String message;
		String screenCode = "SIL";
		if (ObjectUtils.isNotEmpty(salesInvoiceLocalDTO.getId())) {
			salesInvoiceLocalVO = salesInvoiceLocalRepo.findById(salesInvoiceLocalDTO.getId())
					.orElseThrow(() -> new ApplicationException("SalesInvoiceLocal Enquiry details"));
			message = "SalesInvoiceLocal Updated Successfully";
			salesInvoiceLocalVO.setUpdatedBy(salesInvoiceLocalDTO.getCreatedBy());

		} else {

			String docId = salesInvoiceLocalRepo.getSalesInvoiceLocalDocId(salesInvoiceLocalDTO.getOrgId(), screenCode);
			salesInvoiceLocalVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(salesInvoiceLocalDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			salesInvoiceLocalVO.setCreatedBy(salesInvoiceLocalDTO.getCreatedBy());
			salesInvoiceLocalVO.setUpdatedBy(salesInvoiceLocalDTO.getCreatedBy());

			message = "SalesInvoiceLocal Created Successfully";
		}
		createUpdatedSalesInvoiceLocalVOFromSalesInvoiceLocalDTO(salesInvoiceLocalDTO, salesInvoiceLocalVO);
		salesInvoiceLocalRepo.save(salesInvoiceLocalVO);
		Map<String, Object> response = new HashMap<>();
		response.put("salesInvoiceLocalVO", salesInvoiceLocalVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSalesInvoiceLocalVOFromSalesInvoiceLocalDTO(SalesInvoiceLocalDTO salesInvoiceLocalDTO,
			SalesInvoiceLocalVO salesInvoiceLocalVO) {
		salesInvoiceLocalVO.setCustomerName(salesInvoiceLocalDTO.getCustomerName());
		salesInvoiceLocalVO.setPackingListNo(salesInvoiceLocalDTO.getPackingListNo());
		salesInvoiceLocalVO.setSalesOrderNo(salesInvoiceLocalDTO.getSalesOrderNo());
		salesInvoiceLocalVO.setGstNo(salesInvoiceLocalDTO.getGstNo());
		salesInvoiceLocalVO.setCurrency(salesInvoiceLocalDTO.getCurrency());
		salesInvoiceLocalVO.setExchangeRate(salesInvoiceLocalDTO.getExchangeRate());
		salesInvoiceLocalVO.setLocation(salesInvoiceLocalDTO.getLocation());
		salesInvoiceLocalVO.setBillingAddress(salesInvoiceLocalDTO.getBillingAddress());
		salesInvoiceLocalVO.setShippingAddress(salesInvoiceLocalDTO.getShippingAddress());
		salesInvoiceLocalVO.setTaxType(salesInvoiceLocalDTO.getTaxType());
		salesInvoiceLocalVO.setRemarks(salesInvoiceLocalDTO.getRemarks());
		salesInvoiceLocalVO.setOrgId(salesInvoiceLocalDTO.getOrgId());
		salesInvoiceLocalVO.setCreatedBy(salesInvoiceLocalDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(salesInvoiceLocalDTO.getId())) {
			List<SalesInvoiceLocalDetailsVO> salesInvoiceLocalDetailsVO1 = salesInvoiceLocalDetailsRepo
					.findBySalesInvoiceLocalVO(salesInvoiceLocalVO);
			salesInvoiceLocalDetailsRepo.deleteAll(salesInvoiceLocalDetailsVO1);

			List<SalesInvoiceLocalTermsVO> salesInvoiceLocalTermsVO1 = salesInvoiceLocalTermsRepo
					.findBySalesInvoiceLocalVO(salesInvoiceLocalVO);
			salesInvoiceLocalTermsRepo.deleteAll(salesInvoiceLocalTermsVO1);
		}

		List<SalesInvoiceLocalDetailsVO> salesInvoiceLocalDetailsVOs = new ArrayList<>();
		for (SalesInvoiceLocalDetailsDTO salesInvoiceLocalDetailsDTO : salesInvoiceLocalDTO
				.getSalesInvoiceLocalDetailsDTO()) {
			SalesInvoiceLocalDetailsVO salesInvoiceLocalDetailsVO = new SalesInvoiceLocalDetailsVO();
			salesInvoiceLocalDetailsVO.setItem(salesInvoiceLocalDetailsDTO.getItem());
			salesInvoiceLocalDetailsVO.setItemDesc(salesInvoiceLocalDetailsDTO.getItemDesc());
			salesInvoiceLocalDetailsVO.setUnits(salesInvoiceLocalDetailsDTO.getUnits());
			salesInvoiceLocalDetailsVO.setAvlStkQty(salesInvoiceLocalDetailsDTO.getAvlStkQty());
			salesInvoiceLocalDetailsVO.setQty(salesInvoiceLocalDetailsDTO.getQty());
			salesInvoiceLocalDetailsVO.setRate(salesInvoiceLocalDetailsDTO.getRate());
			salesInvoiceLocalDetailsVO.setTaxCode(salesInvoiceLocalDetailsDTO.getTaxCode());
			salesInvoiceLocalDetailsVO.setDiscount(salesInvoiceLocalDetailsDTO.getDiscount());
			salesInvoiceLocalDetailsVO.setSgst(salesInvoiceLocalDetailsDTO.getSgst());
			salesInvoiceLocalDetailsVO.setCgst(salesInvoiceLocalDetailsDTO.getSgst());
			salesInvoiceLocalDetailsVO.setIgst(salesInvoiceLocalDetailsDTO.getSgst());
			salesInvoiceLocalDetailsVO.setSalesInvoiceLocalVO(salesInvoiceLocalVO);
			salesInvoiceLocalDetailsVOs.add(salesInvoiceLocalDetailsVO);
		}
		salesInvoiceLocalVO.setSalesInvoiceLocalDetailsVO(salesInvoiceLocalDetailsVOs);
		List<SalesInvoiceLocalTermsVO> salesInvoiceLocalTermsVOs = new ArrayList<>();
		for (SalesInvoiceLocalTermsDTO salesInvoiceLocalTermsDTO : salesInvoiceLocalDTO
				.getSalesInvoiceLocalTermsDTO()) {
			SalesInvoiceLocalTermsVO salesInvoiceLocalTermsVO = new SalesInvoiceLocalTermsVO();
			salesInvoiceLocalTermsVO.setTerms(salesInvoiceLocalTermsDTO.getTerms());
			salesInvoiceLocalTermsVO.setDescription(salesInvoiceLocalTermsDTO.getDescription());

			salesInvoiceLocalTermsVO.setSalesInvoiceLocalVO(salesInvoiceLocalVO);
			salesInvoiceLocalTermsVOs.add(salesInvoiceLocalTermsVO);
		}
		salesInvoiceLocalVO.setSalesInvoiceLocalTermsVO(salesInvoiceLocalTermsVOs);
	}

	@Override
	public List<SalesInvoiceLocalVO> getAllSalesInvoiceLocalByOrgId(Long orgId) {

		return salesInvoiceLocalRepo.getAllSalesInvoiceLocalByOrgId(orgId);
	}

	@Override
	public SalesInvoiceLocalVO getSalesInvoiceLocalById(Long id) {

		return salesInvoiceLocalRepo.getSalesInvoiceLocalById(id);
	}

	@Override
	public String getSalesInvoiceLocalDocId(Long orgId) {
		String ScreenCode = "SIL";
		return salesInvoiceLocalRepo.getSalesInvoiceLocalDocId(orgId, ScreenCode);
	}

}
