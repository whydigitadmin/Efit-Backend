package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.xmlbeans.impl.xb.xmlconfig.NamespaceList.Member2.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryDetailsDTO;
import com.efitops.basesetup.dto.EnquirySummaryDTO;
import com.efitops.basesetup.dto.ItemParticularsDTO;
import com.efitops.basesetup.dto.QuotationDTO;
import com.efitops.basesetup.dto.QuotationDetailsDTO;
import com.efitops.basesetup.dto.TermsAndConditionsDTO;
import com.efitops.basesetup.dto.WorkOrderDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EnquiryDetailsVO;
import com.efitops.basesetup.entity.EnquirySummaryVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.ItemParticularsVO;
import com.efitops.basesetup.entity.QuotationDetailsVO;
import com.efitops.basesetup.entity.QuotationVO;
import com.efitops.basesetup.entity.TermsAndConditionsVO;
import com.efitops.basesetup.entity.WorkOrderVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.EnquiryDetailsRepo;
import com.efitops.basesetup.repo.EnquiryRepo;
import com.efitops.basesetup.repo.EnquirySummaryRepo;
import com.efitops.basesetup.repo.ItemParticularsRepo;
import com.efitops.basesetup.repo.QuotationDetailsRepo;
import com.efitops.basesetup.repo.QuotationRepo;
import com.efitops.basesetup.repo.TermsAndConditionsRepo;
import com.efitops.basesetup.repo.WorkOrderRepo;

@Service
public class CustomerEnquiryServiceImpl implements CustomerEnquiryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CustomerEnquiryServiceImpl.class);

	@Autowired
	EnquiryRepo enquiryRepo;

	@Autowired
	EnquiryDetailsRepo enquiryDetailsRepo;

	@Autowired
	EnquirySummaryRepo enquirySummaryRepo;

	@Autowired
	QuotationRepo quotationRepo;

	@Autowired
	QuotationDetailsRepo quotationDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	WorkOrderRepo workOrderRepo;

	@Autowired
	ItemParticularsRepo itemParticularsRepo;

	@Autowired
	TermsAndConditionsRepo termsAndConditionsRepo;

	// Enquiry

	@Override
	public Map<String, Object> createUpdateEnquiry(EnquiryDTO enquiryDTO) throws ApplicationException {
		EnquiryVO enquiryVO = new EnquiryVO();
		String message;
		String screenCode = "ENY";
		if (ObjectUtils.isNotEmpty(enquiryDTO.getId())) {
			enquiryVO = enquiryRepo.findById(enquiryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Enquiry details"));
			message = "Enquiry Updated Successfully";
			enquiryVO.setUpdatedBy(enquiryDTO.getCreatedBy());

		} else {

			String docId = enquiryRepo.getEnquiryDocId(enquiryDTO.getOrgId(), screenCode);
			enquiryVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(enquiryDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			enquiryVO.setCreatedBy(enquiryDTO.getCreatedBy());
			enquiryVO.setUpdatedBy(enquiryDTO.getCreatedBy());

			message = "Enquiry Created Successfully";
		}
		createUpdatedEnquiryVOFromEnquiryDTO(enquiryDTO, enquiryVO);
		enquiryRepo.save(enquiryVO);
		Map<String, Object> response = new HashMap<>();
		response.put("enquiryVO", enquiryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedEnquiryVOFromEnquiryDTO(EnquiryDTO enquiryDTO, EnquiryVO enquiryVO) {
		enquiryVO.setEnquiryType(enquiryDTO.getEnquiryType());
		enquiryVO.setCustomer(enquiryDTO.getCustomer());
		enquiryVO.setCustomerCode(enquiryDTO.getCustomerCode());
		enquiryVO.setEnquiryDueDate(enquiryDTO.getEnquiryDueDate());
		enquiryVO.setContactName(enquiryDTO.getContactName());
		enquiryVO.setContactNo(enquiryDTO.getContactNo());
		enquiryVO.setOrgId(enquiryDTO.getOrgId());
		enquiryVO.setActive(enquiryDTO.isActive());
		enquiryVO.setCreatedBy(enquiryDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(enquiryDTO.getId())) {
			List<EnquiryDetailsVO> materialDetailVO1 = enquiryDetailsRepo.findByEnquiryVO(enquiryVO);
			enquiryDetailsRepo.deleteAll(materialDetailVO1);

			List<EnquirySummaryVO> materialDetailVO2 = enquirySummaryRepo.findByEnquiryVO(enquiryVO);
			enquirySummaryRepo.deleteAll(materialDetailVO2);
		}

		List<EnquiryDetailsVO> enquiryDetailsVOs = new ArrayList<>();
		for (EnquiryDetailsDTO enquiryDetailsDTO : enquiryDTO.getEnquiryDetailsDTO()) {
			EnquiryDetailsVO enquiryDetailsVO = new EnquiryDetailsVO();
			enquiryDetailsVO.setPartCode(enquiryDetailsDTO.getPartCode());
			enquiryDetailsVO.setPartDescription(enquiryDetailsDTO.getPartDescription());
			enquiryDetailsVO.setDrawingNo(enquiryDetailsDTO.getDrawingNo());
			enquiryDetailsVO.setRevisionNo(enquiryDetailsDTO.getRevisionNo());
			enquiryDetailsVO.setUnit(enquiryDetailsDTO.getUnit());
			enquiryDetailsVO.setRequireQty(enquiryDetailsDTO.getRequireQty());
			enquiryDetailsVO.setDeliveryDate(enquiryDetailsDTO.getDeliveryDate());
			enquiryDetailsVO.setRemarks(enquiryDetailsDTO.getRemarks());
			enquiryDetailsVO.setEnquiryVO(enquiryVO);
			enquiryDetailsVOs.add(enquiryDetailsVO);
		}
		enquiryVO.setEnquiryDetailsVO(enquiryDetailsVOs);

		List<EnquirySummaryVO> enquirySummaryVOs = new ArrayList<>();
		for (EnquirySummaryDTO enquirySummaryDTO : enquiryDTO.getEnquirySummaryDTO()) {
			EnquirySummaryVO enquirySummaryVO = new EnquirySummaryVO();
			enquirySummaryVO.setAnyAdditionalInverstment(enquirySummaryDTO.getAnyAdditionalInverstment());
			enquirySummaryVO.setAdditionalManPower(enquirySummaryDTO.getAdditionalManPower());
			enquirySummaryVO.setTimeFrame(enquirySummaryDTO.getTimeFrame());
			enquirySummaryVO.setExpectedTimeForDeliverySample(enquirySummaryDTO.getExpectedTimeForDeliverySample());
			enquirySummaryVO.setRegularProduction(enquirySummaryDTO.getRegularProduction());
			enquirySummaryVO.setInitialReviewComments(enquirySummaryDTO.getInitialReviewComments());
			enquirySummaryVO.setDetailreview(enquirySummaryDTO.getDetailreview());
			enquirySummaryVO.setConclusion(enquirySummaryDTO.getConclusion());
			enquirySummaryVO.setRemarks(enquirySummaryDTO.getRemarks());
			enquirySummaryVO.setEnquiryVO(enquiryVO);
			enquirySummaryVOs.add(enquirySummaryVO);
		}
		enquiryVO.setEnquirySummaryVO(enquirySummaryVOs);
	}

	@Override
	public List<EnquiryVO> getAllEnquiryByOrgId(Long orgId) {
		List<EnquiryVO> enquiryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Enquiry BY OrgId : {}", orgId);
			enquiryVO = enquiryRepo.getAllEnquiryByOrgId(orgId);
		}
		return enquiryVO;
	}

	@Override
	public List<EnquiryVO> getEnquiryById(Long id) {
		List<EnquiryVO> enquiryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  Enquiry BY Id : {}", id);
			enquiryVO = enquiryRepo.getEnquiryById(id);
		}
		return enquiryVO;
	}

	@Override
	public String getEnquiryDocId(Long orgId) {
		String screenCode = "ENY";
		String result = enquiryRepo.getEnquiryDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getCustomerNameAndCode(Long orgId) {
		Set<Object[]> chType = enquiryRepo.getCustomerNameAndCode(orgId);
		return getCustomerName(chType);
	}

	private List<Map<String, Object>> getCustomerName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customer", ch[0] != null ? ch[0].toString() : "");
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");
			map.put("currency", ch[2] != null ? ch[2].toString() : "");
			map.put("taxCode", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getContactNameAndNo(Long orgId, String partyName) {
		Set<Object[]> chType = enquiryRepo.getContactNameAndNo(orgId, partyName);
		return getContactName(chType);
	}

	private List<Map<String, Object>> getContactName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("contactName", ch[0] != null ? ch[0].toString() : "");
			map.put("contactNo", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartNoAndDescription(Long orgId) {
		Set<Object[]> chType = enquiryRepo.getPartNoAndDescription(orgId);
		return getPartNo(chType);
	}

	private List<Map<String, Object>> getPartNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("unit", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDrawingNoAndRevNo(Long orgId, String partNo) {
		Set<Object[]> chType = enquiryRepo.getDrawingNoAndRevNo(orgId, partNo);
		return getDrawingNoAndRev(chType);
	}

	private List<Map<String, Object>> getDrawingNoAndRev(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingNo", ch[0] != null ? ch[0].toString() : "");
			map.put("revisionNo", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// Quotation

	@Override
	public Map<String, Object> createUpdateQuotation(QuotationDTO quotationDTO) throws ApplicationException {
		QuotationVO quotationVO = new QuotationVO();
		String message;
		String screenCode = "QOT";
		if (ObjectUtils.isNotEmpty(quotationDTO.getId())) {
			quotationVO = quotationRepo.findById(quotationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Quotation Enquiry details"));
			message = "Quotation Updated Successfully";
			quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());

		} else {

			String docId = quotationRepo.getQuotationDocId(quotationDTO.getOrgId(), screenCode);
			quotationVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(quotationDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			quotationVO.setCreatedBy(quotationDTO.getCreatedBy());
			quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());

			message = "Quotation Created Successfully";
		}
		createUpdatedQuotationVOFromQuotationDTO(quotationDTO, quotationVO);
		quotationRepo.save(quotationVO);
		Map<String, Object> response = new HashMap<>();
		response.put("quotationVO", quotationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedQuotationVOFromQuotationDTO(QuotationDTO quotationDTO, QuotationVO quotationVO) {
		quotationVO.setCustomerName(quotationDTO.getCustomerName());
		quotationVO.setCustomerId(quotationDTO.getCustomerId());
		quotationVO.setEnquiryNo(quotationDTO.getEnquiryNo());
		quotationVO.setEnquiryDate(quotationDTO.getEnquiryDate());
		quotationVO.setVaidTill(quotationDTO.getVaidTill());
		quotationVO.setKindAttention(quotationDTO.getKindAttention());
		quotationVO.setTaxCode(quotationDTO.getTaxCode());
		quotationVO.setProductionManager(quotationDTO.getProductionManager());
		quotationVO.setCurrency(quotationDTO.getCurrency());
		quotationVO.setContactNo(quotationDTO.getContactNo());
		quotationVO.setOrgId(quotationDTO.getOrgId());
		quotationVO.setActive(quotationDTO.isActive());
		quotationVO.setCreatedBy(quotationDTO.getCreatedBy());

		BigDecimal grocessAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(quotationDTO.getId())) {
			List<QuotationDetailsVO> quotationDetailsVO1 = quotationDetailsRepo.findByQuotationVO(quotationVO);
			quotationDetailsRepo.deleteAll(quotationDetailsVO1);

		}

		List<QuotationDetailsVO> quotationDetailsVOs = new ArrayList<>();
		for (QuotationDetailsDTO quotationDetailsDTO : quotationDTO.getQuotationDetailsDTO()) {
			QuotationDetailsVO quotationDetailsVO = new QuotationDetailsVO();
			quotationDetailsVO.setPartCode(quotationDetailsDTO.getPartCode());
			quotationDetailsVO.setPartDescription(quotationDetailsDTO.getPartDescription());
			quotationDetailsVO.setDrawingNo(quotationDetailsDTO.getDrawingNo());
			quotationDetailsVO.setRevisionNo(quotationDetailsDTO.getRevisionNo());
			quotationDetailsVO.setUnit(quotationDetailsDTO.getUnit());
			quotationDetailsVO.setUnitPrice(quotationDetailsDTO.getUnitPrice());
			quotationDetailsVO.setQtyOffered(quotationDetailsDTO.getQtyOffered());
			quotationDetailsVO.setDiscount(quotationDetailsDTO.getDiscount());

			BigDecimal discountamount;

			BigDecimal amountSet = quotationDetailsDTO.getUnitPrice().multiply(quotationDetailsDTO.getQtyOffered());
			quotationDetailsVO.setBasicPrice(amountSet);

			grocessAmount = grocessAmount.add(amountSet);

			discountamount = quotationDetailsVO.getBasicPrice().multiply(quotationDetailsDTO.getDiscount())
					.divide(BigDecimal.valueOf(100));
			quotationDetailsVO.setDiscountAmount(discountamount);
			quotationDetailsVO.setQuoteAmount(
					quotationDetailsVO.getBasicPrice().subtract(quotationDetailsVO.getDiscountAmount()));

			netAmount = netAmount.add(quotationDetailsVO.getQuoteAmount());
			quotationDetailsVO.setDeliveryDate(quotationDetailsDTO.getDeliveryDate());
			quotationDetailsVO.setQuotationVO(quotationVO);
			quotationDetailsVOs.add(quotationDetailsVO);
		}
		quotationVO.setGrossAmount(grocessAmount);
		quotationVO.setNetAmount(netAmount);

		quotationVO.setAmountInWords(amountInWordsConverterService.convert(quotationVO.getNetAmount().longValue()));
		quotationVO.setQuotationDetailsVO(quotationDetailsVOs);
	}

	@Override
	public List<QuotationVO> getAllQuotationByOrgId(Long orgId) {
		List<QuotationVO> quotationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Quotation  BY OrgId : {}", orgId);
			quotationVO = quotationRepo.getAllQuotationByOrgId(orgId);
		}
		return quotationVO;
	}

	@Override
	public List<QuotationVO> getQuotationById(Long id) {
		List<QuotationVO> quotationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  Quotation BY Id : {}", id);
			quotationVO = quotationRepo.getQuotationById(id);
		}
		return quotationVO;
	}

	@Override
	public String getQuotationDocId(Long orgId) {
		String screenCode = "QOT";
		String result = quotationRepo.getQuotationDocId(orgId, screenCode);
		return result;
	}
	
	
	@Override
	public List<Map<String, Object>> getEnquiryNoAndDate(Long orgId, String customer) {
		Set<Object[]> chType = quotationRepo.getEnquiryNoAndDate(orgId,customer);
		return getEnquiryNo(chType);
	}

	private List<Map<String, Object>> getEnquiryNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("enquiryDocNo", ch[0] != null ? ch[0].toString() : "");
			map.put("enquiryDocDate", ch[1] != null ? ch[1].toString() : "");
			map.put("kindAttention", ch[2] != null ? ch[2].toString() : "");
			map.put("contactNo", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getProductionManager(Long orgId) {
		Set<Object[]> chType = quotationRepo.getProductionManager(orgId);
		return getProduction(chType);
	}

	private List<Map<String, Object>> getProduction(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("productionManager", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// WorkOrder

	@Override
	public Map<String, Object> createUpdateWorkOrder(WorkOrderDTO workOrderDTO) throws ApplicationException {
		WorkOrderVO workOrderVO = new WorkOrderVO();
		String message;
		String screenCode = "WOP";
		if (ObjectUtils.isNotEmpty(workOrderDTO.getId())) {
			workOrderVO = workOrderRepo.findById(workOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("Quotation Enquiry details"));
			message = "Quotation Updated Successfully";
			workOrderVO.setUpdatedBy(workOrderDTO.getCreatedBy());

		} else {

			String docId = workOrderRepo.getWorkOrderDocId(workOrderDTO.getOrgId(), screenCode);
			workOrderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(workOrderDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			workOrderVO.setCreatedBy(workOrderDTO.getCreatedBy());
			workOrderVO.setUpdatedBy(workOrderDTO.getCreatedBy());

			message = "Quotation Created Successfully";
		}
		createUpdatedWorkOrderVOFromWorkOrderDTO(workOrderDTO, workOrderVO);
		workOrderRepo.save(workOrderVO);
		Map<String, Object> response = new HashMap<>();
		response.put("workOrderVO", workOrderVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedWorkOrderVOFromWorkOrderDTO(WorkOrderDTO workOrderDTO, WorkOrderVO workOrderVO) {
		workOrderVO.setCustomerName(workOrderDTO.getCustomerName());
		workOrderVO.setCustomerPoNo(workOrderDTO.getCustomerPoNo());
		workOrderVO.setQuotationNo(workOrderDTO.getQuotationNo());
		workOrderVO.setCurrency(workOrderDTO.getCurrency());
		workOrderVO.setCustomerDueDate(workOrderDTO.getCustomerDueDate());
		workOrderVO.setVapDueDate(workOrderDTO.getVapDueDate());
		workOrderVO.setProductionMgr(workOrderDTO.getProductionMgr());
		workOrderVO.setCustomerSpecialRequirement(workOrderDTO.getCustomerSpecialRequirement());
		workOrderVO.setCreatedBy(workOrderDTO.getCreatedBy());
		workOrderVO.setActive(workOrderDTO.isActive());
		workOrderVO.setOrgId(workOrderDTO.getOrgId());
 
		if (ObjectUtils.isNotEmpty(workOrderDTO.getId())) {
			List<ItemParticularsVO> itemParticularsVO1 = itemParticularsRepo.findByWorkOrderVO(workOrderVO);
			itemParticularsRepo.deleteAll(itemParticularsVO1);

			List<TermsAndConditionsVO> termsAndConditionsVO2 = termsAndConditionsRepo.findByWorkOrderVO(workOrderVO);
			termsAndConditionsRepo.deleteAll(termsAndConditionsVO2);
		}

		BigDecimal requiredQty;
		List<ItemParticularsVO> itemParticularsVOs = new ArrayList<>();
		for (ItemParticularsDTO itemParticularsDTO : workOrderDTO.getItemParticularsDTO()) {
			ItemParticularsVO itemParticularsVO = new ItemParticularsVO();
			itemParticularsVO.setPartNo(itemParticularsDTO.getPartNo());
			itemParticularsVO.setPartName(itemParticularsDTO.getPartName());
			itemParticularsVO.setDrawingNo(itemParticularsDTO.getDrawingNo());
			itemParticularsVO.setRevisionNo(itemParticularsDTO.getRevisionNo());
			itemParticularsVO.setUom(itemParticularsDTO.getUom());
			itemParticularsVO.setOrdQty(itemParticularsDTO.getOrdQty());
			itemParticularsVO.setFreeQty(itemParticularsDTO.getFreeQty());
			itemParticularsVO.setAvailableStockQty(itemParticularsDTO.getAvailableStockQty());
			requiredQty=itemParticularsDTO.getOrdQty().add(itemParticularsDTO.getFreeQty()).subtract(itemParticularsDTO.getAvailableStockQty());
			itemParticularsVO.setRequiredQty(requiredQty);
			itemParticularsVO.setWorkOrderVO(workOrderVO);
			itemParticularsVOs.add(itemParticularsVO);
		}
		workOrderVO.setItemParticularsVO(itemParticularsVOs);

		List<TermsAndConditionsVO> termsAndConditionsVOs = new ArrayList<>();
		for (TermsAndConditionsDTO termsAndConditionsDTO : workOrderDTO.getTermsAndConditionsDTO()) {
			TermsAndConditionsVO termsAndConditionsVO = new TermsAndConditionsVO();
			termsAndConditionsVO.setTemplate(termsAndConditionsDTO.getTemplate());
			termsAndConditionsVO.setDescription(termsAndConditionsDTO.getDescription());
			termsAndConditionsVO.setWorkOrderVO(workOrderVO);
			termsAndConditionsVOs.add(termsAndConditionsVO);
		}
		workOrderVO.setTermsAndConditionsVO(termsAndConditionsVOs);

	}

	@Override
	public List<WorkOrderVO> getAllWorkOrderByOrgId(Long orgId) {
		List<WorkOrderVO> workOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received WorkOrder  BY OrgId : {}", orgId);
			workOrderVO = workOrderRepo.getAllWorkOrderByOrgId(orgId);
		}
		return workOrderVO;
	}

	@Override
	public List<WorkOrderVO> getWorkOrderById(Long id) {
		List<WorkOrderVO> workOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  WorkOrder BY Id : {}", id);
			workOrderVO = workOrderRepo.getWorkOrderById(id);
		}
		return workOrderVO;
	}

	@Override
	public String getWorkOrderDocId(Long orgId) {
		String screenCode = "WOP";
		String result = workOrderRepo.getWorkOrderDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getQuotationNumber(Long orgId) {
		Set<Object[]> chType = workOrderRepo.getQuotationNumber(orgId);
		return getPQuotation(chType);
	}

	private List<Map<String, Object>> getPQuotation(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("quotationNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}



}
