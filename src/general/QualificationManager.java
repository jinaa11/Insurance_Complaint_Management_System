package general;

import java.util.ArrayList;
import java.util.List;

import user.General;

public class QualificationManager {
    public static QualificationManager instance;

    List<Qualification> qualifications = new ArrayList<Qualification>();

    // 생성자
    public QualificationManager() {}

    // 싱글톤 패턴
    public QualificationManager getInstance() {
        if (instance == null) {
            synchronized (QualificationManager.class) {
                if (instance == null) {
                    instance = new QualificationManager();
                }
            }
        }
        return instance;
    }

    public void addQualification(Qualification qualification) {
        qualifications.add(qualification);
    }

    public void addQualifications(List<Qualification> qualifications) {
        this.qualifications.addAll(qualifications);
    }

    //메소드
    // 자격 사항 요약 확인 전체 조회
    public void showCertificateOfQualification(General general) {
        qualifications.stream().filter(q -> q.getGeneral().getInsuranceNumber() == general.getInsuranceNumber()).forEach(Qualification::show);
    }

    // 자격 상세 확인서 전체 조회
    public void showCertificateOfQualificationDetail(General general) {
        qualifications.stream().filter(q -> q.getGeneral().getInsuranceNumber() == general.getInsuranceNumber()).forEach(Qualification::showDetail);

    }

    // 자격 득실 확인 전체 조회
    public void showCertificateOfEligibilityn(General general) {
        qualifications.stream().sorted().forEach(q-> q.showRelationShip());

    }

    /*public QualificationManagement(String relationship, long bname, String acquireDate, String lossDate) {
        this.relationship = relationship;
        this.bname = bname;
        this.acquireDate = acquireDate;
        this.lossDate = lossDate;
    }*/

    // 직장 정보 추가
    // 수정해야할 메소드들 이외에는 무시해도 됨
/*    private WorkInfo getWorkInfo() {
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

        return new WorkInfo(createdDate, position);
    }
    public void showQualificationConfirmationSummary() {
    generalList.forEach(g -> {
        System.out.println("가입자 구분\t사업장 명칭\t취득일\t상실일");
        System.out.println(g.getRelationship() + "\t"+ business.getBname()  + "\t" + g.getAcquireDate() + "\t" + g.getLossDate());
    });
}
    // 일반 사용자 자격 확인서
    public void showQualificationConfirmation() {
        generalList.forEach(g -> {
            System.out.println("가입자 구분\t사업장 명칭\t취득일\t상실일");
            System.out.println(g.getRelationship() + "\t"+ business.getBname()  + "\t" + g.getAcquireDate() + "\t" + g.getLossDate());
        });
    }

    // 일반 사용자 자격 득실 확인서
    public void showQualificationConfirmationDetail() {
        generalList.forEach(g -> {
            System.out.println("납부자 번호\t가입자 구분\t사업장 명칭\t취득일\t상실일");
            System.out.println(g.getPayerNumber() + "\t" + g.getRelationship() + "\t" + business.getBname() + "\t" + g.getAcquireDate() + "\t" + g.getLossDate());
        });
    }*/
    // 일반 사용자 관계 업데이트
//    public void updateGeneral() {
//        // 검색할 값
//        System.out.println("수정하고자 하는 일반 사용자 이름 입력란 :");
//        String name = DataInput.sc.nextLine();
//
//        System.out.println("변경할 내용 작성 : ");
//        String newRelationship = DataInput.sc.nextLine();
//
//        boolean dataFound = false;  // 데이터 유무를 파악
//
//        // 데이터가 존재 할 시
//        generalList.stream()
//                .filter(g -> g.getName().equals(name))
//                .forEach(g -> {
//                    g.setRelationship(newRelationship);
//                });
//
//        dataFound = true;
//
//        // 데이터가 존재하지 않을 시
//        if (!dataFound) {
//            System.out.println("일치하는 일반 사용자 이름에 대한 정보가 없습니다.");
//        }
//    }

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
