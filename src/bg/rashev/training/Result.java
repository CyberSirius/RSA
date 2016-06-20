package bg.rashev.training;

import java.util.List;

/**
 * Created by CyberSirius on 12-Jun-16.
 */
public class Result {
    private List<ThreadResult> threadResults;
    private long result;

    public Result(List<ThreadResult> threadResults, long result) {
        this.threadResults = threadResults;
        this.result = result;
    }

    public List<ThreadResult> getThreadResults() {
        return threadResults;
    }

    public long getResult() {
        return result;
    }
}
