package user;

import common.SystemManager;

import java.io.BufferedReader;
import java.io.IOException;

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

  public void adminScreenLogic(SystemManager manager, BufferedReader bf) throws IOException {
    System.out.println("========================================");
    System.out.println("\t 현수진 건강보험 민원 관리자 시스템");
    System.out.println("========================================\n");
    System.out.println("\t1. 보험급여 관리");
    System.out.println("\t2. 요양비 관련 서비스");
    System.out.println("\t3. 로그아웃\n");
    System.out.println("========================================");
    System.out.print("[번호 입력]: ");
    String menu = bf.readLine();

    switch (menu) {
      case "1":

        break;
      case "2":

        break;
      case "3":
        System.out.println("\n 로그아웃 합니다.");
        manager.logout();
        break;
    }
  }
}
