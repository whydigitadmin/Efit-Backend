package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RouteCardEntryVO;

@Repository
public interface RouteCardEntryRepo extends JpaRepository<RouteCardEntryVO, Long>{

	@Query(nativeQuery = true, value = "select * from t_routecardentry where orgid=?1")
	List<RouteCardEntryVO> findRouteCardEntryByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_routecardentry where routecardentryid=?1")
	List<RouteCardEntryVO> findRouteCardEntryById(Long id);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getRouteCardEntryDocId(Long orgId, String screenCode);

}
