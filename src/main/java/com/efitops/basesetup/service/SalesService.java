package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DeliveryChalanForFgDTO;
import com.efitops.basesetup.entity.DeliveryChalanForFgVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface SalesService {

	// DeliveryChalanForFg

	Map<String, Object> createUpdateDeliveryChalanForFg(DeliveryChalanForFgDTO deliveryChalanForFgDTO)
			throws ApplicationException;

	List<DeliveryChalanForFgVO> getAllDeliveryChalanForFgByOrgId(Long orgId);

	DeliveryChalanForFgVO getDeliveryChalanForFgById(Long id);

	String getDeliveryChalanForFgDocId(Long orgId);

}
