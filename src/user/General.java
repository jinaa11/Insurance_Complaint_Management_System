package user;

import general.WorkInfo;

public class General extends User {
  // 건강보험증번호
  private long insuranceNumber;
  // 납부자 번호
  private long payerNumber;
  private boolean isDelete;
  private int insuranceFee;
  private boolean isPaid;
  private WorkInfo workInfo;  // 직장 번호 (1 대 1)

  public General() {}

  public General(long userId, String name, String birth, String phoneNumber, String residentNumber, long insuranceNumber, long payerNumber, int insuranceFee, WorkInfo workInfo) {
    super(userId, name, birth, phoneNumber, residentNumber);
    this.insuranceNumber = insuranceNumber;
    this.payerNumber = payerNumber;
    this.isDelete = false;
    this.insuranceFee = insuranceFee;
    this.isPaid = false;
    this.workInfo = workInfo;
  }

  public void show() {
    System.out.println("건강보험증번호: " + insuranceNumber);
    System.out.println("납부자 번호: " + payerNumber);
    System.out.println("일반 사용자 ID :" + getUserId());
    System.out.println("이름: " + getName());
    System.out.println("생년월일: " + getBirth());
    System.out.println("휴대폰 번호: " + getPhoneNumber());
    System.out.println("주민등록번호: " + getMaskedResidentNumber());
    System.out.println("보험료: " + insuranceFee);
    System.out.println("일반 사용자 건강보험증번호 :" + insuranceNumber);
    System.out.println("일반 사용자 납부자 번호 :" + payerNumber);
    System.out.println("납부 여부: " + (isPaid ? "납부" : "미납"));
    System.out.println("삭제 여부: " + isDelete);
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

  public boolean isDelete() {
    return isDelete;
  }

  public void setDelete(boolean delete) {
    isDelete = delete;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  public int getInsuranceFee() {
    return insuranceFee;
  }

  public void setInsuranceFee(int insuranceFee) {
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
