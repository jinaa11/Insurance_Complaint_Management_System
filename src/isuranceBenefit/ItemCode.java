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

    public static void showValues() {
        System.out.print("폼목코드 목록: ");
        ItemCode[] codes = ItemCode.values();
        for (int i = 0; i < codes.length; i++) {
            System.out.print(codes[i].value);
            if (i < codes.length - 1) {
                System.out.print(", ");  // 마지막 요소가 아니라면 쉼표와 공백을 추가
            }
        }
        System.out.println();  // 모든 요소 출력 후 줄바꿈
    }

    public static ItemCode getCode(String value) {
        for (ItemCode code : ItemCode.values()) {
            if (code.value.equals(value)) {
                return code;
            }
        }
        return null;
    }
}
