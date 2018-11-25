package pl.darczuk.warehouse.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import pl.darczuk.warehouse.entity.User;

import javax.crypto.SecretKey;
import java.util.UUID;

public class TokenGenerator {
    SecretKey key;

    public TokenGenerator() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    }

    public String createToken(User user) {

        String token = Jwts.builder()
                .setSubject(user.getLogin())
                .claim("Role", user.getRole())
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, "warehousewarehousewarehousewarehousewarehouse")
                .compact();

        return token;
    }
}
