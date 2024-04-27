package sr.thrift.server;


import org.apache.thrift.TException;
import sr.rpc.thrift.SortingMachine;

import java.util.List;
import java.util.Collections;

public class SortingMachineHandler implements SortingMachine.Iface {

    int id;

    public SortingMachineHandler(int id) {
        this.id = id;
    }
    @Override
    public List<Integer> sortNumbers(List<Integer> nums) throws TException {
        Collections.sort(nums);
        return nums;
    }
}
