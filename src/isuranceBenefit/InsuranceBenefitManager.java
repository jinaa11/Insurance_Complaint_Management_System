package isuranceBenefit;

import medicaltreatment.MedicalTreatment;
import medicaltreatment.MedicalTreatmentManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsuranceBenefitManager {
    private static InsuranceBenefitManager instance;

    // 관리중인 의료기기 목록
    private List<MedicalDevice> medicalDevices = new ArrayList<>();

    private InsuranceBenefitManager() {}
    public static InsuranceBenefitManager getInstance() {
        if (instance == null) {
            synchronized (InsuranceBenefitManager.class) {
                if (instance == null) {
                    instance = new InsuranceBenefitManager();
                }
            }
        }

        return instance;
    }

    public MedicalDevice searchMedicalDevice(ItemCode itemCode, String deviceName) throws Exception {
        return medicalDevices.stream()
            .filter(m -> m.getItemCode().equals(itemCode) && m.getDeviceName().equals(deviceName))
            .findFirst().orElseThrow(() -> new Exception("존재하지 않는 의료기기 입니다."));
    }

    public void addMedicalDevice(MedicalDevice medicalDevice) {
        this.medicalDevices.add(medicalDevice);
    }

    public void addMedicalDevices(List<MedicalDevice> medicalDevices) {
        this.medicalDevices.addAll(medicalDevices);
    }

    public void updateDeviceQuantity(ItemCode itemCode, String deviceName, long quantity) {
        try {
            MedicalDevice medicalDevice = searchMedicalDevice(itemCode, deviceName);
            medicalDevice.updateQuantity(quantity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMedicalDevice(ItemCode itemCode, String deviceName) throws Exception {
        MedicalDevice deviceToDelete = medicalDevices.stream()
            .filter(m -> m.getItemCode().equals(itemCode) && m.getDeviceName().equals(deviceName))
            .findFirst()
            .orElseThrow(() -> new Exception("존재하지 않는 의료기기 입니다."));

        medicalDevices.remove(deviceToDelete);
    }

    public void showMedicalDevices() {
        for (MedicalDevice medicalDevice : medicalDevices) {
            medicalDevice.showMedicalDevice();
            System.out.println("===================");
        }
    }

    public void showMedicalCareStatustics(LocalDate startDate, LocalDate endDate, MedicalTreatmentManager manager) {
        List<MedicalTreatment> treatments = manager.getMedicalTreatments();

        for (MedicalTreatment treatment : treatments) {
            if (treatment.getReturnDate() != null && treatment.getReturnDate().isAfter(startDate) && treatment.getReturnDate().isBefore(endDate)) {
                treatment.showMedicalTreatment();
            }
        }
    }
}
