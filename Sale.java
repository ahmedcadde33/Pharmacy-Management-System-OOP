/**
 * Victoria University Kampala
 * Faculty of Science and Technology
 * Module: 1301 ST - Object Oriented Programming
 * Student Name: Ahmed Abdijabar Farah
 * Registration Number: VU-BCS-2603-1795-EVE
 * 
 * Abstract base class representing a generic pharmacy sale.
 * Implements Chargeable and enforces strict encapsulation and validation.
 */
public abstract class Sale implements Chargeable {

    // Discount rate constant P = B + 5 = 9 + 5 = 14% (0.14)
    public static final double SENIOR_DISCOUNT_RATE = 0.14;
    public static final int SENIOR_AGE_THRESHOLD = 60;

    // Encapsulated private attributes
    private String saleId;
    private String customerName;
    private int customerAge;
    private int quantity;
    private double unitPrice;
    private boolean discountEligible;

    /**
     * Parameterized constructor initializing and validating a sale record.
     */
    public Sale(String saleId, String customerName, int customerAge, int quantity, double unitPrice) 
            throws InvalidSaleException {
        setSaleId(saleId);
        setCustomerName(customerName);
        setCustomerAge(customerAge);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
        // Automatically determine discount eligibility based on business rule (age >= 60)
        this.discountEligible = (this.customerAge >= SENIOR_AGE_THRESHOLD);
    }

    // --- Getters and Setters with Business Validations ---

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) throws InvalidSaleException {
        if (saleId == null || saleId.trim().isEmpty()) {
            throw new InvalidSaleException("Sale ID cannot be blank or null.");
        }
        this.saleId = saleId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) throws InvalidSaleException {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new InvalidSaleException("Customer name cannot be blank or empty.");
        }
        if (customerName.trim().length() < 2) {
            throw new InvalidSaleException("Customer name must contain at least 2 characters.");
        }
        this.customerName = customerName.trim();
    }

    public int getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(int customerAge) throws InvalidSaleException {
        if (customerAge < 0 || customerAge > 125) {
            throw new InvalidSaleException("Customer age must be between 0 and 125 years.");
        }
        this.customerAge = customerAge;
        this.discountEligible = (this.customerAge >= SENIOR_AGE_THRESHOLD);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws InvalidSaleException {
        if (quantity <= 0) {
            throw new InvalidSaleException("Quantity must be greater than zero.");
        }
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) throws InvalidSaleException {
        if (unitPrice < 0) {
            throw new InvalidSaleException("Unit price cannot be negative.");
        }
        this.unitPrice = unitPrice;
    }

    public boolean isDiscountEligible() {
        return discountEligible;
    }

    /**
     * Calculates the gross amount before any discounts or surcharges.
     */
    public double calculateBaseAmount() {
        return quantity * unitPrice;
    }

    /**
     * Returns the name/category of the sale record type for sorting and reporting.
     */
    public abstract String getRecordType();

    /**
     * Calculates the exact monetary discount applied in UGX.
     */
    public abstract double calculateDiscountAmount();

    /**
     * Returns the gross amount before discount.
     */
    public abstract double calculateGrossAmount();

    /**
     * Contract from Chargeable implemented as abstract for concrete subclasses.
     */
    @Override
    public abstract double calculateCharge();

    @Override
    public String toString() {
        return String.format("[%s] ID: %s | Customer: %s (Age: %d) | Qty: %d @ %,.0f UGX | Discount Eligible: %s",
                getRecordType(), saleId, customerName, customerAge, quantity, unitPrice, 
                discountEligible ? "Yes (14%)" : "No");
    }
}
