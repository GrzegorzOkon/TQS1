package okon;

import com.jcraft.jsch.Session;

public class HostConnectionWindows implements HostConnection {
    @Override
    public Session getSession() {
        return null;
    }

    @Override
    public void close() throws Exception {}
}