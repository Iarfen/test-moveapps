package moveapps;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LoginController {

  private final UserRepository repository;

  LoginController(UserRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/login")
  String login(@RequestBody User loginUser) {
	  List<User> userList = repository.findAll();
	  for (User x_user : userList)
	  {
		  if (x_user.getEmail() == loginUser.getEmail() && x_user.getPassword() == loginUser.getPassword())
		  {
			  return x_user.getToken();
		  }
	  }
    return "{\"codigo\": \"401\", \"mensaje\": \"Email o contraseña inválidos\"}";
  }
}