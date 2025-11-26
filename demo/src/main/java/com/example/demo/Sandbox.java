package com.example.demo;

import java.io.IOException;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Sandbox {
    public static void main(String[] args) throws IOException {
        OtherFunctions otherFunctions = new OtherFunctions();
        List<String> difference = otherFunctions.findDifferentFiles();

        if (!difference.isEmpty()) {
            for (String name : difference) {

                //otherFunctions.writeNewPagesFile(name);
                String origName = otherFunctions.getOrigName();
                String newContent = otherFunctions.editRoutesAppFile(name);
                String newContent2 = otherFunctions.editLinksAppFile(name, origName, newContent);
                String newContent3 = otherFunctions.addImportLine(name, newContent2);
                System.out.println(newContent3);

                String fileName1 = "C:\\Windows\\System32\\my-app12\\src\\App.js";
                Path filePath1 = Paths.get(fileName1);
                //Files.writeString(filePath1, newContent3, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        }
    }
}