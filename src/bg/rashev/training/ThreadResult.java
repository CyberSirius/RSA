package bg.rashev.training;

import org.apfloat.Apfloat;

/**
 * Created by CyberSirius on 12-Jun-16.
 */
public class ThreadResult {
    private Apfloat result;
    private long resultTime;
    private int threadId;

    public ThreadResult(Apfloat result, long resultTime, int threadId) {
        this.result = result;
        this.resultTime = resultTime;
        this.threadId=threadId;
    }

    public Apfloat getResult() {
        return result;
    }

    public long getResultTime() {
        return resultTime;
    }

    public int getThreadId() {
        return threadId;
    }
}
