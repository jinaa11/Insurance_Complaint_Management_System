package general;

import business.BusinessManager;
import common.Payment;
import user.General;

import java.util.HashMap;
import java.util.Map;

public class InsuranceMangement implements Comparable<InsuranceMangement>{
    // 필드
    private General general;  // (1 대 1)
    private BusinessManager businessManagerList;  // (1 대 1)
    private Payment payment;  // (1 대 1)

    Map<Payment, String> payInfo = new HashMap<Payment, String>();

    private double healthInsurance;  // 직장가입자 본인 부담분
    private double healthInsurancePremium;  // 건강보험료
    private double longTermCareInsurancePremium;  // 장기요양보험료
    private String startPaymentPeriod;  // (임시)  납부 기간 시작
    private String finishPaymentPeriod;  // (임시)  납부 기간 시작
    private String insurancePremiumType;  // (임시)  보험료 종류
    private int money;  // (임시) 납부 금액
    private String paymentDueDate;  // (임시)  납부 마감일
    private String paymentMethodsAvailable;  // (임시)  납부 가능한 방법

    // 생성자
    public InsuranceMangement() {}

    public InsuranceMangement(General general, BusinessManager businessManagerList, Payment payment) {
        this.general = general;
        this.businessManagerList = businessManagerList;
        this.payment = payment;
    }

    // 메소드
    // 일반 보험료 납부
    public void defaultPay(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("결제 방법을 입력 되지 않았습니다.");
        }

        this.payment = payment;

        switch (payment) {
            case CARD:
                System.out.print("카드 일련 번호 : ");
                String cardInfo = DataInput.sc.nextLine();
                payInfo.put(payment, cardInfo);
                break;
            case ACCOUNT:
                System.out.print("주민 등록 번호 : ");
                String residentNumber = DataInput.sc.nextLine();
                System.out.print("은행 이름 : ");
                String bankName = DataInput.sc.nextLine();
                System.out.print("계좌 번호 : ");
                String accountNo = DataInput.sc.nextLine();

                String combinedAccountInfo = residentNumber + "," + bankName + "," + accountNo;
                payInfo.put(payment, combinedAccountInfo);
                break;
            default:
                System.out.println("지원하지 않는 결제 수단 : " + payment);
        }
    }

    // 자동이체 보험료 납부
    public void outoPay() {
        if (payment == null) {
            throw new IllegalArgumentException("결제 방법을 입력 되지 않았습니다.");
        }

        System.out.print("주민 등록 번호 : ");
        String residentNumber = DataInput.sc.nextLine();
        System.out.print("은행 이름 : ");
        String bankName = DataInput.sc.nextLine();
        System.out.print("계좌 번호 : ");
        String accountNo = DataInput.sc.nextLine();

        String combinedAccountInfo = residentNumber + "," + bankName + "," + accountNo;
        payInfo.put(payment, combinedAccountInfo);
    }

    // 타인 보험료 납부
    public void anotherPersonPay() {
        if (payment == null) {
            throw new IllegalArgumentException("결제 방법을 입력 되지 않았습니다.");
        }

        System.out.print("payerNumber : ");
        String payerNumber = DataInput.sc.nextLine();
        System.out.print("money : ");
        String money = DataInput.sc.nextLine();

        String combinedAccountInfo = payerNumber + "," + money;
        payInfo.put(payment, combinedAccountInfo);
    }

    // 건강 보험료 계산기
    public double calcaulateHealthInsurance(double monthlySalary) {
        healthInsurancePremium = monthlySalary * (7.09 / 100) * (50 / 100);
        longTermCareInsurancePremium = healthInsurancePremium * (0.9182 / 100 * 7.09 / 100);

        healthInsurance = healthInsurance + longTermCareInsurancePremium;

        return healthInsurance;
    }

    // 보험료 고지서
    public void insurancePremiumNotice() {
        System.out.println("납부 기간 : " + startPaymentPeriod + "~" + finishPaymentPeriod);
        System.out.println("보험료 종류 : " + insurancePremiumType);
        System.out.println("납부 금액 : " + money);
        System.out.println("납부 마감일 : " + paymentDueDate);
        System.out.println("납부 가능한 방법 : " + paymentMethodsAvailable);
    }

    // 직장 보험료 조회
    public void getWorkInsurance(String acquireDate) {
        if (acquireDate.equals(acquireDate)) {
            //System.out.println("건강보험증번호: " + insuranceNumber);
            //System.out.println("사업장 명칭: " + bname);
            System.out.println("취득일: " + startPaymentPeriod);
            System.out.println("상실일: " + finishPaymentPeriod);
        } else {
            System.out.println("납부 기간이 잘못 입력되었습니다.");
        }
    }

    // 납부 현황 조회
    public void getDetailWorkInsurance(double monthlySalary) {
        healthInsurancePremium = monthlySalary * (7.09 / 100) * (50 / 100);
        System.out.println("건강보험료 : " + healthInsurancePremium);
        longTermCareInsurancePremium = healthInsurancePremium * (0.9182 / 100 * 7.09 / 100);
        System.out.println("장기요양 보험료 : " + longTermCareInsurancePremium);

        System.out.println("가입자부담금 : "+ healthInsurance);
    }

    // 지역 보험료 고지
    public void getLocalInsuranceNoticeAndPaymentStatus(long payerNumber) {
        System.out.println("납부자 번호 : " + payerNumber);
        insurancePremiumNotice();
    }

    // 지역 보험료 조회
    public void getLocalInsurance() {
        insurancePremiumNotice();
    }

    @Override
    public int compareTo(InsuranceMangement i) {
        // 최신순 정렬(내림차순)
        if(startPaymentPeriod.compareTo(i.startPaymentPeriod) > 0) {
            return -1;
        } else if (startPaymentPeriod.compareTo(i.startPaymentPeriod) < 0) {
            return 1;
        }
        return 0;
    }

}
