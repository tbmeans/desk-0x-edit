package io.github.tbmeans.hexedit;
import io.github.tbmeans.filebytes.*;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.io.IOException;
import java.io.RandomAccessFile;

public class HexEditApp {
    public static void main(String[] args) {
        final String NO_ARGS = "Please enter a filename";
        if (args == null) {
            System.out.println(NO_ARGS);
            return;
        }
        final String MENU_ERR = "Please enter valid menu option.";
        final String XTOKEN = "q";
        final String DELIM1 = " - ";
        final String XNAME = "Quit";
        final String[] MENU = {
            "O - select Offset of first byte in a block",
            "L - set Length of block (1 minimum)",
            "P - Print values of block at sel'd offset",
            "V - enter new byte Value(s) (space-separated)",
            "U - Undo pending changes to byte/block at selected offset",
            "W - make copy of original and overWrite it with " +
                    "new values entered",
            XTOKEN.toUpperCase() + DELIM1 + XNAME
        };
        final String ASK4LEN = "Enter the amount of bytes.";
        final String ASK4POS = "Enter the offset in file.";
        final String ASK4DAT = "Enter byte values, space-sep, " +
                "without radix specifier.";
        final String BYTEONLY = "All values must be 0 to 255--try again.";
        final String NONE2DO = "No such changes to file have been defined.";
        Scanner sc = new Scanner(System.in);
        String sel = "";
        int offset = 0;
        int len = 1;
        ArrayList<FileBytes> edits = new ArrayList<>();

        while (true) {
            for (int i = 0; i < MENU.length; i++) {
                System.out.println(MENU[i]);
            }
            try {
                sel = sc.next();
            } catch (NoSuchElementException e) {
                System.err.println(e.getMessage());
                break;
            }
            switch (sel.toLowerCase()) {            
                case "l":
                    len = takeInt(ASK4LEN, sc);
                    break;
                case "o":
                    offset = takeInt(ASK4POS, sc);
                    break;
                case "p":
                    printBytes(offset, len, args[0]);
                    break;
                case "u":
                    for (int i = 0; i < edits.size(); i++) {
                        if (edits.get(i).getOffset() == offset) {
                            edits.remove(i);
                            break;
                        }
                        if (i == edits.size() - 1) {
                            System.out.println(NONE2DO);
                        }
                    }
                    break;
                case "v":
                    sc.nextLine();
                    String[] tokens = takeBytes(ASK4DAT, sc).split(" +");
                    ArrayList<Byte> bytes = new ArrayList<>();
                    boolean is3plusChars = false;
                    Byte byteObj = Byte.valueOf("0", 16);
                    for (int i = 0; i < tokens.length; i++) {
                        if (tokens[i].length() > 2) {
                            is3plusChars = true;
                            break;
                        }
                        try {
                            byteObj = Byte.decode("0x" + tokens[i]);
                        } catch (NumberFormatException e) {
                            byteObj = Byte.valueOf(String.valueOf(
                                    Integer.parseInt(tokens[i], 16) - 256));
                        } finally {
                            bytes.add(byteObj);
                        }
                    }
                    if (is3plusChars) {
                        System.out.println(BYTEONLY);
                        break;
                    }
                    byte[] buf = new byte[len];
                    for (int i = 0; i < len; i++) {
                        buf[i] = bytes.get(i).byteValue();
                    }
                    edits.add(new FileBytes(offset, buf));
                    break;
                case "w":
                    if (edits.size() == 0) {
                        System.out.println(NONE2DO);
                    } else {
                        writeBytes(args[0], edits);
                    }
                    break;
                case XTOKEN:
                    break;
                default:
                    System.out.println(MENU_ERR);
            }
            if (sel.equals(XTOKEN)) {
                break;
            }
        }

        sc.close();
    }

    private static int takeInt(String prompt, Scanner sc) {
        int n = 0;
        System.out.println(prompt);
        try {
            n = sc.nextInt();
            System.out.println();
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
        return n;
    }

    private static String takeBytes(String prompt, Scanner sc) {
        String str = "";
        System.out.println(prompt);
        try {
            str = sc.nextLine();
            System.out.println();
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
        return str;
    }

    private static void printBytes(int pos, int len, String fname) {
        if (len > 1024 * 1024 * 1024) {
            System.out.println("Selected file portion is too big.");
            return;
        }
        try (RandomAccessFile src = new RandomAccessFile(fname, "r")) {
            String num;
            int strlen;
            byte[] buf = new byte[len];
            src.seek(pos);
            src.readFully(buf);
            for (int i = 0; i < buf.length; i++) {
                num = Integer.toUnsignedString(buf[i], 16);
                strlen = num.length();
                if (strlen > 2) {
                    num = num.substring(strlen - 2, strlen);
                } else if (strlen == 1) {
                    num = "0" + num;
                }
                System.out.print(num);
                if (i > 0 && i % 16 == 15) {
                    System.out.println();
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    private static void writeBytes(String fname, ArrayList<FileBytes> edits) {
        try (RandomAccessFile src = new RandomAccessFile(fname, "r")) {
            long fsize = src.length();
            if (fsize > 1024 * 1024 * 1024) {
                System.out.println("File must be 1GB or less.");
                return;
            }
            byte[] buf = new byte[(int) fsize];
            src.read(buf);
            try (RandomAccessFile cpy = new RandomAccessFile(
                    fname + ".modified", "rws")) {
                cpy.write(buf);
                for (int i = 0; i < edits.size(); i++) {
                    cpy.seek(edits.get(i).getOffset());
                    cpy.write(edits.get(i).getValues());
                }
                System.out.println("Done!");
                System.out.println();
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}

/*
 * > javac -d '.\out' -cp 'C:\Users\tbmea\src' '.\hexedit\HexEditApp.java'
 * > java -cp '.\out' io.github.tbmeans.hexedit.HexEditApp .\myfile
 */
