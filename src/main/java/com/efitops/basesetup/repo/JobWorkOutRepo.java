package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobWorkOutVO;

@Repository
public interface JobWorkOutRepo extends JpaRepository<JobWorkOutVO, Long>{

	@Query(nativeQuery = true, value = "select * from  t_jobworkout  where orgid=?1")
	List<JobWorkOutVO> getAllJobWorkOutByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_jobworkout where jobworkoutid=?1")
	List<JobWorkOutVO> getAllJobWorkOutById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getJobWorkOutDocId(Long orgId, String screenCode);

	
	@Query(nativeQuery = true, value ="SELECT customername,customeraddress,dcno,gstno,routecardno,scissueno,subcontractorid,subcontractorname,subcontractoraddress,vehicleno FROM efit_ops.t_dcforsubcontract\r\n"
			+ "where orgid=?1 and active=1 and cancel=0 ")
	Set<Object[]> findDcForSubContractDetails(Long orgId);
}
