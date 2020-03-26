package okon;

import com.jcraft.jsch.*;
import okon.exception.AppException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogReaderUnix implements LogReader {
    private HostConnection connection;
    private String path;

    public LogReaderUnix(HostConnection connection, String pathToLog) {
        this.connection = connection;
        this.path = pathToLog;
    }

    @Override
    public List<String> getLastLines(int numLinesToRead) {
        List<String> result = null;
        try {
            if (!connection.getSession().isConnected())
                throw new RuntimeException("Brak połączenia do sesji. Najpierw wywołaj open()!");
            ChannelExec channel = null;
            channel = (ChannelExec) connection.getSession().openChannel("exec");
            channel.setCommand("tail -n " + numLinesToRead + " " + path);
            PrintStream out = new PrintStream(channel.getOutputStream());
            InputStream in = channel.getInputStream();
            channel.connect();
            result = getChannelOutput(channel, in);
            Collections.reverse(result);
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
}