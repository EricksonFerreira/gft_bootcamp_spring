package br.com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.springboot.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{
    
}
