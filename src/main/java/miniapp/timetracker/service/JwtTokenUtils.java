package miniapp.timetracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import miniapp.timetracker.model.contracts.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String generateToken(CustomUserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", roleList);
        claims.put("userId", userDetails.getUserId());

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
        String base64EncodedSecret = secret;
        byte[] decodedSecret = Base64.getDecoder().decode(base64EncodedSecret);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, decodedSecret)
                .compact();
    }

    public String getUsername (String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token){
        return (List<String>) getAllClaimsFromToken(token).get("roles");
    }

    public String getUserId(String token){
        return (String) getAllClaimsFromToken(token.substring(7)).get("userId");
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
