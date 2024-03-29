package com.school.sba.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.school.sba.Repository.AcademicProgramRepository;
import com.school.sba.Repository.SubjectRepository;
import com.school.sba.Repository.UserRepository;
import com.school.sba.Service.UserService;
import com.school.sba.entity.School;
import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.AcademicProgramNotFoundException;
import com.school.sba.exception.AdminAllReadyExistsException;
import com.school.sba.exception.AdminCanNotBeAssignedToAcademicProgram;
import com.school.sba.exception.IsNotAdminException;
import com.school.sba.exception.OnlyTeacherCanBeAssignedToSubjectException;
import com.school.sba.exception.SubjectNotFoundByIdException;
import com.school.sba.exception.SubjectsWillNotAssignToStudent;
import com.school.sba.exception.UserNotFoundById;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.AdminResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.utility.ResponseEntityProxy;
import com.school.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResponseStructure<UserResponse> structure;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	private SubjectRepository subjectRepository;


	//mapper methods
	public User mapToUser(UserRequest userRequest)
	{
		return User.builder()
				.userName(userRequest.getUserName())
				.password( encoder.encode(userRequest.getPassword()))
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.contactNo(userRequest.getContactNo())
				.email(userRequest.getEmail())
				.userRole(userRequest.getUserRole())
				.build();
	}

	public UserResponse mapToUserResponse(User user)
	{
		List<String> listOfProgramName = new ArrayList();

		if( user.getAcademicPrograms() != null) {
			user.getAcademicPrograms().forEach(academicProgram -> {
				listOfProgramName.add(academicProgram.getProgramName());
			});
		}
		
		return UserResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.contactNo(user.getContactNo())
				.email(user.getEmail())
				.userRole(user.getUserRole())
				.isDeleted(user.isDeleted())
				.listOfAcademicPrograms(listOfProgramName)
				.subject(user.getSubject())
				.build();
	}

	public AdminResponse mapToAdminResponse(User user)
	{
		
		return AdminResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.contactNo(user.getContactNo())
				.email(user.getEmail())
				.userRole(user.getUserRole())
				.isDeleted(user.isDeleted())
				.school(user.getSchool())
				.build();
	}

  //For Converting List Of UserResponse
	
	public List<UserResponse> mapToListOfUserResponse(List<User> users )
	{
		List<UserResponse> listOfUserResponses=new ArrayList<>();
		
		users.forEach( user -> {
			UserResponse userresponse=mapToUserResponse(user);
			listOfUserResponses.add(userresponse);
		});
		
		return listOfUserResponses;
	}
	



	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> registerAdmin( UserRequest userRequest) {

	  ResponseStructure<AdminResponse> structure=new ResponseStructure<>();		
		
		if( userRequest.getUserRole().equals(UserRole.ADMIN) )
		{
			if( userRepository.existsByIsDeletedAndUserRole(false,userRequest.getUserRole()))
			{
				throw new AdminAllReadyExistsException("Admin Already Exists!!");
			}
			else
			{
				if(userRepository.existsByIsDeletedAndUserRole(true, userRequest.getUserRole())) {
					User user = mapToUser(userRequest);
					user=userRepository.save(user);

					structure.setStatus(HttpStatus.CREATED.value());
					structure.setMessage("Admin Registered successfully");
					structure.setData(mapToAdminResponse(user));

					return new ResponseEntity<ResponseStructure<AdminResponse>>(structure, HttpStatus.CREATED);
				}
				else {
					User user = mapToUser(userRequest);
					user.isDeleted();
					user=userRepository.save(user);

					structure.setStatus(HttpStatus.CREATED.value());
					structure.setMessage("Admin Registered successfully");
					structure.setData(mapToAdminResponse(user));

					return new ResponseEntity<ResponseStructure<AdminResponse>>(structure, HttpStatus.CREATED);	
				}
			}
		}
		else
		{
			throw new IsNotAdminException("User is Not Admin & Only Admin Can Register First!!");
		}

	}


	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest) {

		String name=SecurityContextHolder.getContext().getAuthentication().getName();

		if( userRequest.getUserRole().equals(UserRole.ADMIN))
		{
			throw new AdminAllReadyExistsException("Admin is Already Exist!!");
			
		}
		else
		{
			
		return userRepository.findByUserName(name).map(admin ->{
				
				School school=admin.getSchool();
				
				User user=mapToUser(userRequest);
				user.setSchool(school);
				
				user = userRepository.save(user);
				
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setMessage(user.getUserRole()+" "+user.getUserName()+"  Registration Done Successfully!!");
				structure.setData(mapToUserResponse(user));

				return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.CREATED);

				
			}).orElseThrow( ()-> new UserNotFoundById("Admin Not Found !!")   );
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
	public ResponseEntity<ResponseStructure<UserResponse>> findUser( int userId) {

		User user=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundById("User Not Found!!"));

		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("User Data is Fetched!");
		structure.setData(mapToUserResponse(user));

		return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(int userId, UserRequest userRequest) {

		User user = userRepository.findById(userId)
				.map( u -> {
					User mappedUser = mapToUser(userRequest);
					mappedUser.setUserId(userId);
					return userRepository.save(mappedUser);
				})
				.orElseThrow(() -> new UserNotFoundById("user not found"));

		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("user updated successfully");
		structure.setData(mapToUserResponse(user));

		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.OK);
	}



	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> assignUserToAcademicProgram(int programId, int userId) {

		return userRepository.findById(userId).map(user ->{

			if(user.getUserRole().equals(UserRole.ADMIN))
			{
				throw new AdminCanNotBeAssignedToAcademicProgram("Admin Can Not Be Assigned To Academic Program!!");
			}
			else
			{
				return academicProgramRepository.findById(programId).map(program ->{

					
					if(user.getUserRole().equals(UserRole.TEACHER))
					{
						if(program.getSubjects().contains(user.getSubject()))
						{
							user.getAcademicPrograms().add(program);
							program.getUsers().add(user);
							
							academicProgramRepository.save(program);
							userRepository.save(user);

							return ResponseEntityProxy.setResponseStructure(HttpStatus.OK,
									user.getUserRole().name().toLowerCase() + " assigned to academic program successfully",
									mapToUserResponse(user));
							
						}
						else
						{
							throw new SubjectNotFoundByIdException("Subjects Present In Academic Program Not Matching Subject Present In User!!");
						}
					}
					else
					{
						program.getUsers().add(user);		
						user.getAcademicPrograms().add(program);

						userRepository.save(user);
						academicProgramRepository.save(program);

						return ResponseEntityProxy.setResponseStructure(HttpStatus.OK,
								user.getUserRole().name().toLowerCase() + " assigned to academic program successfully",
								mapToUserResponse(user));	
					}
					
				})

						.orElseThrow(()-> new AcademicProgramNotFoundException("Academic Not Found Exception"));
			}

		})
				.orElseThrow(()-> new UserNotFoundById("User Not Found!!"));


	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTeacher(int subjectId, int userId) {

		return userRepository.findById(userId).map(user ->{

			if(user.getUserRole().equals(UserRole.TEACHER))
			{
				return subjectRepository.findById(subjectId)
						.map(subject -> {
							user.setSubject(subject);
							userRepository.save(user);

							return ResponseEntityProxy.setResponseStructure(HttpStatus.OK,
									"subject assigned to teacher successfully",
									mapToUserResponse(user));
						})


						.orElseThrow(()-> new SubjectNotFoundByIdException("Subject Not Found !!") );
			}
			else
			{
				throw new OnlyTeacherCanBeAssignedToSubjectException("only teacher can be assigned to the subject");
			}

		})

				.orElseThrow( ()-> new UserNotFoundById("User Not Found !!") );



	}


}
