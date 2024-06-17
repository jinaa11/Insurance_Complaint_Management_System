package isuranceBenefit;

public enum ItemCode {
    ASSISTIVE_DEVICE("보조기기"),
    THERAPEUTIC_DEVICE("치료기기"),
    REHABILITATION_DEVICE("재활기기"),
    SPECIAL_BEDDING("특수침구");

    private final String value;

    ItemCode(String value) {
        this.value = value;
    }

    public ItemCode getCode(String value) {
        for (ItemCode code : ItemCode.values()) {
            if (code.value.equals(value)) {
                return code;
            }
        }
        return null;
    }
}
