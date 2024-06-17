package general;

import common.DataInput;
import common.Payment;
import isuranceBenefit.InsuranceBenefit;
import user.General;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InsuranceManager {
    public static InsuranceManager instance;

    List<InsuranceBenefit> insuranceBenefits;

    private InsuranceManager() {
        this.insuranceBenefits = new ArrayList<>();
    }

    public static InsuranceManager getInstance() {
        if (instance == null) {
            synchronized (InsuranceManager.class) {
                if (instance == null) {
                    instance = new InsuranceManager();
                }
            }
        }
        return instance;
    }

    private static boolean isValidAccountNumber(String accountNo) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        return pattern.matcher(accountNo).matches();
    }
    private static boolean isValidCardNumber(String cardInfo) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        return pattern.matcher(cardInfo).matches();
    }

    public void addInsuranceBenefit(InsuranceBenefit insuranceBenefit) {
        insuranceBenefits.add(insuranceBenefit);
    }

    public void addInsuranceBenefits(List<InsuranceBenefit> insuranceBenefits) {
        this.insuranceBenefits.addAll(insuranceBenefits);
    }

    // 일반 보험료 납부
    public void defaultPay(Payment payment, General general) throws Exception {
        InsuranceBenefit recentBenefit = insuranceBenefits.stream()
                .filter(ift -> ift.getGeneral().equals(general))
                .max(Comparator.comparing(InsuranceBenefit::getCreateDate))
                .orElseThrow(() -> new Exception("보험료 내역이 존재하지 않습니다."));

        switch (payment) {
            case CARD:
                System.out.print("카드 일련 번호: ");
                String cardInfo = DataInput.sc.nextLine();
                if (isValidCardNumber(cardInfo)) {
                    recentBenefit.setPaymentSource(cardInfo);
                    recentBenefit.getGeneral().getWorkInfo().setIsPaid(true);
                } else {
                    System.out.println("유효하지 않은 카드 일련번호입니다. 다시 입력해주세요.");
                }
                break;
            case ACCOUNT:
                System.out.print("은행 이름 : ");
                String bankName = DataInput.sc.nextLine();
                System.out.print("계좌 번호 : ");
                String accountNo = DataInput.sc.nextLine();
                if (isValidAccountNumber(accountNo)) {
                    recentBenefit.setPaymentSource(bankName + " " + accountNo);
                    recentBenefit.getGeneral().getWorkInfo().setIsPaid(true);
                } else {
                    System.out.println("유효하지 않은 카드 일련번호입니다. 다시 입력해주세요.");
                }
                break;
        }
    }

    // 자동이체 보험료 납부
    public void autoPay(String bankName, String accountNo, General general) throws Exception {
        InsuranceBenefit recentBenefit = insuranceBenefits.stream()
                .filter(ift -> ift.getGeneral().equals(general))
                .max(Comparator.comparing(InsuranceBenefit::getCreateDate))
                .orElseThrow(() -> new Exception("보험료 내역이 존재하지 않습니다."));
        if (isValidAccountNumber(accountNo)) {
            recentBenefit.setPaymentSource(bankName + " " + accountNo);
            recentBenefit.getGeneral().getWorkInfo().setIsPaid(true);
        } else {
            System.out.println("유효하지 않은 카드 일련번호입니다. 다시 입력해주세요.");
        }
    }

    // 건강 보험료 계산기
    public double calculateHealthInsurance(double monthlySalary) {
        double healthInsurancePremium = monthlySalary * (7.09 / 100) / 2; // 1. 건강
        double longTermCareInsurancePremium = healthInsurancePremium * (0.9182 / 100 * 7.09 / 100); // 2. 장기
        return healthInsurancePremium + longTermCareInsurancePremium; // 납부금액
    }

    // 직장 보험료 조회
    public void showWorkInsurance(General general) {
        List<InsuranceBenefit> benefits = insuranceBenefits.stream()
                .filter(ift -> ift.getGeneral().equals(general))
                .collect(Collectors.toList());
        System.out.println("건강보험증번호\t사업장 명칭\t취득일\t상실일\t가입자부담금");
        for (InsuranceBenefit benefit : benefits) {
            benefit.showWorkInsurance();
        }
    }

    // 납부 현황 조회
    public void showDetailWorkInsurance(General general) {
        List<InsuranceBenefit> benefits = insuranceBenefits.stream()
                .filter(ift -> ift.getGeneral().equals(general))
                .collect(Collectors.toList());
        for (InsuranceBenefit benefit : benefits) {
            benefit.showInsuranceBenefit();
        }
    }

    // 지역 보험료 조회
    public void showLocalInsurance(LocalDate startDate, LocalDate endDate, General general) {
        List<InsuranceBenefit> benefits = insuranceBenefits.stream()
                .filter(ift -> ift.getCreateDate().isAfter(startDate) && ift.getCreateDate().isBefore(endDate))
                .filter(ift -> ift.getGeneral().equals(general))
                .collect(Collectors.toList());
        for (InsuranceBenefit benefit : benefits) {
            benefit.showLocalInsurance();
        }
    }
}
