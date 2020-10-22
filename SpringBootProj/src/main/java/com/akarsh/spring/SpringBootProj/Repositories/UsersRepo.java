package com.akarsh.spring.SpringBootProj.Repositories;

import com.akarsh.spring.SpringBootProj.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepo extends JpaRepository<Users, Integer> {

}
