package okon;

import com.jcraft.jsch.Session;

public interface HostConnection extends AutoCloseable {
    public Session getSession();
}