package com.intproject.repositories;

import com.intproject.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRep extends JpaRepository<Rating, Long> {
}
