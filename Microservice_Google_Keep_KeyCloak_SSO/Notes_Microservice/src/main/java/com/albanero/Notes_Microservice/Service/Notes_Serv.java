package com.albanero.Notes_Microservice.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.albanero.Notes_Microservice.Model.Notes;
import com.albanero.Notes_Microservice.Repository.Notes_Repo;

@Service
public class Notes_Serv {

	@Autowired
	private Notes_Repo notes_Repo;

	public Notes createNotes(Notes notes) {
		return notes_Repo.save(notes);
	}

	public Notes getNotesById(String id) {
		Optional<Notes> optionalNotes = notes_Repo.findById(id);
		return optionalNotes.get();
	}

	public Notes getNotesByName(String name) {
		Optional<Notes> optionalNotes = notes_Repo.findByName(name);
		return optionalNotes.get();
	}

	public Notes updateNotes(String id, Notes notes) {
		Optional<Notes> optionalNotes = notes_Repo.findById(id);
		Notes updateNK = optionalNotes.get();
		updateNK.setName(notes.getName());
		updateNK.setNotes(notes.getNotes());
		updateNK.setFile(notes.getFile());
		updateNK.setFileURL(notes.getFileURL());
		return notes_Repo.save(updateNK);
	}

	public void deleteNotes(String id) {
		notes_Repo.deleteById(id);
	}

}
