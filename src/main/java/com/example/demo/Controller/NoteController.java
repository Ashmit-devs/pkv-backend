package com.example.demo.Controller;

import com.example.demo.Model.Note;
import com.example.demo.Model.User;
import com.example.demo.Repository.NoteRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a note for a user
    @PostMapping("/create/{userId}")
    public Map<String, Object> createNote(@PathVariable Long userId, @RequestBody Note note) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            note.setUser(user.get());
            noteRepository.save(note);
            response.put("message", "Note created!");
            response.put("noteId", note.getId());
        } else {
            response.put("message", "User not found!");
        }
        return response;
    }

    // Get all notes of a user
    @GetMapping("/user/{userId}")
    public List<Note> getUserNotes(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(noteRepository::findByUser).orElse(Collections.emptyList());
    }

    // Update a note
    @PutMapping("/{noteId}")
    public Map<String, String> updateNote(@PathVariable Long noteId, @RequestBody Note updatedNote) {
        Map<String, String> response = new HashMap<>();
        Optional<Note> note = noteRepository.findById(noteId);

        if (note.isPresent()) {
            Note existing = note.get();
            existing.setTitle(updatedNote.getTitle());
            existing.setContent(updatedNote.getContent());
            noteRepository.save(existing);
            response.put("message", "Note updated!");
        } else {
            response.put("message", "Note not found!");
        }
        return response;
    }

    // Delete a note
    @DeleteMapping("/{noteId}")
    public Map<String, String> deleteNote(@PathVariable Long noteId) {
        Map<String, String> response = new HashMap<>();
        if (noteRepository.existsById(noteId)) {
            noteRepository.deleteById(noteId);
            response.put("message", "Note deleted!");
        } else {
            response.put("message", "Note not found!");
        }
        return response;
    }
}