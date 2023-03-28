package com.wonseok.subject.domain.common.token;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.wonseok.subject.domain.common.exception.JwtTokenException;
import com.wonseok.subject.domain.entity.Member;
import com.wonseok.subject.domain.service.CustomUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider implements InitializingBean {

    public final String header;
    public final String tokenType = "Bearer ";
    private final String secret;
    private Key key;
    private final long tokenValidityInMilliseconds;
    private final String authoritiesKey;

    private CustomUserDetailsService customUserDetailsService;

    public TokenProvider(
            @Value("${jwt.header}") String header,
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.authorities-key}") String authoritiesKey,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            CustomUserDetailsService customUserDetailsService) {
        this.header = header;
        this.secret = secret;
        this.authoritiesKey = authoritiesKey;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 2 * 1000;
        this.customUserDetailsService = customUserDetailsService;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /* Token 생성 (2시간) */
    public String makeJwtToken(Member member) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts
                .builder()
                .setSubject(member.getUserId())
                .claim(authoritiesKey, "ROLE_USER")
                .claim("id",member.getMemberId())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JwtTokenException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtTokenException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtTokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtTokenException("JWT 토큰이 잘못되었습니다.");
        }
        return true;
    }

    public String getJwtToken(HttpServletRequest request) {
        String requestHeaderToken = request.getHeader(header);
        String jwtToken = "";
        if (StringUtils.hasText(requestHeaderToken) && requestHeaderToken.startsWith("Bearer ")) {
            jwtToken = requestHeaderToken.substring(7);
        }
        return jwtToken;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(authoritiesKey).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        /* 토큰 */
        User principal = new User(claims.getSubject(), "", authorities);
        /* DB */
        UserDetails user = customUserDetailsService.loadUserByUsername(principal.getUsername());

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

}
