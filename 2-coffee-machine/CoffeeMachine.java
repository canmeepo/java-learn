import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);

        var water = 400;
        var milk = 540;
        var beans = 120;
        var cups = 9;
        var money = 550;

        var exit = true;

        while (exit) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = sc.nextLine();

            switch (action) {
            case "buy":
                System.out.println(
                        "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                var press = sc.nextLine();

                if (press.equals("1")) {
                    System.out.println("I have enough resources, making you a coffee!");
                    water = water - 250;
                    beans = beans - 16;
                    cups = cups - 1;
                    money = money + 4;
                    break;
                } else if (press.equals("2")) {
                    if (water - 350 > 0) {
                        water = water - 350;
                        milk = milk - 75;
                        beans = beans - 20;
                        cups = cups - 1;
                        money = money + 7;
                        System.out.println("I have enough resources, making you a coffee!");
                        break;
                    } else {
                        System.out.println("Sorry, not enough water!");
                        break;
                    }

                } else if (press.equals("3")) {
                    System.out.println("I have enough resources, making you a coffee!");
                    water = water - 200;
                    milk = milk - 100;
                    beans = beans - 12;
                    cups = cups - 1;
                    money = money + 6;
                    break;
                } else {
                    break;
                }
            case "fill":
                System.out.println("Write how many ml of water do you want to add:");
                water = water + sc.nextInt();
                System.out.println("Write how many ml of milk do you want to add:");
                milk = milk + sc.nextInt();
                System.out.println("Write how many grams of coffee beans do you want to add:");
                beans = beans + sc.nextInt();
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                cups = cups + sc.nextInt();
                break;
            case "take":
                System.out.println("I gave you $" + money);
                money = 0;
                break;
            case "remaining":
                System.out.println("The coffee machine has:");
                System.out.println(water + " of water");
                System.out.println(milk + " of milk");
                System.out.println(beans + " of coffee beans");
                System.out.println(cups + " of disposable cups");
                System.out.println(money + " of money");
                break;
            case "exit":
                exit = false;
                break;

            default:
                System.out.println("default");
            }

        }

    }
}
