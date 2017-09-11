package com.medicine.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessUtil {
    public static final Boolean isAliveByProName(String proName) {
        BufferedReader br = null;
        try {
            String line = null;
            Process proc = Runtime.getRuntime().exec("tasklist");
            br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            while ((line = br.readLine()) != null) {
                if (line.indexOf(proName) != -1) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}
