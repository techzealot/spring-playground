package com.techzealot.spring.playground.orm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link com.techzealot.spring.playground.orm.jpa.entity.Role}
 */
public record RoleDto(Long id, @NotNull @Size(max = 255) String name,
                      @NotNull @Size(max = 255) String description) implements Serializable {
}