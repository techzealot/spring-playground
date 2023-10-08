package com.techzealot.spring.playground.orm.dto;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * DTO for {@link com.techzealot.spring.playground.orm.jpa.entity.AppUser}
 */
public record AppUserDto(Long id, Integer age, @NotEmpty String name, RoleDto role)
    implements Serializable {
}