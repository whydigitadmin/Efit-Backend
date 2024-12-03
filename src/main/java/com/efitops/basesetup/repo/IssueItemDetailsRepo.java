package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IssueItemDetailsVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;

@Repository
public interface IssueItemDetailsRepo extends JpaRepository<IssueItemDetailsVO, Long>{

	List<IssueItemDetailsVO> findByIssueToSubContractorVO(IssueToSubContractorVO issueToSubContractorVO);

}
