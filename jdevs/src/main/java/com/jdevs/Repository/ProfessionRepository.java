package com.jdevs.Repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jdevs.domain.Profession;

@Repository
@org.springframework.transaction.annotation.Transactional
public interface ProfessionRepository extends CrudRepository<Profession, Long>{

}
