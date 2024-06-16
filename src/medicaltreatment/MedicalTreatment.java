package medicaltreatment;

import common.Payment;
import isuranceBenefit.MedicalDevice;
import user.General;

import java.util.Date;

public class MedicalTreatment {
    private static long MT_ID = 1L;
    private long id;
    private DiseaseCode diseaseCode;
    private General general;
    private MedicalDevice device;
    private Process process;
    private boolean isPaid = false;
    private Payment payment;
    private long amount;
    private String account;
    private Date rentalDate;
    private Date returnDate;
    private Date createDate;

    private MedicalTreatment() {}
    public MedicalTreatment(DiseaseCode code, General general) {
        this.id = MT_ID++;
        this.diseaseCode = code;
        this.general = general;
        this.createDate = new Date();
    }

    public void updateProcess(Process process) {
        this.process = process;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public General getGeneral() {
        return this.general;
    }

    public MedicalDevice getDevice() {
        return this.device;
    }

    public long getAmount() {
        return this.amount;
    }

    public String getAccount() {
        return this.account;
    }

    public Date getRentalDate() {
        return this.rentalDate;
    }

    public Date getReturnDate() {
        return this.returnDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void updateRentalData(MedicalDevice device, Payment payment, Date rentalDate, Date returnDate) {
        this.device = device;
        this.payment = payment;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.isPaid = true;
        this.process = Process.PROCESS;
    }

    public void showMedicalTreatment() {
        System.out.println("|" + this.id + "\t" + this.diseaseCode.getValue() + "\t" +
            this.general.getResidentNumber() + "\t" + this.general.getName() + "\t" +
            this.amount + "\t" + this.account + "\t" + this.device.getDeviceName() + "\t" +
            this.rentalDate + "\t" + this.returnDate + "|");
    }

    public boolean isPeriodOverlap(Date rentalDate, Date returnDate) {
        return !(this.returnDate.before(rentalDate) || this.rentalDate.after(returnDate));
    }
}
