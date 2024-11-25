package com.efitops.basaesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basaesetup.entity.ListOfValuesVO;
@Repository
public interface ListOfValuesRepo extends JpaRepository<ListOfValuesVO, Long> {

	@Query(nativeQuery = true, value = "select * from listofvalues where listofvaluesid=?1")
	List<ListOfValuesVO> getListOfValuesById(Long id);
	
	@Query(nativeQuery = true, value = "select * from listofvalues where orgid=?1")
	List<ListOfValuesVO> getListOfValuesByOrgId(Long orgid);

	boolean existsByListCodeAndOrgId(String listCode, Long orgId);

	boolean existsByListDescriptionAndOrgId(String listDescription, Long orgId);

	@Query(nativeQuery = true,value = "select b.valuedescription from listofvalues a, listofvalues1 b where a.orgid=?1 and a.active=1  group by b.valuedescription")
	Set<Object[]> getChargeType(Long orgId);


        
}
