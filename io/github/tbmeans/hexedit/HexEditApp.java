package io.github.tbmeans.hexedit;
import java.io.*;
import java.nio.file.*;
import io.github.tbmeans.filebytes;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HexEditApp {
    public static void main(String[] args) {
        final String PATH_ERR = "File not found.";
        final String SCAN_ERR = "Empty entry. Exiting.";
        final String MENU_ERR = "Please enter valid menu option.";
        final String XTOKEN = "q";
        final String DELIM1 = " - ";
        final String XNAME = "Quit";
        final String FILLER = "Get right witcha";

        try {
            Path srcPath = Paths.get(args[0]);
            Path destPath = Paths.get(args[0] + ".modified");
            File srcFile = srcPath.toFile();
            File destFile = destPath.toFile();
            ArrayList<FileBytes> edits = new ArrayList<>();
            Scanner sc = new Scanner(System.in);
            String[] menu = {
                "O - select Offset of first byte in a block",
                "L - select Length of block (1 minimum)",
                "V - enter new byte Value(s) (comma-separated)",
                "U - Undo pending changes to byte/block at offset",
                "W - make copy of original and overWrite it with " +
                        "new values entered",
                XTOKEN.toUpperCase() + DELIM1 + XNAME
            };
            String sel;

            try {
                while(true) {
                    for (int i = 0; i < menu.length; i++) {
                        System.out.println(menu[i]);
                    }
                    sel = sc.nextLine().toLowerCase();
                    switch (sel) {            
                        case "l":
                        case "o":
                        case "u":
                        case "v":
                        case "w":
                            System.out.println(FILLER);
                            break;
                        case XTOKEN:
                            break;
                        default:
                            System.out.println(MENU_ERR);
                    }
                    if (sel.equals(XTOKEN)) {
                        sc.close();
                        break;
                    }
                }
            } catch (NoSuchElementException e) {
                System.out.println(SCAN_ERR);
            }
        } catch (InvalidPathException e) {
            System.out.println(PATH_ERR);
        }
    }
}

/*
 * > javac -d '.\out' '.\hexedit\HexEditApp.java'
 * > java -cp '.\out' io.github.tbmeans.hexedit.HexEditApp .\myfile
 */
