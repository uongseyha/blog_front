package edu.mum.cs544.service;

import edu.mum.cs544.model.User;

import java.util.List;

public interface IUserService {
    User get(Long id);
    List<User> getAll();
    void add(User user);
    void update(User user);
    void delete(Long id);
    User isCorrectUser(String email, String password);
}
