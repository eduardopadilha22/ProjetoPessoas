package com.jdevs.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jdevs.domain.Person;
@Repository
@org.springframework.transaction.annotation.Transactional
public interface PersonRespository extends CrudRepository<Person, Long> {
	
	@Query("select p from Person p where p.name like %?1%")
	List<Person> findPersonByName(String name);
	
	@Query("select p from Person p where p.sexopessoa= ?1")
	List<Person> findPersonBySexoPessoa(String name);

    @Query("select p from Person p where p.name like %?1% and p.sexopessoa = ?2")
    List<Person> findPersonByNameSexo(String name,String sexopessoa);
}
