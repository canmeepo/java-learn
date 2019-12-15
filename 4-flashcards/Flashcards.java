
//  package flashcards;
import java.util.*;
import java.io.*;

public class Flashcards {

    static ArrayList<String> log = new ArrayList<String>();
    static Map<String, Integer> hardest = new LinkedHashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        var run = true;
        ArrayList<Card> cards = new ArrayList<Card>();

        while (run) {
            var input = print(
                    "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):", sc);
            switch (input) {
            case "add":
                addCard(sc, cards);
                break;
            case "remove":
                removeCard(sc, cards);
                break;
            case "import":
                importCards(sc, cards);
                break;
            case "export":
                exportCards(sc, cards);
                break;
            case "ask":
                ask(sc, cards);
                break;
            case "log":
                exportLog(sc);
                break;
            case "hardest card":
                hardestCard(sc);
                break;
            case "reset stats":
                hardest.clear();
                simplePrint("Card statistics has been reset.", sc);
                break;
            case "exit":
                simplePrint("Bye bye!", sc);
                run = false;
                break;
            }

        }
    }

    public static void addCard(Scanner sc, ArrayList<Card> cards) {
        var input1 = print("The card:", sc);

        var check1 = true;
        while (check1) {
            if (containsC(cards, input1) == null) {
                check1 = false;
            } else {
                simplePrint("The card \"" + containsC(cards, input1) + "\" already exists.", sc);
                return;
            }
        }

        var input2 = print("The definition of the card", sc);

        var check2 = true;
        while (check2) {
            if (containsD(cards, input2) == null) {
                check2 = false;
            } else {
                simplePrint("The definition \"" + containsD(cards, input2) + "\" already exists.", sc);
                return;
            }
        }

        simplePrint("The pair (\"" + input1 + "\":\"" + input2 + "\") has been added.", sc);

        cards.add(new Card(input1, input2));
        hardest.put(input1, 0);
    }

    public static void removeCard(Scanner sc, ArrayList<Card> cards) {
        var input = print("The card:", sc);
        for (int i = 0; i < cards.size(); i++) {
            if (input.equals(cards.get(i).getCard())) {
                cards.remove(i);

                if (hardest.containsKey(input)) {
                    hardest.remove(input);
                }
                simplePrint("The card has been removed.", sc);
                return;
            }
        }

        simplePrint("Can't remove \"" + input + "\": there is no such card.", sc);
    }

    public static void ask(Scanner sc, ArrayList<Card> cards) {
        simplePrint("How many times to ask?", sc);
        var n = sc.nextInt();
        log.add("" + n + "");

        int lower = 0;
        int upper = cards.size();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            int intervalLength = upper - lower;
            var randomInt = random.nextInt(intervalLength);

            var answer = print("Print the definition of \"" + cards.get(randomInt).card + "\":", sc);

            if (answer.equals(cards.get(randomInt).des)) {
                simplePrint("Correct answer.", sc);
            } else {
                if (containsCard(cards, answer) == null) {
                    simplePrint("Wrong answer. The correct one is \"" + cards.get(randomInt).des + "\".", sc);
                } else {
                    simplePrint("Wrong answer. The correct one is \"" + cards.get(randomInt).des
                            + "\", you've just written the definition of \"" + containsCard(cards, answer) + "\"", sc);
                }

                if (hardest.containsKey(cards.get(randomInt).card)) {
                    hardest.put(cards.get(randomInt).card, hardest.get(cards.get(randomInt).card) + 1);
                } else {
                    hardest.put(cards.get(randomInt).card, 1);
                }
            }
        }

    }

    public static void importCards(Scanner sc, ArrayList<Card> cards) {
        var input = print("File name:", sc);
        var counter = 0;

        try {
            Scanner fileScanner = new Scanner(new File(input));
            // hardest.clear();

            while (fileScanner.hasNext()) {
                var string = fileScanner.nextLine().split(":");

                var card = string[0].trim();
                var des = string[1].trim();
                var err = string[2].trim();

                if (hardest.containsKey(card)) {
                    hardest.put(card, hardest.get(card) + Integer.parseInt(err));
                } else {
                    hardest.put(card, Integer.parseInt(err));
                }

                if (contsD(cards, card) != -1) {
                    cards.set(contsD(cards, card), new Card(card, des));
                } else {
                    cards.add(new Card(card, des));
                }
                counter++;

            }
            simplePrint(counter + " cards have been loaded.", sc);

        } catch (Exception e) {
            simplePrint("File not found.", sc);
        }
    }

    public static int contsD(ArrayList<Card> cards, String card) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCard().equals(card)) {
                return i;
            }
        }

        return -1;
    }

    public static void saveToLog(String input) {
        log.add(input);
    }

    public static void exportLog(Scanner sc) {
        var input = print("File name:", sc);
        try {
            PrintWriter writer = new PrintWriter(input);
            for (String line : log) {
                writer.println(line);
            }
            simplePrint("The log has been saved.", sc);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            simplePrint("Error", sc);
        }
    }

    public static void exportCards(Scanner sc, ArrayList<Card> cards) {
        var input = print("File name:", sc);
        var counter = 0;

        try {
            PrintWriter writer = new PrintWriter(input);
            for (Card card : cards) {
                if (hardest.containsKey(card.getCard())) {
                    writer.println(card.getCard() + ":" + card.getDes() + ":" + hardest.get(card.getCard()));
                } else {
                    writer.println(card.getCard() + ":" + card.getDes() + ":" + 0);
                }
                counter++;
            }
            writer.flush();
            writer.close();
            simplePrint(counter + " cards have been saved.", sc);
        } catch (Exception e) {
            simplePrint("Error while writing to file.", sc);
        }

    }

    public static String containsC(ArrayList<Card> cards, String input) {
        for (Card o : cards) {
            if (o != null && input.equals(o.getCard())) {
                return o.getCard();
            }
        }
        return null;
    }

    public static String containsD(ArrayList<Card> cards, String input) {
        for (Card o : cards) {
            if (o != null && input.equals(o.getDes())) {
                return o.getDes();
            }
        }
        return null;
    }

    public static void hardestCard(Scanner sc) {
        Map<String, Integer> cardsWithMostMistakes = new HashMap<>();
        int maxErr = -1;
        if (1 > hardest.size()) {
            simplePrint("There are no cards with errors.", sc);
        } else {

            for (Map.Entry<String, Integer> card : hardest.entrySet()) {
                int errCounter = card.getValue();
                if (errCounter != 0 && errCounter > maxErr) {
                    cardsWithMostMistakes.clear();
                    cardsWithMostMistakes.put(card.getKey(), card.getValue());
                    maxErr = errCounter;
                } else if (errCounter == maxErr) {
                    cardsWithMostMistakes.put(card.getKey(), card.getValue());
                }
            }

            String str = "";

            for (Map.Entry<String, Integer> card : cardsWithMostMistakes.entrySet()) {
                if (str.equals("")) {
                    str += "\"" + card.getKey() + "\"";
                } else {
                    str += ", \"" + card.getKey() + "\"";
                }
            }

            if (2 > cardsWithMostMistakes.size()) {
                simplePrint("The hardest card is " + str + ". You have " + maxErr + " errors answering it.", sc);
            } else {
                simplePrint("The hardest cards are " + str + " with " + maxErr + " mistakes.", sc);
            }

        }
    }

    public static String print(String show, Scanner sc) {
        System.out.println(show);
        saveToLog(show);
        var input = sc.next();
        input += sc.nextLine();
        saveToLog(input);
        return input;
    }

    public static void simplePrint(String show, Scanner sc) {
        saveToLog(show);
        System.out.println(show);
    }

    public static String containsCard(ArrayList<Card> cards, String input) {
        for (Card o : cards) {
            if (o != null && input.equals(o.getDes())) {
                return o.getCard();
            }
        }
        return null;
    }
}

class Card {
    public String card;
    public String des;

    public Card(String card, String des) {
        this.card = card;
        this.des = des;
    }

    public String getCard() {
        return card;
    }

    public String getDes() {
        return des;
    }
}
