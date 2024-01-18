package com.school.sba.requestdto;

import java.time.LocalDate;

import com.school.sba.enums.ProgramType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicProgramRequest {
	
	
	private String programName;
	private ProgramType programType;
	private LocalDate beginsAt;
	private LocalDate endsAt;


}
