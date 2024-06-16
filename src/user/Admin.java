package user;

import isuranceBenefit.InsuranceBenefitManager;

public class Admin extends User {
  private InsuranceBenefitManager manager;

  private Admin() {}
  public Admin(String name, String birth, String phoneNumber, String residentNumber, InsuranceBenefitManager manager){
    super(name, birth, phoneNumber, residentNumber);
    this.manager = manager;
  }

  public InsuranceBenefitManager getManager() {
    return this.manager;
  }
}
