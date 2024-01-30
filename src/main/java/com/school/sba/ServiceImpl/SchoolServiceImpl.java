package com.school.sba.ServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.school.sba.Repository.SchoolRepository;
import com.school.sba.Repository.UserRepository;
import com.school.sba.Service.SchoolService;
import com.school.sba.entity.School;
import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.IsNotAdminException;
import com.school.sba.exception.SchoolExistException;
import com.school.sba.exception.SchoolNotFoundException;
import com.school.sba.exception.UserNotFoundById;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.utility.ResponseStructure;

@Service
public class SchoolServiceImpl implements SchoolService{

	@Autowired
	public SchoolRepository schoolRepository;

	@Autowired
	public UserRepository userRepository;


	@Autowired
	public ResponseStructure<SchoolResponse> responseStructure;


	//mapping Methods

	//Mapping SchoolRequestObject to School Object

	public School mapToSchool(SchoolRequest schoolRequest)
	{
		return School.builder()
				.schoolName(schoolRequest.getSchoolName())
				.contactNo(schoolRequest.getContactNo())
				.emailId(schoolRequest.getEmailId())
				.address(schoolRequest.getAddress())
				.build();
	}

	public SchoolResponse mapToSchoolResponse(School school)
	{
		return SchoolResponse.builder()
				.schoolId(school.getSchoolId())
				.schoolName(school.getSchoolName())
				.contactNo(school.getContactNo())
				.emailId(school.getEmailId())
				.address(school.getAddress())
				.weekOffDay(school.getWeekOffDay())
				.build();
	}






	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(SchoolRequest schoolRequest) {
		String name=SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByUserName(name).map(user ->{

			if(user.getUserRole().equals(UserRole.ADMIN))
			{
				if(user.getSchool()==null)
				{
					School school = schoolRepository.save(mapToSchool(schoolRequest));

					userRepository.findAll().forEach( u ->{
						u.setSchool(school);
						schoolRepository.save(school);
					});
					
					userRepository.save(user);
					
					responseStructure.setStatus(HttpStatus.CREATED.value());
					responseStructure.setMessage("School is created!!");
					responseStructure.setData(mapToSchoolResponse(school));

					return new ResponseEntity<ResponseStructure<SchoolResponse>>(responseStructure,HttpStatus.CREATED);
				}
				else
				{
					throw new SchoolExistException("School is Already Present!!");	
				}
			}
			else
			{
				throw new IsNotAdminException("The UserRole is Not Admin!!");	
			}

		}) .orElseThrow( ()->  new UserNotFoundById("User Not Found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(SchoolRequest schoolRequest, int schoolId) {

		return schoolRepository.findById(schoolId).map(school ->{

			school=mapToSchool(schoolRequest);
			school.setSchoolId(schoolId);

			school=schoolRepository.save(school);

			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("School Updated Sccessfully!!");
			responseStructure.setData(mapToSchoolResponse(school));

			return new ResponseEntity<ResponseStructure<SchoolResponse>>(responseStructure,HttpStatus.OK);

		})

				.orElseThrow( ()-> new SchoolNotFoundException("School Not Found!!"));


	}



	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchool(int schoolId) {

		return  schoolRepository.findById(schoolId).map(school ->{


			responseStructure.setStatus(HttpStatus.FOUND.value());
			responseStructure.setMessage("School data found in database");
			responseStructure.setData(mapToSchoolResponse(school));

			return new ResponseEntity<ResponseStructure<SchoolResponse>>(responseStructure, HttpStatus.FOUND);

		})
				.orElseThrow(() -> new SchoolNotFoundException("School not present in DB"));

	}

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(int schoolId) {

		return  schoolRepository.findById(schoolId).map(school ->{

			schoolRepository.deleteById(school.getSchoolId());
     
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("School Deleted in database");
			responseStructure.setData(mapToSchoolResponse(school));

			return new ResponseEntity<ResponseStructure<SchoolResponse>>(responseStructure, HttpStatus.OK);

		})
				.orElseThrow(() -> new SchoolNotFoundException("School not present in DB"));
	}








}
