package com.school.sba.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.school.sba.enums.ClassStatus;
import com.school.sba.enums.ProgramType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class ClassHour {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int classHourId;
	
	private LocalDateTime beginsAt;
	private LocalDateTime endsAt;
	private int roomNo;
	
	@Enumerated(EnumType.STRING)
    private ClassStatus classStatus;
	
	@ManyToOne
    private AcademicProgram academicProgram;
	
    
    @ManyToOne
    private Subject subject;
    
    @ManyToOne
    private User user;
	
    

}
