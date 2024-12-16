package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;

@Repository
public interface ThirdPartyInspectionRepo extends JpaRepository<ThirdPartyInspectionVO, Long> 
{
	@Query(nativeQuery = true, value = "select * from  t_thirdPartyInspection  where orgid=?1")
	List<ThirdPartyInspectionVO> findThirdPartyInspectionOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from t_thirdPartyInspection where thirdPartyInspectionid=?1")
	List<ThirdPartyInspectionVO> getThirdPartyInspectionById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getThirdPartyInspectionDocId(Long orgId, String screenCode);

	
	@Query(nativeQuery = true, value ="SELECT grnno,inwardno,pono,customer,suppliername FROM efit_ops.t_grn where cancel=0 and orgid=?1")
	Set<Object[]> findGRNForThirdPartyInspDetails(Long orgId);

	@Query(
		    nativeQuery = true,
		    value = "SELECT partyname, " +
		            "CONCAT(" +
		            "    COALESCE(a.addressline1, ''), ', ', " +
		            "    COALESCE(a.addressline2, ''), ', ', " +
		            "    COALESCE(a.addressline3, ''), ', ', " +
		            "    COALESCE(a.city, ''), ', ', " +
		            "    COALESCE(a.pincode, ''), ', ', " +
		            "    COALESCE(a.state, '')" +
		            ") AS fulladdress " +
		            "FROM efit_ops.partymaster b " +
		            "JOIN efit_ops.partyaddress a " +
		            "ON a.partymasterid = b.partymasterid " +
		            "WHERE UPPER(addresstype) = 'BILLING' " +
		            "AND orgid = ?1" +
		            "and b.partytype='THIRD PARTY'"
		)
		Set<Object[]> findgetThirdPartyDetailsForThirdPartyInsp(Long orgId);


	

	

}
