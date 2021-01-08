package moveapps;

class UserNotFoundException extends RuntimeException {

	UserNotFoundException(Long id) {
    super("No se encontró el usuario de id " + id);
  }
}