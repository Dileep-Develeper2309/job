package com.jobportal.repository;

import com.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecuriterProfileRepository extends JpaRepository<RecruiterProfile,Integer> {
}
