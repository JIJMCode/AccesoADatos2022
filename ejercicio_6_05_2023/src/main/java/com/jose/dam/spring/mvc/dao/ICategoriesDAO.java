package com.jose.dam.spring.mvc.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jose.dam.spring.mvc.entity.Categories;

@Repository
public interface ICategoriesDAO extends CrudRepository<Categories, Integer> {

}
