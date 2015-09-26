/*****************************************************************************
 * Copyright (c) 2006 Jesen Ha, Tiffany Hsu, Jeff Moguillansky, Robert Wei, 
 * Brian Shen, KuoYen Lo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.  At your option
 * you can also redistribute it and/or modify this program and the
 * accompanying materials under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.  
**************************************************************************/

package mips.interpreter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class spimCommand extends Thread {
    private BufferedReader in;
    private BufferedWriter out;
    private String line = "";
    private Process process;

    public spimCommand() {
        try {
            String spimExe = "support/spim/spim.exe";
            String spimPath = new File(spimExe).getParent();
            String[] cmd = { spimExe };
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.directory(new File(spimPath));
            process = pb.start();
            in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            out = new BufferedWriter(
                    new OutputStreamWriter(process.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            in.close();
            out.close();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCommand(String command) {
        try {
            out.write(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readOutput() {
        String output = "";
        line = "";
        try {
            while (!(line.startsWith("(spim)"))) {
                line = in.readLine();
                if (line.startsWith("(input)")) {
                    return "(input)\n" + output;
                } else if (!(line.startsWith("(spim)"))) {
                    output += line;
                    output += "\n";
                }
            }
        } catch (IOException e) {
            System.err.println("spim Command error\n");
            e.printStackTrace();
        }
        return output;
    }
}
