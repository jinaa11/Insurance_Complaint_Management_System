package isuranceBenefit;

import java.util.ArrayList;
import java.util.List;

public class InsuranceBenefitManager {
    private static InsuranceBenefitManager instance;

    // 관리중인 의료기기 목록
    private final List<MedicalDevice> medicalDevices = new ArrayList<>();

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

    public MedicalDevice findMedicalDevice(ItemCode itemCode, String deviceName) throws Exception {
        return medicalDevices.stream()
            .filter(m -> m.getItemCode().equals(itemCode) && m.getDeviceName().equals(deviceName))
            .findFirst().orElseThrow(() -> new Exception("존재하지 않는 의료기기 입니다."));
    }

    public void addDevice(MedicalDevice medicalDevice) {
        this.medicalDevices.add(medicalDevice);
    }

    public void addDevices(List<MedicalDevice> medicalDevices) {
        this.medicalDevices.addAll(medicalDevices);
    }

    public void updateDeviceQuantity(ItemCode itemCode, String deviceName, long quantity) {
        try {
            MedicalDevice medicalDevice = findMedicalDevice(itemCode, deviceName);
            medicalDevice.updateQuantity(quantity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDevice(ItemCode itemCode, String deviceName) throws Exception {
        MedicalDevice deviceToDelete = medicalDevices.stream()
            .filter(m -> m.getItemCode().equals(itemCode) && m.getDeviceName().equals(deviceName))
            .findFirst()
            .orElseThrow(() -> new Exception("존재하지 않는 의료기기 입니다."));

        medicalDevices.remove(deviceToDelete);
    }

    public void showDevices() {
        medicalDevices.forEach(MedicalDevice::showMedicalDevice);
    }
}
