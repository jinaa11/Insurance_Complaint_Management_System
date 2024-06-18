package business;

import common.Payment;
import common.SystemManager;
import general.WorkInfo;
import user.Business;
import user.General;
import user.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BusinessManager {
  private LinkedList<General> employees;
  private LinkedList<General> deleteEmployees;
  private Business business;
  Scanner sc = new Scanner(System.in);

  public BusinessManager() {
    employees = new LinkedList<>();
    deleteEmployees = new LinkedList<>();
  }

  public BusinessManager(LinkedList<General> employees, LinkedList<General> deleteEmployees) {
    this.employees = employees;
    this.deleteEmployees = deleteEmployees;
  }

    public void setBusiness(Business business) {
        this.business = business;
    }

    // 사업장 직원 목록 조회
    public void showEmployees() {
        System.out.println("========================================");
        System.out.println("\t\t사업장 직원 목록 조회");
        System.out.println("========================================");
        for (General employee : employees) {
            employee.showBusinessEmployees();
            System.out.println("----------------------------------------");
        }

    }

    // 사업장 직원 등록
    public void addEmployee(SystemManager manager) throws Exception {
        System.out.println("========================================");
        System.out.println("\t\t사업장 직원 등록");
        System.out.println("========================================");
        System.out.println("직원 정보를 입력하세요.");

        System.out.print("이름: ");
        String name = sc.nextLine();
        if (name.isEmpty()) {
            System.out.println("이름은 필수 입력 항목입니다.");
            return;
        }

        System.out.print("생년월일 (YYYY-MM-DD): ");
        String birth = sc.nextLine().trim();
        if (birth.isEmpty()) {
            System.out.println("생년월일은 필수 입력 항목입니다.");
        }

        System.out.print("주민등록번호: ");
        String residentNumber = sc.nextLine();
        if (!residentNumber.matches("\\d{6}-\\d{7}")) {
            System.out.println("주민등록번호는 13자리 숫자로 입력해야 합니다.");
            return;
        }

        System.out.print("연락처 (예.010-1111-2222): ");
        String phoneNumber = sc.nextLine().trim();
        if (!phoneNumber.matches("\\d{3}-\\d{4}-\\d{4}")) {
            System.out.println("연락처는 10자리 또는 11자리 숫자로 입력해야 합니다.");
            return;
        }

        System.out.print("직책: ");
        String position = sc.nextLine().trim();
        System.out.println("========================================");


        // 중복 검사
        boolean isDuplicate = employees.stream()
                .anyMatch(employee -> employee.getName().equals(name) && employee.getResidentNumber().equals(residentNumber));
        if (isDuplicate) {
            System.out.println("이미 등록된 직원입니다.");
            return;
        }

        WorkInfo workInfo = new WorkInfo(business.getBid(), position, business.getBname());
        // 직원 조회
        List<User> users = manager.getUsers();
        General general = (General) users.stream()
                .filter(user -> user.getName().equals(name) && user.getResidentNumber().equals(residentNumber) && user instanceof General)
                .filter(user -> user.getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .orElseThrow(() -> new Exception("존재하지 않는 사용자입니다."));
        general.setWorkInfo(workInfo);
        employees.add(general);
        System.out.println("직원 등록이 완료되었습니다.");
    }

    // 사업장 직원 삭제
    public void deleteEmployee() throws Exception {
        System.out.println("========================================");
        System.out.println("\t\t사업장 직원 삭제");
        System.out.println("========================================");
        System.out.print("삭제할 직원들의 이름을 쉼표로 구분하여 입력하세요: ");
        String inputNames = sc.nextLine();
        List<String> employeeNames = Arrays.asList(inputNames.split("\\s*,\\s"));

        for (String name : employeeNames) {
            General removeEmployee = employees.stream()
                    .filter(employee -> employee.getName().equals(name.trim()))
                    .findFirst()
                    .orElseThrow(() -> new Exception("존재하지 않는 직원입니다."));

            // 사용자에게 삭제 확인 요청
            System.out.print("정말로 " + name.trim() + "님을 삭제하시겠습니까? (Y/N): ");
            String confirm = sc.nextLine().trim().toUpperCase();

            if (confirm.equals("Y")) {
                // 삭제한 직원을 리스트로 관리
                deleteEmployees.add(removeEmployee);
                employees.remove(removeEmployee);
                System.out.println(name.trim() + "님이 삭제되었습니다.");
            } else {
                System.out.println(name.trim() + "님 삭제가 취소되었습니다.");
            }
        }
    }

    // 결제 수단 변경
    public void changePayment() {
        System.out.print("등록할 결제 수단을 입력하세요.(카드/계좌): ");
        String payment = sc.nextLine().trim();
        if (payment.equals(Payment.CARD.getValue())) {
            System.out.print("카드 번호 입력 (16자리): ");
            String cardNo = sc.nextLine().trim();

            // 카드 번호 정규표현식 체크
            if (Pattern.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$", cardNo)) {
                System.out.println("카드 등록이 완료되었습니다.");
                System.out.println("등록된 카드 번호: " + cardNo);
            } else {
                System.out.println("잘못된 카드 번호입니다.");
            }
        } else if (payment.equals(Payment.ACCOUNT.getValue())) {
            System.out.print("은행명: ");
            String bankName = sc.nextLine().trim();
            System.out.print("계좌 번호 입력 (10~14자리): ");
            String accountNo = sc.nextLine().trim();

            // 계좌 번호 정규표현식 체크
            if (Pattern.matches("\"^\\\\d{4}-\\\\d{2}-\\\\d{6}$\"", accountNo)) {
                System.out.println("계좌 번호 등록이 완료되었습니다.");
                System.out.println("은행명: " + bankName + ", 계좌 번호: " + accountNo);
            } else {
                System.out.println("잘못된 계좌 번호입니다.");
            }
        }
        if (!Payment.isValidPayment(payment)) {
            System.out.println("잘못된 결제 수단입니다.");
        }
    }

    // 보험료 납부 현황 조회
    public void showPaidInsurance() throws Exception {
        System.out.println("========================================");
        System.out.println("\t당월 보험료 납부 현황");
        System.out.println("========================================");
        System.out.println("이름\t\t직책\t\t보험료 납부 금액\t납부 유무");

        for (General employee : employees) {
            System.out.print(employee.getName() + "\t");
            System.out.print(employee.getWorkInfo().getPosition() + "\t\t");
            System.out.print(employee.getInsuranceFee() + "\t\t\t");
            System.out.println(employee.isPaid() ? "납부" : "미납");
            System.out.println("----------------------------------------");
        }

        // 경고 메시지 출력
        if (employees.stream()
                .anyMatch(employee -> !employee.isPaid())) {
            System.out.println("미납 상태인 직원의 보험료를 납부하세요.");
            System.out.println("※ 당월 마지막 영업일까지 보험료를 납부하지 않으면 관리 시스템 사용이 제한됩니다.");

            System.out.print("1. 납부 2. 돌아가기: ");
            int num = Integer.parseInt(sc.nextLine());

            if (num == 1) {
                payInsurance();
            }
        }
    }

    // 보험료 납부
    public void payInsurance() throws Exception {
        System.out.println("========================================");
        System.out.println("\t보험료 납부하기");
        System.out.println("========================================");

        System.out.print("납부할 직원들의 이름을 쉼표(,)로 구분하여 입력하세요: ");
        String inputNames = sc.nextLine();
        List<String> names = Arrays.asList(inputNames.split("\\s*,\\s"));

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