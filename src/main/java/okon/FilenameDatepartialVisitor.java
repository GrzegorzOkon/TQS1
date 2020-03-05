package okon;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FilenameDatepartialVisitor extends FilenamePartialVisitor {
    public FilenameDatepartialVisitor(String datePattern) {
        super(getCurrentDate(datePattern));
    }

    private static String getCurrentDate(String datePattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        Date date = new Date();
        return formatter.format(date);
    }
}
