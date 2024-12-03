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

	@Query(nativeQuery = true, value = " select a.docid from t_quotation a where a.orgid=?1 and a.customername=?2 and active=true group by  a.docid order by  a.docid")
	Set<Object[]> getQuotationNumber(Long orgId,String custmoerName);

	
@Query(nativeQuery = true,value="select a1.partcode,a1.partdescription,a1.unit,a1.qtyoffered from t_quotation a ,t_quotationdetails a1 where a.orgid=?1 and\r\n"
		+ " a.quotationid=a1.quotationid  and a.docid=?2 and  active=true group by a1.partcode,a1.partdescription,a1.unit,a1.qtyoffered order by a1.partcode")
Set<Object[]> getWorkOderPartNo(Long orgId,String docId);
}
