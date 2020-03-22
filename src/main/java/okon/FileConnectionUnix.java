package okon;

import com.jcraft.jsch.*;
import okon.exception.AppException;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileConnectionUnix extends FileConnection {
    private Session session;
    private String path;

    public FileConnectionUnix(Host host, Authorization authorization, String path) {
        try {
            this.path = path;
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
    public List<String> getLastLines(int numLinesToRead) {
        List<String> result = null;
        try {
            if (!session.isConnected())
                throw new RuntimeException("Brak połączenia do sesji. Najpierw wywołaj open()!");
            ChannelExec channel = null;
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("tail -n " + numLinesToRead + " " + path);
            PrintStream out = new PrintStream(channel.getOutputStream());
            InputStream in = channel.getInputStream();
            channel.connect();
            result = getChannelOutput(channel, in);
        } catch (JSchException | IOException e) {
            throw new AppException(e);
        }
        return new ArrayList<>(result);
    }

    private List<String> getChannelOutput(Channel channel, InputStream in) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        channel.disconnect();
        return result;
    }

    @Override
    public void close() throws Exception {
        session.disconnect();
    }
}