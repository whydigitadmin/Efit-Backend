package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DeliveryChalanForFgDTO;
import com.efitops.basesetup.dto.SalesInvoiceLocalDTO;
import com.efitops.basesetup.entity.DeliveryChalanForFgVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface SalesService {

	// DeliveryChalanForFg

	Map<String, Object> createUpdateDeliveryChalanForFg(DeliveryChalanForFgDTO deliveryChalanForFgDTO)
			throws ApplicationException;

	List<DeliveryChalanForFgVO> getAllDeliveryChalanForFgByOrgId(Long orgId);

	DeliveryChalanForFgVO getDeliveryChalanForFgById(Long id);

	String getDeliveryChalanForFgDocId(Long orgId);

	List<Map<String, Object>> getCustomerNameFromPartyMaster(Long orgId);

	List<Map<String, Object>> getSoNoFromSaleOrder(Long orgId, String customerName);

	List<Map<String, Object>> getItemNameFromSaleOrder(String customerName);

	// SalesInvoiceLocal

	Map<String, Object> createUpdateSalesInvoiceLocal(SalesInvoiceLocalDTO salesInvoiceLocalDTO)
			throws ApplicationException;

	List<SalesInvoiceLocalVO> getAllSalesInvoiceLocalByOrgId(Long orgId);

	SalesInvoiceLocalVO getSalesInvoiceLocalById(Long id);

	String getSalesInvoiceLocalDocId(Long orgId);

}
