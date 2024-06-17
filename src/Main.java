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

    public static void main(String[] args) throws IOException {
        BaseData baseData = new BaseData();
        SystemManager systemManager = SystemManager.getInstance();
        baseData.generateGeneralInitDate(systemManager);
        String name = "김현민";
        String phoneNumber = "010-1111-2222";
        try {
            systemManager.login(name, phoneNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        baseData.generateInsuranceBenefits(systemManager);
        baseData.generateQualifications(systemManager);

//        systemManager.getQualificationManager().showCertificateOfQualification((General) systemManager.getLoggedInUser());
//        systemManager.getQualificationManager().showCertificateOfQualificationDetail((General) systemManager.getLoggedInUser());
//        systemManager.getQualificationManager().showCertificateOfQualification((General) systemManager.getLoggedInUser());

//        systemManager.getInsuranceManager().showWorkInsurance((General) systemManager.getLoggedInUser());
        systemManager.getInsuranceManager().showLocalInsurance(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 8, 31), (General) systemManager.getLoggedInUser());
        systemManager.getInsuranceManager().showDetailWorkInsurance((General) systemManager.getLoggedInUser());
//        while (true) {
//            try {
//                if (systemManager.getLoggedInUser() instanceof General) {
//                    generalScreenLogic();
//                } else if (systemManager.getLoggedInUser() instanceof Admin) {
//                    Admin admin = (Admin) systemManager.getLoggedInUser();
//                    admin.adminScreenLogic(systemManager, bf);
//                } else if (systemManager.getLoggedInUser() instanceof Business) {
//
//                } else {
//                    welcomeScreenLogic(systemManager);
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        testMedicalTreatment(systemManager, general);
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
                System.out.print("이름: ");
                String name = bf.readLine();
                System.out.print("번호: ");
                String phoneNumber = bf.readLine();
                manager.login(name, phoneNumber);
                break;
            case "2":
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

    private static void generalScreenLogic() {
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
    }
}