package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FinalInspectionReportVO;

@Repository
public interface FinalInspectionReportRepo extends JpaRepository<FinalInspectionReportVO, Long> {

	@Query(nativeQuery = true, value = "select * from  finalinspectionreport where orgid=?1")
	List<FinalInspectionReportVO> getAllFinalInspectionReportByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from finalinspectionreport  where finalinspectionreportid=?1")
	FinalInspectionReportVO getFinalInspectionReportById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getFinalInspectionReportDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.fgpartname,a.fgpartdesc,b.primaryunit,a.customername,c.customerpono,a.invoice from routecardentry a,\r\n"
			+ "item b, workorder c where a.active = 1 and  c.customername=a.customername and c.orgid=a.orgid and b.itemname=a.fgpartname and b.orgid=a.orgid and \r\n"
			+ "a.orgid=?1 and a.fgpartname=?2 group by\r\n"
			+ " a.docid,a.fgpartname,a.fgpartdesc,b.primaryunit,a.customername,c.customerpono,a.invoice order by a.docid")
	Set<Object[]> getRouteCardNumberFromRouteCard(Long orgId, String fgPartName);

}
