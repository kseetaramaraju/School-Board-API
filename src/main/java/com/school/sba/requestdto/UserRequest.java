package com.school.sba.requestdto;

import org.springframework.stereotype.Component;

import com.school.sba.entity.School;
import com.school.sba.enums.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class UserRequest {

	@NotEmpty(message = "UserName Can not Be Blank Or Null")
	@Pattern(regexp = "^[A-Z][a-z]* [A-Z][a-z]*$",message = "All Username's word's should start will Capital Letters")
	private String userName;

	@NotEmpty(message = "UserPassword Can not Be Blank Or Null")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
			+ " contain at least one letter, one number, one special character")
	private String password;

	@NotEmpty(message = "User FirstName Can not Be Blank Or Null")
	private String firstName;

	@NotEmpty(message = "User LastName Can not Be Blank Or Null")
	private String lastName;

	@Min(value = 6000000000l, message = " phone number must be valid")
	@Max(value = 9999999999l, message = " phone number must be valid")
	@Schema(required = true)
	private long contactNo;

	@NotEmpty(message = "userEmail Can not Be Blank Or Null")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String email;


	private UserRole userRole;


}
