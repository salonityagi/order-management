package com.sl.ms.ordermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sl.ms.ordermanagement.entity.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer>{

}
