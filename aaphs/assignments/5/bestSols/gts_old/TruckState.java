class TruckState {
    double time;
    double truckWeigth;
    double pathLen;
    double cost;
    double[] trash;

    public TruckState(double time, double truckWeigth, double cost, double[] trash, double pathLen) {
        this.trash = trash;
        this.time = time;
        this.cost = cost;
        this.pathLen = pathLen;
        this.truckWeigth = truckWeigth;
    }

    public double actualCost() {
        double c = this.cost;
        if (this.time > 8) {
            c += 20 * (this.time - 8) + 80;
        } else {
            c += 10 * this.time;
        }
        c += this.pathLen / 50.0;
        return c;
    }
}