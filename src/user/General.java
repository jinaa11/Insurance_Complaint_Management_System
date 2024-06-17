package user;

import general.WorkInfo;

public class General extends User {
  // 건강보험증번호
  private long insuranceNumber;
  // 납부자 번호
  private long payerNumber;
  private double insuranceFee;
  private boolean isPaid;
  private String position;

  private String bname;  // (임시) 사업장명
  private String acquireDate; // (임시) 취득일
  private String lossDate; // (임시) 상실일
  private WorkInfo workInfo;  // 직장 번호 (1 대 1)
  private Long salary;

  public General() {}

  // 사업장 생성자
  public General(String name, String birth, String phoneNumber, String residentNumber, String position, long insuranceNumber, long payerNumber) {
    super(name, birth, phoneNumber, residentNumber);
    this.position = position;
    this.insuranceNumber = insuranceNumber;
    this.payerNumber = payerNumber;
    this.isPaid = false;
  }

  // 일반 사용자 생성자
  public General(String name, String birth, String phoneNumber, String residentNumber, long insuranceNumber, long payerNumber, String bname, String acquireDate, String lossDate, WorkInfo workInfo, Long salary) {
    super(name, birth, phoneNumber, residentNumber);
    this.insuranceNumber = insuranceNumber;
    this.payerNumber = payerNumber;
    this.bname = bname;
    this.acquireDate = acquireDate;
    this.lossDate = lossDate;
    this.workInfo = workInfo;
    this.salary = salary;
    try {
      double healthInsurancePremium = this.salary * (7.09 / 100) / 2;
      double longTermCareInsurancePremium = healthInsurancePremium * (0.9182 / 100 * 7.09 / 100);
      this.insuranceFee = healthInsurancePremium + longTermCareInsurancePremium;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void show() {
    System.out.println("일반 사용자 이름 :" + getName());
    System.out.println("일반 사용자 생일 :" + getBirth());
    System.out.println("일반 사용자 전화번호 :" + getPhoneNumber());
    System.out.println("일반 사용자 주민등록번호 :" + getResidentNumber());
    System.out.println("일반 사용자 건강보험증번호 :" + insuranceNumber);
    System.out.println("일반 사용자 납부자 번호 :" + payerNumber);
  }

  // 사업장 직원 print
  public void showBusinessEmployees() {
    System.out.println("건강보험증번호: " + insuranceNumber);
    System.out.println("납부자 번호: " + payerNumber);
    System.out.println("이름: " + getName());
    System.out.println("생년월일: " + getBirth());
    System.out.println("휴대폰 번호: " + getPhoneNumber());
    System.out.println("주민등록번호: " + getMaskedResidentNumber());
    System.out.println("직책: " + position);
    System.out.println("보험료: " + insuranceFee);
    System.out.println("납부 여부: " + (isPaid ? "납부" : "미납"));
  }

  // 주민등록번호 뒷자리 마스킹 처리
  public String getMaskedResidentNumber() {
    String residentNumber = getResidentNumber();
    if(residentNumber == null || residentNumber.length() != 13) {
      // 주민등록번호가 null이거나 13자리가 아니면 그대로 반환
      return residentNumber;
    }

    String maskedNumber = residentNumber.substring(0,6) + "-" + residentNumber.substring(6,7) + "******";
    return maskedNumber;
  }

  public Long getSalary() throws Exception {
    if (this.salary != null)
      return salary;
    else
      throw new Exception("월급내역이 없습니다.");
  }

  public long getInsuranceNumber() {
    return insuranceNumber;
  }

  public void setInsuranceNumber(long insuranceNumber) {
    this.insuranceNumber = insuranceNumber;
  }

  public long getPayerNumber() {
    return payerNumber;
  }

  public void setPayerNumber(long payerNumber) {
    this.payerNumber = payerNumber;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  public double getInsuranceFee() {
    return insuranceFee;
  }

  public void setInsuranceFee(double insuranceFee) {
    this.insuranceFee = insuranceFee;
  }

  public WorkInfo getWorkInfo() {
    return workInfo;
  }
  public void setWorkInfo(WorkInfo workInfo) {
    this.workInfo = workInfo;
  }

  public String getPosition() {
    return position;
  }
  public void setPosition(String position) {
    this.position = position;
  }
  public String getAcquireDate() {
    return acquireDate;
  }
  public void setAcquireDate(String acquireDate) {
    this.acquireDate = acquireDate;
  }
  public String getLossDate() {
    return lossDate;
  }
  public void setLossDate(String lossDate) {
    this.lossDate = lossDate;
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
