package in.yash.jwtServices;

import in.yash.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;



@Service
public class JwtTokenService {

    private final String secretKey="819542816b9d0a6f0c615fe72bedfc344f5c0bfe8700c8b7ecfb2f074557502f";

    private Key key(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return createToken(user);
    }

    private String createToken(User user) {

        return Jwts
                .builder()
                .claim("role",user.getAuthorities())
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(key())
                .compact();
    }

    public Long extractId(String token) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String id=claims.getSubject();
            return Long.parseLong(id);
    }


}
