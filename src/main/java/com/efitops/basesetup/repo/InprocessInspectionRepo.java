package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InprocessInspectionVO;

@Repository
public interface InprocessInspectionRepo extends JpaRepository<InprocessInspectionVO, Long> {

	@Query(nativeQuery = true, value = "select * from  inprocessinspection where orgid=?1")
	List<InprocessInspectionVO> getAllInprocessInspectionByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from inprocessinspection  where inprocessinspectionid=?1")
	InprocessInspectionVO getInprocessInspectionById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getInprocessInspectionDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.wono,a.fgpartname,fgpartdesc,a.customername from routecardentry a where \r\n"
			+ "a.orgid=?1 and a.active = true group by a.docid,a.wono,a.fgpartname,fgpartdesc,a.customername order by a.docid")
	Set<Object[]> getDocIdFromRouteCardNumber(Long orgId);

	@Query(nativeQuery = true, value = "select a.drawingno from drawingmaster a where a.orgid=?1 and a.fgpartno=?2 and \r\n"
			+ " a.active = 1 and a.cancel = 0 order by a.drawingno")
	Set<Object[]> getDrawingNumberFromDrawingMaster(Long orgId, String fgPartno);

	@Query(nativeQuery = true, value = "select employee from employee where orgid=?1 and active = 1 order by employee")
	Set<Object[]> getEmployeeNameFromEmployeeMaster(Long orgId);

}
