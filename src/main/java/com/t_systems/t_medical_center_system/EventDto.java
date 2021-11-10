package com.t_systems.t_medical_center_system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto implements Serializable {
    private String date;
    private String time;
    private String patientName;
    private String patientSurname;
    private String status;
    private String therapyType;
    private String reasonToCancel;
}
