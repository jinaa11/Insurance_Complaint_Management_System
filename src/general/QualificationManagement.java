package general;

import business.Business;
import user.General;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class QualificationManagement {
    // 필드
    List<General> generalList = new ArrayList<General>();  // (1 대 다)
    private Business business;
//    private String relationship;  // (임시) 관계
//    private String bname;  // (임시) 사업장명
//    private String acquireDate; // (임시) 취득일
//    private String lossDate; // (임시) 상실일

    // 생성자
    public QualificationManagement() {}

    /*public QualificationManagement(String relationship, long bname, String acquireDate, String lossDate) {
        this.relationship = relationship;
        this.bname = bname;
        this.acquireDate = acquireDate;
        this.lossDate = lossDate;
    }*/

    // 메소드
    // 일반 사용자 추가
    public void addGeneral() {
        System.out.println("일반 사용자 이름 :");
        String name = DataInput.sc.nextLine();
        System.out.println("일반 사용자 생일 :");
        String birth = DataInput.sc.nextLine();
        System.out.println("일반 사용자 전화번호 :");
        String phoneNumber = DataInput.sc.nextLine();
        System.out.println("일반 사용자 주민등록번호 :");
        String residentNumber = DataInput.sc.nextLine();
        System.out.println("일반 사용자 건강보험증번호 :");
        Long insuranceNumber = DataInput.sc.nextLong();
        System.out.println("일반 사용자 납부자 번호 :");
        long payerNumber = DataInput.sc.nextLong();
        DataInput.sc.nextLine();
        System.out.println("관계 :");
        String relationship = DataInput.sc.nextLine();
        System.out.println("사업장명 :");
        String bname = DataInput.sc.nextLine();
        System.out.println("취득일 :");
        String acquireDate = DataInput.sc.nextLine();
        System.out.println("상실일 :");
        String lossDate = DataInput.sc.nextLine();

        // 직장 정보 입력
        WorkInfo workInfo = getWorkInfo();

        General general = new General(name, birth, phoneNumber, residentNumber, insuranceNumber, payerNumber, relationship, bname, acquireDate, lossDate, workInfo);
        generalList.add(general);
    }

    // 직장 정보 추가
    private WorkInfo getWorkInfo() {
        System.out.println("입사 날짜 입력 (yyyy-MM-dd):");
        String createdDateTime= DataInput.sc.nextLine();
        Date createdDate = null;
        try {
            createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(createdDateTime);
        } catch (ParseException e) {
            System.out.println("날짜 형식이 올바르지 않습니다. 다시 입력해주세요.");
            createdDateTime = DataInput.sc.nextLine();
            try {
                createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(createdDateTime);
            } catch (ParseException ex) {
                System.out.println("입력을 취소합니다.");
                return null;
            }
        }

        System.out.println("직책 입력:");
        String position = DataInput.sc.nextLine();

        System.out.println("대납 여부 (true/false):");
        String isPaidStr = DataInput.sc.nextLine();
        boolean isPaid = false;
        if (isPaidStr.equalsIgnoreCase("true")) {
            isPaid = true;
        } else if (isPaidStr.equalsIgnoreCase("false")) {
            isPaid = false;
        } else {
            System.out.println("대납 여부가 올바르지 않습니다. 다시 입력해주세요.");
            isPaidStr = DataInput.sc.nextLine();
            if (isPaidStr.equalsIgnoreCase("true")) {
                isPaid = true;
            } else if (isPaidStr.equalsIgnoreCase("false")) {
                isPaid = false;
            } else {
                System.out.println("입력을 취소합니다.");
                return null;
            }
        }

        return new WorkInfo(createdDate, position, isPaid);
    }

    // 일반 사용자 자격 확인서
    public void showQualificationConfirmation() {
        generalList.stream().forEach(g -> {
            System.out.println("가입자 구분\t사업장 명칭\t취득일\t상실일");
            System.out.println(g.getRelationship() + "\t"+ business.getBname()  + "\t" + g.getAcquireDate() + "\t" + g.getLossDate());
        });
    }

    // 일반 사용자 자격 득실 확인서
    public void showQualificationConfirmationDetail() {
        generalList.stream().forEach(g -> {
            System.out.println("납부자 번호\t가입자 구분\t사업장 명칭\t취득일\t상실일");
            System.out.println(g.getPayerNumber() + "\t" + g.getRelationship() + "\t" + business.getBname() + "\t" + g.getAcquireDate() + "\t" + g.getLossDate());
        });
    }

    // 일반 사용자 검색
    public void searchGeneral() {
        // 검색할 값
        System.out.println("검색하고자 하는 건강보험증 번호 입력란 :");
        String residentNumber = DataInput.sc.nextLine();

        boolean dataFound = false;  // 데이터 유무를 파악

        // 데이터가 존재 할 시
        generalList.stream()
                .filter(g -> g.getResidentNumber().equals(residentNumber))
                .forEach(g -> g.show());

        dataFound = true;

        // 데이터가 존재하지 않을 시
        if (!dataFound) {
            System.out.println("일치하는 건강보험증 번호에 대한 정보가 없습니다.");
        }
    }

    // 일반 사용자 관계 업데이트
    public void updateGeneral() {
        // 검색할 값
        System.out.println("수정하고자 하는 일반 사용자 이름 입력란 :");
        String name = DataInput.sc.nextLine();

        System.out.println("변경할 내용 작성 : ");
        String newRelationship = DataInput.sc.nextLine();

        boolean dataFound = false;  // 데이터 유무를 파악

        // 데이터가 존재 할 시
        generalList.stream()
                .filter(g -> g.getName().equals(name))
                .forEach(g -> {
                    g.setRelationship(newRelationship);
                });

        dataFound = true;

        // 데이터가 존재하지 않을 시
        if (!dataFound) {
            System.out.println("일치하는 일반 사용자 이름에 대한 정보가 없습니다.");
        }
    }

    // 일반 사용자 삭제
    public void deleteGeneral() {
        // 삭제할 값
        System.out.println("삭제하고자 하는 건강보험증 번호 입력란 :");
        String residentNumber = DataInput.sc.nextLine();

        generalList.stream()
                .filter(g-> g.getResidentNumber().equals(residentNumber))
                .forEach(g -> generalList.remove(g));
    }

    // getter / setter
//    public String getRelationship() {
//        return relationship;
//    }
//    public void setRelationship(String relationship) {
//        this.relationship = relationship;
//    }
//
//    public String getBname() {
//        return bname;
//    }
//    public void setBname(String bname) {
//        this.bname = bname;
//    }
//    public String getAcquireDate() {
//        return acquireDate;
//    }
//    public void setAcquireDate(String acquireDate) {
//        this.acquireDate = acquireDate;
//    }
//    public String getLossDate() {
//        return lossDate;
//    }
//
//    public void setLossDate(String lossDate) {
//        this.lossDate = lossDate;
//    }

}
