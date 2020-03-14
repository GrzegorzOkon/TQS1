package okon;

import okon.exception.AppException;

public abstract class FileDetectorFactory {
    public static FileDetector makeDetector(String system, Authorization credentials, String directoryPath) {
        if (system.equals("Windows")) {
            return new FileDetectorWindows(directoryPath);
        } else if (system.equals("Unix")) {
            return new FileDetectorUnix(credentials, directoryPath);
        } else {
            throw new AppException("Błędnie podana nazwa systemu.");
        }
    }
}