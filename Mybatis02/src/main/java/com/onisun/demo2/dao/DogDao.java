package com.onisun.demo2.dao;

import com.onisun.demo2.bean.Dog;

/**
 * @author Neo
 * @version 1.0
 */
public interface DogDao {

    Dog selectDogById(Integer id);
}
