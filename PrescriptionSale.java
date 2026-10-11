/**
 * Victoria University Kampala
 * Faculty of Science and Technology
 * Module: 1301 ST - Object Oriented Programming
 * Student Name: Ahmed Abdijabar Farah
 * Registration Number: VU-BCS-2603-1795-EVE
 * 
 * Subclass representing prescription medication sales.
 * Rule: Quantity times price. Senior discount of 14% applies if age >= 60.
 */
public class PrescriptionSale extends Sale {

    private String doctorRegNumber;
    private String medicationName;

    /**
     * Parameterized constructor invoking super to initialize common Sale attributes.
     */
    public PrescriptionSale(String saleId, String customerName, int customerAge, 
                            int quantity, double unitPrice, String doctorRegNumber, 
                            String medicationName) throws InvalidSaleException {
        super(saleId, customerName, customerAge, quantity, unitPrice);
        setDoctorRegNumber(doctorRegNumber);
        setMedicationName(medicationName);
    }

    public String getDoctorRegNumber() {
        return doctorRegNumber;
    }

    public void setDoctorRegNumber(String doctorRegNumber) throws InvalidSaleException {
        if (doctorRegNumber == null || doctorRegNumber.trim().isEmpty()) {
            throw new InvalidSaleException("Doctor registration number cannot be blank.");
        }
        this.doctorRegNumber = doctorRegNumber.trim();
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) throws InvalidSaleException {
        if (medicationName == null || medicationName.trim().isEmpty()) {
            throw new InvalidSaleException("Medication name cannot be blank.");
        }
        this.medicationName = medicationName.trim();
    }

    @Override
    public String getRecordType() {
        return "Prescription Sale";
    }

    @Override
    public double calculateGrossAmount() {
        // Prescriptions do not incur packaging surcharge: gross = quantity * unitPrice
        return calculateBaseAmount();
    }

    @Override
    public double calculateDiscountAmount() {
        if (isDiscountEligible()) {
            return calculateGrossAmount() * SENIOR_DISCOUNT_RATE;
        }
        return 0.0;
    }

    /**
     * Overrides calculateCharge(): gross amount minus senior discount (if eligible).
     */
    @Override
    public double calculateCharge() {
        return calculateGrossAmount() - calculateDiscountAmount();
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Rx: %s (Dr. %s) | Final Charge: %,.2f UGX",
                medicationName, doctorRegNumber, calculateCharge());
    }
}
