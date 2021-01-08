package moveapps;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(UserRepository repository,PhoneRepository phoneRepository) {
	 Phone phone1 = new Phone("12345678","9","56");
	 Phone phone2 = new Phone("56735401","9","56");
	 Phone phone3 = new Phone("45689612","9","56");
	 Phone phone4 = new Phone("43679854","9","56");
	 phoneRepository.save(phone1);
	 phoneRepository.save(phone2);
	 phoneRepository.save(phone3);
	 phoneRepository.save(phone4);
	 ArrayList<Phone> phonesUser1 = new ArrayList<>();
	 phonesUser1.add(phone1);
	 phonesUser1.add(phone2);
	 ArrayList<Phone> phonesUser2 = new ArrayList<>();
	 phonesUser2.add(phone3);
	 phonesUser2.add(phone4);
	 String token1 = User.createJWT("1", "prueba", "subject1", 123045);
	 String token2 = User.createJWT("1", "prueba2", "subject2", 34575);
    return args -> {
      log.info("Preloading " + repository.save(new User("prueba@gmail.com", "1234", phonesUser1, new Timestamp(System.currentTimeMillis()), token1)));
      log.info("Preloading " + repository.save(new User("prueba2@gmail.com", "5678", phonesUser2, new Timestamp(System.currentTimeMillis()), token2)));
    };
  }
}