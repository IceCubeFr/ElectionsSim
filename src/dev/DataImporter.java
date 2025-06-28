package dev;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DataImporter {
    public static ArrayList<String> fileList() {
        Path dossier = Paths.get("./.data/");
        ArrayList<String> files = new ArrayList<String>();
        ArrayList<String> finalFiles = new ArrayList<String>();
        try (Stream<Path> fichiers = Files.list(dossier)) {
            fichiers.forEach(f -> files.add(f.getFileName().toString()));
            for(String s : files) {
                try {
                    if(".electionssim".equals(s.substring(s.length()-13))) {
                        finalFiles.add(s);
                    }
                } catch(Exception e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalFiles;
    }

    public static Elections importElections(String fileName) {
        try(ObjectInputStream ois
            = new ObjectInputStream(
                new FileInputStream(new File("./.data/" + fileName)))) {
            return (Elections)(ois.readObject());
        } catch(Exception e) {e.printStackTrace();}
        return null;
    }
}
