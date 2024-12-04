package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemIssueToProductionVO;

@Repository
public interface ItemIssueToProductionRepo extends JpaRepository<ItemIssueToProductionVO, Long>{

	List<ItemIssueToProductionVO> findItemIssueToProductionById(Long id);

	List<ItemIssueToProductionVO> findItemIssueToProductionByOrgId(Long orgId);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getItemIssueToProductionDocId(Long orgId, String screenCode);


}
