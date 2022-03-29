package com.albanero.User_Microservice.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.albanero.User_Microservice.Exceptions.Resource_NotFound_Exception;
import com.albanero.User_Microservice.Request.Notes;
import com.albanero.User_Microservice.Response.Microservice_NotesList_Response;
import com.albanero.User_Microservice.Response.Microservice_Notes_Response;
import com.albanero.User_Microservice.Response.Microservice_Response;
import com.albanero.User_Microservice.Response.Microservice_UserList_Response;
import com.albanero.User_Microservice.Response.Microservice_User_Response;
import com.albanero.User_Microservice.Service.User_Serv;

@RestController
@RequestMapping(value = "/keep")
public class User_Contro {

	@Autowired
	private User_Serv user_Serv;

	@Autowired
	private WebClient.Builder webClient;

	@PostMapping("/Create/User-Note")
	public Microservice_User_Response createUserNotes(@RequestParam("username") String UserName, @RequestParam("password") String Password,
			@RequestParam("email") String Email) throws IOException, Resource_NotFound_Exception {
		return user_Serv.createUserNotes(UserName, Password, Email);
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/Create/Notes/{userName}")
	public Microservice_Response createNotesByUserName(@RequestParam("file") MultipartFile File,
			@RequestParam("notesname") String NotesName, @RequestParam("notes") String Notes,
			@PathVariable String userName) throws Resource_NotFound_Exception, IOException {
		return user_Serv.createNotesByUserName(File, NotesName, Notes, userName);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/Get/AllUsers")
	public Microservice_UserList_Response getAllUsers() {
		return user_Serv.getAllUsers();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/Get/User/{userName}")
	public Microservice_User_Response getUserByUserName(@PathVariable String userName)
			throws Resource_NotFound_Exception {
		return user_Serv.getUserByUserName(userName);
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/Get/AllNotes/{userName}")
	public Microservice_NotesList_Response getAllNotesByUserName(@PathVariable String userName) throws Resource_NotFound_Exception {
		return user_Serv.getAllNotesByUserName(userName);
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/Get/Notes/{userName}/{name}")
	public Microservice_Notes_Response getNotesByName(@PathVariable String userName, @PathVariable String name)
			throws Resource_NotFound_Exception {
		return user_Serv.getNotesByName(userName, name);
	}

	@PreAuthorize("hasAuthority('USER')")
	@PutMapping("/Update/User/{userName}")
	public Microservice_User_Response updateUser(@RequestParam("username") String UserName,
			@RequestParam("password") String Password, @PathVariable String userName)
			throws Resource_NotFound_Exception {
		return user_Serv.updateUser(UserName, Password, userName);
	}

	@PreAuthorize("hasAuthority('USER')")
	@PutMapping("/Update/Notes/{userName}/{name}")
	public Microservice_Notes_Response updateNotes(@RequestParam("file") MultipartFile File,
			@RequestParam("notesname") String NotesName, @RequestParam("notes") String Notes,
			@PathVariable String userName, @PathVariable String name) throws IOException, Resource_NotFound_Exception {
		return user_Serv.updateNotes(File, NotesName, Notes, userName, name);
	}

	@PreAuthorize("hasAuthority('USER')")
	@DeleteMapping("/Delete/Notes/{userName}/{name}")
	public String deleteNotes(@PathVariable String userName, @PathVariable String name)
			throws Resource_NotFound_Exception {
		user_Serv.deleteNotes(userName, name);
		return userName + ", Your " + name + " notes was successfully removed\nStatus : " + HttpStatus.OK.toString();
	}

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@DeleteMapping("/Delete/User/{userName}")
	public String deleteUser(@PathVariable String userName) throws Resource_NotFound_Exception {
		user_Serv.deleteUser(userName);
		return userName + ", your account and all your notes were successfully removed\nStatus : "
				+ HttpStatus.OK.toString();
	}

	@PreAuthorize("hasAuthority('USER')")	
	@GetMapping("/{fileName}/{id}")
	ResponseEntity<byte[]> downloadFile(@PathVariable String fileName, @PathVariable String id,
			HttpServletRequest request) {
		Notes notesResponse = webClient.build().get().uri("http://localhost:1999/Get/NotesById/" + id).retrieve()
				.bodyToMono(Notes.class).block();
		String mimeType = request.getServletContext().getMimeType(fileName);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + fileName).body(notesResponse.getFile());
	}

}
