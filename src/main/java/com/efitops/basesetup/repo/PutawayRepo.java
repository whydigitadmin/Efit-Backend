package com.efitops.basesetup.repo;

import java.util.List;

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

}
