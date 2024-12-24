package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GateOutwardEntryVO;

@Repository
public interface GateOutwardEntryRepo extends JpaRepository<GateOutwardEntryVO, Long>{

	@Query(nativeQuery = true, value = "select * from gateoutwardentry where orgid=?1")
	List<GateOutwardEntryVO> findGateOutwardEntryByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from gateoutwardentry where gateoutwardentryid=?1")
	List<GateOutwardEntryVO> findGateOutwardEntryById(Long id);

}
