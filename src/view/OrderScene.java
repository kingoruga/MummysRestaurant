/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import com.syntel.DatabaseAction;
import com.syntel.Models.FoodItem;
import com.syntel.Models.Order;
import com.syntel.SessionState;
import java.util.ArrayList;
import java.util.List;

public class OrderScene extends Scene {

    private enum State {
        Address,
        Date,
        Time,
        Payment,
        Receipt
    }

    private State state;

    // Both of these fields would be more complex classes later on
    private String address;
    private String paymentInfo;
    private String deliveryDate;
    private int deliveryTime;
    private Order order;
    private boolean purchasing;

    public OrderScene() {
        deliveryTime = -1;
        state = State.Address;
        order = SessionState.ongoingOrder;
    }

    @Override
    public Scene transitionNext() {
        switch (selectedChoice) {
            case "Back":
                return new FoodScene();

            case "Finish":
                order.setDeliveryAddress(address);
                order.setDeliveryDate(deliveryDate);
                order.setDeliveryTime(deliveryTime);
                //DatabaseAction.addOrder(SessionState.customer, order);
                return new HomeScene();
        }

        return this;
    }

    @Override
    public void process() {

        List<String> choices;

        switch (state) {
            case Address:
                choices = new ArrayList<>();
                if (address == null) {
                    choices.add("Use default address");
                    choices.add("Use new address");
                } else {
                    choices.add("Unset address");
                    choices.add("Next");
                }
                choices.add("Back");

                do {
                    // Display all choices
                    for (int i = 0; i < choices.size(); i++) {
                        System.out.println("(" + i + ")" + " " + choices.get(i));
                    }

                    // Match user input with choice
                    selectedChoice = matchInputWithChoice(scanner.nextLine(), choices);

                } while (selectedChoice == null);

                switch (selectedChoice) {

                    case "Back":
                        purchasing = false;
                        requestTransition = true;
                        break;

                    case "Use default address":
                        address = SessionState.customerEmail;// .getAddress();
                        // fix this when we have more concrete classes
                        if (address == null) {
                            address = "";
                        }
                        break;

                    case "Use new address":
                        System.out.println("Type new address: ");
                        address = scanner.nextLine();
                        if (address.length() == 0) {
                            address = null;
                        }
                        break;

                    case "Unset address":
                        address = null;
                        break;

                    case "Next":
                        state = State.Date;
                        selectedChoice = null;
                        break;
                }
                break;

            case Date:
                choices = new ArrayList<>();

                if (deliveryDate == null) {
                    choices.add("Set date of delivery");
                } else {
                    choices.add("Unset date");
                    choices.add("Next");
                }
                choices.add("Back");

                do {
                    // Display all choices
                    for (int i = 0; i < choices.size(); i++) {
                        System.out.println("(" + i + ")" + " " + choices.get(i));
                    }

                    // Match user input with choice
                    selectedChoice = matchInputWithChoice(scanner.nextLine(), choices);

                } while (selectedChoice == null);

                switch (selectedChoice) {

                    case "Back":
                        purchasing = false;
                        requestTransition = true;
                        break;

                    case "Unset date":
                        deliveryDate = null;
                        break;

                    case "Set date of delivery":
                        // TODO: this has to be a date, parsed to a date
                        // this also cannot be 30 days beyond the current date,
                        // maybe just set this as "Delivery date (days from now)"
                        // and create this Date when about to process the database action?
                        
                        System.out.println("Type delivery date: ");
                        deliveryDate = scanner.nextLine();
                        if (deliveryDate.length() == 0) {
                            deliveryDate = null;
                        }
                        break;

                    case "Next":
                        state = State.Time;
                        selectedChoice = null;
                        break;
                }
                break;

            case Time:
                choices = new ArrayList<>();

                if (deliveryTime == -1) {
                    choices.add("Set time of delivery");
                } else {
                    choices.add("Unset time");
                    choices.add("Begin Payment");
                }
                choices.add("Back");

                do {
                    // Display all choices
                    for (int i = 0; i < choices.size(); i++) {
                        System.out.println("(" + i + ")" + " " + choices.get(i));
                    }

                    // Match user input with choice
                    selectedChoice = matchInputWithChoice(scanner.nextLine(), choices);

                } while (selectedChoice == null);

                switch (selectedChoice) {

                    case "Back":
                        purchasing = false;
                        requestTransition = true;
                        break;

                    case "Unset time":
                        deliveryDate = null;
                        break;

                    case "Set time of delivery":
                        System.out.println("(Note: post the 24 hour time e.g. 22 = 10pm)");
                        System.out.println("Type delivery time: ");
                        try {
                            deliveryTime = scanner.nextInt();
                            scanner.nextLine(); // flush buffer
                            if (deliveryTime < 0 || deliveryTime > 23)
                                deliveryTime = -1;
                        } catch (Exception e) {
                            deliveryTime = -1;
                        }
                        break;

                    case "Begin Payment":
                        state = State.Payment;
                        selectedChoice = null;
                        break;
                }
                break;

            case Payment:

                choices = new ArrayList<>();
                if (paymentInfo == null) {
                    choices.add("Use default payment");
                    choices.add("Use new payment information");
                } else {
                    choices.add("Unset payment");
                    choices.add("Finish");
                }
                choices.add("Back");

                do {
                    // Display all choices
                    for (int i = 0; i < choices.size(); i++) {
                        System.out.println("(" + i + ")" + " " + choices.get(i));
                    }

                    // Match user input with choice
                    selectedChoice = matchInputWithChoice(scanner.nextLine(), choices);

                } while (selectedChoice == null);

                switch (selectedChoice) {

                    case "Back":
                        purchasing = false;
                        requestTransition = true;
                        break;

                    case "Use default payment":
                        paymentInfo = SessionState.customerEmail;// .getPaymentInfo();
                        // fix this when we have more concrete classes
                        if (paymentInfo == null) {
                            paymentInfo = "";
                        }
                        selectedChoice = null;
                        break;

                    case "Use new payment information":
                        System.out.println("Type new payment information: ");
                        paymentInfo = scanner.nextLine();
                        if (paymentInfo.length() == 0) {
                            paymentInfo = null;
                        }
                        break;

                    case "Unset payment":
                        paymentInfo = null;
                        break;

                    case "Finish":
                        state = State.Receipt;
                        break;
                }
                break;

            case Receipt:
                System.out.println("Payment finished.");
                requestTransition = true;
        }
    }
}
