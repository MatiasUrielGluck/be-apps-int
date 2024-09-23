package com.uade.beappsint.repository;

import com.uade.beappsint.entity.AdminRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRequestRepository extends CrudRepository<AdminRequest, Integer> {
    List<AdminRequest> findByApprovedFalse();
}