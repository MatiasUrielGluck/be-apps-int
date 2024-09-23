package com.uade.beappsint.repository;

import com.uade.beappsint.entity.AdminRequest;
import com.uade.beappsint.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRequestRepository extends CrudRepository<AdminRequest, Integer> {
    Optional<AdminRequest> findById(Integer id);
    List<AdminRequest> findByStatusFalse();
    List<AdminRequest> findByCustomer(Customer customer);
}