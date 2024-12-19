package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SettingApprovalVO;

@Repository
public interface SettingApprovalRepo extends JpaRepository<SettingApprovalVO, Long>{

	@Query(nativeQuery = true, value = "select * from  t_settingapproval where orgid=?1")
	List<SettingApprovalVO> getAllSettingApprovalByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from  t_settingapproval where settingapprovalid=?1")
	SettingApprovalVO getSettingApprovalById(Long id);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getSettingApprovalDocId(Long orgId, String screenCode);

}
