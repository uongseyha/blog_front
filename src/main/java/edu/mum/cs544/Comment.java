package edu.mum.cs544;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    @Lob
    private String description;
    private Date createDate;
    private Integer createdUserId;
    private Integer createdPostId;

    public Comment() {
    }

    public Integer getCreatedPostId() {
        return createdPostId;
    }

    public void setCreatedPostId(Integer createdPostId) {
        this.createdPostId = createdPostId;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Comment(String description, Integer createdPostId, Integer createdUserId) {
        this.description = description;
        this.createDate = new Date();
        this.createdPostId = createdPostId;
        this.createdUserId = createdUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}