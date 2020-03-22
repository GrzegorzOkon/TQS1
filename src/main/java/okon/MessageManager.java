package okon;

import okon.exception.AppException;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    public static String searchPathToFile(Job job) {
        try (FileDetector detector = FileDetectorFactory.makeDetector(job.getHost().getSystem(), job.getHost(), job.getAuthorization(), job.getLog().getDirectory())) {
            List<FilenameVisitor> visitors = new ArrayList<>();
            for (LogMatch match : job.getLog().getMatches()) {
                visitors.add(FilenameVisitorFactory.makeVisitor(match.getMatch(), match.getFilename()));
            }
            return detector.accept(visitors);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    public static Message getMessage(Host host, Authorization authorization, Log log, String path) {
        if (path != null) {
            try (FileConnection connection = FileConnectionFactory.makeConnection(host, authorization, path)) {
                List<String> readedLines = connection.getLastLines(log.getLines());
                return new Message(path, readedLines);
            } catch (Exception e) {
                throw new AppException(e);
            }
        }
        return null;
    }
}
