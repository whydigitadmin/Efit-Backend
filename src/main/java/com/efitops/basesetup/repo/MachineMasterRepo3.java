package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MachineMasterVO3;

@Repository
public interface MachineMasterRepo3 extends JpaRepository<MachineMasterVO3, Long>{

	//List<MachineMasterVO3> findByMachineMasterVO(MachineMasterVO machineMasterVO);

}
