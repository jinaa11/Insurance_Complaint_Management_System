package medicaltreatment;

import common.Payment;
import isuranceBenefit.InsuranceBenefitManager;
import isuranceBenefit.MedicalDevice;
import user.General;

import java.util.*;
import java.util.stream.Collectors;

public class MedicalTreatmentManager {


    private InsuranceBenefitManager insuranceBenefitManager;
    private List<MedicalTreatment> medicalTreatments;
    private List<General> currentlyBenefitUsers;

    private MedicalTreatmentManager() {}
    public MedicalTreatmentManager(InsuranceBenefitManager manager) {
        this.insuranceBenefitManager = manager;
        this.medicalTreatments = new ArrayList<>();
        this.currentlyBenefitUsers = new LinkedList<>();
    }

    public void showMedicalTreatmentsByGeneral(General general) {
        System.out.println("==================================================================================");
        System.out.println("|No\t처리상태\t질병분류\t수진자주민번호\t수진자명\t환급금액\t환급수단\t의료기기명\t대여일\t반납일|");
        List<MedicalTreatment> treatments = medicalTreatments.stream()
            .filter(m -> m.getGeneral().equals(general))
            .collect(Collectors.toList());

        for (MedicalTreatment treatment : treatments) {
            treatment.showMedicalTreatment();
        }
    }

    public void insertMedicalTreatment(DiseaseCode code, String itemCode, General general) {
        MedicalTreatment medicaltreatment = new MedicalTreatment(code, general);
        medicalTreatments.add(medicaltreatment);
        try {
            if (isValidMedicalTreatment(medicaltreatment, itemCode)) {
                medicaltreatment.updateProcess(Process.PROCESS);
            }
        } catch (Exception e) {
            medicaltreatment.updateProcess(Process.UNABLE);
            System.out.println(e.getMessage());
        }
    }

    public void rentalMedicalDevice(String itemCode, String deviceName, Payment payment, General general, Date rentalDate, Date returnDate, long quantity) {
        try {
            MedicalDevice device = insuranceBenefitManager.searchMedicalDevice(itemCode, deviceName);
            MedicalTreatment medicalTreatment = medicalTreatments.stream()
                .sorted(Comparator.comparing(MedicalTreatment::getCreateDate, Comparator.reverseOrder()))
                .filter(m -> m.getGeneral().getResidentNumber().equals(general.getResidentNumber()))
                .findFirst().orElseThrow(() -> new Exception("요양비 대상자가 아닙니다."));

            medicalTreatment.updateRentalData(device, payment, rentalDate, returnDate);
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

    public void returnMedicalDevice(String itemCode, String deviceName, General general, long quantity) {
        try {
            MedicalDevice device = insuranceBenefitManager.searchMedicalDevice(itemCode, deviceName);
            MedicalTreatment medicalTreatment = medicalTreatments.stream()
                .sorted(Comparator.comparing(MedicalTreatment::getCreateDate, Comparator.reverseOrder()))
                .filter(m -> m.getGeneral().getResidentNumber().equals(general.getResidentNumber()))
                .findFirst().orElseThrow(() -> new Exception("요양비 대상자가 아닙니다."));

            if (medicalTreatment.getDevice().equals(device)) {
                device.increaseQuantity(quantity);
                medicalTreatment.updateProcess(Process.RETURN);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isValidMedicalTreatment(MedicalTreatment medicalTreatment, String itemCode) throws Exception {
        List<MedicalTreatment> medicalTreatmentsBySameGeneral = medicalTreatments.stream()
            .filter(m -> m.getGeneral().equals(medicalTreatment.getGeneral())).collect(Collectors.toList());

        for (MedicalTreatment treatment : medicalTreatmentsBySameGeneral) {
            if (treatment.getDevice().getItemCode().equals(itemCode)
                && !treatment.isPeriodOverlap(medicalTreatment.getRentalDate(), medicalTreatment.getReturnDate())) {
                throw new Exception("기존에 대여하고 있는 요양비 내역이 있습니다.");
            }
        }

        return true;
    }

    /**
     * 추가 필요 로직
     * 1. 매일 대여기간이 끝나는지 체크. 반납 안되었다면, 알림 발송
     *
     * */
}
