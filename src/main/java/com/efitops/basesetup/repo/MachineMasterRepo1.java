package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineMasterVO1;

public interface MachineMasterRepo1 extends JpaRepository<MachineMasterVO1, Long>{

	List<MachineMasterVO1> findByMachineMasterVO(MachineMasterVO machineMasterVO);

}
