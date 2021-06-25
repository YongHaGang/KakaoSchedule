package com.yongha.toy1.repository;

import com.yongha.toy1.entity.Test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

}