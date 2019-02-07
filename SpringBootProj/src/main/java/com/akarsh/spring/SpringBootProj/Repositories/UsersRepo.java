package com.akarsh.spring.SpringBootProj.Repositories;

import com.akarsh.spring.SpringBootProj.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Integer> {

}
