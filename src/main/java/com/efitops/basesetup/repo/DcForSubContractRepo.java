package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DcForSubContractVO;

@Repository
public interface DcForSubContractRepo extends JpaRepository<DcForSubContractVO, Long> 
{

	@Query(nativeQuery = true,value="select*from dcforsubcontract where orgid=?1")
	List<DcForSubContractVO> findDcforSCByOrgId(Long orgId);

	@Query(nativeQuery = true,value="select*from dcforsubcontract where grnid=?1")
	List<DcForSubContractVO> getDcforSCById(Long id);
	

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getdcForSubcontractDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value ="SELECT a.docid,a.docdate,a.routecardno,a.customername,b.gstin,b.currency FROM efit_ops.issuetosubcontractor a,efit_ops.partymaster b "
			+ "where a.customername =b.partyname and a.orgid=?1  and active=1 and cancel = 0")
	Set<Object[]> findIssueSCNoDetails(Long orgId);

	@Query(nativeQuery = true, value =("SELECT \r\n"
			+ "    CONCAT(\r\n"
			+ "        COALESCE(a.addressline1, ''), ', ', \r\n"
			+ "        COALESCE(a.addressline2, ''), ', ', \r\n"
			+ "        COALESCE(a.addressline3, ''), ', ', \r\n"
			+ "        COALESCE(a.city, ''), ', ', \r\n"
			+ "        COALESCE(a.pincode, ''), ', ', \r\n"
			+ "        COALESCE(a.state, '')\r\n"
			+ "    ) AS full_address\r\n"
			+ "FROM  efit_ops.partyaddress JOIN efit_ops.partymaster b  ON a.partymasterid = b.partymasterid WHERE b.cancel = 0  AND b.active = 1  AND a.orgid = ?1 \r\n"
			+ "    AND b.partyname = ?2"))
	Set<Object[]> findAddressDetails(Long orgId, String customerName);

	
	
	

	

}
