package general;

import java.time.LocalDate;

public class WorkInfo {
    private LocalDate createdDateTime = LocalDate.now();// 입사일
    private long bid;
    private String bname;
    private String position;  // 직책

    private WorkInfo() {}
    public WorkInfo(long bid, String position, String bname) {
        this.bid = bid;
        this.position = position;
        this.bname = bname;
    }

    public String getBname() {
        return bname;
    }

    public long getBid() {
        return bid;
    }

    public String getPosition() {return position;}
}
