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
}
