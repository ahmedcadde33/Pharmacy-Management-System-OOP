/**
 * Victoria University Kampala
 * Faculty of Science and Technology
 * Module: 1301 ST - Object Oriented Programming
 * Student Name: Ahmed Abdijabar Farah
 * Registration Number: VU-BCS-2603-1795-EVE
 * 
 * Subclass representing over-the-counter (OTC) pharmacy sales.
 * Rule: Quantity times price plus 1,000 UGX packaging fee.
 * Senior discount of 14% applies to the total amount for customers aged 60 or over.
 */
public class CounterSale extends Sale {

    // Packaging fee constant mandated by Table 1 (1,000 UGX)
    public static final double PACKAGING_FEE = 1000.0;

    private String itemName;
    private String category; // e.g., "First Aid", "Vitamins", "Hygiene", "Pain Relief"

    /**
     * Parameterized constructor invoking super to initialize common Sale attributes.
     */
    public CounterSale(String saleId, String customerName, int customerAge, 
                       int quantity, double unitPrice, String itemName, 
                       String category) throws InvalidSaleException {
        super(saleId, customerName, customerAge, quantity, unitPrice);
        setItemName(itemName);
        setCategory(category);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) throws InvalidSaleException {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new InvalidSaleException("Item name cannot be blank.");
        }
        this.itemName = itemName.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) throws InvalidSaleException {
        if (category == null || category.trim().isEmpty()) {
            throw new InvalidSaleException("Item category cannot be blank.");
        }
        this.category = category.trim();
    }

    @Override
    public String getRecordType() {
        return "Counter Sale";
    }

    @Override
    public double calculateGrossAmount() {
        // Table 1 rule: quantity times price plus 1,000 packaging fee
        return calculateBaseAmount() + PACKAGING_FEE;
    }

    @Override
    public double calculateDiscountAmount() {
        if (isDiscountEligible()) {
            return calculateGrossAmount() * SENIOR_DISCOUNT_RATE;
        }
        return 0.0;
    }

    /**
     * Overrides calculateCharge(): gross amount (including packaging) minus senior discount (if eligible).
     */
    @Override
    public double calculateCharge() {
        return calculateGrossAmount() - calculateDiscountAmount();
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Item: %s (%s) [Pkg: %,.0f UGX] | Final Charge: %,.2f UGX",
                itemName, category, PACKAGING_FEE, calculateCharge());
    }
}
