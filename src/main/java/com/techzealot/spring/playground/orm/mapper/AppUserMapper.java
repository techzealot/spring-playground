package com.techzealot.spring.playground.orm.mapper;

import com.techzealot.spring.playground.orm.dto.AppUserDto;
import com.techzealot.spring.playground.orm.jpa.entity.AppUser;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppUserMapper extends EntityMapper<AppUserDto, AppUser> {
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AppUser partialUpdate(AppUserDto appUserDto, @MappingTarget AppUser appUser);
}