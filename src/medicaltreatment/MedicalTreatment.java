package medicaltreatment;

import common.Payment;
import isuranceBenefit.ItemCode;
import isuranceBenefit.MedicalDevice;
import user.General;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private ItemCode itemCode;
    private String account;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private LocalDateTime createDate;

    private MedicalTreatment() {}
    public MedicalTreatment(DiseaseCode code, General general, ItemCode itemCode) {
        this.id = MT_ID++;
        this.diseaseCode = code;
        this.general = general;
        this.process = Process.WAIT;
        this.createDate = LocalDateTime.now();
        this.itemCode = itemCode;
    }

    public void updateProcess(Process process) {
        this.process = process;
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

    public Process getProcess() {
        return this.process;
    }

    public ItemCode getItemCode() {
        return this.itemCode;
    }

    public LocalDate getRentalDate() {
        return this.rentalDate;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public void updateRentalData(MedicalDevice device, Payment payment, LocalDate rentalDate, LocalDate returnDate, long amount) {
        this.device = device;
        this.payment = payment;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.isPaid = true;
        this.process = Process.PROCESS;
        this.amount = amount;
    }

    public void showMedicalTreatment() {
        String maskedResidentNumber = getGeneral().getMaskedResidentNumber();
        String formatAmount = String.format("%,d원", getAmount());

        if (this.process.equals(Process.WAIT) || this.process.equals(Process.UNABLE)) {
            System.out.println("|" + this.id + "\t" + this.process.getValue() + "\t" + this.diseaseCode.getValue() + "\t" +
                    maskedResidentNumber + "\t" + this.general.getName() + "\t" +
                    " "+ "\t" + " " + "\t" + " " + "\t\t\t\t\t\t" +
                    " " + "\t" + " " + "\t\t\t\t\t\t\t\t\t" + this.createDate + "|");
            return;
        }
        // localdate format yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rentalDate = sdf.format(Date.from(this.rentalDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        String returnDate = sdf.format(Date.from(this.returnDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()));

        String accountNo;
        if (this.account != null) {
            accountNo = this.account + "\t\t";
        } else {
            accountNo = " " + "\t\t\t\t\t\t";
        }

        System.out.println("|" + this.id + "\t" + this.process.getValue() + "\t" + this.diseaseCode.getValue() + "\t" +
                maskedResidentNumber + "\t" + this.general.getName() + "\t" +
                formatAmount + "\t" + accountNo + this.device.getDeviceName() + "\t\t" +
                rentalDate + "\t" + returnDate + "\t" + this.createDate + "|");

    }

    public boolean isPeriodOverlap(LocalDate rentalDate, LocalDate returnDate) {
        return !(this.returnDate.isBefore(rentalDate) || this.rentalDate.isAfter(returnDate));
    }
}
