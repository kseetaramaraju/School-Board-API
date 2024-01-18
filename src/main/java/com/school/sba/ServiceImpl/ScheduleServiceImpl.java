package com.school.sba.ServiceImpl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Repository.ScheduleRepository;
import com.school.sba.Repository.SchoolRepository;
import com.school.sba.Service.ScheduleService;
import com.school.sba.entity.Schedule;
import com.school.sba.exception.ScheduleExistException;
import com.school.sba.exception.SchoolNotFoundException;
import com.school.sba.requestdto.ScheduleRequest;
import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.utility.ResponseStructure;


@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private SchoolRepository schoolRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ResponseStructure<ScheduleResponse> responseStructure;
	
	private Duration convertToDuration(int data)
	{
		return Duration.ofMinutes(data); 
	}
	
	private int convertToInteger(Duration data)
	{
		return (int)data.toMinutes(); 
	}
	
	
	//mapping methods
	private Schedule mapToSchedule(ScheduleRequest scheduleRequest) {

		return Schedule.builder()
				.opensAt(scheduleRequest.getOpensAt())
				.closesAt(scheduleRequest.getClosesAt())
				.classHoursPerDay(scheduleRequest.getClassHoursPerDay())
				.classHoursLengthInMin(convertToDuration(scheduleRequest.getClassHoursLengthInMin()))
				.breakTime(scheduleRequest.getBreakTime())
				.breakLengthInMin(convertToDuration(scheduleRequest.getBreakLengthInMin()))
				.lunchTime(scheduleRequest.getLunchTime())
				.lunchLengthInMin(convertToDuration(scheduleRequest.getLunchLengthInMin()))
				.build();

	}

	private ScheduleResponse mapToScheduleResponse(Schedule schedule) {
 
		
		return ScheduleResponse.builder()
				.scheduleId(schedule.getScheduleId())
				.opensAt(schedule.getOpensAt())
				.closesAt(schedule.getClosesAt())
				.classHoursPerDay(schedule.getClassHoursPerDay())
				.classHoursLengthInMin( convertToInteger(schedule.getClassHoursLengthInMin()))
				.breakTime(schedule.getBreakTime())
				.breakLengthInMin(convertToInteger(schedule.getBreakLengthInMin()))
				.lunchTime(schedule.getLunchTime())
				.lunchLengthInMin(convertToInteger(schedule.getLunchLengthInMin()))
				.build();

	}





	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(ScheduleRequest scheduleRequest, int schoolId){

		System.out.println(scheduleRequest);
		
		return schoolRepository.findById(schoolId).map( school ->
		{
			if(school.getSchedule()==null)
			{
				System.out.println(scheduleRequest.getOpensAt());
				Schedule schedule = scheduleRepository.save(mapToSchedule(scheduleRequest));
				
				System.out.println(schedule);
				
				school.setSchedule(schedule);
				schoolRepository.save(school);
				
				responseStructure.setStatus(HttpStatus.CREATED.value());
				responseStructure.setMessage("Schedule Saved Successfully!!");
				responseStructure.setData(mapToScheduleResponse(schedule));
				
				return new ResponseEntity<ResponseStructure<ScheduleResponse>>(responseStructure,HttpStatus.CREATED);
			}
			else
			{
              throw new ScheduleExistException("Schedule Already Present!!");
			}
		}

		)			
	   .orElseThrow( ()-> new SchoolNotFoundException("School Not Found")   );

	}



}
