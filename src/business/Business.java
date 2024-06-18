package business;

import user.User;

public class Business extends User{
  private long bid;
  private String bname;
  private String password;

  public Business() {}

  public Business(String name, String birth, String phoneNumber, String residentNumber, long bid, String bname, String password) {
    super(name, birth, phoneNumber, residentNumber);
    this.bid = bid;
    this.bname = bname;
    this.password = password;
  }

  public long getBid() {
    return bid;
  }

  public void setBid(long bid) {
    this.bid = bid;
  }

  public String getBname() {
    return bname;
  }

  public void setBname(String bname) {
    this.bname = bname;
  }
}
