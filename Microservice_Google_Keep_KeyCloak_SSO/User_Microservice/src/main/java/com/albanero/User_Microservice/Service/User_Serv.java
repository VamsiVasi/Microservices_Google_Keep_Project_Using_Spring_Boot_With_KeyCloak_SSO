package com.albanero.User_Microservice.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.albanero.User_Microservice.Exceptions.Resource_NotFound_Exception;
import com.albanero.User_Microservice.Model.Users;
import com.albanero.User_Microservice.Repository.User_Repo;
import com.albanero.User_Microservice.Request.Notes;
import com.albanero.User_Microservice.Response.Microservice_NotesList_Response;
import com.albanero.User_Microservice.Response.Microservice_Notes_Response;
import com.albanero.User_Microservice.Response.Microservice_Response;
import com.albanero.User_Microservice.Response.Microservice_UserList_Response;
import com.albanero.User_Microservice.Response.Microservice_User_Response;

import reactor.core.publisher.Mono;

@Service
public class User_Serv {

	@Autowired
	private User_Repo user_Repo;

	@Autowired
	private WebClient.Builder webClient;

	Logger log = LoggerFactory.getLogger(User_Serv.class);

	public Microservice_User_Response createUserNotes(String userName, String password, String email) throws IOException, Resource_NotFound_Exception {
		ArrayList<String> ua =new ArrayList<String>();
		Users user = new Users();
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassword(password);
		user.setNotesIds(ua);
		user_Repo.save(user);
		log.info("\nUser with id : " + user.getId() + " was Successfully Created");
		return new Microservice_User_Response(user, HttpStatus.OK.toString());
	}

	public Microservice_Response createNotesByUserName(MultipartFile file, String notesName, String notes,
			String userName) throws IOException, Resource_NotFound_Exception {
		Optional<Users> optionalUser = Optional.of(user_Repo.findByUserName(userName).orElseThrow(
				() -> new Resource_NotFound_Exception("No user with username :- '" + userName + "' was found.")));
		Users user = optionalUser.get();
		ArrayList<String> ua = user.getNotesIds();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.isEmpty()) {
			log.warn("\nNo File was Selected");
			throw new Resource_NotFound_Exception("No File was Selected");
		}
		String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/keep/").path(fileName).toUriString();
		Notes newNotes = new Notes(notesName, notes, file.getBytes(), url);
		Notes notesResponse = webClient.build().post().uri("http://localhost:1999/Create/Notes")
				.body(Mono.just(newNotes), Notes.class).retrieve().bodyToMono(Notes.class).block();
		String url1 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/keep/").path(fileName + "/")
				.path(notesResponse.getId()).toUriString();
		newNotes.setFileURL(url1);
		Notes notesResponse2 = webClient.build().put()
				.uri("http://localhost:1999/Put/NotesById/" + notesResponse.getId())
				.body(Mono.just(newNotes), Notes.class).retrieve().bodyToMono(Notes.class).block();
		ua.add(notesResponse.getId());
		user.setNotesIds(ua);
		user_Repo.save(user);
		log.info("\n" + user.getUserName() + ", created a New Notes with id : " + notesResponse.getId());
		return new Microservice_Response(user, notesResponse2, HttpStatus.OK.toString());
	}

	public Microservice_UserList_Response getAllUsers() {
		List<Users> allUsers = user_Repo.findAll();
		log.info("\n Total Users returned are {}", allUsers.size());
		return new Microservice_UserList_Response(allUsers, HttpStatus.OK.toString());
	}

	public Microservice_User_Response getUserByUserName(String userName) throws Resource_NotFound_Exception {
		Optional<Users> optionalUser = Optional.of(user_Repo.findByUserName(userName).orElseThrow(
				() -> new Resource_NotFound_Exception("No user with username :- '" + userName + "' was found.")));
		log.info("\n" + userName + " Details are Successfully Retrieved");
		return new Microservice_User_Response(optionalUser.get(), HttpStatus.OK.toString());
	}

	public Microservice_NotesList_Response getAllNotesByUserName(String userName) throws Resource_NotFound_Exception {
		Optional<Users> optionalUser = Optional.of(user_Repo.findByUserName(userName).orElseThrow(
				() -> new Resource_NotFound_Exception("No user with username :- '" + userName + "' was found.")));
		Users user = optionalUser.get();
		ArrayList<String> ua = user.getNotesIds();
		ArrayList<Notes> allNotesOfUser = new ArrayList<Notes>();
		for (int i = 0; i < ua.size(); i++) {
			Notes notesResponse = webClient.build().get().uri("http://localhost:1999/Get/NotesById/" + ua.get(i))
					.retrieve().bodyToMono(Notes.class).block();
			allNotesOfUser.add(notesResponse);
		}
		log.info("\n Total Notes of the " + userName + " are {}", allNotesOfUser.size());
		return new Microservice_NotesList_Response(allNotesOfUser, HttpStatus.OK.toString());
	}

	public Microservice_Notes_Response getNotesByName(String userName, String name) throws Resource_NotFound_Exception {
		Optional<Users> optionalUser = Optional.of(user_Repo.findByUserName(userName).orElseThrow(
				() -> new Resource_NotFound_Exception("No user with username :- '" + userName + "' was found.")));
		try {
			Users user = optionalUser.get();
			ArrayList<String> ua = user.getNotesIds();
			ArrayList<Notes> allNotesOfUser = new ArrayList<Notes>();
			for (int i = 0; i < ua.size(); i++) {
				Notes notesResponse = webClient.build().get().uri("http://localhost:1999/Get/NotesById/" + ua.get(i))
						.retrieve().bodyToMono(Notes.class).block();
				allNotesOfUser.add(notesResponse);
			}
			Notes notesResponse1 = webClient.build().get().uri("http://localhost:1999/Get/NotesByName/" + name)
					.retrieve().bodyToMono(Notes.class).block();
			log.info("\n" + userName + "'s Notes With Name : " + name + " Was Successfully Retrieved");
			return new Microservice_Notes_Response(notesResponse1, HttpStatus.OK.toString());
		} catch (Exception e) {
			throw new Resource_NotFound_Exception("No Notes was found with the name :- '" + name + "'.");
		}
	}

	public Microservice_User_Response updateUser(String UserName, String password, String userName)
			throws Resource_NotFound_Exception {
		Optional<Users> optionalUser = Optional.of(user_Repo.findByUserName(userName).orElseThrow(
				() -> new Resource_NotFound_Exception("No user with username :- '" + userName + "' was found.")));
		Users updateUK = optionalUser.get();
		updateUK.setUserName(UserName);
		updateUK.setPassword(password);
		user_Repo.save(updateUK);
		log.info("\n" + userName + " Updated Details : \nUser Name was updated to '" + UserName
				+ "'\nPassword was updated to '" + password + "'");
		return new Microservice_User_Response(updateUK, HttpStatus.OK.toString());
	}

	public Microservice_Notes_Response updateNotes(MultipartFile file, String notesName, String notes, String userName,
			String name) throws IOException, Resource_NotFound_Exception {
		Optional<Users> optionalUser = Optional.of(user_Repo.findByUserName(userName).orElseThrow(
				() -> new Resource_NotFound_Exception("No user with username :- '" + userName + "' was found.")));
		try {
			Users user = optionalUser.get();
			ArrayList<String> ua = user.getNotesIds();
			ArrayList<Notes> allNotesOfUser = new ArrayList<Notes>();
			for (int i = 0; i < ua.size(); i++) {
				Notes notesResponse = webClient.build().get().uri("http://localhost:1999/Get/NotesById/" + ua.get(i))
						.retrieve().bodyToMono(Notes.class).block();
				allNotesOfUser.add(notesResponse);
			}
			Notes notesResponse1 = webClient.build().get().uri("http://localhost:1999/Get/NotesByName/" + name)
					.retrieve().bodyToMono(Notes.class).block();
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			if (fileName.isEmpty()) {
				log.warn("\nNo File was Selected");
				throw new Resource_NotFound_Exception("No File was Selected");
			}
			String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/keep/").path(fileName + "/")
					.path(notesResponse1.getId()).toUriString();
			Notes updateNK = new Notes();
			updateNK.setName(notesName);
			updateNK.setNotes(notes);
			updateNK.setFile(file.getBytes());
			updateNK.setFileURL(url);
			Notes notesResponse2 = webClient.build().put()
					.uri("http://localhost:1999/Put/NotesById/" + notesResponse1.getId())
					.body(Mono.just(updateNK), Notes.class).retrieve().bodyToMono(Notes.class).block();
			log.info("\n" + userName + "'s, " + name + " Notes Updated Details : \nNotes Name was updated to '"
					+ notesName + "'\nNotes was updated to '" + notes + "'");
			return new Microservice_Notes_Response(notesResponse2, HttpStatus.OK.toString());
		} catch (Exception e) {
			throw new Resource_NotFound_Exception(
					"No Notes was found with the name :- '" + name + "' / No File was Selected");
		}
	}

	public void deleteNotes(String userName, String name) throws Resource_NotFound_Exception {
		Optional<Users> optionalUser = Optional.of(user_Repo.findByUserName(userName).orElseThrow(
				() -> new Resource_NotFound_Exception("No user with username :- '" + userName + "' was found.")));
		try {
			Users user = optionalUser.get();
			ArrayList<String> ua = user.getNotesIds();
			ArrayList<Notes> allNotesOfUser = new ArrayList<Notes>();
			for (int i = 0; i < ua.size(); i++) {
				Notes notesResponse = webClient.build().get().uri("http://localhost:1999/Get/NotesById/" + ua.get(i))
						.retrieve().bodyToMono(Notes.class).block();
				allNotesOfUser.add(notesResponse);
			}
			Notes notesResponse1 = webClient.build().get().uri("http://localhost:1999/Get/NotesByName/" + name)
					.retrieve().bodyToMono(Notes.class).block();
			for (int i = 0; i < ua.size(); i++) {
				String nid = notesResponse1.getId();
				if (ua.get(i).equals(nid)) {
					ua.remove(i);
				}
			}
			user.setNotesIds(ua);
			user_Repo.save(user);
			webClient.build().delete().uri("http://localhost:1999/Delete/NotesById/" + notesResponse1.getId())
					.retrieve().bodyToMono(Void.class).block();
			log.info("\n" + userName + ", " + name + " Notes Was Successfully Removed");
		} catch (Exception e) {
			throw new Resource_NotFound_Exception("No Notes was found with the name :- '" + name + "'.");
		}
	}

	public void deleteUser(String userName) throws Resource_NotFound_Exception {
		Optional<Users> optionalUser = Optional.of(user_Repo.findByUserName(userName).orElseThrow(
				() -> new Resource_NotFound_Exception("No user with username :- '" + userName + "' was found.")));
		Users user = optionalUser.get();
		ArrayList<String> ua = user.getNotesIds();
		for (int i = 0; i < ua.size(); i++) {
			webClient.build().delete().uri("http://localhost:1999/Delete/NotesById/" + ua.get(i)).retrieve()
					.bodyToMono(Void.class).block();
		}
		user_Repo.delete(user);
		log.info("\n" + userName + ", your account and all your notes were successfully removed");
	}

}
