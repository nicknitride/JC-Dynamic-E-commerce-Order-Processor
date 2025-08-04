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


        System.out.println("String 1: "+s1);
        System.out.println("String 2: "+s2);
        System.out.println("== Comparison (compares memory addresses, works for items interned into the string pool");
        System.out.println(s1 == s2);

        System.out.println(".equals() comparison - compares the actual string content, works even with new String('') or un-interned strings");
        System.out.println("Most-preferred due to content comparison");
        System.out.println(s1.equals(s2));

        System.out.println("case insensitive equals: ");
        System.out.println(s1.equalsIgnoreCase(s2));
    }

    public static void main(String[] args) {
        double unitPrice, subTotal, shippingCost = 0,finalOrderTotal;
        int quantity;
        double initialTotal,customerTierDiscount = 0, shippingDiscount,promoCodeDiscount = 0, quantityDiscount, smallOrderSurcharge;
        boolean isMember, shippingIsFree = false;
        String shippingZone = "", customerTier = "", discountCode;

        System.out.println("Welcome to the Interactive Order Processor!");
        System.out.println("--- Enter Order Details ---");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter unit price of the SKU: ");
        try{
            unitPrice = sc.nextDouble();
        }catch (Exception e){
            System.out.println("Entered an invalid number.");
            throw new RuntimeException("Ending Execution, please restart order.\n Exception: "+e);
        }
        sc.nextLine();

        System.out.print("Enter quantity: ");
        try{
            quantity = sc.nextInt();
        }catch (Exception e){
            System.out.println("Entered an invalid number.");
            throw new RuntimeException("Ending Execution, please restart order.\n Exception: "+e);
        }
        sc.nextLine();

        System.out.print("Is the customer a member (true/false)?: ");
        try{
            isMember = sc.nextBoolean();
            sc.nextLine();
        }catch (Exception e){
            System.out.println("Invalid input, assuming non-membership by default. Please restart order if incorrect.");
            isMember = false;
        }


        if(isMember){
            System.out.print("Enter customer tier (Regular, Silver, Gold, '' for none/Regular): ");
            customerTier = sc.nextLine().toLowerCase();
            if (!customerTier.equals("regular") && !customerTier.equals("gold") && !customerTier.equals("silver")) {
                System.out.println("Customer is a member with invalid input, voiding discount, given regular status.");
                customerTier = "regular";
            }
        }else{
            System.out.println("Customer is not a member, given 'Regular' tier discount");
            customerTier = "regular/none";
        }

        System.out.print("Enter shipping zone (ZoneA, ZoneB, ZoneC, unknown): ");
        shippingZone = sc.nextLine().toLowerCase();
        System.out.print("Enter your discount code (SAVE10, FREESHIP, or '' for none): ");
        discountCode = sc.nextLine();
        if (!discountCode.equals("SAVE10") && !discountCode.equalsIgnoreCase("freeship")){
            discountCode = "none";
        }

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
        }else{
            customerTierDiscount = subTotal;
        }

        if (quantity >= 5){
            subTotal = subTotal * .95;
            quantityDiscount = subTotal;
        }else{
            quantityDiscount = subTotal;
        }

        if(discountCode.equals("SAVE10") && subTotal > 75){
            subTotal -=10;
            promoCodeDiscount = subTotal;
        } else if (discountCode.equalsIgnoreCase("FREESHIP")) {
            shippingIsFree = true;
            promoCodeDiscount = subTotal;
        }else{
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
                    shippingZone = "Unknown shipping zone";
                    break;
            }
        }


        System.out.println("--- Order Details ---");
        System.out.printf("Unit Price: %.2f\n",unitPrice);
        System.out.printf("Quantity: %d\n",quantity);
        System.out.printf("Is Member: %b\n",isMember);
        System.out.printf("Customer Tier: %s\n",customerTier);
        System.out.printf("Shipping Zone: %s\n",shippingZone);
        System.out.printf("Discount Code: %s\n",discountCode);

        System.out.println("--- Calculation Steps ---");
        System.out.printf("Initial Subtotal: $%.2f\n",initialTotal);
        System.out.printf("After Tier Discount (%s): $%.2f\n",customerTier,customerTierDiscount);
        System.out.printf("After Quantity Discount (5 percent off if quantity >= 5): $%.2f\n",quantityDiscount);
        System.out.printf("Promo Code Discount (%s): $%.2f\n",discountCode,promoCodeDiscount);
        System.out.printf("After Small Order Surcharge: $%.2f \n",smallOrderSurcharge);

        System.out.printf("Shipping Cost (Zone: %s): $%.2f\n",shippingZone,shippingCost);
        finalOrderTotal = subTotal + shippingCost;
        System.out.printf("Final Order Total: $%.2f\n",finalOrderTotal);

        stringComparison(sc);
    }
}