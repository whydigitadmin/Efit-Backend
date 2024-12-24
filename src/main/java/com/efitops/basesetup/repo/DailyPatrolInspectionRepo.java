package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DailyPatrolInspectionVO;

@Repository

public interface DailyPatrolInspectionRepo extends JpaRepository<DailyPatrolInspectionVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getDailyPatrolInspectionDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select * from dailypatrolinspection d where d.dailypatrolinspectionid=?1")
	Optional<DailyPatrolInspectionVO> getDailyPatrolInspectionById(Long id);

	@Query(nativeQuery = true, value = "select * from dailypatrolinspection d where d.orgid=?1")
	List<DailyPatrolInspectionVO> getAllDPI(Long orgId);

    @Query(nativeQuery = true,value="select r.docid,r.fgpartname,r.fgpartdesc From routecardentry r where cancel = 'F' and  orgid=?1 order by docid ")
	Set<Object[]> getRouteCardNo(Long orgId);

}
