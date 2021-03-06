package okon;

import okon.exception.AppException;

public abstract class FileDetectorFactory {
    public static FileDetector makeDetector(Host host, HostConnection connection, Log log) {
        if (host.getSystem().equals("Windows")) {
            return new FileDetectorWindows(log);
        } else if (host.getSystem().equals("Unix")) {
            return new FileDetectorUnix(connection, log);
        } else {
            throw new AppException("Błędnie podana nazwa systemu.");
        }
    }
}