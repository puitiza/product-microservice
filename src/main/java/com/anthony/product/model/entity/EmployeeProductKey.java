package com.anthony.product.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class EmployeeProductKey implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "product_id")
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeProductKey that)) return false;
        return Objects.equals(getEmployeeId(), that.getEmployeeId()) &&
                Objects.equals(getProductId(), that.getProductId());
    }

    @Override
    public int hashCode() {
        int hsCode;
        hsCode = employeeId.hashCode();
        hsCode = 19 * hsCode+ productId.hashCode();
        return hsCode;
    }
}
