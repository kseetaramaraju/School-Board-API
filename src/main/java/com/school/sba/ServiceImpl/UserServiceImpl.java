package com.school.sba.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Repository.UserRepository;
import com.school.sba.Service.UserService;
import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.AdminAllReadyExistsException;
import com.school.sba.exception.UserNotFoundById;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	//mapper methods
	private User mapToUser(UserRequest userRequest)
	{
		return User.builder()
				.userName(userRequest.getUserName())
				.password(userRequest.getPassword())
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.contactNo(userRequest.getContactNo())
				.email(userRequest.getEmail())
				.userRole(userRequest.getUserRole())
				.build();
	}

	private UserResponse mapToUserResponse(User user)
	{
		return UserResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.contactNo(user.getContactNo())
				.email(user.getEmail())
				.userRole(user.getUserRole())
				.build();
	}




	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResponseStructure<UserResponse> structure;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUserRegister(UserRequest userRequest) {


		if( userRequest.getUserRole().equals(UserRole.ADMIN) )
		{
			if( userRepository.existsByUserRole(userRequest.getUserRole())==false)
			{
				User user = userRepository.save(mapToUser(userRequest));
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setMessage("User Registration Done Successfully!1");
				structure.setData(mapToUserResponse(user));

				return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.CREATED);
			}
			else
			{
				throw new AdminAllReadyExistsException("Admin is Already Exist!!");
			}
		}
		else
		{
			User user = userRepository.save(mapToUser(userRequest));
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setMessage("User Registration Done Successfully!1");
			structure.setData(mapToUserResponse(user));

			return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.CREATED);
		}
		
	}

	
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) {
		
		User user=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundById("User Not Found!!"));
		user.setDeleted(true);
		
		userRepository.save(user);
		
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("User is Soft Deleted!");
		structure.setData(mapToUserResponse(user));
		
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.OK);	
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUser(@Valid int userId) {
		
		User user=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundById("User Not Found!!"));
		
		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("User Data is Fetched!");
		structure.setData(mapToUserResponse(user));
		
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.FOUND);
	}


}
