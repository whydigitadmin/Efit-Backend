package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BrsExcelUploadVO;
@Repository
public interface BrsExcelUploadRepo extends JpaRepository<BrsExcelUploadVO, Long>{

	@Query(nativeQuery = true,value="select * from brsexcelupload where orgid=?1")
	List<BrsExcelUploadVO> findBrsExcelByOrgId(Long orgId);

}
