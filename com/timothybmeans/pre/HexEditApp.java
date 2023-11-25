/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/*
 * Based on https://docs.oracle.com/javase/tutorial/uiswing/examples/start/HelloWorldSwingProject/src/start/HelloWorldSwing.java
 */

package com.timothybmeans.pre;

import java.awt.event.KeyEvent;

import javax.swing.*;

public class HexEditApp {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */

    final private static String TITLE = "Hex Editor";
    final private static String MENUS = "File,Edit,View,About";
    final private static int MENULEN = itemCount(MENUS);
    final private static int SUBCOUNT = MENULEN - 1;
    final private static String ITEMS1 = "New,Open,Save,Save As,Close,Exit";
    final private static int LEN1 = itemCount(ITEMS1);
    final private static String ITEMS2 = "by Byte,by Code" +
            ",Find,Find Again" + ",Replace,Goto,Select" + 
                    ",Copy,Paste Write,Paste Insert,Preferences";
    final private static int LEN2 = itemCount(ITEMS2);
    final private static String ITEMS3 = "Zoom In,Zoom Out,Encoding";
    final private static int LEN3 = itemCount(ITEMS3);
    private static String[] ITEMS = { ITEMS1, ITEMS2, ITEMS3 };
    private static int[] LENGTHS = { LEN1, LEN2, LEN3 };

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Hex Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu;

        for (int i = 0; i < SUBCOUNT; i++) {
            menu = new JMenu(itemPick(MENUS, i));
            menuBar.add(menu);
            for (int j = 0; j < LENGTHS[i]; j++) {
                menu.add(new JMenuItem(itemPick(ITEMS[i], j)));
            }
        }

        frame.setJMenuBar(menuBar);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static int itemCount(String csv) {
        return csv.split(",").length;
    }

    public static String itemPick(String csv, int index) {
        return csv.split(",")[index];
    }
}
