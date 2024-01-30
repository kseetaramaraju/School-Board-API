package com.school.sba.responsedto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import com.school.sba.enums.ClassStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassHourResponse {
	
	private LocalTime beginsAt;
	private LocalTime endsAt;
	private DayOfWeek day;
	private LocalDate date;
	private int roomNo;
    private ClassStatus classStatus;


}
