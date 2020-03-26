package okon;

import okon.exception.AppException;

public class HostConnectionFactory {
    public static HostConnection makeConection(Host host, Authorization authorization) {
        if (host.getSystem().equals("Windows")) {
            return new HostConnectionWindows();
        } else if (host.getSystem().equals("Unix")) {
            return new HostConnectionUnix(host, authorization);
        } else {
            throw new AppException("Błędnie podana nazwa systemu.");
        }
    }
}
