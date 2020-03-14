package okon;

public class Job {
    private Log log;
    private Host host;
    private Authorization authorization;

    public Job(Log log, Host host, Authorization authorization) {
        this.log = log;
        this.host = host;
        this.authorization = authorization;
    }

    public Log getLog() {
        return log;
    }

    public Host getHost() {
        return host;
    }

    public Authorization getAuthorization() {
        return authorization;
    }
}
