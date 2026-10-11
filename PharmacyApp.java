import java.util.List;
import java.util.Scanner;

/**
 * Victoria University Kampala
 * Faculty of Science and Technology
 * Module: 1301 ST - Object Oriented Programming
 * Student Name: Ahmed Abdijabar Farah
 * Registration Number: VU-BCS-2603-1795-EVE
 * 
 * Main executable class providing a menu-driven CLI interface for the
 * Victoria Pharmacy Charging System.
 */
public class PharmacyApp {

    // Student identification constants
    public static final String STUDENT_NAME = "Ahmed Abdijabar Farah";
    public static final String REG_NUMBER = "VU-BCS-2603-1795-EVE";

    private final PharmacyManager manager;
    private final InputHelper input;

    public PharmacyApp() {
        this.manager = new PharmacyManager();
        this.input = new InputHelper(new Scanner(System.in));
    }

    public static void main(String[] args) {
        PharmacyApp app = new PharmacyApp();
        app.displayWelcomeBanner();
        app.loadSampleData(); // Pre-load realistic pharmacy data
        app.runMenu();
    }

    /**
     * Displays personal details and course parameters required by personal touches spec.
     */
    private void displayWelcomeBanner() {
        System.out.println("================================================================================");
        System.out.println("                 VICTORIA UNIVERSITY KAMPALA - OOP COURSEWORK                   ");
        System.out.println("                   Faculty of Science and Technology (1301 ST)                  ");
        System.out.println("================================================================================");
        System.out.println("  Student Name : " + STUDENT_NAME);
        System.out.println("  Reg. Number  : " + REG_NUMBER);
        System.out.println("  Digits       : A = 7 (Sort: Type first, then Name alphabetically)");
        System.out.println("                 B = 9 (Senior Discount Rate P = 9 + 5 = 14%)");
        System.out.println("                 C = 5 (Business Scenario: Pharmacy Charging System)");
        System.out.println("  Record Prefix: AAF795-XXX");
        System.out.println("================================================================================");
        System.out.println();
    }

    /**
     * Pre-populates the system with realistic Ugandan pharmacy data
     * to immediately demonstrate sorting, both subclasses, and discount rules.
     */
    private void loadSampleData() {
        try {
            // Sample 1: Prescription, Senior customer (Age 65 >= 60 -> 14% discount)
            // Gross = 3 * 25,000 = 75,000. Discount 14% = 10,500. Net = 64,500 UGX
            manager.addSale(new PrescriptionSale(
                    manager.generateNextId(), "Mugisha Patrick", 65, 3, 25000.0,
                    "MED-UG-4412", "Amoxicillin 500mg"
            ));

            // Sample 2: Prescription, Non-senior customer (Age 34 < 60 -> No discount)
            // Gross = 2 * 18,000 = 36,000. Discount = 0. Net = 36,000 UGX
            manager.addSale(new PrescriptionSale(
                    manager.generateNextId(), "Akello Grace", 34, 2, 18000.0,
                    "MED-UG-8821", "Ciprofloxacin 500mg"
            ));

            // Sample 3: Counter sale, Non-senior customer (Age 28 < 60 -> No discount)
            // Base = 4 * 3,000 = 12,000. Packaging = 1,000. Gross = 13,000. Net = 13,000 UGX
            manager.addSale(new CounterSale(
                    manager.generateNextId(), "Tumusiime Brian", 28, 4, 3000.0,
                    "Panadol Extra", "Pain Relief"
            ));

            // Sample 4: Counter sale, Senior customer (Age 72 >= 60 -> 14% discount)
            // Base = 2 * 15,000 = 30,000. Packaging = 1,000. Gross = 31,000.
            // Discount 14% = 4,340. Net = 26,660 UGX
            manager.addSale(new CounterSale(
                    manager.generateNextId(), "Babirye Sarah", 72, 2, 15000.0,
                    "Vitamin C Zinc Effervescent", "Supplements"
            ));

            // Sample 5: Prescription, Senior customer (Age 60 boundary test -> 14% discount)
            // Gross = 1 * 50,000 = 50,000. Discount 14% = 7,000. Net = 43,000 UGX
            manager.addSale(new PrescriptionSale(
                    manager.generateNextId(), "Kato Joseph", 60, 1, 50000.0,
                    "MED-UG-1092", "Insulin Glargine"
            ));

        } catch (InvalidSaleException e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }

    /**
     * Main menu control loop.
     */
    private void runMenu() {
        boolean running = true;
        while (running) {
            displayMenuOptions();
            int choice = input.readInt("Enter your choice (1-6): ", 1, 6);
            System.out.println();

            switch (choice) {
                case 1:
                    handleAddSale();
                    break;
                case 2:
                    handleListSortedReport();
                    break;
                case 3:
                    handleFindSale();
                    break;
                case 4:
                    handleRemoveSale();
                    break;
                case 5:
                    handleShowSummary();
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using the Victoria Pharmacy Charging System. Goodbye!");
                    break;
            }
            System.out.println();
        }
    }

    private void displayMenuOptions() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("                   VICTORIA PHARMACY - MAIN MENU");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("  1. Add a New Sale Record (Prescription or Counter)");
        System.out.println("  2. Display Sorted Polymorphic Report (Digit A = 7)");
        System.out.println("  3. Find a Sale Record by ID");
        System.out.println("  4. Remove a Sale Record (with confirmation)");
        System.out.println("  5. View Summary Financial Statistics");
        System.out.println("  6. Exit");
        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Handles adding either a PrescriptionSale or a CounterSale.
     */
    private void handleAddSale() {
        System.out.println("=== Add New Pharmacy Sale Record ===");
        System.out.println("Select Sale Type:");
        System.out.println("  1. Prescription Sale (Quantity x Price, Senior Discount 14%)");
        System.out.println("  2. Counter Sale      (Quantity x Price + 1,000 UGX Pkg, Senior Discount 14%)");
        
        int typeChoice = input.readInt("Choice (1-2): ", 1, 2);

        String autoId = manager.generateNextId();
        System.out.println("Auto-generated Record ID: " + autoId);

        String name = input.readNonEmptyString("Enter customer name: ");
        int age = input.readInt("Enter customer age (0 - 125): ", 0, 125);
        int quantity = input.readPositiveInt("Enter quantity purchased: ");
        double unitPrice = input.readDouble("Enter unit price (UGX): ", 100.0, 50_000_000.0);

        try {
            Sale newSale;
            if (typeChoice == 1) {
                String medName = input.readNonEmptyString("Enter prescribed medication name: ");
                String docReg = input.readNonEmptyString("Enter prescribing doctor reg. number: ");
                newSale = new PrescriptionSale(autoId, name, age, quantity, unitPrice, docReg, medName);
            } else {
                String itemName = input.readNonEmptyString("Enter item name: ");
                String category = input.readNonEmptyString("Enter item category (e.g., First Aid, Vitamins): ");
                newSale = new CounterSale(autoId, name, age, quantity, unitPrice, itemName, category);
            }

            manager.addSale(newSale);
            System.out.println("\n[Success] Record successfully added!");
            System.out.printf("  Record Type     : %s%n", newSale.getRecordType());
            System.out.printf("  Gross Charge    : %,.2f UGX%n", newSale.calculateGrossAmount());
            System.out.printf("  Senior Discount : %,.2f UGX (%s)%n", 
                    newSale.calculateDiscountAmount(), 
                    newSale.isDiscountEligible() ? "Applied (14%)" : "Not Applicable");
            System.out.printf("  Final Charge    : %,.2f UGX%n", newSale.calculateCharge());

        } catch (InvalidSaleException e) {
            System.out.println("  [Validation Error] " + e.getMessage());
        }
    }

    /**
     * Polymorphic report displaying records sorted as requested.
     */
    private void handleListSortedReport() {
        System.out.println("============================================================================================================");
        System.out.println("                                  VICTORIA PHARMACY - SALES CHARGES REPORT                                  ");
        System.out.println("                   Sorted by: [Record Type ASC] then [Customer Name ASC] (Rule A = 7)                      ");
        System.out.println("============================================================================================================");

        List<Sale> sortedList = manager.getSortedSales();
        if (sortedList.isEmpty()) {
            System.out.println("No records found in the system.");
            return;
        }

        System.out.printf("%-11s | %-17s | %-18s | %-4s | %-4s | %-11s | %-11s | %-12s%n",
                "ID", "Type", "Customer Name", "Age", "Qty", "Gross(UGX)", "Disc(UGX)", "Net Pay(UGX)");
        System.out.println("------------------------------------------------------------------------------------------------------------");

        for (Sale sale : sortedList) {
            // Polymorphic invocation of calculateCharge(), calculateGrossAmount(), calculateDiscountAmount()
            System.out.printf("%-11s | %-17s | %-18s | %-4d | %-4d | %,11.1f | %,11.1f | %,12.1f%n",
                    sale.getSaleId(),
                    sale.getRecordType(),
                    sale.getCustomerName(),
                    sale.getCustomerAge(),
                    sale.getQuantity(),
                    sale.calculateGrossAmount(),
                    sale.calculateDiscountAmount(),
                    sale.calculateCharge());
        }

        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.printf("Total Records: %d | Overall Revenue: %,.2f UGX | Total Discounts Awarded: %,.2f UGX%n",
                manager.getRecordCount(), manager.calculateTotalCharges(), manager.calculateTotalDiscounts());
        System.out.println("============================================================================================================");
    }

    /**
     * Searches and displays an individual record by ID.
     */
    private void handleFindSale() {
        System.out.println("=== Search Sale Record ===");
        String searchId = input.readNonEmptyString("Enter record ID to find (e.g., AAF795-001): ");
        Sale sale = manager.findSaleById(searchId);

        if (sale == null) {
            System.out.println("  [Not Found] No sale record found with ID: " + searchId);
        } else {
            System.out.println("\n--- Record Details ---");
            System.out.println("  Record ID       : " + sale.getSaleId());
            System.out.println("  Record Type     : " + sale.getRecordType());
            System.out.println("  Customer Name   : " + sale.getCustomerName());
            System.out.println("  Customer Age    : " + sale.getCustomerAge());
            System.out.println("  Quantity        : " + sale.getQuantity());
            System.out.printf("  Unit Price      : %,.2f UGX%n", sale.getUnitPrice());
            System.out.printf("  Gross Amount    : %,.2f UGX%n", sale.calculateGrossAmount());
            System.out.printf("  Senior Discount : %,.2f UGX (%s)%n", 
                    sale.calculateDiscountAmount(),
                    sale.isDiscountEligible() ? "Eligible (14%)" : "Not Eligible");
            System.out.printf("  Net Payable     : %,.2f UGX%n", sale.calculateCharge());

            if (sale instanceof PrescriptionSale) {
                PrescriptionSale rx = (PrescriptionSale) sale;
                System.out.println("  Medication      : " + rx.getMedicationName());
                System.out.println("  Doctor Reg. No. : " + rx.getDoctorRegNumber());
            } else if (sale instanceof CounterSale) {
                CounterSale cs = (CounterSale) sale;
                System.out.println("  Item Name       : " + cs.getItemName());
                System.out.println("  Category        : " + cs.getCategory());
                System.out.printf("  Packaging Surcharge: %,.0f UGX%n", CounterSale.PACKAGING_FEE);
            }
        }
    }

    /**
     * Removes a record with user confirmation.
     */
    private void handleRemoveSale() {
        System.out.println("=== Remove Sale Record ===");
        String deleteId = input.readNonEmptyString("Enter record ID to remove: ");
        Sale sale = manager.findSaleById(deleteId);

        if (sale == null) {
            System.out.println("  [Not Found] Cannot delete. Record does not exist with ID: " + deleteId);
            return;
        }

        System.out.printf("Found record: %s for %s (%s). Amount: %,.2f UGX%n",
                sale.getSaleId(), sale.getCustomerName(), sale.getRecordType(), sale.calculateCharge());

        boolean confirm = input.readConfirmation("Are you sure you want to permanently delete this record?");
        if (confirm) {
            boolean removed = manager.removeSale(deleteId);
            if (removed) {
                System.out.println("  [Success] Record " + deleteId + " has been successfully removed.");
            } else {
                System.out.println("  [Error] Failed to remove record.");
            }
        } else {
            System.out.println("  [Cancelled] Deletion cancelled by user.");
        }
    }

    /**
     * Summarizes aggregate totals.
     */
    private void handleShowSummary() {
        System.out.println("================================================================================");
        System.out.println("                       VICTORIA PHARMACY - FINANCIAL SUMMARY                    ");
        System.out.println("================================================================================");
        int totalCount = manager.getRecordCount();
        double grossRevenue = manager.calculateTotalGross();
        double totalDiscounts = manager.calculateTotalDiscounts();
        double netRevenue = manager.calculateTotalCharges();

        System.out.printf("  Total Sales Transactions  : %d%n", totalCount);
        System.out.printf("  Gross Sales Total         : %,.2f UGX%n", grossRevenue);
        System.out.printf("  Total Discounts Granted   : %,.2f UGX (14%% Senior Relief)%n", totalDiscounts);
        System.out.printf("  Net Payable Revenue       : %,.2f UGX%n", netRevenue);
        if (totalCount > 0) {
            System.out.printf("  Average Transaction Value : %,.2f UGX%n", (netRevenue / totalCount));
        }
        System.out.println("================================================================================");
    }
}
