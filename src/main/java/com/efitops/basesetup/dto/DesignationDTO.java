package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignationDTO {
	
	private Long id;
    private String docid;
    private String designation;
    private Long orgId;
    private String createdBy;
}
