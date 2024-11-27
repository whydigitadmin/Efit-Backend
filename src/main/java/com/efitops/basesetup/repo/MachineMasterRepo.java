package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MachineMasterVO;

@Repository
public interface MachineMasterRepo extends JpaRepository<MachineMasterVO, Long>{

	@Query(nativeQuery =true,value ="select * from machinemaster where orgid=?1")
	List<MachineMasterVO> getMachineMasterByOrgId(Long orgId);

	@Query(nativeQuery =true,value ="select * from machinemaster where machinemasterid=?1")
	Optional<MachineMasterVO> getMachineMasterById(Long id);

}