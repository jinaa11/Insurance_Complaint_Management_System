import business.Business;
import business.BusinessManager;
import user.General;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Business business = new Business(1L, "A회사");
        Scanner sc = new Scanner(System.in);

        LinkedList<General> employees = new LinkedList<>();
        LinkedList<General> deleteEmployees = new LinkedList<>();

        BusinessManager businessManager = new BusinessManager(employees, deleteEmployees, business);

        // 직원 추가
        General employee1 = new General("김진아", "19970101", "01012345678", "9701012111111", 12345, 567895, 100000);
        General employee2 = new General("이진아", "20000101", "01056789105", "0001012111111", 65478, 456866, 10000);
        General employee3 = new General("박진아", "20000101", "01056789105", "0001012111111", 65478, 456866, 10000);
        General employee4 = new General("유진아", "20000101", "01056789105", "0001012111111", 65478, 456866, 10000);
        General employee5 = new General("서진아", "20000101", "01056789105", "0001012111111", 65478, 456866, 10000);

        businessManager.addEmployee();

        // 사업장 직원 목록 조회 예시
        System.out.println("=== 사업장 직원 목록 ===");
        businessManager.showEmployees();
        System.out.println();

        // 직원 삭제

        try {
            System.out.println();
            System.out.println("==== 사업장 직원 삭제 ===");
            System.out.print("삭제할 직원들의 이름을 쉼표로 구분하여 입력하세요: ");

            String inputNames = sc.nextLine();
            List<String> names = Arrays.asList(inputNames.split("\\s*,\\s"));

            businessManager.deleteEmployee(names);

            System.out.println();
            System.out.println("=== 삭제 후 사업장 직원 목록 ===");
            businessManager.showEmployees();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 보험료 납부
        try {
            System.out.println();
            System.out.println("=== 보험료 납부 ===");
            System.out.print("납부할 직원들의 이름을 쉼표(,)로 구분하여 입력하세요: ");
            String inputNames = sc.nextLine();
            List<String> names = Arrays.asList(inputNames.split("\\s*,\\s"));
            businessManager.payInsurance(names);

            System.out.println();
            System.out.println("=== 납부 후 보험료 납부 현황 === ");
            businessManager.showPaidInsurance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}