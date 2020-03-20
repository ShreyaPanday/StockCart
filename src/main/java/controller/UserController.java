package controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import repository.UserRepository;

@RestController
@RequestMapping("/api/User")
public class UserController {
 
	@Autowired UserRepository userRepo;
	  @Autowired
	   

	    @GetMapping("/User")
	    public List < User > getAllUsers() {
	        return userRepo.findAll();
	    }

	    @GetMapping("/User/{id}")
	    public ResponseEntity < User > getUsersById(@PathVariable(value = "id") Long UserId)
	    throws ResourceNotFoundException {
	        User user = userRepo.findById(UserId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + UserId));
	        return ResponseEntity.ok().body(user);
	    }

	    @PostMapping("/User")
	    public User createUser(@Valid @RequestBody User user) {
	        return userRepo.save(user);
	    }

	    @PutMapping("/User/{id}")
	    public ResponseEntity < User > updateUser(@PathVariable(value = "id") Long UserId,
	        @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
	        User user = userRepo.findById(UserId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + UserId));

	        
	        
	        user.setFirstName(userDetails.getFirstName());
	        user.setLastName(userDetails.getLastName());
	        user.setUsername(userDetails.getUserName());
	        user.setPassword(userDetails.getPassword());
	        
	        final User updateduser = userRepo.save(user);
	        return ResponseEntity.ok(updateduser);
	    }

	    @DeleteMapping("/User/{id}")
	    public Map < String, Boolean > deleteUser(@PathVariable(value = "id") Long UserId)
	    throws ResourceNotFoundException {
	        User user = userRepo.findById(UserId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + UserId));

	        userRepo.delete(user);
	        Map < String, Boolean > response = new HashMap < > ();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
	}
}
}
