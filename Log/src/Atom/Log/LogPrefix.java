package Atom.Log;

public class LogPrefix {

    public volatile String debugPrefix, InfoPrefix, WarnPrefix, AlertPrefix, basePrefix, startCoverPrefix, endCoverPrefix, innerSpacerPrefix, afterCoverPrefix;


    public LogPrefix() {
        startCoverPrefix = "[";
        innerSpacerPrefix = "-";
        endCoverPrefix = "]";
        afterCoverPrefix = ": ";

        basePrefix = "Atomic";

        debugPrefix = "Debug";//0
        InfoPrefix = "Info";//1
        WarnPrefix = "Warn";//2
        AlertPrefix = "Error";//3
    }


    public String cover(Object data, int level) {
        switch (level) {
            case (0):
                return startCoverPrefix + basePrefix + innerSpacerPrefix + debugPrefix + endCoverPrefix + afterCoverPrefix + data;
            case (1):
                return startCoverPrefix + basePrefix + innerSpacerPrefix + InfoPrefix + endCoverPrefix + afterCoverPrefix + data;
            case (2):
                return startCoverPrefix + basePrefix + innerSpacerPrefix + WarnPrefix + endCoverPrefix + afterCoverPrefix + data;
            case (3):
                return startCoverPrefix + basePrefix + innerSpacerPrefix + AlertPrefix + endCoverPrefix + afterCoverPrefix + data;
            default:
                return startCoverPrefix + basePrefix + endCoverPrefix + afterCoverPrefix + data;
        }
    }

}
