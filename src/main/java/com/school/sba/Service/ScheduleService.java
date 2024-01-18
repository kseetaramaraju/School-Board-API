package com.school.sba.Service;

import org.springframework.http.ResponseEntity;

import com.school.sba.entity.Schedule;
import com.school.sba.requestdto.ScheduleRequest;
import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface ScheduleService {

	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(ScheduleRequest scheduleRequest,  int schoolId);

	public ResponseEntity<ResponseStructure<ScheduleResponse>> findSchedule(int schoolId);

}
