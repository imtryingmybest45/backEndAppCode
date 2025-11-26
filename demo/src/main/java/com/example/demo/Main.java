package com.example.demo;
import org.springframework.boot.SpringApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String mainFolderPath = "C:\\Mac\\Home\\Documents\\SampleFolder";
        File mainFolderDir = new File(mainFolderPath);
        File[] mainFolderListFiles = mainFolderDir.listFiles();
        int numMainFiles= mainFolderListFiles.length;
        SpringApplication.run(DemoApplication.class, args);
        OtherFunctions otherFunctions = new OtherFunctions();
        otherFunctions.npmStart();
        while(true) {
            mainFolderListFiles = mainFolderDir.listFiles();
            if (mainFolderListFiles.length != numMainFiles) {
                numMainFiles =  mainFolderListFiles.length;
                Runtime rt = Runtime.getRuntime();
                String command = "taskkill /F /IM WindowsTerminal.exe";
                rt.exec(command);
                Main.editFrontEndFiles();
                otherFunctions.npmStart();
            }
        }
    }
    public static void editFrontEndFiles() throws IOException {
        OtherFunctions otherFunctions = new OtherFunctions();
        List<String> difference = otherFunctions.findDifferentFiles();

        if (!difference.isEmpty()) {
            for (String name : difference) {

                otherFunctions.writeNewPagesFile(name);
                String origName = otherFunctions.getOrigName();
                String newContent = otherFunctions.editRoutesAppFile(name);
                String newContent2 = otherFunctions.editLinksAppFile(name, origName, newContent);
                String newContent3 = otherFunctions.addImportLine(name, newContent2);

                String fileName1 = "C:\\Windows\\System32\\my-app12\\src\\App.js";
                Path filePath1 = Paths.get(fileName1);
                Files.writeString(filePath1, newContent3, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        }
    }
}
