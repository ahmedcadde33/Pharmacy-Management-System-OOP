import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Victoria University Kampala
 * Faculty of Science and Technology
 * Module: 1301 ST - Object Oriented Programming
 * Student Name: Ahmed Abdijabar Farah
 * Registration Number: VU-BCS-2603-1795-EVE
 * 
 * Manager class responsible for maintaining the collection of Sale records
 * using an ArrayList, calculating aggregates, and generating sorted polymorphic reports.
 */
public class PharmacyManager {

    // Internal in-memory collection of sales
    private final List<Sale> sales;

    // Sequential counter for auto-generating customized sale IDs (AAF795-xxx)
    private int idCounter;
    public static final String ID_PREFIX = "AAF795-";

    public PharmacyManager() {
        this.sales = new ArrayList<>();
        this.idCounter = 1;
    }

    /**
     * Generates a unique, standardized record ID matching the student's credentials:
     * Initials (AAF) + Registration Suffix (795) + formatted counter.
     */
    public String generateNextId() {
        return String.format("%s%03d", ID_PREFIX, idCounter++);
    }

    /**
     * Adds a sale record to the repository.
     */
    public void addSale(Sale sale) throws InvalidSaleException {
        if (sale == null) {
            throw new InvalidSaleException("Cannot add a null sale record.");
        }
        // Verify unique ID constraint
        if (findSaleById(sale.getSaleId()) != null) {
            throw new InvalidSaleException("A sale with ID " + sale.getSaleId() + " already exists.");
        }
        sales.add(sale);
    }

    /**
     * Searches for a record by its unique ID (case-insensitive).
     */
    public Sale findSaleById(String saleId) {
        if (saleId == null || saleId.trim().isEmpty()) {
            return null;
        }
        for (Sale sale : sales) {
            if (sale.getSaleId().equalsIgnoreCase(saleId.trim())) {
                return sale;
            }
        }
        return null;
    }

    /**
     * Removes a sale record from the collection by ID.
     */
    public boolean removeSale(String saleId) {
        Sale found = findSaleById(saleId);
        if (found != null) {
            return sales.remove(found);
        }
        return false;
    }

    /**
     * Returns the total count of records currently stored.
     */
    public int getRecordCount() {
        return sales.size();
    }

    /**
     * Calculates the sum of all final charges across all records.
     * Demonstrates polymorphism by treating each element as Chargeable.
     */
    public double calculateTotalCharges() {
        double total = 0.0;
        for (Chargeable item : sales) {
            total += item.calculateCharge();
        }
        return total;
    }

    /**
     * Calculates total discounts awarded across all records.
     */
    public double calculateTotalDiscounts() {
        double totalDiscount = 0.0;
        for (Sale sale : sales) {
            totalDiscount += sale.calculateDiscountAmount();
        }
        return totalDiscount;
    }

    /**
     * Calculates the gross charges before discount.
     */
    public double calculateTotalGross() {
        double totalGross = 0.0;
        for (Sale sale : sales) {
            totalGross += sale.calculateGrossAmount();
        }
        return totalGross;
    }

    /**
     * Returns a copy of the sales list sorted according to Digit A = 7:
     * "Type of record first, then customer name alphabetically, using thenComparing"
     */
    public List<Sale> getSortedSales() {
        List<Sale> sortedList = new ArrayList<>(sales);

        // Comparator chain satisfying Digit A = 7 requirement
        Comparator<Sale> saleComparator = Comparator
                .comparing(Sale::getRecordType, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Sale::getCustomerName, String.CASE_INSENSITIVE_ORDER);

        Collections.sort(sortedList, saleComparator);
        return sortedList;
    }

    /**
     * Returns an unmodifiable view or direct copy of all sales.
     */
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }
}
