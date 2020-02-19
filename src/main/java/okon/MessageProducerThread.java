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
                try (FileConnection connection = new FileConnection(job.getPath())) {
                    List<String> result = connection.getLines(job.getLines());
                    message = new Message(job.getPath(), result);
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
