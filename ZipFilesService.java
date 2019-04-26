package learning.java8.stream.Examples;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFilesService {
    public static void main(String[] args) {
        byte[] buffer = new byte[1024];

        try {

            FileOutputStream fos = new FileOutputStream("/Users/raghavendra/Desktop/MyFile.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            LocalDate fromDate = LocalDate.now();
/*                LocalDate toDate = fromDate.plus(Period.ofDays(3));
                System.out.println(fromDate +"--------"+toDate);*/
            int retention=2;
            List<File> files=new ArrayList<>();
            for(int strt=0;strt<=retention;strt++){
                LocalDate toDate = fromDate.minus(Period.ofDays(strt));
                files.addAll(listAllFiles(toDate.toString()));
            }
//            File[] files=listAllFiles(toDate.toString());
            for(File file: files) {
                FileInputStream in = new FileInputStream(file.getAbsolutePath());
                ZipEntry ze = new ZipEntry(file.getAbsolutePath());
                zos.putNextEntry(ze);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
                zos.closeEntry();
            }

            //remember close it
            zos.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static List<File> listAllFiles(String date){
        File dir = new File("/Users/raghavendra/Desktop/");
        List<File> files = Arrays.asList(dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {


                return (name.contains(date));
            }
        }));

        for (File xmlfile : files) {
            System.out.println(xmlfile);
        }
        return files;
    }

}




