package ch.fitnessExerciseApi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/token")
@Tag(name = "Token", description = "Token API")
public class TokenController {

    @GetMapping()
    @Operation(summary = "Retrieve Token", description = "retrieve JWT Token user logged in with", operationId = "get-token")
    @ResponseBody
    public ResponseEntity<String> token(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String jwtToken = (String) oAuth2User.getAttributes().get("jwtToken");

        String html = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "  <meta charset='utf-8'>\n"
                + "  <title>Token</title>\n"
                + "  <script type='text/javascript'>\n"
                + "    window.onload = function() {\n"
                + "      window.location.href = 'loginApp://token?jwtToken=" + jwtToken + "';\n"
                + "    };\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <p>"+jwtToken+"</p>\n"
                + "</body>\n"
                + "</html>";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(html, headers, HttpStatus.OK);
    }
}
