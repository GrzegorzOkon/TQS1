package okon;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import okon.exception.AppException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDetectorUnix extends FileDetector {
    private Session session;
    private List<Path> filePaths;

    public FileDetectorUnix(Authorization credentials, String directory) {
        /*try {
            connect(credentials.getIp(), credentials.getPort(), credentials.getUsername(), credentials.getPassword());
        } catch (JSchException e) {
            throw new AppException("Błąd połączenia ssh.");
        };*/
        //filePaths = getFilePaths(Paths.get(directory));
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
        System.out.println("Is connected: " + session.isConnected());
    }

    private List<Path> getFilePaths(Path directoryPath) {
        List<Path> result = null;
        try (Stream<Path> walk = Files.walk(directoryPath, 1)) {
            result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new AppException(e);
        }
        return new ArrayList<>(result);
    }

    @Override
    public Path accept(FilenameVisitor visitor) {
        return null;
    }

    @Override
    public Path accept(List<FilenameVisitor> visitors) {
        return null;
    }

    @Override
    public void close() throws Exception {
        session.disconnect();
    }
}