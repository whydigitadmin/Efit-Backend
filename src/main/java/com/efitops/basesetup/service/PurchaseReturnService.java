package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PurchaseInvoiceDTO;
import com.efitops.basesetup.dto.PurchaseReturnDTO;
import com.efitops.basesetup.entity.PurchaseInvoiceVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface PurchaseReturnService {

	// PurchaseReturn

	Map<String, Object> createUpdatePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO) throws ApplicationException;

	List<PurchaseReturnVO> getAllPurchaseReturnByOrgId(Long orgId);

	PurchaseReturnVO getPurchaseReturnById(Long id);

	String getPurchaseReturnDocId(Long orgId);

	// PurchaseReturn

	Map<String, Object> createUpdatePurchaseInvoice(PurchaseInvoiceDTO purchaseInvoiceDTO) throws ApplicationException;

	List<PurchaseInvoiceVO> getAllPurchaseInvoiceByOrgId(Long orgId);

	PurchaseInvoiceVO getPurchaseInvoiceById(Long id);

	String getPurchaseInvoiceDocId(Long orgId);

}
