package okon;

import okon.exception.AppException;

public class FilenameVisitorFactory {
    public static FilenameVisitor makeVisitor(String type, String filename) {
        FilenameVisitor visitor = null;
        if (type.equals("exact")) {
            visitor = new FilenameExactVisitor(filename);
        } else if (type.equals("partial")) {
            visitor = new FilenamePartialVisitor(filename);
        } else if (type.equals("partial by date pattern")) {
            visitor = new FilenameDatepartialVisitor(filename);
        } else {
            throw new AppException("Błędnie podane dopasowanie nazwy pliku.");
        }
        return visitor;
    }
}