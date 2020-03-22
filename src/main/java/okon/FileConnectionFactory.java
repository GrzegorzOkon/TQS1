package okon;

import okon.exception.AppException;

public class FileConnectionFactory {
    public static FileConnection makeConnection(Host host, Authorization authorization, String path) {
        if (host.getSystem().equals("Windows")) {
            return new FileConnectionWindows(path);
        } else if (host.getSystem().equals("Unix")) {
            return new FileConnectionUnix(host, authorization, path);
        } else {
            throw new AppException("Błędnie podana nazwa systemu.");
        }
    }
}
