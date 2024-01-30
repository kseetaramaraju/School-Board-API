package com.school.sba.responsedto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.school.sba.entity.School;
import com.school.sba.entity.Subject;
import com.school.sba.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponse {
	
	private int userId;
	private String userName;
	private String firstName;
	private String lastName;
	private long contactNo;
	private String email;
	private UserRole userRole;
	private Boolean isDeleted;
    private School school;

}
