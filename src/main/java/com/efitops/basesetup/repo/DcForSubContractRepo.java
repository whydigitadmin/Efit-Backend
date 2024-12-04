package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DcForSubContractDetailsVO;
import com.efitops.basesetup.entity.DcForSubContractVO;

@Repository
public interface DcForSubContractRepo extends JpaRepository<DcForSubContractVO, Long> 
{

	@Query(nativeQuery = true,value="select*from t_dcforsubcontract where orgid=?1")
	List<DcForSubContractVO> findDcforSCByOrgId(Long orgId);

	@Query(nativeQuery = true,value="select*from t_dcforsubcontract where grnid=?1")
	List<DcForSubContractVO> getDcforSCById(Long id);
	

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getdcForSubcontractDocId(Long orgId, String screenCode);

	

}
