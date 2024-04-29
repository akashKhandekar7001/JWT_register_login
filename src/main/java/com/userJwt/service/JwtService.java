package com.userJwt.service;

import com.userJwt.db.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "bb01f0359790d8efb59683cc5925855f431b7a0d054b3428937e89de12a6b046";

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails userDetails){
        String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExprire(token);
    }

    private boolean isTokenExprire(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractsAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractsAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
    }
    //generate token and add user name
    //generate token with current time and expiration time
    public String gernateTocken(User user){
        return Jwts
             .builder()
             .setSubject(user.getUsername())
             .setIssuedAt(new Date(System.currentTimeMillis()))
             .setExpiration(new Date(System.currentTimeMillis() + 26*50*60*1000))
             .signWith(getSignKey())
             .compact();
    }

    private SecretKey getSignKey(){
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyByte);
    }

}
