package com.usermanager;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Severin Müller
 * Repository für das Laden von User-Objekten. JpaRepository bietet unter anderem Methoden zur Paginierung.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	User findByUserId(int userId);
	Page<User> findByEmailContaining(String email,Pageable pa);	
	}
