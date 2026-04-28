package com.example.marketplace.infrastructure.security;

import com.example.marketplace.infrastructure.entities.RoleEntity;
import com.example.marketplace.infrastructure.entities.UserEntity;
import com.example.marketplace.infrastructure.repository.RoleRepository;
import com.example.marketplace.infrastructure.repository.UserRepository;
import com.example.marketplace.infrastructure.security.dto.LoginRequest;
import com.example.marketplace.infrastructure.security.dto.RefreshTokenRequest;
import com.example.marketplace.infrastructure.security.dto.RegisterRequest;
import com.example.marketplace.infrastructure.security.dto.TokenResponse;
import com.example.marketplace.shared.exception.DuplicateResourceException;
import com.example.marketplace.shared.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Usuário", "email", request.email());
        }

        if (userRepository.existsByDocument(request.document().replaceAll("[^0-9]", ""))) {
            throw new DuplicateResourceException("Usuário", "documento", request.document());
        }

        RoleEntity customerRole = roleRepository.findByAuthority("ROLE_CUSTOMER")
                .orElseGet(() -> {
                    var role = new RoleEntity();
                    role.setAuthority("ROLE_CUSTOMER");
                    return roleRepository.save(role);
                });

        var user = new UserEntity();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setDocument(request.document().replaceAll("[^0-9]", ""));
        user.setRoles(Set.of(customerRole));

        UserEntity saved = userRepository.save(user);
        log.info("Novo usuário registrado: {} ({})", saved.getEmail(), saved.getId());

        return buildTokenResponse(saved);
    }

    @Transactional
    public TokenResponse registerSeller(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Usuário", "email", request.email());
        }

        RoleEntity sellerRole = roleRepository.findByAuthority("ROLE_SELLER")
                .orElseGet(() -> {
                    var role = new RoleEntity();
                    role.setAuthority("ROLE_SELLER");
                    return roleRepository.save(role);
                });

        var user = new UserEntity();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setDocument(request.document().replaceAll("[^0-9]", ""));
        user.setRoles(Set.of(sellerRole));

        UserEntity saved = userRepository.save(user);
        log.info("Novo lojista registrado: {} ({})", saved.getEmail(), saved.getId());

        return buildTokenResponse(saved);
    }

    public TokenResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Email ou senha inválidos");
        }

        log.info("Login bem-sucedido: {}", user.getEmail());
        return buildTokenResponse(user);
    }

    public TokenResponse refresh(RefreshTokenRequest request) {
        try {
            if (!tokenProvider.validateToken(request.refreshToken())) {
                throw new UnauthorizedException("Refresh token inválido");
            }

            UUID userId = tokenProvider.getUserIdFromToken(request.refreshToken());
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new UnauthorizedException("Usuário não encontrado"));

            return buildTokenResponse(user);
        } catch (Exception e) {
            throw new UnauthorizedException("Refresh token inválido ou expirado");
        }
    }

    private TokenResponse buildTokenResponse(UserEntity user) {
        List<String> roles = user.getRoles().stream()
                .map(RoleEntity::getAuthority)
                .toList();

        String accessToken = tokenProvider.generateAccessToken(user.getId(), user.getEmail(), roles);
        String refreshToken = tokenProvider.generateRefreshToken(user.getId());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }
}
