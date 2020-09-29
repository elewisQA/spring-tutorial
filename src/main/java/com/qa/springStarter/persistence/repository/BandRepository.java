package com.qa.springStarter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.springStarter.persistence.domain.Band;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {

}
