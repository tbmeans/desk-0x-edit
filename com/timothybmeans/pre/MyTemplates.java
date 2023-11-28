package com.timothybmeans.pre;

public class MyTemplates {
    public static final String PREAMBLE = "<!DOCTYPE html>";
    public static final String OPENHTML = "<html lang=\"en\">";
    public static final String OPENHEAD = "<head>" + "<meta charset=\"utf-8" +
            "\"><meta name=\"viewport\" content=\"width=device-width, " + 
                    "initial-scale=1.0\">" + "<title>";
    public static final String BEGINBOD = "</head><body>";
    public static final String CLOSEBOD = "</body>";
    public static final String CLOSEHTM = "</html>";

    public static final String XEDTITLE = "Hex editor";
    public static final String XEDMENUS = "File,Edit,View,About";
    public static final int XEDCTDIA = 1; // # of menus that open dialog only
    public static final String XEDSUBM1 = "New,Open,Save,Save As,Close,Exit";
    public static final String XEDSUBM2 = "by Byte,by Code" +
            ",Find,Find Again" + ",Replace,Goto,Select" + 
                    ",Copy,Paste Write,Paste Insert,Preferences";
    public static final String XEDSUBM3 = "Zoom In,Zoom Out,Encoding";
    public static final String XEDSTYLE = "<style>body {font-family: " +
            "monospace; font-size: 1.1em; background-color: #333333; color: " +
                    "#228800;}table {border-spacing: 0;}th {background-color" +
                            ": #777777; color: #dddddd;}td, th {padding: 2px" +
                                    " 4px 2px 4px;}</style>";
    public static final String OPENXED1 = "<table><thead><tr><th scope=\"col" +
            "\"></th><th scope=\"col\">00</th><th scope=\"col\">01</th><th s" +
                    "cope=\"col\">02</th><th scope=\"col\">03</th><th scope=" +
                            "\"col\">04</th><th scope=\"col\">05</th><th sco" +
                                    "pe=\"col\">06</th><th scope=\"col\">07";
    public static final String OPENXED2 = "</th><th scope=\"col\">08</th><th" +
            " scope=\"col\">09</th><th scope=\"col\">0A</th><th scope=\"col" +
                    "\">0B</th><th scope=\"col\">0C</th><th scope=\"col\">0D" +
                            "</th><th scope=\"col\">0E</th><th scope=\"col\"" +
                                    ">0F</th></tr></thead><tbody>";
    public static final String OPENXEDT = OPENXED1 + OPENXED2;
    public static final String XEDRWOPN = "<tr><th scope=\"row\">";
    public static final String CLOSETHD = "</th>";
    public static final String XEDOPNDT = "<td>";
    public static final String XEDCLSDT = "</td>";
    public static final String XEDRWCLS = "</tr>";
    public static final String CLOSEXED = "</tbody></table>";
 
    public static String beginDoc() {
        return PREAMBLE + OPENHTML + OPENHEAD;
    }

    public static String headTitle(String title) {
        return title + "</title>";
    }

    public static String endDocNoScript() {
        return CLOSEBOD + CLOSEHTM;
    }

    public static String helloDoc() {
        return (beginDoc() + headTitle("Hello") + BEGINBOD + "Hello world!" +
            endDocNoScript());
    }

    public static int itemCount(String csv) {
        return csv.split(",").length;
    }

    public static String itemPick(String csv, int index) {
        return csv.split(",")[index];
    }

    public static String hexCap(int i) {
        return Integer.toString(i, 16).toUpperCase();
    }

    public static String leadPad(int i, int maxLength) {
        String num = hexCap(i);
        return "0".repeat(maxLength - num.length()) + num;
    }

    public static String writeXedTitle() {
        return headTitle(XEDTITLE);
    }

    public static String getMenuName(String naturalAppName, int i) {
        String menuConstant;

        switch (naturalAppName.toLowerCase()) {
            case "hex editor":
                menuConstant = XEDMENUS;
                break;
            default:
                menuConstant = XEDMENUS;
        }

        return itemPick(menuConstant, i);
    }

    public static int branchCount(String naturalAppName) {
        int count;

        switch (naturalAppName.toLowerCase()) {
            case "hex editor":
                count = itemCount(XEDMENUS) - XEDCTDIA;
                break;
            default:
                count = itemCount(XEDMENUS) - XEDCTDIA;
        }

        return count;
    }

    public static int getMenuLength(
            String naturalAppName, int indexOfMenu) {
        String[] xedAppSubs = { XEDSUBM1, XEDSUBM2, XEDSUBM3 }; 
        int length;

        switch (naturalAppName.toLowerCase()) {
            case "hex editor":
                length = itemCount(xedAppSubs[indexOfMenu]);
                break;
            default:
                length = itemCount(xedAppSubs[indexOfMenu]);
        }

        return length;
    }

    public static String getMenuItemName(
            String naturalAppName, int indexOfMenu, int indexOfMenuItem) {
        String[] xedAppSubs = { XEDSUBM1, XEDSUBM2, XEDSUBM3 }; 
        String name;

        switch (naturalAppName.toLowerCase()) {
            case "hex editor":
                name = itemPick(xedAppSubs[indexOfMenu], indexOfMenuItem);
                break;
            default:
                name = itemPick(xedAppSubs[indexOfMenu], indexOfMenuItem);
        }

        return name;
    }

    public static String xedLabelRow(int lineNum) {
        return XEDRWOPN + leadPad((lineNum - 1) * 16, 6) + CLOSETHD;
    }

    public static String xedBytesRow(int lineNum, int[] bytes) {
        String data = "";

        for (int i = 0; i < 16; i++) {
            try {
                data += XEDOPNDT + leadPad(bytes[i], 2) + XEDCLSDT;
            } catch (ArrayIndexOutOfBoundsException e) {
                data += XEDOPNDT + XEDCLSDT;
            }
        }

        return xedLabelRow(lineNum) + data + XEDRWCLS;
    }

    public static String initXedBytes() {
        return (beginDoc() + headTitle(XEDTITLE) + XEDSTYLE + BEGINBOD + OPENXEDT +
            xedLabelRow(1) + XEDRWCLS + CLOSEXED + endDocNoScript());
    }
}
