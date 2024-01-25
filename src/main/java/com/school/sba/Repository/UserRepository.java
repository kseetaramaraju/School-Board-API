package com.school.sba.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	public boolean existsByUserRole(UserRole userRole);
	
	public boolean existsByIsDeletedAndUserRole(boolean b,UserRole userRole);
	

	public Optional<User> findByUserName(String username);

}
