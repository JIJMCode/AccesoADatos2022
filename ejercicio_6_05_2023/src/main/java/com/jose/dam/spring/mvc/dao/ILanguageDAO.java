package com.jose.dam.spring.mvc.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jose.dam.spring.mvc.entity.Language;

@Repository
public interface ILanguageDAO extends CrudRepository<Language, Integer>{

}
