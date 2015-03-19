package com.sunkun.gh.code;
import java.io.File;
import java.io.IOException;

public class TestOCR {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String path = "code.gif";
        try {
            String valCode = new OCR().recognizeText(new File(path), "gif");
            System.out.println(valCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
