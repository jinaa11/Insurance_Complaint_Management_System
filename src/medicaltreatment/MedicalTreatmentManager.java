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

    public void showMedicalTreatmentsByGeneral(General general) {
        System.out.println("=================================================================================================================================================");
        System.out.println("|No\t처리상태\t질병분류\t수진자주민번호\t\t수진자명\t환급금액\t\t환급수단\t\t\t\t\t의료기기명\t대여일\t\t반납일\t\t등록일\t\t\t\t\t\t|");
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        List<MedicalTreatment> treatments = medicalTreatments.stream()
                .filter(m -> m.getGeneral().equals(general))
                .collect(Collectors.toList());

        for (MedicalTreatment treatment : treatments) {
            treatment.showMedicalTreatment();
        }
        System.out.println("=================================================================================================================================================");
    }

    public void insertMedicalTreatment(DiseaseCode code, ItemCode itemCode, General general) {
        MedicalTreatment medicaltreatment = new MedicalTreatment(code, general, itemCode);
        try {
            if (isValidMedicalTreatment(medicaltreatment, itemCode)) {
                medicaltreatment.updateProcess(Process.WAIT);
            }
        } catch (Exception e) {
            medicaltreatment.updateProcess(Process.UNABLE);
            System.out.println(e.getMessage());
        }
        medicalTreatments.add(medicaltreatment);
        System.out.println("요양비 대상자 등록이 완료 되었습니다.\n");
    }

    public void rentalMedicalDevice(ItemCode itemCode, String deviceName, Payment payment, General general,
                                    LocalDate rentalDate, LocalDate returnDate, long quantity, long amount) {
        try {
            MedicalDevice device = insuranceBenefitManager.findMedicalDevice(itemCode, deviceName);
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
            MedicalDevice device = insuranceBenefitManager.findMedicalDevice(itemCode, deviceName);
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
            boolean isDevice = treatment.getItemCode().equals(itemCode);
            if (isProcess && isDevice) {
                throw new Exception("기존에 대여하고 있는 요양비 내역이 있습니다.");
            }
        }

        return true;
    }

    public void showCurrentlyBenefitUsers() {
        List<MedicalTreatment> medicalTreatments = new ArrayList<>();
        for (General general : currentlyBenefitUsers) {
            medicalTreatments.addAll(this.medicalTreatments.stream()
                .filter(m -> m.getGeneral().equals(general))
                .collect(Collectors.toList()));
        }
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
    }

    public void showMedicalCareStatustics(LocalDate startDate, LocalDate endDate) {
        for (MedicalTreatment treatment : this.medicalTreatments) {
            if (treatment.getReturnDate() != null && treatment.getReturnDate().isAfter(startDate) && treatment.getReturnDate().isBefore(endDate)) {

                treatment.showMedicalTreatment();
            }
        }
    }
}
