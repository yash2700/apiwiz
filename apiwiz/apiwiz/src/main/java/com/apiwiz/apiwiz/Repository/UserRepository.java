package com.apiwiz.apiwiz.Repository;

import com.apiwiz.apiwiz.Model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,String> {

    UserEntity findByEmail(String emailId);

    boolean existsByEmail(String email);

}
