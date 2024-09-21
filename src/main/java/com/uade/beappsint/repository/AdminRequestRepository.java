package com.uade.beappsint.repository;

import com.uade.beappsint.entity.AdminRequest;
import com.uade.beappsint.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRequestRepository extends CrudRepository<AdminRequest, Integer> {
    List<AdminRequest> findByStatusFalse();  // Encontrar solicitudes no aprobadas
    List<AdminRequest> findByCustomer(Customer customer);  // Encontrar las solicitudes de un cliente
}