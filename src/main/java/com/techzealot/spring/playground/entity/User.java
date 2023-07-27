package com.techzealot.spring.playground.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class User {
    @NotNull(message = "name不能为空")
    private String name;
    @Range(min = 0, max = 120, message = "必须在0-120之间")
    private int age;
}
