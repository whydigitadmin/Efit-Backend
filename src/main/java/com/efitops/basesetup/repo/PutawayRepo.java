package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PutawayVO;

@Repository
public interface PutawayRepo extends JpaRepository<PutawayVO, Long>{

	@Query(nativeQuery = true, value = "select * from t_putaway where orgid=?1")
	List<PutawayVO> findPutawayByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_putaway where putawayid=?1")
	List<PutawayVO> findPutawayById(Long id);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getPutawayDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "SELECT DISTINCT a.grnno, a.grndate, a.suppliername, a.invdcno, c.invoiceno, c.vehicleno " +
		       "FROM t_grn a, t_grndetails b, t_gateinwardentry c, t_gateinwardentrydetails d " +
		       "WHERE a.grnid = b.grnid AND c.invoiceno = a.invdcno AND a.orgid = ?1 " +
		       "GROUP BY a.grnno, a.grndate, a.suppliername, a.invdcno, c.invoiceno, c.vehicleno " +
		       "ORDER BY a.grnno")
	Set<Object[]> findGrnDetailsForPutaway(Long orgId);

	@Query(nativeQuery = true, value = "select  locationcode from m_stocklocation where  orgid=?1 and  cancel='F'")
	Set<Object[]> findLocationCodeForPutaway(Long orgId);

	@Query(nativeQuery = true, value = "select c.itemname,c.itemdesc,b.primaryunit,b.recievedqty,ROW_NUMBER() OVER (ORDER BY c.itemname) AS id  from m_item c ,t_grndetails b,t_grn a\r\n"
			+ "where a.orgid=?1 and a.grnno =?2  \r\n"
			+ "and a.grnid = b.grnid")
	Set<Object[]> findFillGridForPutaway(Long orgId, String grnNo);

}
