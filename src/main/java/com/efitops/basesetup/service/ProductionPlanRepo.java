package com.efitops.basesetup.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ProductionPlanVO;

public interface ProductionPlanRepo extends JpaRepository<ProductionPlanVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM productionplan WHERE id=?1")
	List<ProductionPlanVO> getAllProductionPlanByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM productionplan WHERE productionplanid=?1")
	List<ProductionPlanVO> getAllProductionPlanById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getProductionPlanDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT A.docid,B.workorderid,B.vapduedate,A.customername,D.partycode,D.partyname,A.fgqty FROM routecardentry A, workorder B,workorderdetails C,partymaster D WHERE A.wono=B.workorderid AND B.workorderid=c.workorderid AND A.fgpartname=D.partyname\r\n"
			+ " AND a.orgid=?1 GROUP BY A.docid,B.workorderid,B.vapduedate,A.customername,D.partycode,D.partyname,A.fgqty;")
	Set<Object[]> getRouteCardNo(Long orgId);

	@Query(nativeQuery = true, value = "SELECT A.itemname ,A.itemdesc FROM m_item A WHERE A.orgid=?1")
	Set<Object[]> getRawMaterialDetails(Long orgId);

	@Query(nativeQuery = true, value = "SELECT DISTINCT B.processname FROM itemwiseprocess A,itemwiseprocessdetails B WHERE A.itemwiseprocessid=B.itemwiseprocessid AND A.processtype=\"Prodution\" AND A.orgid=?1 AND A.item=?2;")
	Set<Object[]> getProcessName(Long orgId, String item);

	@Query(nativeQuery = true, value = "SELECT CAST(A.machineno AS CHAR(100)) as machineno, A.machinename FROM machinemaster A WHERE A.machinename NOT IN (SELECT C.machinename FROM productionplan B JOIN productionplandetails C ON B.productionplanid = C.productionplanid WHERE A.orgid=?1 AND (?2 BETWEEN C.fromdate AND C.todate) OR C.status = 'PENDING') GROUP BY A.machineno, A.machinename HAVING (?3 = 0)\r\n"
			+ " UNION\r\n"
			+ " SELECT CAST(B.machineno AS CHAR(100)) AS machineno, B.machinename FROM productionplan A, productionplandetails B WHERE A.productionplanid = B.productionplanid AND a.orgid=?1 AND A.docid = ?4 GROUP BY B.machineno, B.machinename HAVING ?3 > 0 ORDER BY machineno;")
	Set<Object[]> getMachineName(Long orgId, String fromDate, Long id, String docId);
}
