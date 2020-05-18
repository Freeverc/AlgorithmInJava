
public class City {
    // provide members here
    int num;
    double x;
    double y;
    public City(int n, double xx, double yy)
    {
        num = n;
        x = xx;
        y = yy;
    }
    public double distance(City b) {
		// how to calculate the Euclidean distance between this and b?
        return Math.sqrt((this.x - b.x) * (this.x - b.x) + (this.y - b.y) * (this.y - b.y));
    }
}
