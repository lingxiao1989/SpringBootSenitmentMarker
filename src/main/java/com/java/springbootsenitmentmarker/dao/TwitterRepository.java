package com.java.springbootsenitmentmarker.dao;

import com.java.springbootsenitmentmarker.entity.twitter_info;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TwitterRepository extends JpaRepository<twitter_info,Integer> {
    public Page<twitter_info> findBySupportLevelIsNull(Pageable pageable);
    public Page<twitter_info> findByAutoIdGreaterThan(int autoId, Pageable pageable);
}

