package com.jfinal.kit;

/**
 * @des
 * @auther linyu
 * @create 2017-02-13:16:43
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public FileUtils() {
    }

    public static byte[] read(String path) throws IOException {
        short base_size = 1024;
        File file = new File(path);
        if(!file.exists()) {
            return null;
        } else {
            FileInputStream fis = new FileInputStream(file);
            boolean len = false;
            byte[] dataByte = new byte[base_size];
            ByteArrayOutputStream out = new ByteArrayOutputStream(base_size);

            int len1;
            while((len1 = fis.read(dataByte)) != -1) {
                out.write(dataByte, 0, len1);
            }

            byte[] content = out.toByteArray();
            fis.close();
            out.close();
            return content.length == 0?null:content;
        }
    }

    public static void write(String path, byte[] data) throws IOException {
        File file = new File(path);
        if(!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.flush();
        fos.close();
    }

    public static List<String> findFiles(String baseDirName) {
        ArrayList files = new ArrayList();
        File baseDir = new File(baseDirName);
        if(baseDir.exists() && baseDir.isDirectory()) {
            String[] filelist = baseDir.list();
            String[] arr$ = filelist;
            int len$ = filelist.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String fileName = arr$[i$];
                files.add(fileName);
            }
        } else {
            System.err.println("search error：" + baseDirName + "is not a dir！");
        }

        return files;
    }

    public static List<String> findFileNames(String baseDirName, FileFilter fileFilter) {
        ArrayList files = new ArrayList();
        File baseDir = new File(baseDirName);
        if(baseDir.exists() && baseDir.isDirectory()) {
            File[] filelist = baseDir.listFiles(fileFilter);
            File[] arr$ = filelist;
            int len$ = filelist.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                File file = arr$[i$];
                if(file.isFile()) {
                    files.add(file.getName());
                }
            }
        } else {
            System.err.println("search error：" + baseDirName + "is not a dir！");
        }

        return files;
    }
}

