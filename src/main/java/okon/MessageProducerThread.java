package okon;

import okon.exception.AppException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static okon.TQS1App.jobs;
import static okon.TQS1App.messages;

public class MessageProducerThread extends Thread {
    @Override
    public void run() {
        while (!jobs.isEmpty()) {
            Job job = null;
            synchronized (jobs) {
                if (!jobs.isEmpty()) {
                    job = jobs.poll();
                }
            }
            if (job != null) {
                Path pathToRead = getPathToRead(job);
                Message message = getMessage(job.getHost().getSystem(), pathToRead, job.getLog().getLines());
                if (message != null) {
                    synchronized (messages) {
                        messages.add(message);
                    }
                }
            }
        }
    }

    private Path getPathToRead(Job job) {
        try (FileDetector detector = FileDetectorFactory.makeDetector(job.getHost().getSystem(), job.getAuthorization(), job.getLog().getDirectory())) {
            List<FilenameVisitor> visitors = new ArrayList<>();
            for (LogMatch match : job.getLog().getMatches()) {
                visitors.add(FilenameVisitorFactory.makeVisitor(match.getMatch(), match.getFilename()));
            }
            return detector.accept(visitors);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    private Message getMessage(String system, Path path, int lines) {
        if (path != null) {
            try (FileConnection connection = FileConnectionFactory.makeConnection(system, path)) {
                List<String> readedLines = connection.getLastLines(lines);
                return new Message(path.toString(), readedLines);
            } catch (Exception e) {
                throw new AppException(e);
            }
        }
        return null;
    }
}
