package com.example.backend.service;


import com.example.backend.model.Comment;
import com.example.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // ✅ Edit a comment (by ID)
    public Optional<Comment> updateComment(Long commentId, String newText) {
        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(newText);
            return commentRepository.save(comment);
        });
    }

    // ✅ Delete a comment (by ID)
    public boolean deleteComment(Long commentId) {
        return commentRepository.findById(commentId).map(comment -> {
            commentRepository.delete(comment);
            return true;
        }).orElse(false);
    }



}

