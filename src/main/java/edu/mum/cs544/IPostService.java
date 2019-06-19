package edu.mum.cs544;

import java.util.List;

public interface IPostService {

    public List<Post> getAll();
    public List<Post> getAllByUserId(long userId);
    public Post add(long userId, Post post);
    public Post get(int postId);
    public void delete(int id);
}

