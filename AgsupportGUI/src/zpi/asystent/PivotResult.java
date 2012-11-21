package zpi.asystent;


/**
 *
 * @author Tomasz Ibrom
 */
public class PivotResult {
    
    private double R1;
    private double S1;
    private double R2;
    private double S2;
    private double Pivot;
    
    public PivotResult(double pp, double r1, double s1, double r2, double s2)
    {
    	Pivot = pp;
        R1 = r1;
        S1 = s1;
        R2 = r2;
        S2 = s2;
    }
    
    public double getR1() { return R1; }
    public double getS1() { return S1; }
    public double getR2() { return R2; }
    public double getS2() { return S2; }
    public double getPivot() { return Pivot; }
}
