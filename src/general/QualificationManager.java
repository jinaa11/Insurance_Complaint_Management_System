package general;

import java.util.ArrayList;
import java.util.Comparator;
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
        System.out.println("===========================");
        qualifications.stream().filter(q -> q.getGeneral().getInsuranceNumber() == general.getInsuranceNumber()).forEach(q -> {
            q.show();
            System.out.println("---");
        });
        System.out.println("===========================");
    }

    // 자격 상세 확인서 전체 조회
    public void showCertificateOfQualificationDetail(General general) {
        System.out.println("=============================");
        qualifications.stream().filter(q -> q.getGeneral().getInsuranceNumber() == general.getInsuranceNumber()).forEach(q -> {
            q.showDetail(general.getWorkInfo().getBname());
            System.out.println("---");
        });
        System.out.println("=============================");
    }

    // 자격 득실 확인 전체 조회
    public void showCertificateOfEligibilityn(General general) {
        System.out.println("=============================");
        qualifications.stream().sorted(Comparator.comparing(Qualification::getAcquireDate, Comparator.reverseOrder())).forEach(q -> {
            q.showRelationShip();
            System.out.println("---");
        });
        System.out.println("=============================");
    }
}
