package okon;

public class Host {
    private final String hostInterface;
    private final String system;
    private final String ip;
    private final Integer port;

    public Host(String hostInterface, String system, String ip, Integer port) {
        this.hostInterface = hostInterface;
        this.system = system;
        this.ip = ip;
        this.port = port;
    }

    public String getHostInterface() {
        return hostInterface;
    }

    public String getSystem() {
        return system;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }
}
