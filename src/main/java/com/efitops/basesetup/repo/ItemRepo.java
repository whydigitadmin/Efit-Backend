package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemVO;

@Repository
public interface ItemRepo extends JpaRepository<ItemVO, Long>{

	@Query(nativeQuery = true, value = "select * from m_item where orgid=?1")
	List<ItemVO> findItemByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from m_item where itemid=?1")
	List<ItemVO> findItemById(Long id);

	@Query(nativeQuery = true, value = "select uomcode from m_uommast where orgid=?1 and active=1 ")
	Set<Object[]> findPrimaryCodeFromUomMaster(Long orgId);

	@Query(nativeQuery = true, value = "select locationcode from m_stocklocation where orgid=? and active=1")
	Set<Object[]> findStockLocationForItemMaster(Long orgId);

	@Query(nativeQuery = true, value = "select gstslab from m_gst where orgid=?1 and active=1")
	Set<Object[]> findTaxSlabFromGst(Long orgId);

	@Query(nativeQuery = true, value = "select materialgroup from m_material where orgid=?1 and materialtype=?2 and active=1")
	Set<Object[]> findMaterialGroupFromMaterialType(Long orgId, String materialType);

	@Query(nativeQuery = true, value = "select materialsubgroup from m_material where orgid=?1 and materialtype=?2 and materialgroup=?3 and active=1")
	Set<Object[]> findMaterialSubGroupFromMaterialType(Long orgId, String materialType, String materialGroup);

	@Query(nativeQuery = true, value = "select materialtype from  m_material where orgid=?1 and active=1")
	Set<Object[]> findMaterialTypeForItemMaster(Long orgId);






}
