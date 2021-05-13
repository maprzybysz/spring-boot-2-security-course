package pl.maprzybysz.jwtclient;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Stream;

@Controller
public class BookApiClient {
    public BookApiClient() {
        Algorithm algorithm = Algorithm.HMAC512("q3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdSgVkYp3s5v8y/B?E(H+MbQeTh");
        String token = JWT.create().withClaim("admin", true).sign(algorithm);
        System.out.println(token);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);

        HttpEntity httpEntity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> exchange = restTemplate.exchange("http://localhost:8080/api/books", HttpMethod.GET, httpEntity, String[].class);

        Stream.of(exchange.getBody()).forEach(System.out::println);
    }
}
