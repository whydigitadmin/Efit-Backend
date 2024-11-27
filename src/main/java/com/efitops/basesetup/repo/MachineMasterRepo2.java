package com.efitops.basesetup.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineMasterVO2;

public interface MachineMasterRepo2 extends JpaRepository<MachineMasterVO2, Long>{

	List<MachineMasterVO2> findByMachineMasterVO(MachineMasterVO machineMasterVO);

}
