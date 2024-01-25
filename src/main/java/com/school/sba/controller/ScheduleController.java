package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Service.ScheduleService;
import com.school.sba.requestdto.ScheduleRequest;
import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.utility.ResponseStructure;


@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("/schools/{schoolId}/schedules")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(@RequestBody ScheduleRequest scheduleRequest,@PathVariable int schoolId )
	{
		
		return scheduleService.saveSchedule(scheduleRequest,schoolId);
	}
	
	@GetMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> findSchedule(@RequestBody @PathVariable int schoolId )
	{
		
		return scheduleService.findSchedule(schoolId);
	}
	
	@PutMapping("/schedules/{scheduleId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(@RequestBody ScheduleRequest scheduleRequest,@PathVariable int scheduleId )
	{
		
		return scheduleService.updateSchedule(scheduleRequest,scheduleId);
	}
	
	
}
