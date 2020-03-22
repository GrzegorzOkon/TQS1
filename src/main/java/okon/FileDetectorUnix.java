package okon;

import com.jcraft.jsch.*;
import okon.exception.AppException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class FileDetectorUnix extends FileDetector {
    private Session session;
    private List<String> filePaths;

    public FileDetectorUnix(Host host, Authorization authorization, String directory) {
        try {
            connect(host.getIp(), host.getPort(), authorization.getUsername(), authorization.getPassword());
            filePaths = getFilePaths(directory);
        } catch (JSchException | IOException e) {
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

    private List<String> getFilePaths(String directoryPath) throws JSchException, IOException {
        List<String> result = new ArrayList<>();
        if (!session.isConnected())
            throw new RuntimeException("Brak połączenia do sesji. Najpierw wywołaj open()!");
        ChannelExec channel = null;
        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand("ls " + directoryPath);
        PrintStream out = new PrintStream(channel.getOutputStream());
        InputStream in = channel.getInputStream();
        channel.connect();
        List<String> fileNames = getChannelOutput(channel, in);
        for (String fileName : fileNames) {
            result.add(directoryPath + "/" + fileName);
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
    public String accept(List<FilenameVisitor> visitors) {
        List<String> paths = new ArrayList<>(filePaths);
        for (FilenameVisitor visitor : visitors) {
            paths = visitor.visit(paths);
        }
        return getNewestPath(paths);
    }

    private String getNewestPath(List<String> paths) {
        if (paths != null && paths.size() > 0) {
            Collections.sort(paths, Collections.reverseOrder());
            return paths.get(0);
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        session.disconnect();
    }
}