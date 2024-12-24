package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DeliveryChalanForFgDTO;
import com.efitops.basesetup.dto.DeliveryChallanForFgDetailsDTO;
import com.efitops.basesetup.entity.DeliveryChalanForFgVO;
import com.efitops.basesetup.entity.DeliveryChallanForFgDetailsVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DeliveryChalanForFgRepo;
import com.efitops.basesetup.repo.DeliveryChallanForFgDetailsRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;

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
		deliveryChalanForFgVO.setStatus(deliveryChalanForFgDTO.getStatus());

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

}
