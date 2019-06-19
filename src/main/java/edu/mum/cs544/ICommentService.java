package edu.mum.cs544;

import java.util.List;

public interface ICommentService {
    Comment getByUserIdAndPostIdAndCommentId(long userId, int postId, int commentId );
    List<Comment> getByUserIdAndPostId(long userId, int postId);
    List<Comment> getAll(long userId, int postId);
    void add(long userId, int postId, Comment comment);
    void update(long userId, int postId, Comment comment);
    void delete( long userId, int postId, int id);
}
