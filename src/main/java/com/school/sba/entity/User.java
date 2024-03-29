package com.school.sba.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.sba.enums.ProgramType;
import com.school.sba.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Column(unique = true)
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private long contactNo;
	
	@Column(unique = true)
	private String email;
	
	private boolean isDeleted;
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	@ManyToOne
	private School school;

	@ManyToMany
	private List<AcademicProgram> academicPrograms;
	
	@ManyToOne
	private Subject subject;

}
