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
                Message message = getMessage(job.getSystem(), pathToRead, job.getLines());
                if (message != null) {
                    synchronized (messages) {
                        messages.add(message);
                    }
                }
            }
        }
    }

    private Path getPathToRead(Job job) {
        FileDetector detector = FileDetectorFactory.makeDetector(job.getSystem(), job.getDirectory());
        List<FilenameVisitor> visitors = new ArrayList<>();
        FilenameVisitor visitor1 = new FilenamePartVisitor(job.getFilename());
        visitors.add(visitor1);
        if (job.getPostfix() != null && !job.getPostfix().equals("")) {
            FilenameVisitor visitor2 = new FilenameDatepartVisitor(job.getPostfix());
            visitors.add(visitor2);
        }
        return detector.accept(visitors);
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
