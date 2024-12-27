package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ThirdPartyAttachmentVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;

@Repository
public interface ThirdPartyAttachmentRepo extends JpaRepository<ThirdPartyAttachmentVO, Long>
{

	List<ThirdPartyAttachmentVO> findByThirdPartyInspectionVO(ThirdPartyInspectionVO thirdPartyInspectionVO);

}
