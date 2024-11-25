package com.efitops.basaesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeMappingDetailsDTO {
	
	private String screenCode;
	private String screenName;
	private String docCode;
	private String branch;
	private String branchCode;
	private String prefixField;
	private String finYear;
	private String finYearIdentifier;
}
