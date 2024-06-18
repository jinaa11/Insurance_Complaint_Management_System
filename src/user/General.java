package user;

import general.WorkInfo;

public class General extends User {
  // 건강보험증번호
  private long insuranceNumber;
  // 납부자 번호
  private long payerNumber;
  private Long insuranceFee;
  private boolean isPaid;
  private WorkInfo workInfo;  // 직장 번호 (1 대 1)
  private Long salary;

  private General() {}
  // 사업장 생성자
  public General(String name, String birth, String phoneNumber, String residentNumber, long insuranceNumber, long payerNumber) {
    super(name, birth, phoneNumber, residentNumber);
    this.insuranceNumber = insuranceNumber;
    this.payerNumber = payerNumber;
    this.isPaid = false;
  }

  // 일반 사용자 생성자
  public General(String name, String birth, String phoneNumber, String residentNumber, long insuranceNumber, long payerNumber, WorkInfo workInfo, Long salary) {
    super(name, birth, phoneNumber, residentNumber);
    this.insuranceNumber = insuranceNumber;
    this.payerNumber = payerNumber;
    this.workInfo = workInfo;
    this.salary = salary;
    try {
      double healthInsurancePremium = this.salary * (7.09 / 100) / 2;
      double longTermCareInsurancePremium = healthInsurancePremium * (0.9182 / 100 * 7.09 / 100);
      this.insuranceFee = (long) healthInsurancePremium + (long) longTermCareInsurancePremium;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // 사업장 직원 print
  public void showBusinessEmployees() {
    System.out.println("건강보험증번호: " + insuranceNumber);
    System.out.println("납부자 번호: " + payerNumber);
    System.out.println("이름: " + getName());
    System.out.println("생년월일: " + getBirth());
    System.out.println("휴대폰 번호: " + getPhoneNumber());
    System.out.println("주민등록번호: " + getMaskedResidentNumber());
    System.out.println("직책: " + workInfo.getPosition());
    System.out.println("보험료: " + insuranceFee);
    System.out.println("납부 여부: " + (isPaid ? "납부" : "미납"));
  }

  // 주민등록번호 뒷자리 마스킹 처리
  public String getMaskedResidentNumber() {
    String residentNumber = getResidentNumber();
    if(residentNumber == null || residentNumber.length() != 14 || !residentNumber.matches("\\d{6}-\\d{7}")) {
      // 주민등록번호가 null이거나 13자리가 아니면 그대로 반환
      return residentNumber;
    }

    return residentNumber.substring(0, 8) + "******";
  }

  public Long getSalary() {
      return salary;
  }

  public void setSalary(Long salary) {
      this.salary = salary;
  }

  public long getInsuranceNumber() {
    return insuranceNumber;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  public long getInsuranceFee() {
    return insuranceFee;
  }

  public void setInsuranceFee(long insuranceFee) {
    this.insuranceFee = insuranceFee;
  }

  public WorkInfo getWorkInfo() {
    return workInfo;
  }
  public void setWorkInfo(WorkInfo workInfo) {
    this.workInfo = workInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof General) {
      General general = (General) o;
      return this.getName().equals(general.getName()) && this.getResidentNumber().equals(general.getResidentNumber());
    }
    return false;
  }
}
