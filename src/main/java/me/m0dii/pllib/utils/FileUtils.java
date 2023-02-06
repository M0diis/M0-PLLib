package me.m0dii.pllib.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    public static void copy(InputStream in, File file) {
        if (in == null) {
            return;
        }

        try {
            OutputStream out = new FileOutputStream(file);

            byte[] buf = new byte[1024];

            int len;

            while((len = in.read(buf)) > 0)
                out.write(buf, 0, len);

            out.close();
            in.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
