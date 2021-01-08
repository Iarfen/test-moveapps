package moveapps;

import java.util.Objects;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import lombok.Data;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.jsonwebtoken.*;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Data
@Table(name = "MoveAppsUser")
class User {

  private @Id @GeneratedValue Long id;
  @Email(message = "\"{\\\"codigo\\\": \\\"400\\\", \\\"mensaje\\\": \\\"Email inválido\\\"}\"")
  private String email;
  @Pattern(regexp="^[A-Z][a-z]*[0-9][0-9]",message="\"{\\\"codigo\\\": \\\"401\\\", \\\"mensaje\\\": \\\"Contraseña inválida\\\"}\"")
  private String password;
  
  @OneToMany
  @JoinTable(name = "user_phones", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "phone_id"))
  private List<Phone> phones;
  
  @CreationTimestamp
  private Timestamp created;
  @UpdateTimestamp
  private Timestamp modified;
  private Timestamp lastLogin;
  
  private String token;
  private Boolean isActive;

  User() {}

  User(String email, String password, List<Phone> phones, Timestamp lastLogin, String token) {

    this.email = email;
    this.password = password;
    this.phones = phones;
    this.lastLogin = lastLogin;
    this.token = token;
    this.isActive = true;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof User))
      return false;
    User user = (User) o;
    return Objects.equals(this.id, user.id) && Objects.equals(this.email, user.email)
        && Objects.equals(this.password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.email, this.password);
  }

  @Override
  public String toString() {
    return "User{" + "id=" + this.id + ", email='" + this.email + '\'' + ", password='" + this.password + '\'' + ", phones='" + this.phones + '\'' + ", created='" + this.created + '\'' + ", token='" + this.token + ", isActive='" + this.isActive.toString() + '\'' + '\'' + '}';
  }
  
  public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
	  String jwt = Jwts.builder()
			  .setSubject("users/TzMUocMF4p")
			  .setExpiration(new Date(1300819380))
			  .claim("name", "Robert Token Man")
			  .claim("scope", "self groups/admins")
			  .compact();
	 return jwt;
  }
}