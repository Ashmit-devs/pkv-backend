package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- Constructors ---
    public Note() {}

    public Note(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}