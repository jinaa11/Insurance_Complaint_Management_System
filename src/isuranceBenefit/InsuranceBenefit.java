package isuranceBenefit;

import common.Payment;
import user.General;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

// 월 별 보험료
public class InsuranceBenefit {
    private static long IBFT_CNT = 1L;
    private long id;
    private General general;

    private LocalDate acquireDate;
    private LocalDate lossDate;

    private double longTermCareInsurancePremium;  // 장기요양보험료
    private LocalDate createDate;  // 가입일
    private Payment payment;  // (임시)  납부 가능한 방법
    private String paymentSource;

    private InsuranceBenefit() {}
    public InsuranceBenefit(General general, LocalDate acquireDate, LocalDate lossDate) {
        this.id = IBFT_CNT++;
        this.general = general;
        this.acquireDate = acquireDate;
        this.lossDate = lossDate;
        createDate = acquireDate;
    }

    // 1인 직장 보험료 출력
    public void showWorkInsurance() {
        try {
            System.out.print(general.getInsuranceNumber() + "\\t");
            System.out.print(general.getWorkInfo().getBname() + "\\t");
            System.out.print(acquireDate + "\\t");
            System.out.print(lossDate + "\\t");
            System.out.println(calculateHealthInsurance(general.getSalary()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double calculateHealthInsurance(double monthlySalary) {
        double healthInsurancePremium = monthlySalary * (7.09 / 100) / 2; // 1. 건강
        double longTermCareInsurancePremium = healthInsurancePremium * (0.9182 / 100 * 7.09 / 100); // 2. 장기
        return healthInsurancePremium + longTermCareInsurancePremium; // 납부금액
    }
    // 지역 보험료 출력1인
    public void showLocalInsurance() {
        double healthInsurancePremium = 0;
        System.out.println("구분\t");
        // 년.월 추가
        printYearMonthRange(acquireDate, lossDate);

        System.out.println("합계\t");
        try {
            System.out.println(calculateHealthInsurance(general.getSalary()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("건강\t");
            healthInsurancePremium = general.getSalary() * (7.09 / 100) * (50 / 100);
            System.out.println(healthInsurancePremium + "\t");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("장기요양\t");
            this.longTermCareInsurancePremium = healthInsurancePremium * (0.9182 / 100 * 7.09 / 100);
            System.out.println(this.longTermCareInsurancePremium + "\t");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 지역 보험료 날짜 추출(년.월 형식)
    public void printYearMonthRange(LocalDate start, LocalDate end) {
        LocalDate current = start;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");

        while(!current.isAfter(end)) {
            System.out.print(current.format(formatter) + "\t");
            current = current.plus(1, ChronoUnit.MONTHS);
        }
        System.out.println();
    }

    public General getGeneral() {
        return general;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setPaymentSource(String paymentSource) {
        this.paymentSource = paymentSource;
    }

    public void showInsuranceBenefit() {
        if (!this.general.getWorkInfo().getIsPaid()) {
            System.out.println("|" + this.id + "\t" + "미납" + "\t" + general.getName() + "\t" + general.getInsuranceFee() + "\t" + longTermCareInsurancePremium);
            return;
        }
        System.out.println("|" + this.id + "\t" + "납부" + "\t" + general.getName() + "\t" + general.getInsuranceFee() + "\t" + longTermCareInsurancePremium);
    }
}
