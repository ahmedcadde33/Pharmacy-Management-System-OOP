/**
 * Victoria University Kampala
 * Faculty of Science and Technology
 * Module: 1301 ST - Object Oriented Programming
 * Student Name: Ahmed Abdijabar Farah
 * Registration Number: VU-BCS-2603-1795-EVE
 * 
 * Chargeable interface declaring the charging contract for all billable entities.
 */
public interface Chargeable {
    /**
     * Calculates the total net charge after applying applicable business logic and discounts.
     * 
     * @return payable amount in Uganda Shillings (UGX)
     */
    double calculateCharge();
}
