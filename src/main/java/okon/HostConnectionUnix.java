package okon;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import okon.exception.AppException;

import java.util.Properties;

public class HostConnectionUnix implements HostConnection {
    private Session session;

    public HostConnectionUnix(Host host, Authorization authorization) {
        try {
            connect(host.getIp(), host.getPort(), authorization.getUsername(), authorization.getPassword());
        } catch (JSchException e) {
            throw new AppException("Błąd połączenia ssh.");
        };
    }

    private void connect(String hostname, Integer port,  String username, String password) throws JSchException {
        JSch jSch = new JSch();
        session = jSch.getSession(username, hostname, port);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
        session.setConfig(config);
        session.setPassword(password);
        session.connect();
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void close() throws Exception {
        session.disconnect();
    }
}
