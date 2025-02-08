package ch.fitnessExerciseApi.services;

import ch.fitnessExerciseApi.models.CustomOAuth2User;
import ch.fitnessExerciseApi.models.User;
import ch.fitnessExerciseApi.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final SecretKey secretKey;
    private final UserRepository userRepository;
    private final long jwtValidityInMillis;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomOAuth2UserService(SecretKey secretKey, UserRepository userRepository, @Value("${jwt.validity.hours}") long jwtValidityInHours, BCryptPasswordEncoder passwordEncoder) {
        this.secretKey = secretKey;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtValidityInMillis = jwtValidityInHours * 3600000; // Convert hours to milliseconds
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> userAttributes = oAuth2User.getAttributes();

        // Determine provider-specific details
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String nameAttributeKey = getNameAttributeKey(registrationId);
        String email = getEmail(userAttributes, registrationId);
        String name = getName(userAttributes, registrationId);
        String providerId = getUserSubject(userAttributes, registrationId);

        // Retrieve or create user in MongoDB
        User user = userRepository.findByEmail(email).orElse(new User());
        user.setEmail(email);
        user.setName(name);
        user.setProvider(registrationId);
        user.setProviderId(providerId);

        // Generate access token (valid for 7 days)
        String jwtToken = Jwts.builder()
                .setSubject(providerId)
                .claim("email", email)
                .claim("name", name)
                .claim("provider", registrationId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        //Generate Refresh Token
        String rawRefreshToken = UUID.randomUUID().toString();
        String hashedRefreshToken = passwordEncoder.encode(rawRefreshToken);
        user.setRefreshToken(hashedRefreshToken);

        // Save updated user record to MongoDB
        userRepository.save(user);

        // Return tokens to client (via user attributes)
        Map<String, Object> attributes = new HashMap<>(userAttributes);
        attributes.put("jwtToken", jwtToken);
        attributes.put("refreshToken", rawRefreshToken);

        return new CustomOAuth2User(oAuth2User.getAuthorities(), attributes, nameAttributeKey);
    }

    private String getNameAttributeKey(String registrationId) {
        return registrationId.equals("google") ? "sub" : "login";
    }

    private String getUserSubject(Map<String, Object> attributes, String registrationId) {
        return registrationId.equals("google")
                ? attributes.get("sub").toString()
                : attributes.get("id").toString();
    }

    private String getEmail(Map<String, Object> attributes, String registrationId) {
        if (registrationId.equals("google")) {
            return attributes.get("email").toString();
        } else {
            // GitHub may set email as private, handle this case
            return attributes.get("login").toString();
        }
    }

    private String getName(Map<String, Object> attributes, String registrationId) {
        if (registrationId.equals("google")) {
            return attributes.get("name").toString();
        } else {
            return attributes.get("name") != null
                    ? attributes.get("name").toString()
                    : attributes.get("login").toString();
        }
    }

}
