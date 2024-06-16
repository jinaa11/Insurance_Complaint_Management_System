package user;

public abstract class User {
  private static long ID_VALUE = 1L;
  private long userId;
  private String name;
  private String birth;
  private String phoneNumber;
  private String residentNumber;

  public User() {}
  public User(String name, String birth, String phoneNumber, String residentNumber) {
    this.userId = ID_VALUE++;
    this.name = name;
    this.birth = birth;
    this.phoneNumber = phoneNumber;
    this.residentNumber = residentNumber;
  }

  public static long getIdValue() {
    return ID_VALUE;
  }

  public static void setIdValue(long idValue) {
    ID_VALUE = idValue;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirth() {
    return birth;
  }

  public void setBirth(String birth) {
    this.birth = birth;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getResidentNumber() {
    return residentNumber;
  }

  public void setResidentNumber(String residentNumber) {
    this.residentNumber = residentNumber;
  }
}
