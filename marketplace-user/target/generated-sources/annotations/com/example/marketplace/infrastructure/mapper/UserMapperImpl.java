package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.model.User;
import com.example.marketplace.infrastructure.dto.UserResponse;
import com.example.marketplace.infrastructure.entities.RoleEntity;
import com.example.marketplace.infrastructure.entities.UserEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-16T00:27:10-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private EmailMapper emailMapper;
    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public UserResponse toUserResponse(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.getId() );
        userResponse.setFirstName( user.getFirstName() );
        userResponse.setLastName( user.getLastName() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setDocument( user.getDocument() );
        userResponse.setPassword( user.getPassword() );

        return userResponse;
    }

    @Override
    public User toUser(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        User.UserBuilder user1 = User.builder();

        user1.id( user.getId() );
        user1.firstName( user.getFirstName() );
        user1.lastName( user.getLastName() );
        user1.email( emailMapper.map( user.getEmail() ) );
        user1.password( user.getPassword() );
        user1.document( documentMapper.map( user.getDocument() ) );
        Set<RoleEntity> set = user.getRoles();
        if ( set != null ) {
            user1.roles( new LinkedHashSet<RoleEntity>( set ) );
        }

        return user1.build();
    }

    @Override
    public UserEntity toUserEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( user.getId() );
        userEntity.setFirstName( user.getFirstName() );
        userEntity.setLastName( user.getLastName() );
        userEntity.setEmail( emailMapper.map( user.getEmail() ) );
        userEntity.setPassword( user.getPassword() );
        userEntity.setDocument( documentMapper.map( user.getDocument() ) );
        Set<RoleEntity> set = user.getRoles();
        if ( set != null ) {
            userEntity.setRoles( new LinkedHashSet<RoleEntity>( set ) );
        }

        return userEntity;
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.getId() );
        userResponse.setFirstName( user.getFirstName() );
        userResponse.setLastName( user.getLastName() );
        userResponse.setEmail( emailMapper.map( user.getEmail() ) );
        userResponse.setDocument( documentMapper.map( user.getDocument() ) );
        userResponse.setPassword( user.getPassword() );

        return userResponse;
    }
}
