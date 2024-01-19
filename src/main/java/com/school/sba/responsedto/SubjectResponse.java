package com.school.sba.responsedto;

import java.util.List;

import com.school.sba.requestdto.SubjectRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SubjectResponse {
	
	private int subjectId;
	
    private List<String> subjectNames;
	

}
