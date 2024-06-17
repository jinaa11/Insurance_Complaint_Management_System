package common;

import isuranceBenefit.InsuranceBenefitManager;
import medicaltreatment.MedicalTreatmentManager;
import user.Admin;
import user.General;
import user.User;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    public static SystemManager instance;
    private List<User> users;
    private InsuranceBenefitManager benefitManager;
    private MedicalTreatmentManager treatmentManager;

    private User loggedInUser;

    public SystemManager() {
        users = new ArrayList<>();
        this.benefitManager = InsuranceBenefitManager.getInstance();
        this.treatmentManager = MedicalTreatmentManager.getInstance(benefitManager);
    }

    public static SystemManager getInstance() {
        if (instance == null) {
            synchronized (SystemManager.class) {
                if (instance == null) {
                    instance = new SystemManager();
                }
            }
        }

        return instance;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public General getUser(long insuranceNumber) {
        return (General) users.stream()
            .filter(u -> u instanceof General && ((General) u).getInsuranceNumber() == insuranceNumber)
            .findFirst()
            .orElse(null);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addUsers(List<User> users) {
        this.users.addAll(users);
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public void login(String name, String phoneNumber) throws Exception {
        this.loggedInUser = users.stream()
            .filter(u -> u.getName().equals(name) && u.getPhoneNumber().equals(phoneNumber))
            .findFirst()
            .orElseThrow(() -> new Exception("존재하지 않는 사용자입니다."));
        System.out.println("로그인이 완료되었습니다.");
    }

    public void login(String name, String phoneNumber, String password) throws Exception {
        User user = users.stream()
                .filter(u -> u.getName().equals(name) && u.getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .orElseThrow(() -> new Exception("존재하지 않는 사용자입니다."));
        if (user instanceof Admin && !((Admin) user).getPassword().equals(password)) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        } else {
            this.loggedInUser = user;
        }
        System.out.println("로그인이 완료되었습니다.");
    }

    public void logout() {
        this.loggedInUser = null;
        System.out.println("로그아웃이 완료되었습니다.");
    }

    public InsuranceBenefitManager getBenefitManager() {
        return benefitManager;
    }

    public MedicalTreatmentManager getTreatmentManager() {
        return treatmentManager;
    }
}
