package edu.mum.cs544;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentService implements ICommentService{
    @Autowired
    private RestTemplate restTemplate;
    public String commentUrl = "http://localhost:8080/api/users/{userId}/posts/{postId}/comments/{commentId}";
    public String commentsUrl = "http://localhost:8080/api/users/{userId}/posts/{postId}/comments/";
    public String commentsAllUrl = "http://localhost:8080/api/users/{userId}/posts/{postId}/comments/all";


    @Override
    public List<Comment> getByUserIdAndPostId(long userId, int postId) {
        ResponseEntity<List<Comment>> response = restTemplate.exchange(commentsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {}, userId, postId);
        return response.getBody();
    }

    @Override
    public Comment getByUserIdAndPostIdAndCommentId(long userId, int postId, int commentId) {
        return restTemplate.getForObject(commentUrl, Comment.class, userId, postId, commentId);
    }

    @Override
    public List<Comment> getAll(long userId, int postId) {
        ResponseEntity<List<Comment>> response = restTemplate.exchange(commentsAllUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {},userId, postId);
        return response.getBody();
    }

    @Override
    public void add(long userId, int postId, Comment comment) {
        restTemplate.postForLocation(commentsUrl, comment, userId, postId);
    }

    @Override
    public void update(long userId, int postId, Comment comment) {
        restTemplate.put(commentUrl, comment, userId, postId, comment.getId());
    }

    @Override
    public void delete(long userId, int postId, int commentId) {
        restTemplate.delete(commentUrl, userId, postId, commentId);
    }
    
}
