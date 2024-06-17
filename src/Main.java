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
        System.out.println("========================================");
        System.out.println("\t\t현수진 건강보험 민원 시스템");
        System.out.println("========================================\n");
        System.out.println("\t1. 자격 사항 조회");
        System.out.println("\t2. 보험료 납부 관리");
        System.out.println("\t3. 보험료 납부 현황 조회");
        System.out.println("\t4. 요양비 관련 서비스");
        System.out.println("\t5. 로그아웃\n");
        System.out.println("========================================");
        System.out.print("[번호 입력]: ");
        String menu = bf.readLine();

        switch (menu) {
            case "1":
                showQualification(manager);
                break;
        }

    }

    private static void adminScreenLogic(SystemManager manager) throws IOException {
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
                manageInsuranceBenefits(manager);
                break;
            case "2":

                break;
            case "3":
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
        System.out.println("========================================");
        System.out.println("현수진 건강보험 민원 시스템-자격 사항 조회");
        System.out.println("========================================\n");
        System.out.println("\t1. 자격 사항 요약 확인서 조회");
        System.out.println("\t2. 자격 사항 상세 확인서 조회");
        System.out.println("\t3. 자격 득실 확인서");
        System.out.println("\t5. 로그아웃\n");
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
        }
    }
}