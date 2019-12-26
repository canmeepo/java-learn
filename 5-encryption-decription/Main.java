// package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    static String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String ABC = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        String result = "";
        String mode = "";
        int key = 0;
        String data = "";
        String inPath = "";
        String outPath = "";
        String alg = "shift";

        for (int i = 0; i < args.length; i = i + 2) {
            String param = args[i];
            if ("-mode".equals(param)) {
                mode = args[i + 1];
            } else if ("-data".equals(param)) {
                data = args[i + 1];
            } else if ("-key".equals(param)) {
                key = Integer.parseInt(args[i + 1]);
            } else if ("-in".equals(param)) {
                inPath = args[i + 1];
            } else if ("-out".equals(param)) {
                outPath = args[i + 1];
            } else if ("-alg".equals(param)) {
                alg = args[i + 1];
            }
        }

        if (data.isEmpty() && !inPath.isEmpty()) {
            try {
                data = new String(Files.readAllBytes(Paths.get(inPath)));
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }

        if (mode.isEmpty()) {
            System.out.println("Unknown operation");
        } else if ("enc".equals(mode)) {
            result = encryption(data, key, alg);
        } else {
            result = decryption(data, key, alg);
        }

        if (inPath.isEmpty()) {
            System.out.println(result);
        } else {
            File outPut = new File(outPath);
            try (FileWriter writer = new FileWriter(outPut)) {
                writer.write(result);
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }
    }

    public static String encryption(String str, int key, String alg) {
        if ("unicode".equals(alg)) {
            String encrStr = "";
            for (int i = 0; i < str.length(); i++) {
                char currentChar = str.charAt(i);
                currentChar += key;
                encrStr += currentChar;

            }
            return encrStr;
        } else {
            int n = str.length();
            String res = new String("");
            for (int i = 0; i < n; i++) {
                char ch = str.charAt(i);
                if (abc.contains(Character.toString(ch))) {
                    int num = abc.indexOf(Character.toString(ch));
                    int rum = (num + key) % 26;
                    ch = abc.charAt(rum);
                    res += Character.toString(ch);
                } else if (ABC.contains(Character.toString(ch))) {
                    int num = ABC.indexOf(Character.toString(ch));
                    int rum = (num + key) % 26;
                    ch = ABC.charAt(rum);
                    res += Character.toString(ch);
                } else {
                    res += Character.toString(ch);
                }
            }
            return res;
        }

    }

    public static String decryption(String str, int key, String alg) {
        if ("unicode".equals(alg)) {
            String decrStr = "";
            for (int i = 0; i < str.length(); i++) {
                char currentChar = str.charAt(i);
                currentChar -= key;
                decrStr += currentChar;
            }
            return decrStr;
        } else {
            int n = str.length();
            String res = new String("");
            for (int i = 0; i < n; i++) {
                char ch = str.charAt(i);
                if (abc.contains(Character.toString(ch))) {
                    int num = abc.indexOf(Character.toString(ch));
                    int rum = (num - key) % 26;
                    rum = rum < 0 ? rum + 26 : rum;
                    ch = abc.charAt(rum);
                    res += Character.toString(ch);
                } else if (ABC.contains(Character.toString(ch))) {
                    int num = ABC.indexOf(Character.toString(ch));
                    int rum = (num - key) % 26;
                    rum = rum < 0 ? rum + 26 : rum;
                    ch = ABC.charAt(rum);
                    res += Character.toString(ch);
                } else {
                    res += Character.toString(ch);
                }
            }
            return res;
        }
    }
}
