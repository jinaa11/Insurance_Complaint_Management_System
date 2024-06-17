package isuranceBenefit;

public class MedicalDevice {
  private ItemCode itemCode;
  private String deviceName;
  private long quantity;

  private MedicalDevice() {}
  public MedicalDevice(ItemCode itemCode, String deviceName, long quantity) {
    this.itemCode = itemCode;
    this.deviceName = deviceName;
    this.quantity = quantity;
  }

  public ItemCode getItemCode() {
    return this.itemCode;
  }

  public String getDeviceName() {
    return this.deviceName;
  }

  public long getQuantity() {
    return this.quantity;
  }

  public void increaseQuantity(long quantity) {
    this.quantity += quantity;
  }

  public void decreaseQuantity(long quantity) throws Exception {
    if (this.quantity - quantity < 0) {
      throw new Exception("재고가 부족합니다.");
    }
    this.quantity -= quantity;
  }

  public void updateQuantity(long quantity) {
    this.quantity = quantity;
  }

  public void showMedicalDevice() {
    System.out.println("분류코드:\t " + itemCode);
    System.out.println("의료기기 명:\t " + deviceName);
    System.out.println("재고:\t " + quantity);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof MedicalDevice) {
      MedicalDevice device = (MedicalDevice) o;
      return this.itemCode.equals(device.getItemCode()) && this.deviceName.equals(device.getDeviceName());
    }
    return false;
  }
}
