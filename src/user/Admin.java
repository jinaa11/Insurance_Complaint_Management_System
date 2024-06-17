package user;

public class Admin extends User {
  private String password;

  private Admin() {}
  public Admin(String name, String birth, String phoneNumber, String residentNumber, String password){
    super(name, birth, phoneNumber, residentNumber);
    this.password = password;
  }

  public String getPassword() {
    return this.password;
  }
}
