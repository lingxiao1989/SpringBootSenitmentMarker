package com.java.springbootsenitmentmarker.dao;

import com.java.springbootsenitmentmarker.entity.Twitter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TwitterRepository extends JpaRepository<Twitter,Integer> {

    public List<Twitter> findBySentimentScoreIsNotNull();
    public List<Twitter> findByIdGreaterThan(int min);
}

