import business.BusinessManager;
import common.Payment;
import general.InsuranceMangement;
import general.QualificationManagement;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static common.Payment.CARD;
import static common.Payment.ACCOUNT;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BusinessManager bm = new BusinessManager();

        // 자격 관리 객체 생성
        QualificationManagement qualificationManagement = new QualificationManagement();
        // 보험료 관리 객체 생성
        InsuranceMangement insuranceMangement = new InsuranceMangement();

        while(true) {
            System.out.println("1.직원 등록");
            System.out.println("2.직원 목록 조회");
            System.out.println("3.직원 삭제");
            System.out.println("4.결제 수단 변경");
            System.out.println("5.보험료 납부 현황 조회");
            System.out.println("6.보험료 납부");
            System.out.println("---------------------------------");
            System.out.println("\n** 자격 관리 시스템 **");
            System.out.println("7. 일반 사용자 추가");
            System.out.println("8. 일반 사용자 자격 확인서 출력");
            System.out.println("9. 일반 사용자 자격 득실 확인서 출력");
            System.out.println("10. 일반 사용자 검색");
            System.out.println("11. 일반 사용자 관계 업데이트");
            System.out.println("12. 일반 사용자 삭제");
            System.out.println("13. 프로그램 종료");

            System.out.print("메뉴를 선택하세요: ");
            String menu = sc.nextLine();

            switch (menu) {
                case "1":
                    bm.addEmployee();
                    break;
                case "2":
                    bm.showEmployees();
                    System.out.println();
                    break;
                case "3":
                    try {
                        bm.deleteEmployee();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    bm.changePayment();
                    break;
                case "5":
                    try {
                        bm.showPaidInsurance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "6":
                    try {
                        bm.payInsurance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "7":
                    qualificationManagement.addGeneral();
                    break;
                case "8":
                    qualificationManagement.showQualificationConfirmation();
                    break;
                case "9":
                    qualificationManagement.showQualificationConfirmationDetail();
                    break;
                case "10":
                    qualificationManagement.searchGeneral();
                    break;
                case "11":
                    qualificationManagement.updateGeneral();
                    break;
                case "12":
                    qualificationManagement.deleteGeneral();
                    break;
                case "13":
                    Payment selectedPayment;
                    int paymentMethod = 1;

                    switch (paymentMethod) {
                        case 1:
                            selectedPayment = Payment.CARD;
                            break;
                        case 2:
                            selectedPayment = Payment.ACCOUNT;
                            break;
                        default:
                            System.out.println("지원하지 않는 결제 방법입니다.");
                            return;
                    }

                    insuranceMangement.defaultPay(selectedPayment);
                    break;
                case "14":
                    insuranceMangement.outoPay();
                    break;
                case "15":
                    insuranceMangement.anotherPersonPay();
                    break;
                case "16":
                    insuranceMangement.calcaulateHealthInsurance(sc.nextDouble());
                    break;
                case "17":
                    insuranceMangement.insurancePremiumNotice();
                    break;
                case "18":
                    insuranceMangement.getWorkInsurance(sc.nextLine());
                    break;
                case "19":
                    insuranceMangement.getDetailWorkInsurance(sc.nextDouble());
                    break;
                case "20":
                    insuranceMangement.getLocalInsuranceNoticeAndPaymentStatus(sc.nextLong());
                    break;
                case "21":
                    insuranceMangement.getLocalInsurance();
                case "22":
                    System.out.println("프로그램 종료");
                    return;
                default:
                    System.out.println("잘못된 메뉴 번호입니다. 다시 입력해주세요.");
                    break;
            }
        }

    }
}