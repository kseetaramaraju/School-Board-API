package com.school.sba.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassHourRequest {

	private int classHourId;
	private int roomNo;
	private int teacherId;
	private int subjectId;


}
