package edu.mum.cs544;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    @Lob
    private String text;
    @ElementCollection
    private List<Category> categories;
    private Long userId;

    public Post() {
        this.categories = new ArrayList<>();
    }

    public Post(String title, String description, String text, Long userId) {
        this();
        this.title = title;
        this.description = description;
        this.text = text;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public boolean addCategory(Category category) {
        return this.categories.add(category);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", text='" + text + '\'' +
                ", userId=" + userId +
                '}';
    }
}
