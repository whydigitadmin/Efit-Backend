package com.efitops.basaesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDTO {

	private Long id;
	private String role;
	private LocalDate startdate;
	private LocalDate enddate;
}