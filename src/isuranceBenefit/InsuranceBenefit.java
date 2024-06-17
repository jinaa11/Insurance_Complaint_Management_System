package isuranceBenefit;

import common.Payment;
import user.General;

import java.time.LocalDate;

// 월 별 보험료
public class InsuranceBenefit {
    private General general;
    private double healthInsurance;  // 직장가입자 본인 부담분
    private double longTermCareInsurancePremium;  // 장기요양보험료
    private LocalDate createDate = LocalDate.now();  // 가입일
    private LocalDate paymentDueDate;  // (임시)  납부 마감일
    private String paymentMethodsAvailable;  // (임시)  납부 가능한 방법
    private Payment payment;  // 결제 방법

    private InsuranceBenefit() {}
//    public
}
