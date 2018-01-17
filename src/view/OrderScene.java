/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import model.Address;
import model.FoodItem;
import model.Orders;

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
    private Address address;
    private String paymentMethod;
    private String deliveryDate;
    private int deliveryTime;
    private Orders order;
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
                order.setOrderAddress(address);
                order.setDeliveryDate(deliveryDate);
                order.setPaymentMethod(paymentMethod);
                order.setTime(Integer.toString(deliveryTime));

                float payment = (float)SessionState.ongoingOrder.getItems().stream().mapToDouble(FoodItem::getPrice).sum();
                order.setPrice(payment);

                connector.addOrder(order, SessionState.user.getAddress(), SessionState.user);

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
                choices.add("Use default address");
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
                        address = SessionState.user.getAddress();
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
                        System.out.println("Type delivery date (MM/DD/YYYY): ");
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

                float payment = (float)SessionState.ongoingOrder.getItems().stream().mapToDouble(FoodItem::getPrice).sum();

                System.out.println("The total payment will be: " + payment);
                choices = new ArrayList<>();
                if (paymentMethod == null) {
                    choices.add("Use Card");
                    choices.add("Use Cash");
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

                    case "Use Card":
                        paymentMethod = "Card";
                        selectedChoice = null;
                        break;

                    case "Use Cash":
                        paymentMethod = "Cash";
                        selectedChoice = null;
                        break;

                    case "Unset payment":
                        paymentMethod = null;
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
