package general;

import user.General;

import java.time.LocalDate;

public class Qualification {
    //필드
    private General general;
    private LocalDate acquireDate;
    private LocalDate lossDate;
    private String bname;

    // 생성자
    private Qualification() {}
    public Qualification(General general, LocalDate acquireDate, LocalDate lossDate, String bname) {
        this.general = general;
        this.acquireDate = acquireDate;
        this.lossDate = lossDate;
        this.bname = bname;
    }

    // 자격 사항 요약 확인 조회
    public void show() {
        String relationship = general.getWorkInfo() == null ? "직장피부양자" : "직장가입자";
        System.out.println("가입자 구분 : " + relationship);
        System.out.println("가입자 이름 : " + general.getName());
        System.out.println("취득일 : " + acquireDate);

    }

    // 자격 상세 확인서 조회
    public void showDetail(String bname) {
        String relationship = general.getWorkInfo() == null ? "직장피부양자" : "직장가입자";
        System.out.println("< 조회 >");
        System.out.println("주민등록번호 : " + general.getResidentNumber());
        System.out.println();
        System.out.println("< 조회결과 >" + "\t" + general.getName());
        System.out.println("건강보험증번호 : " + general.getInsuranceNumber());
        System.out.println("사업장명 : " + bname + "\n" + "가입자 구분 : " +  relationship);
        if (lossDate == null) {
            System.out.println("자격 상태 : 현자격");
        }
        System.out.println("취득일 : " + acquireDate);
        System.out.println("상실일 : " + lossDate);
    }

    // 자격 득실 확인서
    public void showRelationShip() {
        String relationship = general.getWorkInfo() == null ? "직장피부양자" : "직장가입자";

        System.out.println("< 조회 >");
        System.out.println("주민등록번호 : " + general.getResidentNumber());
        System.out.println();
        System.out.println("< 자격득실내역 >");
        System.out.println("가입자 구분: " + relationship);
        System.out.println("사업장명 : " + this.bname);
        System.out.println("취득일 : " + acquireDate);
        System.out.println("상실일 : " + lossDate);
    }

    public LocalDate getAcquireDate() {
        return acquireDate;
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }
}
