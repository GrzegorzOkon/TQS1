package okon;

import okon.exception.AppException;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    public static Message getMessage(Host host, Authorization authorization, Log log) {
        try (HostConnection connection = HostConnectionFactory.makeConection(host, authorization)) {
            Message result = null;
            String pathToFile = findPathToFile(host, connection, log);
            result = readLog(host, connection, log, pathToFile);
            return result;
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    private static String findPathToFile(Host host, HostConnection connection, Log log) {
        FileDetector detector = FileDetectorFactory.makeDetector(host, connection, log);
        List<FilenameVisitor> visitors = new ArrayList<>();
        for (LogMatch match : log.getMatches()) {
            visitors.add(FilenameVisitorFactory.makeVisitor(match.getMatch(), match.getFilename()));
        }
        return detector.accept(visitors);
    }

    private static Message readLog(Host host, HostConnection connection, Log log, String path) {
        if (path != null) {
            LogReader reader = LogReaderFactory.makeReader(host, connection, path);
            List<String> readedLines = reader.getLastLines(log.getLines());
            return new Message(path, readedLines);
        }
        return null;
    }
}