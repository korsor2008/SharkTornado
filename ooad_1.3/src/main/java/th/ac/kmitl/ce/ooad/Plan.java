package th.ac.kmitl.ce.ooad;

import th.ac.kmitl.ce.ooad.CloudProvider;

/**
 * Created by Nut on 10/12/2015.
 */
public class Plan {
    public CloudProvider cloudProv;
    public String ip;
    public float monthlyRate, cpu, mem, network;
    public int storage;

    public Plan(CloudProvider cloudProv, String ip, float monthlyRate,
                float cpu, float mem, float network, int storage) {
        this.cloudProv = cloudProv;
        this.ip = ip;
        this.monthlyRate = monthlyRate;
        this.cpu = cpu;
        this.mem = mem;
        this.network = network;
        this.storage = storage;
    }
}
