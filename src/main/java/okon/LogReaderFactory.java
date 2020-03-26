package okon;

import okon.exception.AppException;

public class LogReaderFactory {
    public static LogReader makeReader(Host host, HostConnection connection, String path) {
        if (host.getSystem().equals("Windows")) {
            return new LogReaderWindows(path);
        } else if (host.getSystem().equals("Unix")) {
            return new LogReaderUnix(connection, path);
        } else {
            throw new AppException("Błędnie podana nazwa systemu.");
        }
    }
}