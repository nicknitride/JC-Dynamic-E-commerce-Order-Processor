package org.nickproj;
import java.util.Scanner;
public class InteractiveOrderProcessor {

// Welcome to the Interactive Order Processor!
//
//--- Enter Order Details ---
//Enter unit price: 30.00
//Enter quantity: 8
//Is customer a member (true/false)?: true
//Enter customer tier (Regular, Silver, Gold): Gold
//Enter shipping zone (ZoneA, ZoneB, ZoneC, Unknown): ZoneB
//Enter discount code (SAVE10, FREESHIP, or "" for none): SAVE10
    static void stringComparison(Scanner sc){
        String s1, s2;
        System.out.print("Enter the first string for the comparison: ");
        s1= sc.nextLine();
        System.out.print("Enter the second string for the comparison: ");
        s2 = sc.nextLine();

        System.out.println("== Comparison (compares memory addresses, works for items interned into the string pool");
        System.out.println(s1 == s2);

        System.out.println(".equals() comparison - compares the actual string content, works even with new String('')");
        System.out.println("Most-preferred due to content comparison");
        System.out.println(s1.equals(s2));

        System.out.println("case insensitive equals: ");
        System.out.println(s1.equalsIgnoreCase(s2));
    }

    public static void main(String[] args) {
        double unitPrice, quantity, subTotal, shippingCost = 0,finalOrderTotal;
        double initialTotal,customerTierDiscount = 0, shippingDiscount,promoCodeDiscount = 0, quantityDiscount, smallOrderSurcharge;
        boolean isMember, shippingIsFree = false;
        String shippingZone = "", customerTier, discountCode;

        System.out.println("Welcome to the Interactive Order Processor!");
        System.out.println("--- Enter Order Details ---");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter unit price of the SKU: ");
        unitPrice = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter quantity: ");
        quantity = sc.nextDouble();
        System.out.print("Is the customer a member (true/false)?: ");
        isMember = sc.nextBoolean();
        sc.nextLine();
        System.out.print("Enter customer tier (Regular, Silver, Gold): ");
        customerTier = sc.nextLine().toLowerCase();
        System.out.print("Enter shipping zone (ZoneA, ZoneB, ZoneC, unknown): ");
        shippingZone = sc.nextLine().toLowerCase();
        System.out.print("Enter your discount code (SAVE10, FREESHIP, or '' for none): ");
        discountCode = sc.nextLine();

        subTotal = unitPrice * quantity;
        initialTotal = subTotal;

        if(customerTier.equals("silver")){
            subTotal = subTotal * .9;
            customerTierDiscount = subTotal;
            isMember = true;
        } else if (customerTier.equals("gold")) {
            subTotal = subTotal*.85;
            customerTierDiscount = subTotal;
            isMember = true;
        }

        if (quantity >= 5){
            subTotal = subTotal * .95;
            quantityDiscount = subTotal;
        }

        if(discountCode.equals("SAVE10") && subTotal > 75){
            subTotal -=10;
            promoCodeDiscount = subTotal;
        } else if (discountCode.equalsIgnoreCase("FREESHIP")) {
            shippingIsFree = true;
            promoCodeDiscount = subTotal;
        }

       subTotal = (subTotal < 25) ? subTotal+=3 : subTotal;
       smallOrderSurcharge = subTotal;
        if(!shippingIsFree){
            switch (shippingZone){
                case "zonea":{
                    shippingCost = 5.0;
                    break;
                }
                case "zoneb":{
                    shippingCost = 12.5;
                    break;
                }
                case "zonec":{
                    shippingCost = 20.00;
                    break;
                }
                default:
                    shippingCost = 25.00;
                    break;
            }
        }


        System.out.println("--- Order Details ---");
        System.out.printf("Unit Price: %.2f\n",unitPrice);
        System.out.printf("Quantity: %f\n",quantity);
        System.out.printf("Is Member: %b\n",isMember);
        System.out.printf("Customer Tier: %s\n",customerTier);
        System.out.printf("Shipping Zone: %s\n",shippingZone);
        System.out.printf("Discount Code: %s\n",discountCode);

        System.out.println("--- Calculation Steps ---");
        System.out.printf("Initial Subtotal: $%.2f\n",initialTotal);
        System.out.printf("After Tier Discount (%s): $%.2f\n",customerTier,customerTierDiscount);
        System.out.printf("Promo Code Discount (%s): $%.2f\n",discountCode,promoCodeDiscount);
        System.out.printf("After Small Order Surcharge: $%.2f \n",smallOrderSurcharge);

        System.out.printf("Shipping Cost: $%.2f\n",shippingCost);
        finalOrderTotal = subTotal + shippingCost;
        System.out.printf("Final Order Total: $%.2f\n",finalOrderTotal);

        stringComparison(sc);
    }
}