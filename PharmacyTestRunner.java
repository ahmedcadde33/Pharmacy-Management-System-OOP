/**
 * Test Runner to verify validation exceptions and generate exact test evidence.
 */
public class PharmacyTestRunner {
    public static void main(String[] args) {
        System.out.println("--- TEST SUITE EXECUTION ---");

        // Test 1: PrescriptionSale Senior Discount (Age 65 >= 60)
        try {
            PrescriptionSale rx = new PrescriptionSale("AAF795-001", "Mugisha Patrick", 65, 3, 25000.0, "MED-UG-4412", "Amoxicillin");
            System.out.printf("Test 1 Result: Gross=%.1f, Disc=%.1f, Net=%.1f%n", rx.calculateGrossAmount(), rx.calculateDiscountAmount(), rx.calculateCharge());
        } catch (Exception e) {
            System.out.println("Test 1 Error: " + e.getMessage());
        }

        // Test 2: PrescriptionSale Non-Senior (Age 34 < 60)
        try {
            PrescriptionSale rx = new PrescriptionSale("AAF795-002", "Akello Grace", 34, 2, 18000.0, "MED-UG-8821", "Ciprofloxacin");
            System.out.printf("Test 2 Result: Gross=%.1f, Disc=%.1f, Net=%.1f%n", rx.calculateGrossAmount(), rx.calculateDiscountAmount(), rx.calculateCharge());
        } catch (Exception e) {
            System.out.println("Test 2 Error: " + e.getMessage());
        }

        // Test 3: CounterSale Senior Discount (Age 72 >= 60)
        try {
            CounterSale cs = new CounterSale("AAF795-004", "Babirye Sarah", 72, 2, 15000.0, "Vitamin C", "Supplements");
            System.out.printf("Test 3 Result: Gross=%.1f, Disc=%.1f, Net=%.1f%n", cs.calculateGrossAmount(), cs.calculateDiscountAmount(), cs.calculateCharge());
        } catch (Exception e) {
            System.out.println("Test 3 Error: " + e.getMessage());
        }

        // Test 4: CounterSale Non-Senior (Age 28 < 60)
        try {
            CounterSale cs = new CounterSale("AAF795-003", "Tumusiime Brian", 28, 4, 3000.0, "Panadol Extra", "Pain Relief");
            System.out.printf("Test 4 Result: Gross=%.1f, Disc=%.1f, Net=%.1f%n", cs.calculateGrossAmount(), cs.calculateDiscountAmount(), cs.calculateCharge());
        } catch (Exception e) {
            System.out.println("Test 4 Error: " + e.getMessage());
        }

        // Test 5: Exact Age 60 Boundary
        try {
            PrescriptionSale rx = new PrescriptionSale("AAF795-005", "Kato Joseph", 60, 1, 50000.0, "MED-UG-1092", "Insulin");
            System.out.printf("Test 5 Result: Gross=%.1f, Disc=%.1f, Net=%.1f%n", rx.calculateGrossAmount(), rx.calculateDiscountAmount(), rx.calculateCharge());
        } catch (Exception e) {
            System.out.println("Test 5 Error: " + e.getMessage());
        }

        // Test 6: Invalid Blank Customer Name
        try {
            PrescriptionSale rx = new PrescriptionSale("AAF795-006", "   ", 45, 1, 10000.0, "DOC123", "Paracetamol");
            System.out.println("Test 6 Failed: Exception not thrown");
        } catch (InvalidSaleException e) {
            System.out.println("Test 6 Result: Caught expected exception -> " + e.getMessage());
        }

        // Test 7: Invalid Age (-5)
        try {
            CounterSale cs = new CounterSale("AAF795-007", "Namubiru Mary", -5, 2, 5000.0, "Bandage", "First Aid");
            System.out.println("Test 7 Failed: Exception not thrown");
        } catch (InvalidSaleException e) {
            System.out.println("Test 7 Result: Caught expected exception -> " + e.getMessage());
        }

        // Test 8: Invalid Negative Unit Price (-2500)
        try {
            PrescriptionSale rx = new PrescriptionSale("AAF795-008", "Okello Daniel", 50, 2, -2500.0, "DOC456", "Metformin");
            System.out.println("Test 8 Failed: Exception not thrown");
        } catch (InvalidSaleException e) {
            System.out.println("Test 8 Result: Caught expected exception -> " + e.getMessage());
        }

        // Test 9: Invalid Non-Positive Quantity (0)
        try {
            CounterSale cs = new CounterSale("AAF795-009", "Atuhaire Fiona", 30, 0, 12000.0, "Ibuprofen", "Pain Relief");
            System.out.println("Test 9 Failed: Exception not thrown");
        } catch (InvalidSaleException e) {
            System.out.println("Test 9 Result: Caught expected exception -> " + e.getMessage());
        }
    }
}
