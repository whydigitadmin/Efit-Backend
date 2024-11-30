package com.efitops.basesetup.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockLocationRepo extends JpaRepository<StockLocationVO, Long>{

	@Query(nativeQuery =true,value ="select * from m_stocklocation where orgid=?1")
	List<StockLocationVO> getStockLocationByOrgId(Long orgId);

	@Query(nativeQuery =true,value ="select * from m_stocklocation where stocklocationid=?1")
	Optional<StockLocationVO> getStockLocationById(Long id);

}


