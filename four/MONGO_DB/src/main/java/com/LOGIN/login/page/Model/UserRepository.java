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

 import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<UserData, Long> {

    Optional<UserData> findByEmail(String email);

 }

