package general;

import user.User;

import java.util.Date;

public class WorkInfo {
    // 필드
    private Date createdDateTime;  // 입사일
    private String position;  // 직책
    private Boolean isPaid;  // 대납 여부

    // 생성자
    public WorkInfo() {}

    public WorkInfo(Date createdDateTime, String position, Boolean isPaid) {
        this.createdDateTime = createdDateTime;
        this.position = position;
        this.isPaid = isPaid;
    }

    // getter / setter
    public Date getCreatedDateTime() {
        return createdDateTime;
    }
    public void setCreatedDateTime(Date createdDateTime) {
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
