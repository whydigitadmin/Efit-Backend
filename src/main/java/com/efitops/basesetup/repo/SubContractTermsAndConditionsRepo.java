package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractInvoiceVO;
import com.efitops.basesetup.entity.SubContractTermsAndConditionsVO;
@Repository
public interface SubContractTermsAndConditionsRepo extends JpaRepository<SubContractTermsAndConditionsVO, Long> {

	List<SubContractTermsAndConditionsVO> findBySubContractInvoiceVO(SubContractInvoiceVO subContractInvoiceVO);

}
