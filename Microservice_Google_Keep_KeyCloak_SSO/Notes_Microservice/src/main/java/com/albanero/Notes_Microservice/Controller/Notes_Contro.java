package com.albanero.Notes_Microservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.albanero.Notes_Microservice.Model.Notes;
import com.albanero.Notes_Microservice.Service.Notes_Serv;

@RestController

public class Notes_Contro {

	@Autowired
	private Notes_Serv notes_Serv;

	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/Create/Notes")
	public Notes createNotes(@RequestBody Notes notes) {
		return notes_Serv.createNotes(notes);
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/Get/NotesById/{id}")
	public ResponseEntity<Notes> getNotesById(@PathVariable String id) {
		return ResponseEntity.ok(notes_Serv.getNotesById(id));
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/Get/NotesByName/{name}")
	public ResponseEntity<Notes> getNotesByName(@PathVariable String name) {
		return ResponseEntity.ok(notes_Serv.getNotesByName(name));
	}

	@PreAuthorize("hasAuthority('USER')")
	@PutMapping("/Put/NotesById/{id}")
	public ResponseEntity<Notes> updateNotes(@PathVariable String id, @RequestBody Notes notes) {
		return ResponseEntity.ok(notes_Serv.updateNotes(id, notes));
	}

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@DeleteMapping("/Delete/NotesById/{id}")
	public String deleteNotes(@PathVariable String id) {
		notes_Serv.deleteNotes(id);
		return "Notes with Id : " + id + " was Successfully deleted";
	}
}