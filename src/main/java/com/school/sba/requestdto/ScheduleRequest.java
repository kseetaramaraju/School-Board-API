package com.school.sba.requestdto;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequest {
	
	private LocalTime opensAt;
	private LocalTime closesAt;
	private int classHoursPerDay;
	private int classHoursLengthInMin;
	private LocalTime breakTime;
	private int breakLengthInMin;
	private LocalTime lunchTime;
	private int lunchLengthInMin;


}
