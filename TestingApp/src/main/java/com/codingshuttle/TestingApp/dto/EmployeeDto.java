package com.codingshuttle.TestingApp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String email;
    private String name;
    private Long salary;

    @Override
    public boolean equals(Object o)     // accepts object
    {
        if (this == o) return true;     // 1. check if objects are same
        if (!(o instanceof EmployeeDto)) return false;  // 2. if not , then check o is instance of EmployeeDto

        EmployeeDto other = (EmployeeDto) o;        // 3. if not then convert object in dto and check variables

        return Objects.equals(id, other.id) &&
                Objects.equals(email, other.email) &&
                Objects.equals(name, other.name) &&
                Objects.equals(salary, other.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getName(), getSalary());
    }
}


/*
We override equals() so that two objects can be compared based on their data.
 2) By default, Java compares memory addresses, not the values inside the object.
 3) If two objects have the same data but different memory locations, Java will say they are not equal.
 4) Implementing equals() allows frameworks like AssertJ or JUnit to compare objects correctly in tests.
5) It is also useful when storing objects in collections like HashSet or HashMap.
6. no need when you comapre premitive datatypes

1) We override hashCode() to generate a hash value based on object data.
2) By default, Java gives different hashCodes for different memory locations.
3) If two objects have same data but different hashCodes, hash-based collections will treat them as different.
4) Implementing hashCode() ensures objects work correctly in HashSet and HashMap.
5) It is used internally to decide the bucket/location where object will be stored.
6) If equals() returns true, hashCode() must return same value.
7) hashCode() improves performance by reducing search time in collections.
8) No need to use directly for primitive comparisons.
9) it uses data inside dto , and finds hashcode

 */