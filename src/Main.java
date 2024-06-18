import business.Business;
import common.Payment;
import common.SystemManager;
import data.BaseData;
import isuranceBenefit.ItemCode;
import isuranceBenefit.MedicalDevice;
import medicaltreatment.DiseaseCode;
import user.Admin;
import user.General;
import user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

import static data.BaseData.genenrateMedicalDevices;

public class Main {

    private static final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        BaseData baseData = new BaseData();
        SystemManager systemManager = SystemManager.getInstance();
        baseData.generateGeneralInitDate(systemManager);
        baseData.generateInsuranceBenefits(systemManager);
        baseData.generateQualifications(systemManager);
        baseData.testMedicalTreatment(systemManager);
        System.out.println();

        while (true) {
            try {
                welcomeScreenLogic(systemManager);
                if (systemManager.getLoggedInUser() instanceof General) {
                    generalScreenLogic(systemManager);
                } else if (systemManager.getLoggedInUser() instanceof Admin) {
                    Admin admin = (Admin) systemManager.getLoggedInUser();
                    adminScreenLogic(systemManager);
                } else if (systemManager.getLoggedInUser() instanceof Business) {

                } else {
                    welcomeScreenLogic(systemManager);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void welcomeScreenLogic(SystemManager manager) throws Exception {
        System.out.println("========================================");
        System.out.println("\t\t현수진 건강보험 민원 시스템");
        System.out.println("========================================");
        System.out.println("\t1. 로그인");
        System.out.println("\t2. 관리자 로그인");
        System.out.println("\t3. 시스템 종료");
        System.out.println("========================================");
        System.out.print("[번호 입력]: ");
        String menu = bf.readLine();

        switch (menu) {
            case "1":
                System.out.println("\n[일반 사용자 로그인]");
                System.out.print("이름: ");
                String name = bf.readLine();
                System.out.print("번호: ");
                String phoneNumber = bf.readLine();
                manager.login(name, phoneNumber);
                break;
            case "2":
                System.out.println("\n[관리자 로그인]");
                System.out.print("이름: ");
                String adminName = bf.readLine();
                System.out.print("번호: ");
                String adminPhoneNumber = bf.readLine();
                System.out.print("비밀번호: ");
                String password = bf.readLine();
                manager.login(adminName, adminPhoneNumber, password);
                break;
            case "3":
                System.out.println("\n 시스템을 종료합니다. 이용해주셔서 감사합니다.");
                System.exit(0);
        }
    }

    private static void generalScreenLogic(SystemManager manager) throws IOException {
        while (true) {
            System.out.println("========================================");
            System.out.println("\t\t현수진 건강보험 민원 시스템");
            System.out.println("========================================\n");
            System.out.println("\t1. 자격 사항 조회");
            System.out.println("\t2. 보험료 관리");
            System.out.println("\t3. 요양비 관련 서비스");
            System.out.println("\t4. 로그아웃\n");
            System.out.println("========================================");
            System.out.print("[번호 입력]: ");
            String menu = bf.readLine();
            switch (menu) {
                case "1":
                    showQualification(manager);
                    break;
                case "2":
                    try {
                        showInsuranceBenefits(manager);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    medicalTreatment(manager);
                    break;
                case "4":
                    manager.logout();
                    return;
            }
        }
    }

    private static void showInsuranceBenefits(SystemManager manager) throws Exception {
        while (true) {
            System.out.println("========================================");
            System.out.println("\t 현수진 건강보험 민원 시스템-보험료 관리");
            System.out.println("========================================\n");
            System.out.println("\t1. 직장 보험료 조회");
            System.out.println("\t2. 지역 보험료 조회");
            System.out.println("\t3. 보험료 납부");
            System.out.println("\t4. 자동이체 신청");
            System.out.println("\t5. 보험료 납부현황 조회");
            System.out.println("\t6. 내 건강보험료 계산기\n");
            System.out.println("\t7. 뒤로가기\n");
            System.out.println("========================================");
            System.out.print("[번호 입력]: ");
            String menu = bf.readLine();
            switch (menu) {
                case "1":
                    manager.getInsuranceManager().showWorkInsurance((General) manager.getLoggedInUser());
                    break;
                case "2":
                    System.out.print("시작일 입력: ");
                    LocalDate startDate = LocalDate.parse(bf.readLine());
                    System.out.print("종료일 입력: ");
                    LocalDate endDate = LocalDate.parse(bf.readLine());
                    manager.getInsuranceManager().showLocalInsurance(startDate, endDate, (General) manager.getLoggedInUser());
                    break;
                case "3":
                    System.out.println("========================================");
                    System.out.println("\t 현수진 건강보험 민원 시스템-보험료 납부");
                    System.out.println("========================================\n");
                    System.out.println("\t1. 카드 납부");
                    System.out.println("\t2. 계좌이체 납부");
                    System.out.println("\t3. 뒤로가기\n");
                    System.out.println("========================================");
                    System.out.print("[번호 입력]: ");
                    String paymentMenu = bf.readLine();
                    switch (paymentMenu) {
                        case "1":
                            manager.getInsuranceManager().defaultPay(Payment.CARD, (General) manager.getLoggedInUser());
                            break;
                        case "2":
                            manager.getInsuranceManager().defaultPay(Payment.ACCOUNT, (General) manager.getLoggedInUser());
                            break;
                        case "3":
                            return;
                    }
                    break;
                case "4":
                    System.out.println("========================================");
                    System.out.println("\t 현수진 건강보험 민원 시스템-자동이체 신청");
                    System.out.println("========================================\n");
                    System.out.print("은행 이름 입력: ");
                    String bankName = bf.readLine();
                    System.out.print("계좌 번호 입력: ");
                    String accountNo = bf.readLine();
                    manager.getInsuranceManager().autoPay(bankName, accountNo, (General) manager.getLoggedInUser());
                    break;
                case "5":
                    manager.getInsuranceManager().showDetailWorkInsurance((General) manager.getLoggedInUser());
                    break;
                case "6":
                    System.out.print("예상 월급을 입력해주세요");
                    long salary = Long.parseLong(bf.readLine());
                    manager.getInsuranceManager().calculateHealthInsurance(salary);
                case "7":
                    return;
            }
        }
    }

    private static void medicalTreatment(SystemManager manager) throws IOException {
        while (true) {
            System.out.println("========================================");
            System.out.println("\t 현수진 건강보험 민원 시스템-요양비 관련 서비스");
            System.out.println("========================================\n");
            System.out.println("\t1. 요양비 대상자 신청");
            System.out.println("\t2. 의료기기 대여");
            System.out.println("\t3. 의료기기 반납");
            System.out.println("\t4. 요양비 청구");
            System.out.println("\t5. 요양비 내역 조회");
            System.out.println("\t6. 뒤로가기\n");
            System.out.println("========================================");
            System.out.print("[번호 입력]: ");
            String menu = bf.readLine();
            switch (menu) {
                case "1":
                    DiseaseCode.showDiseaseCode();
                    System.out.print("질병 코드 입력: ");
                    DiseaseCode diseaseCode = DiseaseCode.getDiseaseCode(bf.readLine());
                    ItemCode.showValues();
                    System.out.print("의료기기 코드 입력: ");
                    String deviceCode = bf.readLine();
                    manager.getTreatmentManager().insertMedicalTreatment(diseaseCode, ItemCode.getCode(deviceCode), (General) manager.getLoggedInUser());
                    break;
                case "2":
                    ItemCode.showValues();
                    System.out.print("의료기기 코드 입력: ");
                    ItemCode itemCode = ItemCode.getCode(bf.readLine());
                    System.out.print("의료기기 이름 입력: ");
                    String deviceName = bf.readLine();
                    System.out.print("대여 시작일 입력(yyyy-MM-dd): ");
                    LocalDate rentalDate = LocalDate.parse(bf.readLine());
                    System.out.print("대여 종료일 입력(yyyy-MM-dd): ");
                    LocalDate returnDate = LocalDate.parse(bf.readLine());
                    System.out.print("대여 수량 입력: ");
                    long quantity = Long.parseLong(bf.readLine());
                    System.out.print("대여 비용 입력: ");
                    long price = Long.parseLong(bf.readLine());
                    manager.getTreatmentManager().rentalMedicalDevice(itemCode, deviceName, Payment.ACCOUNT, (General) manager.getLoggedInUser(), rentalDate, returnDate, quantity, price);
                    System.out.println("의료기기 대여 신청이 완료 되었습니다.\n");
                    break;
                case "3":
                    ItemCode.showValues();
                    System.out.print("의료기기 코드 입력: ");
                    ItemCode returnItemCode = ItemCode.getCode(bf.readLine());
                    System.out.print("의료기기 이름 입력: ");
                    String returnDeviceName = bf.readLine();
                    System.out.print("반납 수량 입력: ");
                    long returnQuantity = Long.parseLong(bf.readLine());
                    manager.getTreatmentManager().returnMedicalDevice(returnItemCode, returnDeviceName, (General) manager.getLoggedInUser(), returnQuantity);
                    System.out.println("의료기기 반납이 완료 되었습니다.\n");
                    break;
                case "4":
                    System.out.println("계좌 형식(xxxx-xx-xxxxxx)");
                    System.out.print("환급 계좌 입력: ");
                    String account = bf.readLine();
                    manager.getTreatmentManager().chargeMedicalTreatment(account, (General) manager.getLoggedInUser());
                    break;
                case "5":
                    manager.getTreatmentManager().showMedicalTreatmentsByGeneral((General) manager.getLoggedInUser());
                    break;
                case "6":
                    return;
            }

        }


    }

    private static void adminScreenLogic(SystemManager manager) throws IOException {
        System.out.println("========================================");
        System.out.println("\t 현수진 건강보험 민원 관리자 시스템");
        System.out.println("========================================\n");
        System.out.println("\t1. 보험급여 관리");
        System.out.println("\t2. 로그아웃\n");
        System.out.println("========================================");
        System.out.print("[번호 입력]: ");
        String menu = bf.readLine();

        switch (menu) {
            case "1":
                manageInsuranceBenefits(manager);
                break;
            case "2":
                System.out.println("\n 로그아웃 합니다.");
                manager.logout();
                break;
        }
    }

    private static void manageInsuranceBenefits(SystemManager manager) throws IOException {
        System.out.println("========================================");
        System.out.println("\t 현수진 건강보험 민원 관리자 시스템");
        System.out.println("========================================\n");
        System.out.println("\t1. 요양비 통계 조회");
        System.out.println("\t2. 요양비 대상자 조회");
        System.out.println("\t3. 의료기기 관리");
        System.out.println("\t4. 로그아웃\n");
        System.out.println("========================================");
        System.out.print("[번호 입력]: ");

        String menu = bf.readLine();
        switch (menu) {
            case "1":
                System.out.print("조회 시작일 입력(yyyy-MM-dd): ");
                LocalDate startDate = LocalDate.parse(bf.readLine());
                System.out.print("조회 종료일 입력(yyyy-MM-dd): ");
                LocalDate endDate = LocalDate.parse(bf.readLine());
                manager.getBenefitManager().showMedicalCareStatustics(startDate, endDate, manager.getTreatmentManager());
                break;
            case "2":

                break;
            case "3":
                System.out.println("\n 로그아웃 합니다.");
                manager.logout();
                break;
        }
    }

    private static void showQualification(SystemManager manager) throws IOException {
        while (true) {
            System.out.println("========================================");
            System.out.println("현수진 건강보험 민원 시스템-자격 사항 조회");
            System.out.println("========================================\n");
            System.out.println("\t1. 자격 사항 요약 확인서 조회");
            System.out.println("\t2. 자격 사항 상세 확인서 조회");
            System.out.println("\t3. 자격 득실 확인서");
            System.out.println("\t4. 뒤로가기\n");
            System.out.println("========================================");
            System.out.print("[번호 입력]: ");
            String menu = bf.readLine();
            switch (menu) {
                case "1":
                    manager.getQualificationManager().showCertificateOfQualification((General) manager.getLoggedInUser());
                    break;
                case "2":
                    manager.getQualificationManager().showCertificateOfQualificationDetail((General) manager.getLoggedInUser());
                    break;
                case "3":
                    manager.getQualificationManager().showCertificateOfEligibilityn((General) manager.getLoggedInUser());
                    break;
                case "4":
                    return;
            }
        }
    }
}