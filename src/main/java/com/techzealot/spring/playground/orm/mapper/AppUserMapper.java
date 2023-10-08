package com.techzealot.spring.playground.orm.mapper;

import com.techzealot.spring.playground.orm.dto.AppUserDto;
import com.techzealot.spring.playground.orm.jpa.entity.AppUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppUserMapper extends EntityMapper<AppUserDto, AppUser> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AppUser partialUpdate(AppUserDto appUserDto, @MappingTarget AppUser appUser);
}