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

    private double healthInsurancePremium;  // 건강보험료
    private long longTermCareInsurancePremium;  // 장기요양보험료
    private long insuranceFee;  // 보험료
    private double salary;  // 급여

    private Payment payment;  // (임시)  납부 가능한 방법
    private String paymentSource;
    private boolean isPaid;

    private LocalDate createDate;  // 월

    private InsuranceBenefit() {}
    public InsuranceBenefit(General general, LocalDate acquireDate, LocalDate lossDate) {
        this.id = IBFT_CNT++;
        this.general = general;
        this.acquireDate = acquireDate;
        this.lossDate = lossDate;
        createDate = acquireDate;
        this.salary = general.getSalary();
        this.insuranceFee = general.getInsuranceFee();
        this.healthInsurancePremium = this.salary * (7.09 / 100) / 2;
        this.longTermCareInsurancePremium = (long) (healthInsurancePremium * (0.9182 / 100 * 7.09 / 100));
        this.isPaid = false;
    }

    // 1인 직장 보험료 출력
    public void showWorkInsurance() {
        try {
            System.out.print(general.getInsuranceNumber() + "\\t");
            System.out.print(general.getWorkInfo().getBname() + "\\t");
            System.out.print(acquireDate + "\\t");
            System.out.print(lossDate + "\\t");
            System.out.println(this.insuranceFee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 지역 보험료 출력1인
    public void showLocalInsurance() {
        System.out.println("구분\t");
        // 년.월 추가
        printYearMonthRange(acquireDate, lossDate);

        System.out.println("합계\t");
        try {
            System.out.println(this.insuranceFee + "\t");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("건강\t");
            System.out.println(this.healthInsurancePremium + "\t");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("장기요양\t");
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
            current = current.plusMonths(1);
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

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public void showInsuranceBenefit(int idx) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedDate = this.createDate.format(formatter);
        if (!this.isPaid) {
            System.out.println("|" + idx + "\t" + "미납" + "\t\t" + general.getName() + "\t" + this.insuranceFee + "원\t\t" + this.longTermCareInsurancePremium + "원\t\t\t" + formattedDate + "\t|");
            return;
        }
        System.out.println("|" + idx + "\t" + "납부완료" + "\t" + general.getName() + "\t" + this.insuranceFee + "원\t\t" + this.longTermCareInsurancePremium + "원\t\t\t" + formattedDate + "\t|");
    }
}
