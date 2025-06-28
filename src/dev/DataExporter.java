package dev;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class DataExporter {
    public static void exporter(Elections e, String name) {
        try(ObjectOutputStream oos
            = new ObjectOutputStream(
                new FileOutputStream(new File("./.data/" + name + ".electionssim")))) {
            oos.writeObject(e);
        } catch(Exception ex) {ex.printStackTrace();}
    }
}
