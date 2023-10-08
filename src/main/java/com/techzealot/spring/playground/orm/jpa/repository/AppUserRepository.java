package com.techzealot.spring.playground.orm.jpa.repository;

import com.techzealot.spring.playground.orm.jpa.entity.AppUser;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    @Query("select a from AppUser a where upper(a.name) = upper(:name)")
    AppUser findByNameIgnoreCase(@Param("name") String name);

    @Query("select a from AppUser a where a.age = :age order by a.id DESC")
    List<AppUser> findByAgeOrderByIdDesc(@Param("age") Integer age, Pageable pageable);


}