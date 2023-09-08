package com.example.hotelbooking.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс, содержащий методы для создания JsonWebToken
 */
@Component
public class JwtTokenUtils {

    /**
     * Поле для секретного ключа JsonWebToken
     */
    @Value(value = "45vsg43gv42vdzdv32vt24gvr5gsvg52tgv4wg")
    private String secret;

    /**
     * Поле, отвечающее за длительность токена
     */
    @Value("30m")
    private Duration tokenDuration;


    /**
     * Метод, формирующих JsonWebToken
     * @param userDetails
     * @return JsonWebToken
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        claims.put("roles", userRoles);

        Date startDate = new Date();

        Date endDate = new Date(startDate.getTime() + tokenDuration.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(startDate)
                .setExpiration(endDate)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    /**
     * Метод для получения username пользователя из JsonWebToken
     * @param token JsonWebToken
     * @return Username пользователя
     */
    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Метод для получения списка ролей у пользовотеля из JsonWebToken
     * @param token JsonWebToken
     * @return List<Roles> Возвращает роли пользователя из JsonWebToken
     */
    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }


    /**
     * @param token JsonWebToken
     * @return Claims
     */
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

    }


}
