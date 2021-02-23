import java.util.Random;

public class Parameters {
    double maxWorkTime;
    double longestPath;
    double emptySpaceOnTruck;
    double chooseRandom;

    double cr;
    double mr;

    double cost = 0;
    Solution solution = null;

    public Parameters() {
        cr = Globals.crossoverRate;
        mr = Globals.mutateScalar;
    }

    public Parameters(double sumOfAllPaths) {
        this();
        this.maxWorkTime = 100.0 * Math.random();
        this.longestPath = sumOfAllPaths * Math.random();
        this.emptySpaceOnTruck = Globals.maxWeigth * Math.random();
        this.chooseRandom = Math.random();
    }

    public Parameters(double maxWorkTime, double longestPath, double emptySpaceOnTruck) {
        this();
        this.maxWorkTime = maxWorkTime;
        this.longestPath = longestPath;
        this.emptySpaceOnTruck = emptySpaceOnTruck;
    }

    public Parameters mutate(Parameters a, Parameters b, Parameters c) {
        Random r = new Random();

        if (r.nextDouble() > Globals.crossoverRate) {
            return new Parameters(maxWorkTime, longestPath, emptySpaceOnTruck);
        }
        Parameters out = new Parameters(maxWorkTime, longestPath, emptySpaceOnTruck);
        switch (r.nextInt(4)) {
            case 0:
                out.maxWorkTime = a.maxWorkTime + Globals.mutateScalar * (b.maxWorkTime - c.maxWorkTime);
                break;
            case 1:
                out.longestPath = a.longestPath + Globals.mutateScalar * (b.longestPath - c.longestPath);
                break;
            case 2:
                out.emptySpaceOnTruck = a.emptySpaceOnTruck
                        + Globals.mutateScalar * (b.emptySpaceOnTruck - c.emptySpaceOnTruck);
                break;
            case 3:
                out.chooseRandom = a.chooseRandom + Globals.mutateScalar * (b.chooseRandom - c.chooseRandom);
                break;
        }
        return out;
    }
}
