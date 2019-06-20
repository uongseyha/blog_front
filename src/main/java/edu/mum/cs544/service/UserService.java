package edu.mum.cs544.service;

import edu.mum.cs544.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService implements IUserService{
    @Autowired
    private RestTemplate restTemplate;
    private final String checkUserUrl = "http://localhost:8080/api/users/{email}/{password}";
    private final String userUrl = "http://localhost:8080/api/users/{id}";
    private final String pplUrl = "http://localhost:8080/api/users";

    @Override
    public User get(Long id) {
        return restTemplate.getForObject(userUrl, User.class, id);
    }

    @Override
    public List<User> getAll() {
        ResponseEntity<List<User>> response = restTemplate.exchange(pplUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        return response.getBody();
    }

    @Override
    public void add(User b) {
//        URI uri = restTemplate.postForLocation(pplUrl, b);
//        if (uri == null) { return null; }
//        Matcher m = Pattern.compile(".*/api/users/(\\d+)").matcher(uri.getPath());
//        m.matches();
//        return Integer.parseInt(m.group(1));
        restTemplate.postForLocation(pplUrl, b);
    }

    @Override
    public void update(User b) {
        restTemplate.put(userUrl, b, b.getId());
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete(userUrl, id);
    }

    @Override
    public User isCorrectUser(String email, String password){
        return restTemplate.getForObject(checkUserUrl, User.class, email,password);
    }
}
