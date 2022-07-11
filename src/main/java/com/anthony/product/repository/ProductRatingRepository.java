package com.anthony.product.repository;

import com.anthony.product.model.entity.EmployeeProductKey;
import com.anthony.product.model.entity.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, EmployeeProductKey> {

}
