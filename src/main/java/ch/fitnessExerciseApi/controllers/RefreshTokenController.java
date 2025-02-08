package ch.fitnessExerciseApi.controllers;

import ch.fitnessExerciseApi.models.User;
import ch.fitnessExerciseApi.repositories.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/refresh")
public class RefreshTokenController {

    private final UserRepository userRepository;
    private final SecretKey secretKey;
    private final long jwtValidityInMillis;
    private final BCryptPasswordEncoder passwordEncoder;

    public RefreshTokenController(UserRepository userRepository, SecretKey secretKey, @Value("${jwt.validity.hours}") long jwtValidityInHours, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.secretKey = secretKey;
        this.passwordEncoder = passwordEncoder;
        this.jwtValidityInMillis = jwtValidityInHours * 3600000; // Convert hours to milliseconds
    }

    @PostMapping
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String incomingRefreshToken = request.get("refreshToken");
        String expiredJwtToken = request.get("jwtToken");

        if (incomingRefreshToken == null || expiredJwtToken == null) {
            return ResponseEntity.badRequest().body("Missing refresh token or jwt token");
        }

        Claims claims;
        try {
            // If token is not expired, parsing will succeed, so we return an error.
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(expiredJwtToken);
            return ResponseEntity.badRequest().body("JWT token is not expired yet");
        } catch (ExpiredJwtException eje) {
            claims = eje.getClaims();
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid JWT token");
        }

        String tokenSubject = claims.getSubject();
        Optional<User> optionalUser = userRepository.findByRefreshToken(incomingRefreshToken);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        User user = optionalUser.get();
        if (!tokenSubject.equals(user.getProviderId())) {
            return ResponseEntity.status(401).body("Token subject does not match");
        }

        // Generate a new access token valid for 7 days.
        String newJwtToken = Jwts.builder()
                .setSubject(user.getProviderId())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("provider", user.getProvider())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // Rotate refresh token: generate a new raw token and hash it before storing.
        String newRawRefreshToken = UUID.randomUUID().toString();
        String newHashedRefreshToken = passwordEncoder.encode(newRawRefreshToken);
        user.setRefreshToken(newHashedRefreshToken);
        userRepository.save(user);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("jwtToken", newJwtToken);
        tokens.put("refreshToken", newRawRefreshToken);

        return ResponseEntity.ok(tokens);
    }
}
