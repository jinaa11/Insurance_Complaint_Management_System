package medicaltreatment;

public enum DiseaseCode {

  LUNG_DISEASE("D_001", "폐질환"),
  CHRONIC_HEART_FAILURE("D_002", "만성심부전증"),
  NEUROGENIC_DISORDERS("D_003", "신경인성질환"),
  RESPIRATORY_DISEASE("D_004", "호흡질환");

  private String id;
  private String value;

  DiseaseCode(String id, String value) {
    this.id = id;
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static void showDiseaseCode() {
    System.out.print("질병 코드 목록: ");
    DiseaseCode[] codes = DiseaseCode.values();
    for (int i = 0; i < codes.length; i++) {
      System.out.print(codes[i].getValue());
      if (i < codes.length - 1) {
        System.out.print(", ");  // 마지막 요소가 아니라면 쉼표와 공백을 추가
      }
    }
    System.out.println();  // 모든 요소 출력 후 줄바꿈
  }

  public static DiseaseCode getDiseaseCode(String value) {
    for (DiseaseCode diseaseCode : DiseaseCode.values()) {
      if (diseaseCode.getValue().equals(value)) {
        return diseaseCode;
      }
    }
    return null;
  }
}
