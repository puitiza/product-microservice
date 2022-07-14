package com.anthony.product.repository;

import com.anthony.product.model.entity.EmployeeProductKey;
import com.anthony.product.model.entity.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, EmployeeProductKey> {

    @Transactional
    void deleteProductRatingByProductId(long product_id);

    @Transactional
    void deleteProductRatingByEmployeeId(long employee_id);

}
