package com.apiwiz.apiwiz.Repository;

import com.apiwiz.apiwiz.Model.Subscribe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SubscirbeRepository extends MongoRepository<Subscribe,String> {
    ArrayList<Subscribe> findByDate(String date);
}
