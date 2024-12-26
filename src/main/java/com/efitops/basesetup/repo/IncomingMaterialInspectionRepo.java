package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;

@Repository
public interface IncomingMaterialInspectionRepo extends JpaRepository<IncomingMaterialInspectionVO, Long> {

	@Query(nativeQuery = true, value = "select * from  incomingmaterialinspection where orgid=?1")
	List<IncomingMaterialInspectionVO> getAllIncomingMaterialInspectionByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from incomingmaterialinspection  where incomingmaterialinspectionid=?1")
	IncomingMaterialInspectionVO getIncomingMaterialInspectionById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getIncomingMaterialInspectionDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select  a.grnno,a.pono,a.suppliername,a.invdcno,b.acceptqty from grn a,grndetails b where a.orgid=?1 and a.grnno=?2 and a.active = 1 and \r\n"
			+ "		     a.cancel= 0 and a.grnid = b.grnid group by a.grnno,a.pono,a.suppliername,a.invdcno,b.acceptqty order by a.grnno")
	Set<Object[]> getGrnNoFromGrnScreen(Long orgId, String grnNo);

	@Query(nativeQuery = true, value = "select a1.itemcode,a1.itemdesc from grn a,grndetails a1 where a.orgid=?1 and \r\n"
			+ "                     a.grnno=?2 and a.grnid =  a1.grnid and a.active = 1 and a.cancel = 0  group by \r\n"
			+ "                     a1.itemcode,a1.itemdesc order by a1.itemcode")
	Set<Object[]> getItemNoFromGrn(Long orgId, String grnNo);
}
