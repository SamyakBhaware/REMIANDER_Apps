//package com.LOGIN.login.page.Model;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<UserData, Long> {
//
//    Optional<UserData> findByEmail(String email);
//
//}


package com.LOGIN.login.page.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for UserData entities.
 * Spring Data JPA automatically provides CRUD operations (Create, Read, Update, Delete).
 */
@Repository // Though JpaRepository extends Repository, explicit @Repository is good practice
public interface UserRepository extends JpaRepository<UserData, Long> {

    /**
     * Finds a user by their email address.
     * Spring automatically generates the implementation for this method based on the name.
     * This is crucial for the login process (UserDetailsService).
     *
     * @param email the email address of the user to find.
     * @return an Optional containing the UserData if found, or empty otherwise.
     */
    Optional<UserData> findByEmail(String email);

 }

