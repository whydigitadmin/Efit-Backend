package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineMasterDTO {

    private Long id;
    private String department;
    private String type;
    private String machineNo;
    private String machineName;
    private boolean calibrationRequired;
    private String location;
    private String processNo;
    private String machineCategory;
    private Long section;
    private String model;
    private String serialNo;
    private String status;
    private String manufacturedBy;
    private String madeIn;
    private String purchasedFrom;
    private String modeOfPurchase;
    private String machineIncharge;
    private String machineUsedFor;
    private String pmCheckListNo;
    private String remarks;
    private boolean active;
    private Long orgId;
    private String createdBy;
    private String finYear;
    private String filePath;
    private String instrumentName;
	
    
    private List<MachineMasterDTO1> machineMasterDTO1;
    private List<MachineMasterDTO2> machineMasterDTO2;
    private List<MachineMasterDTO3> machineMasterDTO3;
    
    
}

