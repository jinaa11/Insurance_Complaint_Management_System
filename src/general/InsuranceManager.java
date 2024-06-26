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
        // valid account number format: xxxx-xx-xxxxxx
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{6}$");
        return pattern.matcher(accountNo).matches();
    }
    private static boolean isValidCardNumber(String cardInfo) {
        // valid card number format: xxxx-xxxx-xxxx-xxxx
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$");
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
        InsuranceBenefit recentBenefit = this.insuranceBenefits.stream()
                .filter(ift -> ift.getGeneral().equals(general))
                .max(Comparator.comparing(InsuranceBenefit::getCreateDate))
                .orElseThrow(() -> new Exception("보험료 내역이 존재하지 않습니다."));

        switch (payment) {
            case CARD:
                System.out.print("카드 번호 입력: ");
                String cardInfo = DataInput.sc.nextLine();
                if (isValidCardNumber(cardInfo)) {
                    recentBenefit.setPayment(payment);
                    recentBenefit.setPaymentSource(cardInfo);
                    recentBenefit.setIsPaid(true);
                } else {
                    System.out.println("유효하지 않은 카드 일련번호입니다. 다시 입력해주세요.");
                }
                break;
            case ACCOUNT:
                System.out.print("은행 이름 입력: ");
                String bankName = DataInput.sc.nextLine();
                System.out.print("계좌 번호 입력: ");
                String accountNo = DataInput.sc.nextLine();
                if (isValidAccountNumber(accountNo)) {
                    recentBenefit.setPayment(payment);
                    recentBenefit.setPaymentSource(bankName + " " + accountNo);
                    recentBenefit.setIsPaid(true);
                } else {
                    System.out.println("유효하지 않은 카드 일련번호입니다. 다시 입력해주세요.");
                }
                break;
        }

        printReceipt(recentBenefit);
    }

    private void printReceipt(InsuranceBenefit benefit) {
        System.out.println("===================================");
        System.out.println("[보험료 납부 영수증]");
        System.out.println("건강보험료 금액: " + String.format("%,d원", (int)benefit.getHealthInsurancePremium()));
        System.out.println("장기요양보험료 금액: " + String.format("%,d원", (int)benefit.getLongTermCareInsurancePremium()));
        System.out.println("합계 가입자부담금: " + String.format("%,d원", (int)benefit.getInsuranceFee()));
        System.out.println("---------------------------");
        System.out.println("납부 분류: " + benefit.getPayment().getValue());
        if (benefit.getPayment().equals(Payment.CARD)) {
            System.out.println("카드 번호: " + benefit.getPaymentSource());
        } else {
            System.out.println("은행명: " + benefit.getPaymentSource().split(" ")[0]);
            System.out.println("계좌번호: " + benefit.getPaymentSource().split(" ")[1]);
        }
        System.out.println("===================================");
        System.out.println("납부가 정상적으로 완료되었습니다.");
    }

    // 자동이체 보험료 납부
    public void autoPay(String bankName, String account, General general) throws Exception {
        InsuranceBenefit recentBenefit = insuranceBenefits.stream()
                .filter(ift -> ift.getGeneral().equals(general))
                .max(Comparator.comparing(InsuranceBenefit::getCreateDate))
                .orElseThrow(() -> new Exception("보험료 내역이 존재하지 않습니다."));
        if (isValidAccountNumber(account)) {
            recentBenefit.setPaymentSource(bankName + " " + account);
            recentBenefit.setIsPaid(true);
        } else {
            System.out.println("유효하지 않은 카드 일련번호입니다. 다시 입력해주세요.");
        }
        System.out.println("자동 이체 계좌가 정상적으로 등록되었습니다.");
    }

    // 건강 보험료 계산기
    public void calculateHealthInsurance(long salary) {
        long healthInsurancePremium = (long) (salary * 0.0709 * 0.5); // 1. 건강
        long longTermCareInsurancePremium = (long) (healthInsurancePremium * (0.009182 / 0.0709)); // 2. 장기
        long result = healthInsurancePremium + longTermCareInsurancePremium;// 납부금액
        System.out.println("===============보험료 계산기===============");
        System.out.println("월급여: " + String.format("%,d원", (long) salary));
        System.out.println("건강보험료: " + String.format("%,d원", healthInsurancePremium));
        System.out.println("장기요양보험료: " + String.format("%,d원", (long) longTermCareInsurancePremium));
        System.out.println("총 납부 금액: " + String.format("%,d원", result));
        System.out.println("=========================================");
    }

    // 직장 보험료 조회
    public void showWorkInsurance(General general) {
        List<InsuranceBenefit> benefits = insuranceBenefits.stream()
                .filter(ift -> ift.getGeneral().equals(general))
                .collect(Collectors.toList());
        System.out.println("건강보험증번호\t사업장 명칭\t고지일\t\t가입자부담금");
        for (InsuranceBenefit benefit : benefits) {
            benefit.showWorkInsurance();
        }
    }

    // 납부 현황 조회
    public void showDetailWorkInsurance(General general) {
        System.out.println("\t\t\t\t" + general.getName()+"님의 보험료 납부 현황");
        System.out.println("=========================================================");
        System.out.println("|No\t납부여부\t납부자\t건강부담금\t장기요양보험료\t\t납부일\t|");
        List<InsuranceBenefit> benefits = insuranceBenefits.stream()
                .sorted(Comparator.comparing(InsuranceBenefit::getCreateDate).reversed())
                .filter(ift -> ift.getGeneral().equals(general))
                .collect(Collectors.toList());
        for (int i = 0; i < benefits.size(); i++) {
            benefits.get(i).showInsuranceBenefit(i+1);
        }
        System.out.println("=========================================================");
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
