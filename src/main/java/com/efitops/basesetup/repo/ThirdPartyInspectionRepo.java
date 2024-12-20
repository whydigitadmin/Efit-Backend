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
		    value = "SELECT partyname,  SELECT CONCAT(\r\n"
			+ "        COALESCE(a.addressline1, ''), ', ',\r\n"
			+ "        COALESCE(a.addressline2, ''), ', ',\r\n"
			+ "        COALESCE(a.addressline3, ''), ', ',\r\n"
			+ "        COALESCE(a.city, ''), ', ',\r\n"
			+ "        COALESCE(a.pincode, ''), ', ',\r\n"
			+ "        COALESCE(a.state, '')\r\n"
			+ "    ) AS full_address,\r\n"
			+ "    a.stategstin,\r\n"
			+ "    a.taxtype,\r\n"
			+ "    a.state,\r\n"
			+ "    a.pincode,\r\n"
			+ "    a.city\r\n"
			+ "FROM \r\n"
			+ "    efit_ops.partyaddress a JOIN efit_ops.partymaster b  ON a.partymasterid = b.partymasterid\r\n"
			+ "    WHERE b.cancel = 0  AND b.active = 1  and partyname = ?2  and  UPPER(addresstype) = 'BILLING' and b.orgid =?1 and partytype = 'THIRD PARTY')")
		Set<Object[]> findgetThirdPartyDetailsForThirdPartyInsp(Long orgId);


	

	

}
