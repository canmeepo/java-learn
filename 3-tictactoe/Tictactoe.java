import java.util.*;

public class Tictactoe {
    public static void main(String[] args) {
        var input = new Scanner(System.in);
        String[] board = { " ", " ", " ", " ", " ", " ", " ", " ", " " };
        print(board);

        while (true) {
            userMove(input, board, "X");
            if (chechWin(board).length() > 0) {
                System.out.println(chechWin(board));
                break;
            }
            System.out.println(chechWin(board));
            userMove(input, board, "O");
            if (chechWin(board).length() > 0) {
                System.out.println(chechWin(board));
                break;
            }
            System.out.println(chechWin(board));
        }
    }

    public static void userMove(Scanner input, String[] ary, String sign) {
        System.out.println("Enter the coordinates: ");
        var inputCoord = input.nextLine();

        while (true) {
            if (checkInput(inputCoord, ary)) {
                ary[inputToCoord(inputCoord)] = sign;
                print(ary);
                break;
            } else {
                System.out.println("Enter the coordinates: ");
                if (input.hasNextLine()) {
                    inputCoord = input.nextLine();
                }
            }
        }
    }

    public static boolean checkInput(String inp, String[] board) {
        var result = true;
        int c1 = 0;
        int c2 = 0;

        try {
            String[] a = inp.split(" ");
            c1 = Integer.parseInt(a[0]);
            c2 = Integer.parseInt(a[1]);

            int intCoordinates = inputToCoord(inp);

            if ((c1 < 1) || (c1 > 3) || (c2 < 1) || (c2 > 3)) {
                result = false;
                System.out.println("Coordinates should be from 1 to 3!");
            }

            else if (!board[intCoordinates].equals(" ")) {
                result = false;
                System.out.println("This cell is occupied! Choose another one!");
            }
        } catch (NumberFormatException e) {
            result = false;
            System.out.println("You should enter numbers!");
        }
        return result;
    }

    public static int inputToCoord(String str) {
        switch (str) {
        case "1 3":
            return 0;
        case "2 3":
            return 1;
        case "3 3":
            return 2;
        case "1 2":
            return 3;
        case "2 2":
            return 4;
        case "3 2":
            return 5;
        case "1 1":
            return 6;
        case "2 1":
            return 7;
        case "3 1":
            return 8;
        default:
            return -1;
        }
    }

    public static String chechWin(String[] board) {
        var o = 0;
        var x = 0;
        var dash = 0;
        for (String val : board) {
            switch (val) {
            case "O":
                o += 1;
                break;
            case "X":
                x += 1;
                break;
            case " ":
                dash += 1;
                break;
            }
        }
        if (x + 1 < o || o + 1 < x) {
            return "Impossible";
        }
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
            case 0:
                line = board[0] + board[1] + board[2];
                break;
            case 1:
                line = board[3] + board[4] + board[5];
                break;
            case 2:
                line = board[6] + board[7] + board[8];
                break;
            case 3:
                line = board[0] + board[3] + board[6];
                break;
            case 4:
                line = board[1] + board[4] + board[7];
                break;
            case 5:
                line = board[2] + board[5] + board[8];
                break;
            case 6:
                line = board[0] + board[4] + board[8];
                break;
            case 7:
                line = board[2] + board[4] + board[6];
                break;
            }
            if (line.equals("XXX")) {
                if (x >= 3) {
                    return "X wins";
                } else {
                    return "";
                }
            } else if (line.equals("OOO")) {
                if (o >= 3) {
                    return "O wins";
                } else {
                    return "";
                }
            }
        }

        for (int a = 0; a < 9; a++) {
            if (Arrays.asList(board).contains(String.valueOf(a + 1))) {
                break;
            } else if (a == 8) {
                if (dash >= 1) {
                    return "";
                } else {
                    return "Draw";
                }
            }
        }

        return "";
    }

    public static void print(String[] arr) {

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("_")) {
                arr[i] = " ";
            }
        }

        System.out.println(arr);
        System.out.println("---------");
        printLine(arr[0], arr[1], arr[2]);
        printLine(arr[3], arr[4], arr[5]);
        printLine(arr[6], arr[7], arr[8]);
        System.out.println("---------");
    }

    public static void printLine(String arr1, String arr2, String arr3) {
        System.out.println("| " + arr1 + " " + arr2 + " " + arr3 + " |");
    }

}

// _XXOO_OX_