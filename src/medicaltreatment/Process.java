package medicaltreatment;

public enum Process {
  UNABLE("처리불가"),
  PROCESS("처리됨"),
  WAIT("대기중"),
  RETURN("반납완료");

  private String value;

  Process(String value) {
    this.value = value;
  }
}
