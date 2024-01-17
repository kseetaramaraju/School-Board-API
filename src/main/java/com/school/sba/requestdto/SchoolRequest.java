package com.school.sba.requestdto;

import org.springframework.stereotype.Component;

import com.school.sba.responsedto.SchoolResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolRequest {
	
	
	private String schoolName;
	private long contactNo;
	private String emailId;
	private String address;
	

}
