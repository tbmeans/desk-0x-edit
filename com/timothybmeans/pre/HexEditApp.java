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

package com.timothybmeans.pre;

import javax.swing.*;

public class HexEditApp {
    final private static String APPNAME = MyTemplates.XEDTITLE;
    final private static int AUXSUBCT = MyTemplates.branchCount(APPNAME);
    final private static int FSIZELIM = 5 * 1024 * 1024; // 5 MB
    final private static String BYTETEMP = MyTemplates.initXedBytes();

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame(APPNAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu;

        for (int i = 0; i < AUXSUBCT; i++) {
            menu = new JMenu(MyTemplates.getMenuName(APPNAME, i));
            menuBar.add(menu);
            for (int j = 0; j < MyTemplates.getMenuLength(APPNAME, i); j++) {
                menu.add(new JMenuItem(
                        MyTemplates.getMenuItemName(APPNAME, i, j)));
            }
        }

        frame.setJMenuBar(menuBar);

        JEditorPane bytesView = new JEditorPane(
                "text/html", MyTemplates.initXedBytes());
        JScrollPane bytesPane = new JScrollPane(bytesView);
        JEditorPane codesView;
        JScrollPane codesPane;
        bytesView.setEditable(false);
        frame.add(bytesPane);

        
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
}
