package business;

import user.User;

public class Business extends User{
  private long bid;
  private String bname;

  public Business() {}

  public Business(String name, String birth, String phoneNumber, String residentNumberlong, long bid, String bname) {
    super(name, birth, phoneNumber, residentNumberlong);
    this.bid = bid;
    this.bname = bname;
  }

  public void show() {
    System.out.println("사업장 번호: " + bid);
    System.out.println("사업장명: " + bname);
  }

  public void searchBusiness(long bid) {
    
  }

  public void save(String actionNo, long pid, String relationship, String bname, String acquire, String lossDate) {

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
