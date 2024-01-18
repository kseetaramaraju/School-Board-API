package com.school.sba.responsedto;

import java.time.LocalTime;

import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse {

	private int scheduleId;
	private LocalTime opensAt;
	private LocalTime closesAt;
	private int classHoursPerDay;
	private int classHoursLengthInMin;
	private LocalTime breakTime;
	private int breakLengthInMin;
	private LocalTime lunchTime;
	private int lunchLengthInMin;


}
