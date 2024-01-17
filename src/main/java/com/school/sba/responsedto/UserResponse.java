package com.school.sba.responsedto;

import org.springframework.stereotype.Component;

import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class UserResponse {
	
	private int userId;
	private String userName;
	private String firstName;
	private String lastName;
	private long contactNo;
	private String email;
	private UserRole userRole;
	private Boolean isDeleted;


}
