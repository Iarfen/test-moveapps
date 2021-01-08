package moveapps;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;

import lombok.Data;

@Entity
@Data
class Phone {

  private @Id @GeneratedValue Long id;
  private String number;
  private String cityCode;
  private String countryCode;
  
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  Phone() {}

  Phone(String number, String cityCode, String countryCode) {

    this.number = number;
    this.cityCode = cityCode;
    this.countryCode = countryCode;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Phone))
      return false;
    Phone phone = (Phone) o;
    return Objects.equals(this.id, phone.id) && Objects.equals(this.number, phone.number)
        && Objects.equals(this.cityCode, phone.cityCode)
        && Objects.equals(this.countryCode, phone.countryCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.number, this.cityCode, this.countryCode);
  }

  @Override
  public String toString() {
    return "Phone{" + "id=" + this.id + ", number='" + this.number + '\'' + ", cityCode='" + this.cityCode + '\'' + ", countryCode='" + this.countryCode + '\'' + '}';
  }
}