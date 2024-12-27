package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RecieveFromSubcontractVO;

@Repository
public interface RecieveFromSubcontractRepo extends JpaRepository<RecieveFromSubcontractVO, Long> {

	@Query(nativeQuery = true, value = "select*from recievefromsubcontract where orgid=?1")
	List<RecieveFromSubcontractVO> findRecieveFromSubcontractByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select*from recievefromsubcontract where recievefromsubcontractid=?1")
	List<RecieveFromSubcontractVO> getRecieveFromSubcontractById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getRecieveFromSubContractDocId(Long orgId, String screenCode);

}
