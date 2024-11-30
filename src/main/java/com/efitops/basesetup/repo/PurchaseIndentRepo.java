package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseIndentVO;

@Repository
public interface PurchaseIndentRepo extends JpaRepository<PurchaseIndentVO, Long>{

	@Query(nativeQuery =true,value ="SELECT * FROM t_purchaseindent where orgid=?1")
	List<PurchaseIndentVO> getAllPurchaseIndentByOrgId(Long orgId);
	
	@Query(nativeQuery =true,value ="SELECT * FROM t_purchaseindent where purchaseindentid=?1")
	Optional<PurchaseIndentVO> getPurchaseIndentById(Long id);

} 
