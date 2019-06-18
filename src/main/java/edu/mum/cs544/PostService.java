package edu.mum.cs544;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class PostService implements IPostService {
    private final String POSTS_BY_USERID_URL = "http://localhost:8282/users/{userId}/posts";
    private final String POSTS_URL = "http://localhost:8282/posts";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Post> getAll() {
        ResponseEntity<List<Post>> response = restTemplate.exchange(
                POSTS_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Post>>() {});
        return response.getBody();
    }

    @Override
    public List<Post> getAllByUserId(long userId) {
        ResponseEntity<List<Post>> response = restTemplate.exchange(
                POSTS_BY_USERID_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Post>>() {},
                userId);
        return response.getBody();
    }

    @Override
    public Post add(long userId, Post post) {
        URI uri = restTemplate.postForLocation(POSTS_BY_USERID_URL, post, userId);
        System.out.println(uri.toString());
        return restTemplate.getForObject(uri, Post.class);
    }
}
