package business;

import user.General;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BusinessManager {
  private LinkedList<General> employees;
  private LinkedList<General> deleteEmployees;
  private Business business;
  Scanner sc = new Scanner(System.in);

  // private common.Payment payment;

  public BusinessManager() {
  }

  public BusinessManager(LinkedList<General> employees, LinkedList<General> deleteEmployees, Business business) {
    this.employees = employees;
    this.deleteEmployees = deleteEmployees;
    this.business = business;
  }

  // 사업장 직원 목록 조회
  public void showEmployees() {
    for (General employee : employees) {
      employee.show();
      System.out.println("===================");
    }
  }

  // 사업장 직원 등록
  public void addEmployee() {
    System.out.println("직원 정보를 입력하세요.");

    System.out.print("이름: ");
    String name = sc.nextLine();
    if (name.isEmpty()) {
      System.out.println("이름은 필수 입력 항목입니다.");
      return;
    }

    System.out.print("생년월일 (YYYY-MM-DD): ");
    String birth = sc.nextLine().trim();
    if(birth.isEmpty()) {
      System.out.println("생년월일은 필수 입력 항목입니다.");
    }

    System.out.print("주민등록번호: ");
    String residentNumber = sc.nextLine();
    if (!residentNumber.matches("\\d{13}")) {
      System.out.println("주민등록번호는 13자리 숫자로 입력해야 합니다.");
      return;
    }

    System.out.print("입사일 (YYYY-MM-DD): ");
    String hireDate = sc.nextLine().trim();
    if (!hireDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
      System.out.println("입사일은 YYYY-MM-DD 형식으로 입력해야 합니다.");
      return;
    }

    System.out.print("연락처: ");
    String phoneNumber = sc.nextLine().trim();
    if(!phoneNumber.matches("\\d{10,11}")) {
      System.out.println("연락처는 10자리 또는 11자리 숫자로 입력해야 합니다.");
      return;
    }

    System.out.print("직책: ");
    String position = sc.nextLine().trim();

    System.out.println("납부자 번호: ");
    long payerNumber;
    try {
      payerNumber = Long.parseLong(sc.nextLine().trim());
    } catch (NumberFormatException e) {
      System.out.println("납부자 번호는 숫자로 입력해야 합니다.");
      return;
    }

    // 중복 검사
    boolean isDuplicate = employees.stream()
            .anyMatch(employee -> employee.getName().equals(name) && employee.getResidentNumber().equals(residentNumber));
    if(isDuplicate) {
      System.out.println("이미 등록된 직원입니다.");
      return;
    }

    General newEmployee = new General(name, birth, phoneNumber, residentNumber, 0, payerNumber, 0);
    employees.add(newEmployee);
    System.out.println("직원 등록이 완료되었습니다.");
    newEmployee.show();
    System.out.println("======================");
  }

  // 사업장 직원 삭제
  public void deleteEmployee(List<String> employeeNames) throws Exception {
    for (String name : employeeNames) {
      General removeEmployee = employees.stream()
          .filter(employee -> employee.getName().equals(name.trim()))
          .findFirst()
          .orElseThrow(() -> new Exception("존재하지 않는 직원입니다."));

      // 사용자에게 삭제 확인 요청
      System.out.print("정말로 " + name.trim() + "님을 삭제하시겠습니까? (Y/N): ");
      String confirm = sc.nextLine().trim().toUpperCase();

      if (confirm.equals("Y")) {
        // 실제 목록에서 삭제되지 않기 때문에 isDelete 상태 값만 true로 변경
        removeEmployee.setDelete(true);
        deleteEmployees.add(removeEmployee);
        System.out.println(name.trim() + "님이 삭제되었습니다.");
      } else {
        System.out.println(name.trim() + "님 삭제가 취소되었습니다.");
      }
      removeEmployee.show();
    }
  }

  public void changePayment() {

  }

  // 보험료 납부 현황 조회
  public void showPaidInsurance() throws Exception {
    System.out.println("당월 보험료 납부 현황");
    System.out.println("이름\t 직책\t 보험료 납부 금액\t 납부 유무");

    for (General employee : employees) {
      if (!employee.isDelete()) {
        System.out.print(employee.getName() + "\t");
        // 직책
        // System.out.print(employee.getName() + "\t");
        System.out.print(employee.getInsuranceFee() + "\t");
        System.out.println(employee.isPaid() ? "납부" : "미납");
        System.out.println("=============================");
      }
    }

    // 경고 메시지 출력
    if (employees.stream()
        .anyMatch(employee -> !employee.isPaid() && !employee.isDelete())) {
      System.out.println("미납 상태인 직원의 보험료를 납부하세요.");
      System.out.println("※ 당월 마지막 영업일까지 보험료를 납부하지 않으면 관리 시스템 사용이 제한됩니다.");

      System.out.print("1. 납부 2. 돌아가기: ");
      int num = Integer.parseInt(sc.nextLine());

      if (num == 1) {
        System.out.print("납부할 직원들의 이름을 쉼표(,)로 구분하여 입력하세요: ");
        String inputNames = sc.nextLine();
        List<String> names = Arrays.asList(inputNames.split("\\s*,\\s"));
        payInsurance(names);
      } else {
        // 아무 동작도 하지 않음
      }
    }
  }

  // 보험료 납부
  public void payInsurance(List<String> names) throws Exception {
    for (String name : names) {
      General employeeToPay = employees.stream()
          .filter(employee -> employee.getName().equals(name))
          .findFirst()
          .orElseThrow(() -> new Exception("해당 이름의 직원을 찾을 수 없습니다."));

      if (employeeToPay.isPaid()) {
        System.out.println(name + "님의 보험료는 이미 납부되었습니다.");
      } else {
        employeeToPay.setPaid(true);
        System.out.println(name + "님의 보험료를 납부하였습니다.");
      }
    }
  }
}
