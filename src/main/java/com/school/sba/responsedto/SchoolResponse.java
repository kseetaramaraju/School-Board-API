package com.school.sba.responsedto;

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
public class SchoolResponse {
	
	private int schoolId;
	private String schoolName;
	private long contactNo;
	private String emailId;
	private String address;
	

}
