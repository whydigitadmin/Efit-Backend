package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemVO;
import com.efitops.basesetup.entity.MeasuringInstrumentsVO;

@Repository
public interface ItemRepo extends JpaRepository<ItemVO, Long>{

	@Query(nativeQuery = true, value = "select * from item where orgid=?1")
	List<ItemVO> findItemByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from item where itemid=?1")
	List<ItemVO> findItemById(Long id);



}
