package medicaltreatment;

import common.Payment;
import isuranceBenefit.InsuranceBenefitManager;
import isuranceBenefit.ItemCode;
import isuranceBenefit.MedicalDevice;
import user.General;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MedicalTreatmentManager {
    private static MedicalTreatmentManager instance;

    private InsuranceBenefitManager insuranceBenefitManager;
    private List<MedicalTreatment> medicalTreatments;
    private List<General> currentlyBenefitUsers;

    private MedicalTreatmentManager() {}
    public MedicalTreatmentManager(InsuranceBenefitManager manager) {
        this.insuranceBenefitManager = manager;
        this.medicalTreatments = new ArrayList<>();
        this.currentlyBenefitUsers = new LinkedList<>();
    }

    public static MedicalTreatmentManager getInstance(InsuranceBenefitManager manager) {
        if (instance == null) {
            synchronized (MedicalTreatmentManager.class) {
                if (instance == null) {
                    instance = new MedicalTreatmentManager(manager);
                }
            }
        }

        return instance;
    }

    public List<MedicalTreatment> getMedicalTreatments() {
        return medicalTreatments;
    }

    public void showMedicalTreatmentsByGeneral(General general) {
        System.out.println("=====================================================================================================================");
        System.out.println("|No\t처리상태\t질병분류\t수진자주민번호\t\t수진자명\t환급금액\t\t환급수단\t\t\t\t\t의료기기명\t대여일\t\t반납일\t\t|");
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        List<MedicalTreatment> treatments = medicalTreatments.stream()
            .filter(m -> m.getGeneral().equals(general))
            .collect(Collectors.toList());

        for (MedicalTreatment treatment : treatments) {
            treatment.showMedicalTreatment();
        }
        System.out.println("=====================================================================================================================");
    }

    public void insertMedicalTreatment(DiseaseCode code, ItemCode itemCode, General general) {
        MedicalTreatment medicaltreatment = new MedicalTreatment(code, general);
        medicalTreatments.add(medicaltreatment);
        try {
            if (isValidMedicalTreatment(medicaltreatment, itemCode)) {
                medicaltreatment.updateProcess(Process.WAIT);
            }
        } catch (Exception e) {
            medicaltreatment.updateProcess(Process.UNABLE);
            System.out.println(e.getMessage());
        }
    }

    public void rentalMedicalDevice(ItemCode itemCode, String deviceName, Payment payment, General general, LocalDate rentalDate, LocalDate returnDate, long quantity, long amount) {
        try {
            MedicalDevice device = insuranceBenefitManager.searchMedicalDevice(itemCode, deviceName);
            MedicalTreatment medicalTreatment = medicalTreatments.stream()
                .sorted(Comparator.comparing(MedicalTreatment::getCreateDate, Comparator.reverseOrder()))
                .filter(m -> m.getGeneral().getResidentNumber().equals(general.getResidentNumber()))
                .findFirst().orElseThrow(() -> new Exception("요양비 대상자가 아닙니다."));

            medicalTreatment.updateRentalData(device, payment, rentalDate, returnDate, amount);
            device.decreaseQuantity(quantity);
            currentlyBenefitUsers.add(general);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void chargeMedicalTreatment(String account, General general) {
        try {
            MedicalTreatment medicalTreatment = medicalTreatments.stream()
                .sorted(Comparator.comparing(MedicalTreatment::getCreateDate, Comparator.reverseOrder()))
                .filter(m -> m.getGeneral().getResidentNumber().equals(general.getResidentNumber()))
                .findFirst().orElseThrow(() -> new Exception("요양비 대상자가 아닙니다."));
            medicalTreatment.setAccount(account);
            System.out.println("요양비 청구를 위한 환급 계좌 등록이 완료 되었습니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void returnMedicalDevice(ItemCode itemCode, String deviceName, General general, long quantity) {
        try {
            MedicalDevice device = insuranceBenefitManager.searchMedicalDevice(itemCode, deviceName);
            MedicalTreatment medicalTreatment = medicalTreatments.stream()
                .sorted(Comparator.comparing(MedicalTreatment::getCreateDate, Comparator.reverseOrder()))
                .filter(m -> m.getGeneral().getResidentNumber().equals(general.getResidentNumber()))
                .findFirst().orElseThrow(() -> new Exception("요양비 대상자가 아닙니다."));

            if (medicalTreatment.getDevice().equals(device)) {
                device.increaseQuantity(quantity);
                medicalTreatment.updateProcess(Process.RETURN);
                currentlyBenefitUsers.remove(general);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isValidMedicalTreatment(MedicalTreatment medicalTreatment, ItemCode itemCode) throws Exception {
        List<MedicalTreatment> medicalTreatmentsBySameGeneral = medicalTreatments.stream()
            .filter(m -> m.getGeneral().equals(medicalTreatment.getGeneral())).collect(Collectors.toList());

        for (MedicalTreatment treatment : medicalTreatmentsBySameGeneral) {
            boolean isProcess = treatment.getProcess().equals(Process.WAIT) || treatment.getProcess().equals(Process.PROCESS);
            boolean isDevice = treatment.getDevice() != null && treatment.getDevice().getItemCode().equals(itemCode);
            boolean isPeriod = medicalTreatment.getRentalDate() != null && !treatment.isPeriodOverlap(medicalTreatment.getRentalDate(), medicalTreatment.getReturnDate());
            if (isProcess && isDevice && isPeriod) {
                throw new Exception("기존에 대여하고 있는 요양비 내역이 있습니다.");
            }
        }

        return true;
    }

    public void showCurrentlyBenefitUsers() {
        System.out.println("================================================================");
        System.out.println("|건강보험증번호\t수진자명\t\t  품목코드\t\t의료기기명\t대여일\t   반납일\t|");
        medicalTreatments.forEach(mt -> {
            System.out.print(" " + mt.getGeneral().getInsuranceNumber() + "\t\t");
            System.out.print(" " + mt.getGeneral().getName() + "\t");
            System.out.print(" " + mt.getDevice().getItemCode() + "\t");
            System.out.print(" " + mt.getDevice().getDeviceName() + "\t");
            System.out.print(" " + mt.getCreateDate() + "\t");
            System.out.println(" " + mt.getReturnDate());
        });
        System.out.println();

//        for (MedicalTreatment medicalTreatment : medicalTreatments) {
//            if (medicalTreatment.getProcess().equals(Process.WAIT) || medicalTreatment.getProcess().equals(Process.PROCESS)) {
//                System.out.println(medicalTreatment.getGeneral().getInsuranceNumber() + "\t" +
//                        medicalTreatment.getGeneral().getName() + "\t" +
//                        medicalTreatment.getDevice().getItemCode() + "\t" +
//                        medicalTreatment.getDevice().getDeviceName() + "\t" +
//                        medicalTreatment.getRentalDate() + "\t" + medicalTreatment.getReturnDate());
//            }
//        }
    }

    /**
     * 추가 필요 로직
     * 1. 매일 대여기간이 끝나는지 체크. 반납 안되었다면, 알림 발송
     *
     * */
}
