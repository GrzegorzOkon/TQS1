package okon;

import okon.exception.AppException;

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
                try (FileConnection connection = new FileConnection(job)) {
                    List<String> lines = connection.getLines(job.getLines());
                    String filename = connection.getFileName() != null ? connection.getFileName() : job.getPath() + "\\" + job.getFilename();
                    message = new Message(filename, lines);
                } catch (Exception e) {
                    throw new AppException(e);
                }
                synchronized (messages) {
                    messages.add(message);
                }
            }
        }
    }
}
