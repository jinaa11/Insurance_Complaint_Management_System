package data;

import user.Business;
import common.Payment;
import common.SystemManager;
import general.Qualification;
import general.WorkInfo;
import isuranceBenefit.InsuranceBenefit;
import isuranceBenefit.ItemCode;
import isuranceBenefit.MedicalDevice;
import medicaltreatment.DiseaseCode;
import user.Admin;
import user.General;
import user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseData {

    public void generateGeneralInitDate(SystemManager manager) {
        List<User> users = List.of(
                // Admin 객체 생성
                new Admin("관리자1", "1980-05-01", "010-1234-5678", "850101-1234567", "admin123"),
                new Admin("관리자2", "1982-07-15", "010-8765-4321", "820715-7654321", "adminpass456"),
                new Admin("이수호", "1998-03-03", "010-7720-7850", "980303-7654321", "admin"),
//                new Admin("1", "1998-03-03", "2", "980303-7654321", "3"),

                // Business 객체 생성
                // public Business(String name, String birth, String phoneNumber, String residentNumber, long bid, String bname) {
                new Business("김진아", "980303", "010-1234-1234", "9803031234567", 1001, "현수진기업", "1234"),
                new Business("사업장2", "970302", "010-5465-2432", "9703026342346", 1002, "테크맥", "1234"),
                new Business("사업장3", "990301", "010-1234-1233", "9903011234567", 1003, "빅빅빅빅빅", "1234"),
                new Business("사업장4", "990301", "010-1234-1233", "9903011234567", 1004, "카카오", "1234"),
                new Business("사업장5", "990301", "010-1234-1233", "9903011234567", 1005, "네이버", "1234"),
                new Business("사업장6", "990301", "010-1234-1233", "9903011234567", 1006, "페이스북", "1234"),

                // General 객체 생성
                //  public General(String name, String birth, String phoneNumber, String residentNumber, long insuranceNumber,
                //  long payerNumber, String bname, String acquireDate, String lossDate, WorkInfo workInfo, Long salary) {
                //
                new General("김현민", "1990-01-01", "010-1111-2222", "900101-1234567", 123123012, 1283712893, new WorkInfo(1001, "사원", "현수진기업"), 3_200_000L),
                new General("김진아", "1992-02-02", "010-3333-4444", "920202-2345678", 123123012, 218736129),
                new General("이수호", "1993-02-03", "010-2222-4444", "930203-1234567", 123123012, 213762173),
                new General("김지영", "1989-01-16",	"010-3333-5555", "890116-1234567"	,123123012,	3450324, new WorkInfo(1004, "부장", "카카오"), 4_320_000L),
                new General("박민수", "1996-05-27",	"010-4444-6666", "960527-2345678"	,123123012,	4095349, new WorkInfo(1002, "대리", "테크맥"), 5_000_000L),
                new General("최영희", "1990-07-10",	"010-5555-7777", "900710-1234567"	,123123012,	324823792, new WorkInfo(1003, "과장", "자바굳"), 6_000_000L)
        );
        ((General) users.get(9)).setWorkInfo(new WorkInfo( 1001, "사원", "현수진기업"));
//        ((General) users.get(10)).setWorkInfo(new WorkInfo( 1002, "대리", "테크맥"));
//        ((General) users.get(11)).setWorkInfo(new WorkInfo( 1003, "과장", "네이버"));
        ((General) users.get(12)).setWorkInfo(new WorkInfo( 1004, "부장", "카카오"));

        manager.addUsers(users);
    }

    public void generateInsuranceBenefits(SystemManager manager) {
        Random random = new Random();
        manager.getUsers().forEach(user -> {
            if (user instanceof General) {
                General general = (General) user;
                List<InsuranceBenefit> insuranceBenefits = new ArrayList<>();
                general.setInsuranceFee(Math.round(random.nextDouble() * (5000 - 1200) + 1200));
                general.setSalary(4_000_000L);
                InsuranceBenefit insuranceBenefit1 = new InsuranceBenefit(general, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 6, 30));
                insuranceBenefit1.setIsPaid(true);
                general.setInsuranceFee(Math.round(random.nextDouble() * (5000 - 1200) + 1200));
                general.setSalary(5_000_000L);
                InsuranceBenefit insuranceBenefit2 = new InsuranceBenefit(general, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 8, 31));
                insuranceBenefit2.setIsPaid(true);
                general.setInsuranceFee(Math.round(random.nextDouble() * (5000 - 1200) + 1200));
                general.setSalary(6_000_000L);
                InsuranceBenefit insuranceBenefit3 = new InsuranceBenefit(general, LocalDate.of(2022, 3, 1), LocalDate.of(2022, 9, 30));
                insuranceBenefits.add(insuranceBenefit1);
                insuranceBenefits.add(insuranceBenefit2);
                insuranceBenefits.add(insuranceBenefit3);
                manager.getInsuranceManager().addInsuranceBenefits(insuranceBenefits);
            }
        });
    }
    // 관리자 시스템용
    public void testMedicalTreatment(SystemManager systemManager) {
        General general = (General) systemManager.getUsers().get(9);
        // 더미 의료기기 목록 등록
        List<MedicalDevice> medicalDevices = genenrateMedicalDevices();
        systemManager.getBenefitManager().addDevices(medicalDevices);

        // 요양비 대상자 등록
        systemManager.getTreatmentManager().insertMedicalTreatment(DiseaseCode.RESPIRATORY_DISEASE, ItemCode.ASSISTIVE_DEVICE, general);

        // 내 요양비 목록 조회
//        systemManager.getTreatmentManager().showMedicalTreatmentsByGeneral(general);

        int quantity = 3;
        // 의료기기 대여
        LocalDate rentalDate = LocalDate.of(2021, 4, 8);
        LocalDate returnDate = rentalDate.plusMonths(7);
        systemManager.getTreatmentManager().rentalMedicalDevice(ItemCode.ASSISTIVE_DEVICE, "휠체어", Payment.ACCOUNT, general, rentalDate, returnDate, quantity, 300_000);
        systemManager.getTreatmentManager().insertMedicalTreatment(DiseaseCode.RESPIRATORY_DISEASE, ItemCode.ASSISTIVE_DEVICE, general);
        systemManager.getTreatmentManager().insertMedicalTreatment(DiseaseCode.RESPIRATORY_DISEASE, ItemCode.ASSISTIVE_DEVICE, general);
        systemManager.getTreatmentManager().chargeMedicalTreatment("신한 123123-012312321", general);
        systemManager.getTreatmentManager().returnMedicalDevice(ItemCode.ASSISTIVE_DEVICE, "휠체어", general, quantity);

        // 내 요양비 목록 조회
//        systemManager.getTreatmentManager().showMedicalTreatmentsByGeneral(general);
    }


    public static List<MedicalDevice> genenrateMedicalDevices() {
        return List.of(
                new MedicalDevice(ItemCode.ASSISTIVE_DEVICE, "휠체어", 10),
                new MedicalDevice(ItemCode.ASSISTIVE_DEVICE, "보청기", 20),
                new MedicalDevice(ItemCode.ASSISTIVE_DEVICE, "목발", 25),
                new MedicalDevice(ItemCode.THERAPEUTIC_DEVICE, "산소마스크", 5),
                new MedicalDevice(ItemCode.THERAPEUTIC_DEVICE, "경피신경자극기(TENS)", 10),
                new MedicalDevice(ItemCode.THERAPEUTIC_DEVICE, "마사지 기기", 8),
                new MedicalDevice(ItemCode.REHABILITATION_DEVICE, "운동 볼", 15),
                new MedicalDevice(ItemCode.REHABILITATION_DEVICE, "계단 트레이너", 3),
                new MedicalDevice(ItemCode.REHABILITATION_DEVICE, "밸런스 트레이너", 12),
                new MedicalDevice(ItemCode.SPECIAL_BEDDING, "압력 매트리스", 7),
                new MedicalDevice(ItemCode.SPECIAL_BEDDING, "전동 침대", 4),
                new MedicalDevice(ItemCode.SPECIAL_BEDDING, "특수 베개", 16)
        );
    }

    public void generateQualifications(SystemManager manager) {
        List<User> users = manager.getUsers();
        List<Qualification> qualifications = new ArrayList<>();
        Qualification qualification1 = new Qualification((General) users.get(9), LocalDate.of(2023, 1, 1), null, "현수진기업");
        Qualification qualification2 = new Qualification((General) users.get(10), LocalDate.of(2010, 12, 12), LocalDate.of(2023, 1, 1), "테크맥");
        Qualification qualification3 = new Qualification((General) users.get(11), LocalDate.of(2000, 5, 2), LocalDate.of(2010, 12, 12), "네이버");
        qualifications.add(qualification1);
        qualifications.add(qualification2);
        qualifications.add(qualification3);
        manager.getQualificationManager().addQualifications(qualifications);
    }
}
