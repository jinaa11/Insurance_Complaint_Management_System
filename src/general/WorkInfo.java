package general;

import java.time.LocalDate;

public class WorkInfo {
    // 필드
    private LocalDate createdDateTime;  // 입사일
    private String position;  // 직책
    private Boolean isPaid;  // 대납 여부
    private String relationship;  // (임시) 관계

    // 생성자
    private WorkInfo() {}
    public WorkInfo(LocalDate createdDateTime, String position) {
        this.createdDateTime = createdDateTime;
        this.position = position;
        this.isPaid = false;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    // getter / setter
    public LocalDate getCreatedDateTime() {
        return createdDateTime;
    }
    public void setCreatedDateTime(LocalDate createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public Boolean getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
