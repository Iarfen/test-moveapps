package moveapps;

import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
class UserController {

  private final UserRepository repository;
  private final PhoneRepository phoneRepository;

  UserController(UserRepository repository, PhoneRepository phoneRepository) {
    this.repository = repository;
    this.phoneRepository = phoneRepository;
  }


  @GetMapping("/users")
  @CrossOrigin(origins = "http://localhost:3000")
  List<User> all() {
	  List<User> userList = repository.findAll();
	  List<User> filteredUserList = new ArrayList<>();
	  for (User x_user : userList)
	  {
		  if (x_user.getIsActive() == true)
		  {
			  filteredUserList.add(x_user);
		  }
	  }
    return filteredUserList;
  }

  @PostMapping("/users")
  User newUser(@RequestBody User newUser) {
	  List<User> userList = repository.findAll();
	  for (User x_user : userList)
	  {
		  if (x_user.getEmail() == newUser.getEmail())
		  {
			  throw new RuntimeException("\"{\\\"codigo\\\": \\\"401\\\", \\\"mensaje\\\": \\\"El correo ya está registrado\\\"}\"");
		  }
	  }
	  
	  List<Phone> phoneList = newUser.getPhones();
	  for (Phone x_phone : phoneList)
	  {
		  phoneRepository.save(x_phone);
	  }
	  newUser.setLastLogin(new Timestamp(System.currentTimeMillis()));
	  newUser.setToken(User.createJWT("4", "prueba3", "subject3", 768934245));
    return repository.save(newUser);
  }

  @GetMapping("/users/{id}")
  User one(@PathVariable Long id) {

    return repository.findById(id)
      .orElseThrow(() -> new UserNotFoundException(id));
  }

  @PutMapping("/users/{id}")
  User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

    return repository.findById(id)
      .map(user -> {
    	  user.setEmail(newUser.getEmail());
    	  user.setPassword(newUser.getPassword());
    	  user.setPhones(newUser.getPhones());
    	  user.setModified(new Timestamp(System.currentTimeMillis()));
        return repository.save(user);
      })
      .orElseGet(() -> {
        newUser.setId(id);
        return repository.save(newUser);
      });
  }

  @DeleteMapping("/users/{id}")
  void deleteUsers(@PathVariable Long id) {
    repository.deleteById(id);
  }
}