package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChalanForFgVO;

@Repository
public interface DeliveryChalanForFgRepo extends JpaRepository<DeliveryChalanForFgVO, Long> {

	@Query(nativeQuery = true, value = "select * from  deliverychalanforfg where orgid=?1")
	List<DeliveryChalanForFgVO> getAllDeliveryChalanForFgByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from deliverychalanforfg  where deliverychalanforfgid=?1")
	DeliveryChalanForFgVO getDeliveryChalanForFgById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getDeliveryChalanForFgDocId(Long orgId, String screenCode);

}
