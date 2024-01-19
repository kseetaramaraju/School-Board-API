package com.school.sba.responsedto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.school.sba.entity.Subject;
import com.school.sba.enums.ProgramType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicProgramResponse {

	private int programId;
	private ProgramType programType;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	private List<Subject> subjects;
	
}
