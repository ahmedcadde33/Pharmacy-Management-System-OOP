/**
 * Victoria University Kampala
 * Faculty of Science and Technology
 * Module: 1301 ST - Object Oriented Programming
 * Student Name: Ahmed Abdijabar Farah
 * Registration Number: VU-BCS-2603-1795-EVE
 * 
 * Custom domain exception thrown when sale data violates business validation rules.
 */
public class InvalidSaleException extends Exception {

    public InvalidSaleException(String message) {
        super(message);
    }

    public InvalidSaleException(String message, Throwable cause) {
        super(message, cause);
    }
}
