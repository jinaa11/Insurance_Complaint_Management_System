package general;

import java.time.LocalDate;

public class WorkInfo {
    // 필드
    private LocalDate createdDateTime = LocalDate.now();// 입사일
    private long bid;
    private String bname;
    private String position;  // 직책
    private Boolean isPaid;  // 대납 여부

    // 생성자
    private WorkInfo() {}
    public WorkInfo(long bid, String position, String bname) {
        this.bid = bid;
        this.position = position;
        this.bname = bname;
        this.isPaid = false;
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

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }
}
