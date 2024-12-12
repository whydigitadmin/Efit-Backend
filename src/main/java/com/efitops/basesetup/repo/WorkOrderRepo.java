package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.WorkOrderVO;

@Repository
public interface WorkOrderRepo extends JpaRepository<WorkOrderVO, Long> {

	@Query(nativeQuery = true, value = "select * from t_workorder  where  orgid=?1")
	List<WorkOrderVO> getAllWorkOrderByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_workorder  where  workorderid=?1")
	List<WorkOrderVO> getWorkOrderById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getWorkOrderDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid from t_quotation a where a.orgid=?1 and\r\n"
			+ "     a.customerid=?2 and active=true group by  a.docid order by  a.docid")
	Set<Object[]> getQuotationNumber(Long orgId,String custmoerId);

	
@Query(nativeQuery = true,value="select a.partcode,a.partdescription,a.drawingno,a.revisionno,a.unit,a.qtyoffered,a1.productionmanager,a1.customername,a1.customerid from t_quotationdetails a,t_quotation a1 where a1.quotationid=a.quotationid and \r\n"
		+ "		a1.orgid=?1 and a1.docid=?2 and a1.customerid=?3 and  active =1 group by \r\n"
		+ "	 a.partcode,a.partdescription,a.drawingno,a.revisionno,a.unit,a.qtyoffered,a1.productionmanager,a1.customername,a1.customerid  order by  a.partcode")
Set<Object[]> getWorkOrderPartNo(Long orgId,String docId,String custmoerId);
}
