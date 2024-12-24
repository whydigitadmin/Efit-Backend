package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SampleApprovalVO;

@Repository
public interface SampleApprovalRepo extends JpaRepository<SampleApprovalVO, Long>{

	@Query(nativeQuery = true, value = "select * from  settingapproval where orgid=?1")
	List<SampleApprovalVO> getAllSampleApprovalByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from  settingapproval where sampleapprovalid=?1")
	SampleApprovalVO getSampleApprovalById(Long id);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSampleApprovalDocId(Long orgId, String screenCode);

}
