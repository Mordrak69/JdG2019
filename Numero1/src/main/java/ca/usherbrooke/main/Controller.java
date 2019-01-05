package ca.usherbrooke.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired private AlienService alienService;
	
	@RequestMapping(value = "/api/alien/", method = RequestMethod.GET)
	public ResponseEntity<String> getAll() {
		String jsonString;
		try {
			List<Alien> aliens = alienService.findAll();
			
			JSONObject rootJSON = new JSONObject();
			List<JSONObject> aliensJSON = new ArrayList<>();
			
			for(Alien alien : aliens) {
				JSONObject alienJSON = new JSONObject();
				alienJSON.put("name", alien.getName());
				List<String> friends = new ArrayList<>();
				for(Alien friend : alien.getFriends()) {
					friends.add(friend.getName());
				}
				alienJSON.put("friends", friends);
				alienJSON.put("age", alien.getAge());
				aliensJSON.add(alienJSON);
			}
			
			rootJSON.put("aliens", aliensJSON);
			jsonString = rootJSON.toString(4);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(encode(jsonString));
	}
	
	@RequestMapping(value = "/api/alien/name/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> get(@PathVariable("id") Long id) {
		String jsonString;
		try {
			Alien alien = alienService.get(id);
			if(alien == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			JSONObject alienJSON = new JSONObject();
			alienJSON.put("name", alien.getName());
			List<String> friends = new ArrayList<>();
			for(Alien friend : alien.getFriends()) {
				friends.add(friend.getName());
			}
			alienJSON.put("friends", friends);
			alienJSON.put("age", alien.getAge());
			jsonString = alienJSON.toString(4);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(encode(jsonString));
	}
	
	@RequestMapping(value = "/api/alien/", method = RequestMethod.POST)
	public ResponseEntity<Object> post(@RequestBody String payload) {
		try {
			payload = decode(payload);
			
			JSONObject object = new JSONObject(payload);
			String name = object.optString("name", null);
			Long age = object.optNumber("age", null) == null ? null : object.optNumber("age", null).longValue();
			
			if(age == null || name == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
			List<Alien> friends = new ArrayList<>();
			JSONArray friendsJSONArray = object.optJSONArray("friends");
			if(friendsJSONArray != null) {
				for(int i = 0; i < friendsJSONArray.length(); i++) {
					String friendName = friendsJSONArray.getString(i);
					Alien friend = alienService.get(friendName);
					if(friend == null) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
					}
					friends.add(friend);
				}
			}
			
			
			Alien alien = new Alien();
			alien.setName(name);
			alien.setAge(age);
			alien.setFriends(friends);
			
			alienService.save(alien);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@RequestMapping(value = "/api/alien/name/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> put(@PathVariable("id") Long id, @RequestBody String payload) {
		try {
			Alien alien = alienService.get(id);
			if(alien == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
			payload = decode(payload);
			
			JSONObject object = new JSONObject(payload);
			String name = object.optString("name");
			Long age = object.optNumber("age", null) == null ? null : object.optNumber("age", null).longValue();
			
			if(age == null || name == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
			List<Alien> friends = new ArrayList<>();
			JSONArray friendsJSONArray = object.optJSONArray("friends");
			if(friendsJSONArray != null) {
				for(int i = 0; i < friendsJSONArray.length(); i++) {
					String friendName = friendsJSONArray.getString(i);
					Alien friend = alienService.get(friendName);
					if(friend == null) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
					}
					friends.add(friend);
				}
			}
			
			alien.setName(name);
			alien.setAge(age);
			alien.setFriends(friends);
			
			alienService.save(alien);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@RequestMapping(value = "/api/alien/name/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		try {
			Alien alien = alienService.get(id);
			if(alien == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
			alienService.delete(id);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	public String encode(String payload) {
		payload = payload.replace("{", "^");
		payload = payload.replace("}", "&");
		payload = payload.replace("[", "/*");
		payload = payload.replace("]", "*/");
		payload = payload.replace("\"", "'");
		return payload;
	}
	
	public String decode(String payload) {
		payload = payload.replace("^", "{");
		payload = payload.replace("&", "}");
		payload = payload.replace("/*", "[");
		payload = payload.replace("*/", "]");
		payload = payload.replace("'", "\"");
		return payload;
	}
}
