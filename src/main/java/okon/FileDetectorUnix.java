package okon;

import com.jcraft.jsch.*;
import okon.exception.AppException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileDetectorUnix implements FileDetector {
    private HostConnection connection;
    private List<String> filePaths;

    public FileDetectorUnix(HostConnection connection, Log log) {
        this.connection = connection;
        try {
            filePaths = getFilePaths(log.getDirectory());
        } catch (JSchException | IOException e) {
            throw new AppException(e);
        }
    }

    private List<String> getFilePaths(String directoryPath) throws JSchException, IOException {
        List<String> result = new ArrayList<>();
        if (!connection.getSession().isConnected())
            throw new RuntimeException("Brak połączenia do sesji. Najpierw wywołaj open()!");
        ChannelExec channel = null;
        channel = (ChannelExec) connection.getSession().openChannel("exec");
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
}