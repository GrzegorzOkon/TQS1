package okon;

import okon.exception.AppException;

public abstract class FileDetectorFactory {
    public static FileDetector makeDetector(String system, String directoryPath) {
        if (system.equals("Windows")) {
            return new FileDetectorWindows(directoryPath);
        } else {
            throw new AppException("Błędnie podana nazwa systemu.");
        }
    }
}