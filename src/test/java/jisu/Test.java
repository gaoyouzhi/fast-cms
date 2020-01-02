package jisu;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File file = new File("E:\\我的代码\\图片\\吊装");
        for(File f : file.listFiles()){
            System.out.println(FilenameUtils.getBaseName(f.getName()));
        }
    }

}
