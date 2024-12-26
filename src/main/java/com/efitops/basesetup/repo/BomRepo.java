package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BomVO;

@Repository
public interface BomRepo extends JpaRepository<BomVO, Long>{
	
	@Query(nativeQuery = true,value="select * from  bom where orgid=?1")
	List<BomVO> getAllBomByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value="select * from bom where bomid=?1")
	List<BomVO> getBomById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getBomDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "SELECT itemname,itemdesc,primaryunit FROM efit_ops.item where orgid=?1 and itemtype=?2 and cancel =0 and active = 1")
	Set<Object[]> findFGSFGPartDetails(Long orgId,String productType);

	@Query(nativeQuery = true, value = "SELECT itemname,itemdesc,primaryunit,itemtype FROM efit_ops.item where orgid=?1 and itemtype in ('Raw Material','SFG')and cancel =0 and active = 1")
	Set<Object[]> findSFGItemDetails(Long orgId);

}
