package com.efitops.basesetup.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DepartmentVO;
@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentVO, Long> {

	@Query(nativeQuery = true,value="select * from department  where orgid=?1")
	List<DepartmentVO> getAllDepartmentByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from department where departid=?1")
	List<DepartmentVO> getDepartmentById(Long id);

	boolean existsByDepartmentNameAndOrgId(String departmentName, Long orgId);

	boolean existsByDepartmentCodeAndOrgId(String departmentCode, Long orgId);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getDepartmentDocId(Long orgId,String screenCode);
}