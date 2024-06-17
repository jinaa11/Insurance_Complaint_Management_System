package common;

public enum Payment {
  CARD("카드"),
  ACCOUNT("계좌");

  private String value;

  Payment(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static boolean isValidPayment(String payment) {
    return payment.equals(CARD.getValue()) || payment.equals(ACCOUNT.getValue());
  }
}
