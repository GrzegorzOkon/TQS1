package okon;

import okon.exception.AppException;

import java.nio.file.*;
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
                Message message = null;

                List<FilenameVisitor> visitors = new ArrayList<>();
                FileDetector detector = new FileDetector(job.getDirectory());
                FilenameVisitor visitor1 = new FilenamePartVisitor(job.getFilename());
                visitors.add(visitor1);
                if (job.getPostfix() != null && !job.getPostfix().equals("")) {
                    FilenameVisitor visitor2 = new FilenameDatepartVisitor(job.getPostfix());
                    visitors.add(visitor2);
                }
                Path path = detector.accept(visitors);

                if (path != null) {
                    try (FileConnection connection = new FileConnection(path)) {
                        List<String> readedLines = connection.getLastLines(job.getLines());
                        message = new Message(path.toString(), readedLines);
                    } catch (Exception e) {
                        throw new AppException(e);
                    }
                }
                synchronized (messages) {
                    messages.add(message);
                }
            }
        }
    }
}
