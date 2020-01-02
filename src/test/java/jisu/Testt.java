package jisu;

import org.apache.commons.io.FilenameUtils;

public class Testt {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String str = "http://img.17sucai.com/upload/1528155/2019-01-29/0e32e144cef753774a851e611dfb481f.jpg?x-oss-process=style/big";
        System.out.println(FilenameUtils.getExtension(str.substring(0,str.lastIndexOf("?"))));
    }

}
