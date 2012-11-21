package zpi.asystent;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jfree.date.DateUtilities;

import com.agsupport.core.service.communication.JSONDerivativeValue;
import com.agsupport.core.service.communication.JSONStockIndex;
import com.agsupport.core.service.communication.ListOfDerivativeValue;
import com.agsupport.core.service.communication.ListOfStockIndex;

/**
*
* @author Tomasz Ibrom
*/
public class Statistics {
	
	
	public static ListOfStockIndex fromDerivativeValue(ListOfDerivativeValue list)
	{
		List<JSONStockIndex> ret = new LinkedList<JSONStockIndex>();
		List<JSONDerivativeValue> der = list.getDerivativeValues();
		
		for(int i = 0; i < der.size(); i++)
		{
			JSONStockIndex ind = new JSONStockIndex();
			ind.setPrice(der.get(i).getPrice());
			ind.setDateOfAdd(der.get(i).getDateOfAdd());
			ret.add(ind);
		}
		
		ListOfStockIndex retlist = new ListOfStockIndex(ret);
		
		return retlist;
	}
	
	public double[] listToArray(List<Double> list)
	{
		double[] ret = new double[list.size()];
		
		for(int i = 0; i < list.size(); i++)
		{
			ret[i] = list.get(i);
		}
		
		return ret;
	}
	
	public List<List<Double>> groupByDay(List<JSONStockIndex> list)
	{
		LinkedList<List<Double>> ret = new LinkedList<List<Double>>();
		
		LinkedList<Double> templist = new LinkedList<Double>();
		
		Date currtt = (Date)list.get(0).getDateOfAdd();
		currtt.setMinutes(0);
		currtt.setHours(0);
		currtt.setSeconds(0);
		
		for(int i = 0; i < list.size(); i++)
		{
			Date tt = (Date)list.get(i).getDateOfAdd().clone();

			tt.setMinutes(0);
			tt.setHours(0);
			tt.setSeconds(0);
			
			if(tt.equals(currtt))
			{
				templist.add(list.get(i).getPrice());
			}
			else
			{
				if(templist.size() != 0)
				{
					ret.add(templist);
				}
				else
				{
				
					Date ntt = (Date)currtt.clone();
					ntt.setTime(currtt.getTime()+86400000);
					if(!tt.equals(ntt))
					{
						templist.add(ret.getLast().get(ret.getLast().size()-1));
						ret.add(templist);
					}

					currtt = (Date)ntt.clone();
					
				}
				i--;
				
				templist = new LinkedList<Double>();
			}
		}
		
		if(templist.size() > 0) ret.add(templist);
		
		return ret;
	}
	
	public double[] extractCloseVals(List<List<Double>> days)
	{
		LinkedList<Double> daylist = new LinkedList<Double>();
		
		for(int i = 0; i <  days.size(); i++)
		{
			daylist.add(days.get(i).get(days.get(i).size()-1));
		}
		
		return listToArray(daylist);
	}
	
	public double[] extractMinVals(List<List<Double>> days)
	{
		LinkedList<Double> daylist = new LinkedList<Double>();
		
		for(int i = 0; i <  days.size(); i++)
		{
			double[] vec = listToArray(days.get(i));
			daylist.add(this.min_vec(vec, 0, days.get(i).size()-1));
		}
		
		return listToArray(daylist);
	}
	
	public double[] extractMaxVals(List<List<Double>> days)
	{
		LinkedList<Double> daylist = new LinkedList<Double>();
		
		for(int i = 0; i <  days.size(); i++)
		{
			double[] vec = listToArray(days.get(i));
			daylist.add(this.max_vec(vec, 0, days.get(i).size()-1));
		}
		
		return listToArray(daylist);
	}
	
	public List<JSONStockIndex> sortStockIndexes(ListOfStockIndex list)
	{
		Date mindate = DateUtilities.createDate(9999, 12, 31);
		List<JSONStockIndex> ids = list.getStockIndexes();
		List<JSONStockIndex> ids2 = new LinkedList<JSONStockIndex>();
		JSONStockIndex temp = ids.get(0);
		while(ids2.size() != ids.size())
		{
			for(int i = 0; i < ids.size(); i++)
			{
				if(!ids2.contains(ids.get(i)))
				{
					if(ids.get(i).getDateOfAdd().before(mindate))
					{
						temp = ids.get(i);
						mindate = temp.getDateOfAdd();
					}
				}
			}
			ids2.add(temp);
			mindate = DateUtilities.createDate(9999, 12, 31);
		}
		
		return ids2;
	}
	
	public double[] getValues(double[] values, int start, int end)
	{
		int len = end-start;
		double[] ret = new double[len];
		
		for(int i = 0; i < len; i++)
		{
			ret[i] = values[start + i];
		}
		return ret;
	}
	
	public double[] plainValuesFromStockIndexes(ListOfStockIndex list)
	{
		
		double[] ret = new double[list.getStockIndexes().size()];
		
		List<JSONStockIndex> ids = sortStockIndexes(list);
		
		for(int i = 0; i < ids.size(); i++)
		{
			ret[i] = ids.get(i).getPrice();
		}
		
		return ret;
	}

	public double min_vec(double[] var, int start, int end)
    {
        double min_var = 999999999;
        
        for(int i = start; i <= end; i++)
        {
            if(var[i] < min_var) min_var = var[i];
        }
        
        return min_var;
    }
    
    public double max_vec(double[] var, int start, int end)
    {
        double max_var = -999999999;
        
        for(int i = start; i <= end; i++)
        {
            if(var[i] > max_var) max_var = var[i];
        }
        
        return max_var;
    }
    
    public double suma(double[] wartosci)
    {
        double len = wartosci.length;
        
        double sum = 0;
        
        for(int i =0; i < len; i++)
        {
            sum += wartosci[i];
        }
        
        return sum;
    }
    
    public double SMA(double[] wartosci)
    {
        double sum = suma(wartosci);
        
        return sum/wartosci.length;
    }
    
    public double WMA(double[] wartosci, double[] wagi)
    {
        if(wartosci.length != wagi.length)
            return 0;
        
        int len = wartosci.length;
        
        double licznik = 0;
        double mianownik = 0;
        
        for(int i = 0; i < len; i++)
        {
            licznik += wartosci[i] * wagi[i];
            mianownik += wagi[i];
        }
        
        return licznik/mianownik;
    }
    
    public double WMA(double[] wartosci)
    {
        double[] wagi = new double[wartosci.length];
        
        for(int i = 0; i < wartosci.length; i++)
        {
            wagi[i] = wartosci.length - i;
        }
        
        return WMA(wartosci,wagi);
    }
    
    public double EMA(double[] wartosci)
    {
        return EMA(wartosci, 0, wartosci.length);
    }
    
    public double EMA(double[] wartosci, int offset, int n)
    {
        double beta = 1 - 2/(double)(n + 1);
        
        double[] wagi = new double[n];
        
        for(int i = 0; i < n; i++)
        {
            wagi[i] = Math.pow(beta, (double)i);
        }
        
        double[] kopwar = new double[n];
        
        for(int i = 0; i < n; i++)
        {
            kopwar[i] = wartosci[i+offset];
        }
        
        return WMA(kopwar,wagi);
    }
    
    public double[] RSI(double[] wartosci, int n)
    {
        int len = wartosci.length;
        double[] rsi = new double[len];
        double[] spadki = new double[len];
        double[] wzrosty = new double[len];
        
        spadki[0] = 0;
        wzrosty[0] = 0;
        
        for(int i = 1; i < len; i++)
        {
            if(wartosci[i] > wartosci[i-1])
            {
                spadki[i] = 0;
                wzrosty[i] = wartosci[i] - wartosci[i - 1];
            }
            else
            {
                spadki[i] = wartosci[i - 1] - wartosci[i];
                wzrosty[i] =  0;
            }
        }
        
        for(int i = 0; i < len; i++)
        {
            int wn = i+1;
            if(wn > n) wn = n;
            
            int offset = 0;
            if(i+1>n) offset = i-n+1;
            
            double srednia_spadkow = EMA(spadki,offset,wn);
            double srednia_wzrostow = EMA(wzrosty,offset,wn);

            
            if(srednia_spadkow == 0)
            {
                rsi[i] = 100;
            }
            else
            {
                double RS = srednia_wzrostow/srednia_spadkow;
                rsi[i] = 100 - 100/(1  + RS);
            }    
        }
        
        return rsi;
    }
    
    public double[] CCI(double[] maks, double[] mini, double[] zamk)
    {
        if(maks.length != mini.length || mini.length != zamk.length) return null;
        
        
        int len = maks.length;
        int n = 20;
        double[] cci = new double[len];
        
        for(int i = 0; i < len; i++)
        {
            int wn = i+1;
            if(wn > n) wn = n;
            
            int offset = 0;
            if(i+1>n) offset = n-i+1;
            
            
            
            double[] kopwar = new double[wn];
            
            for(int a = 0; a < wn; a++)
            {
                double tp = (maks[a+offset] + mini[a+offset] + zamk[a+offset])/3;
                kopwar[i] = tp;
            }
            
            double odchylenie = Math.abs(kopwar[kopwar.length-1] - kopwar[(int)Math.floor(kopwar.length/2)]);
             
            if(odchylenie == 0) cci[i] = 0;
            else cci[i] = 1/0.015 * (kopwar[kopwar.length-1] - SMA(kopwar))/odchylenie;
        }
        
        return cci;
    }
    
    public double[] ROC(double[] wartosci, int n)
    {
        double[] roc = new double[wartosci.length];
        for(int i = 0; i < wartosci.length; i++)
        {
            if(i<n) roc[i] = 0;
            else
            {
                roc[i] = (wartosci[i] - wartosci[i-n])/wartosci[i-n];
            }
        }
        return roc;
    }
    
    public double[] Williams(double[] wartosci, int n)
    {

        int len = wartosci.length;
        double[] will = new double[len];
        
        for(int i = 0; i < len; i++)
        {
            if(i<n) will[i] = 0;
            else
            {
               double maxv = max_vec(wartosci,i-n,i);
               double minv = min_vec(wartosci,i-n,i);
               if(maxv == minv) will[i] = 0;
               else
               {
                    will[i] = 100*(wartosci[i] - maxv)/(maxv - minv);
               }
            }
        }
        
        return will;
    }
    
    public PivotResult[] PivotPoint(double[] maks, double[] mini, double[] zamk)
    {
        if(maks.length != mini.length || mini.length != zamk.length) return null;
        
        
        int len = maks.length;
        PivotResult[] pivot = new PivotResult[len];
        
        for(int i = 0; i < len; i++)
        {
            double pp = (mini[i] + zamk[i] + maks[i])/3;
            
            double r1 = 2*pp - mini[i];
            double s1 = 2*pp - maks[i];
            double r2 = pp + (r1 - s1);
            double s2 = pp - (r1 - s1);
            
            pivot[i] = new PivotResult(pp,r1,s1,r2,s2);
        }
        
        return pivot;
    }
    
    public double[] ATR(double[] maks, double[] mini, double[] zamk)
    {
        if(maks.length != mini.length || mini.length != zamk.length) return null;
        
        int len = maks.length;
        double[] atr = new double[len];
        
        atr[0] = 0;
        
        for(int i = 1; i < len; i++)
        {
            double[] m = new double[3];
            m[0] = maks[i] - mini[i];
            m[1] = Math.abs(maks[i] - zamk[i-1]);
            m[2] = Math.abs(mini[i] - zamk[i-1]);
            
            double tmp  = Math.max(m[0], m[1]);
            atr[i] = Math.max(tmp, m[2]);
        }
        
        return atr;
    }
    
    public Trend obliczTrend(double[] wartosci)
    {
        double a0 = 0;
        double a1 = 0;
        
        int len = wartosci.length;
        
        double sum_w = 0;
        double sum_t = 0;
        
        double sr_w = 0;
        double sr_t = 0;
        
        double sum_ty = 0;
        double sum_tt = 0;
        
        for(int i = 0; i < len; i++)
        {
            sum_t += (i+1);
            sum_w += wartosci[i];
            
            sum_tt = (i+1)*(i+1);
            sum_ty = (i+1)*wartosci[i];
        }
        
        sr_w = sum_w / len;
        sr_t = sum_t / len;
        
        a1 = (sum_ty - len * sr_w * sr_t)/(sum_tt - len * sr_t * sr_t);
        a0 = sr_w - a1 * sr_t;
        
        Trend trend = new Trend(a1,a0);
        return trend;
    }
}

