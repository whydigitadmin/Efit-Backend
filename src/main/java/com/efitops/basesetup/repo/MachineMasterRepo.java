package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MachineMasterVO;

@Repository
public interface MachineMasterRepo extends JpaRepository<MachineMasterVO, Long>{

	@Query(nativeQuery =true,value ="select * from m_machinemaster where orgid=?1")
	List<MachineMasterVO> getMachineMasterByOrgId(Long orgId);

	@Query(nativeQuery =true,value ="select * from m_machinemaster where machinemasterid=?1")
	Optional<MachineMasterVO> getMachineMasterById(Long id);

	@Query(nativeQuery = true, 	value ="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getMachineMasterByDocId(Long orgId, String screenCode);

	
    @Query(nativeQuery =true,value ="SELECT * FROM m_machinemaster where orgid=?1 and docid=?2")
	MachineMasterVO findALLMachineMasterByDocId(Long orgId, String docId);

    @Query(nativeQuery =true,value ="select m.machineno,m.machinename from m_machinemaster m where m.orgid=?1 order by m.machineno")
	Set<Object[]> getMachineDetail1(Long orgId);

	@Query(nativeQuery =true,value ="select s.shiftmastid,s.shiftname,s.timing from m_shiftmast s")
	Set<Object[]> getShiftDetails1();

	@Query(nativeQuery =true,value ="select s.shiftmastid,s.shiftname,s.timing from m_shiftmast s")
	Set<Object[]> getJobOrderNo1();

}