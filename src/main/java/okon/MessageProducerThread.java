package okon;

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
                String pathToLog = MessageManager.searchPathToFile(job);
                Message message = MessageManager.getMessage(job.getHost(), job.getAuthorization(), job.getLog(), pathToLog);
                if (message != null) {
                    synchronized (messages) {
                        messages.add(message);
                    }
                }
            }
        }
    }
}
