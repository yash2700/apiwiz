package com.apiwiz.apiwiz.Repository;

import com.apiwiz.apiwiz.Model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}
