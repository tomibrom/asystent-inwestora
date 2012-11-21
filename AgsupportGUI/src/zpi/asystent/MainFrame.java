package zpi.asystent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import com.agsupport.core.service.communication.*;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainFrame extends JFrame implements Observer {

	private final String lblSMAcap = "årednia krokowa prosta";
	private final String lblWMAcap = "årednia krokowa waøona";
	private final String lblEMAcap = "årednia krokowa wykladnicza";
	
	private ListOfStockIndex tempStockIndexes;
	
	private JPanel contentPane;
	private JTextField fromField;
	private JTextField toField;
	final JList list;
	static JFreeChart chart;
	static XYPlot plot;
	static JFreeChart trendchart;
	static XYPlot trendplot;
	JComboBox comboBox;
	
	
	JLabel lblSMA;
	JLabel lblWMA;
	JLabel lblEMA;
	
	
	final ControllerInterface controller;

	/**
	 * Create the frame.
	 */
	public MainFrame(final ControllerInterface controller) {
		
		this.controller = controller;
		
		setTitle("Asystent Inwestora Gie≈Çdowego");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmZakocz = new JMenuItem("Zako≈Ñcz");
		mntmZakocz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Pobierz gie≈Çdy");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.getStockMarketList();
				comboBox.setEnabled(false);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmPobierzInstrPochodne = new JMenuItem("Pobierz instr. pochodne");
		mntmPobierzInstrPochodne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.getDerivativeList();
				comboBox.setEnabled(true);
			}
		});
		mnNewMenu.add(mntmPobierzInstrPochodne);
		mnNewMenu.add(mntmZakocz);
		
		JMenu mnNewMenu_widok = new JMenu("Wskaüniki");
		
		JMenuItem mntmRSI = new JMenuItem("RSI z 5 dni");
		mntmRSI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Statistics stat = new Statistics();
				TimeSeries series1 = new TimeSeries("RSI", Minute.class);
	
				
				List<JSONStockIndex> list = stat.sortStockIndexes(tempStockIndexes);
				List<List<Double>> days = stat.groupByDay(list);
				double[] vals = stat.extractCloseVals(days);
				double[] rsi = stat.RSI(vals, 5);
				

				for(int i =0; i <  rsi.length; i++) {
					Date tt = (Date)list.get(0).getDateOfAdd().clone();
					tt.setTime(tt.getTime()+i*86400000);
					series1.add(new Minute(tt), rsi[i]);

				}
				
				trendplot.setDataset(new TimeSeriesCollection(series1));
				

				
			}
		});
		
		mnNewMenu_widok.add(mntmRSI);
		
		JMenuItem mntmCCI = new JMenuItem("CCI");
		mntmCCI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Statistics stat = new Statistics();
				TimeSeries series1 = new TimeSeries("CCI", Minute.class);
	
				
				List<JSONStockIndex> list = stat.sortStockIndexes(tempStockIndexes);
				List<List<Double>> days = stat.groupByDay(list);
				double[] vals = stat.extractCloseVals(days);
				double[] mins = stat.extractMinVals(days);
				double[] maxs = stat.extractMaxVals(days);
				double[] cci = stat.CCI(maxs, mins, vals);
				

				for(int i =0; i <  cci.length; i++) {
					Date tt = (Date)list.get(0).getDateOfAdd().clone();
					tt.setTime(tt.getTime()+i*86400000);
					series1.add(new Minute(tt), cci[i]);

				}
				
				trendplot.setDataset(new TimeSeriesCollection(series1));
				

				
			}
		});
		
		mnNewMenu_widok.add(mntmCCI);
		
		JMenuItem mntmROC = new JMenuItem("ROC z 5 dni");
		mntmROC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Statistics stat = new Statistics();
				TimeSeries series1 = new TimeSeries("ROC", Minute.class);
	
				
				List<JSONStockIndex> list = stat.sortStockIndexes(tempStockIndexes);
				List<List<Double>> days = stat.groupByDay(list);
				double[] vals = stat.extractCloseVals(days);
				
				double[] roc = stat.ROC(vals,5);
				

				for(int i =0; i <  roc.length; i++) {
					Date tt = (Date)list.get(0).getDateOfAdd().clone();
					tt.setTime(tt.getTime()+i*86400000);
					series1.add(new Minute(tt), roc[i]);

				}
				
				trendplot.setDataset(new TimeSeriesCollection(series1));
				

				
			}
		});
		
		mnNewMenu_widok.add(mntmROC);
		
		JMenuItem mntmWilliams = new JMenuItem("Williams %R z 10 dni");
		mntmWilliams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Statistics stat = new Statistics();
				TimeSeries series1 = new TimeSeries("Williams %R", Minute.class);
	
				
				List<JSONStockIndex> list = stat.sortStockIndexes(tempStockIndexes);
				List<List<Double>> days = stat.groupByDay(list);
				double[] vals = stat.extractCloseVals(days);
				
				double[] will = stat.Williams(vals,10);
				

				for(int i =0; i <  will.length; i++) {
					Date tt = (Date)list.get(0).getDateOfAdd().clone();
					tt.setTime(tt.getTime()+i*86400000);
					series1.add(new Minute(tt), will[i]);

				}
				
				trendplot.setDataset(new TimeSeriesCollection(series1));
				

				
			}
		});
		
		mnNewMenu_widok.add(mntmWilliams);
		
		JMenuItem mntmWilliams5 = new JMenuItem("Williams %R z 5 dni");
		mntmWilliams5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Statistics stat = new Statistics();
				TimeSeries series1 = new TimeSeries("Williams %R", Minute.class);
	
				
				List<JSONStockIndex> list = stat.sortStockIndexes(tempStockIndexes);
				List<List<Double>> days = stat.groupByDay(list);
				double[] vals = stat.extractCloseVals(days);
				
				double[] will = stat.Williams(vals,5);
				

				for(int i =0; i <  will.length; i++) {
					Date tt = (Date)list.get(0).getDateOfAdd().clone();
					tt.setTime(tt.getTime()+i*86400000);
					series1.add(new Minute(tt), will[i]);

				}
				
				trendplot.setDataset(new TimeSeriesCollection(series1));
				

				
			}
		});
		
		mnNewMenu_widok.add(mntmWilliams5);
		
		JMenuItem mntmPivot = new JMenuItem("PivotPoint");
		mntmPivot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				
				Statistics stat = new Statistics();
				TimeSeries series1 = new TimeSeries("Pivot", Minute.class);
				TimeSeries series2 = new TimeSeries("R1", Minute.class);
				TimeSeries series3 = new TimeSeries("S1", Minute.class);
				TimeSeries series4 = new TimeSeries("Cena", Minute.class);
				
				for(JSONStockIndex si : tempStockIndexes.getStockIndexes() ) {
					series4.add(new Minute(si.getDateOfAdd()), si.getPrice());
					
				}
	
				
				List<JSONStockIndex> list = stat.sortStockIndexes(tempStockIndexes);
				List<List<Double>> days = stat.groupByDay(list);
				double[] vals = stat.extractCloseVals(days);
				double[] mins = stat.extractMinVals(days);
				double[] maxs = stat.extractMaxVals(days);
				
				PivotResult[] pivot = stat.PivotPoint(maxs, mins, vals);
				

				for(int i =0; i <  pivot.length; i++) {
					Date tt = (Date)list.get(0).getDateOfAdd().clone();
					tt.setTime(tt.getTime()+i*86400000+43200000);
					series1.add(new Minute(tt), pivot[i].getPivot());

				}
				
				for(int i =0; i <  pivot.length; i++) {
					Date tt = (Date)list.get(0).getDateOfAdd().clone();
					tt.setTime(tt.getTime()+i*86400000+43200000);
					series2.add(new Minute(tt), pivot[i].getR1());

				}
				
				for(int i =0; i <  pivot.length; i++) {
					Date tt = (Date)list.get(0).getDateOfAdd().clone();
					tt.setTime(tt.getTime()+i*86400000+43200000);
					series3.add(new Minute(tt), pivot[i].getS1());

				}
				
				TimeSeriesCollection tsc = new TimeSeriesCollection(series4);
				tsc.addSeries(series1);
				tsc.addSeries(series2);
				tsc.addSeries(series3);
				
				trendplot.setDataset(tsc);
				

				
			}
		});
		
		mnNewMenu_widok.add(mntmPivot);
		
		JMenuItem mntmATR = new JMenuItem("ATR");
		mntmATR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				Statistics stat = new Statistics();
				TimeSeries series1 = new TimeSeries("ATR", Minute.class);

				
				List<JSONStockIndex> list = stat.sortStockIndexes(tempStockIndexes);
				List<List<Double>> days = stat.groupByDay(list);
				double[] vals = stat.extractCloseVals(days);
				double[] mins = stat.extractMinVals(days);
				double[] maxs = stat.extractMaxVals(days);
				
				double[] atr = stat.ATR(maxs, mins, vals);
				

				for(int i =0; i <  atr.length; i++) {
					Date tt = (Date)list.get(0).getDateOfAdd().clone();
					tt.setTime(tt.getTime()+i*86400000);
					series1.add(new Minute(tt), atr[i]);

				}
				
				
				
				TimeSeriesCollection tsc = new TimeSeriesCollection(series1);
				
				trendplot.setDataset(tsc);
				

				
			}
		});
		
		mnNewMenu_widok.add(mntmATR);
		
		menuBar.add(mnNewMenu_widok);
		
		JMenu mnNewMenu_1 = new JMenu("Pomoc");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmOProgramie = new JMenuItem("O programie");
		mntmOProgramie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        AboutFrame about = new AboutFrame();
		        about.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmOProgramie);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(list.getSelectedValue() instanceof JSONDerivative) {
					if(!list.getValueIsAdjusting())
						controller.getDerivativeExpiredDate((JSONDerivative)list.getSelectedValue());
				}
			}
		});

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//contentPane.add(list, BorderLayout.WEST);
		
		JScrollPane listContainer = new JScrollPane(list);
		listContainer.setBackground(Color.WHITE);
		listContainer.setPreferredSize(new Dimension(170, 0));
		contentPane.add(listContainer, BorderLayout.WEST);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout());
		
		JPanel factorsPanel = new JPanel();
		centerPanel.add(factorsPanel, BorderLayout.SOUTH);
		
		
		lblSMA = new JLabel(lblSMAcap + "0");
		factorsPanel.add(lblSMA);
		
		lblWMA = new JLabel(lblWMAcap + "0");
		factorsPanel.add(lblWMA);
		
		lblEMA = new JLabel(lblEMAcap + "0");
		factorsPanel.add(lblEMA);
		
		
		
		
		JPanel rangePanel = new JPanel();
		centerPanel.add(rangePanel, BorderLayout.NORTH);
		rangePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Zakres Pobranych Danych");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		rangePanel.add(lblNewLabel_1, BorderLayout.NORTH);
		
		JPanel periodPanel = new JPanel();
		rangePanel.add(periodPanel, BorderLayout.SOUTH);
		periodPanel.setPreferredSize(new Dimension(200, 35));
		periodPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(66dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(52dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(52dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblOd = new JLabel("Od");
		periodPanel.add(lblOd, "2, 2, right, default");
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date dateNow = new Date();
		dateNow.setMinutes(0);
		dateNow.setHours(0);
		StringBuilder DateFromString = new StringBuilder( format.format( dateNow ) );
		dateNow.setTime(dateNow.getTime() + 1*24*60*60*1000);
		StringBuilder DateToString = new StringBuilder( format.format( dateNow ) );
		
		fromField = new JTextField();
		periodPanel.add(fromField, "4, 2, fill, default");
		fromField.setColumns(10);
		fromField.setText(DateFromString.toString());
		
		JLabel lblDo = new JLabel("Do");
		periodPanel.add(lblDo, "6, 2, right, default");
		
		toField = new JTextField();
		periodPanel.add(toField, "8, 2, fill, default");
		toField.setColumns(10);
		toField.setText(DateToString.toString());
		
		JButton btnPobierz = new JButton("Pobierz");
		btnPobierz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object sv = list.getSelectedValue();
				
				if(sv instanceof JSONStockMarket) {
					controller.getStockIndexList((JSONStockMarket)list
						.getSelectedValue(), fromField.getText(), toField.getText());
				}
				else if(sv instanceof JSONDerivative) {
					controller.getDerivativeValuesList((JSONDerivative)list.getSelectedValue(), fromField.getText(), 
							toField.getText(), comboBox.getSelectedItem().toString());
				}
					
			}
		});
		
		JLabel lblWygasa = new JLabel("Wygasa");
		periodPanel.add(lblWygasa, "10, 2, right, default");
		
		comboBox = new JComboBox();
		comboBox.setEnabled(false);
		comboBox.setBackground(Color.WHITE);
		comboBox.setPreferredSize(new Dimension(200, 20));
		periodPanel.add(comboBox, "12, 2, fill, default");
		periodPanel.add(btnPobierz, "14, 2");
		
		JFreeChart chart = createChart("");   
		chart.setBackgroundPaint(new java.awt.Color(238, 238, 238));
        ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
        centerPanel.add(panel);
        
        
        JFreeChart pchart = ChartFactory.createTimeSeriesChart(   
                "",    
                "Okres",    
                "Wartosc",   
                null,    
                true,   
                true,   
                false   
            );   
		chart.setBackgroundPaint(new java.awt.Color(238, 238, 238));
        ChartPanel ppanel = new ChartPanel(pchart, true, true, true, false, true);
        ppanel.setPreferredSize(new Dimension(200,200));
        centerPanel.add(ppanel,BorderLayout.SOUTH);
        

        trendchart = pchart;
        trendplot = pchart.getXYPlot();
        
        NumberAxis rangeAxis1 = (NumberAxis) trendplot.getRangeAxis();   
        rangeAxis1.setLowerMargin(0.40);  // to leave room for volume bars   
        DecimalFormat fformat = new DecimalFormat("00.00");   
        rangeAxis1.setNumberFormatOverride(fformat);   
   
        XYItemRenderer renderer1 = trendplot.getRenderer();   
        renderer1.setToolTipGenerator(   
            new StandardXYToolTipGenerator(   
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,   
                new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00")   
            )   
        );   
	}
	
	
	private static JFreeChart createChart(String name) {   
	       
        //String title = "Gie≈Çda jaka≈õ tam co nie";   
        chart = ChartFactory.createTimeSeriesChart(   
            name,    
            "Okres",    
            "Cena",   
            null,    
            true,   
            true,   
            false   
        );   
        plot = chart.getXYPlot();   
        NumberAxis rangeAxis1 = (NumberAxis) plot.getRangeAxis();   
        rangeAxis1.setLowerMargin(0.40);  // to leave room for volume bars   
        DecimalFormat format = new DecimalFormat("00.00");   
        rangeAxis1.setNumberFormatOverride(format);   
   
        XYItemRenderer renderer1 = plot.getRenderer();   
        renderer1.setToolTipGenerator(   
            new StandardXYToolTipGenerator(   
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,   
                new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00")   
            )   
        );   
        return chart;   
   
    }


	@Override
	public void setStockList(final ListOfStockMarket stockMarkets) {
		
		list.setModel(new AbstractListModel() {
			
			List<JSONStockMarket> values = stockMarkets.getStockMarkets();
			
			public int getSize() {
				return values.size();
			}
			public Object getElementAt(int index) {
				return values.get(index);
			}
		});

	}
	
	public void updateStockStatistics(ListOfStockIndex indexes)
	{
		tempStockIndexes = indexes;
		plot.clearRangeMarkers();
		
		Statistics stat = new Statistics();
		
		double[] values = stat.plainValuesFromStockIndexes(indexes);
		
		Double vSMA = stat.SMA(values);
		DecimalFormat df = new DecimalFormat("#.###");
		lblSMA.setText(lblSMAcap + df.format(vSMA));	
		
		ValueMarker marker = new ValueMarker(vSMA);
		marker.setPaint(Color.orange);
		marker.setLabel(lblSMAcap);
		marker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
		marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
		plot.addRangeMarker(marker);
		
		Double vWMA = stat.WMA(values);
		lblWMA.setText(lblWMAcap + df.format(vWMA));	
		
		marker = new ValueMarker(vWMA);
		marker.setPaint(Color.green);
		marker.setLabel(lblWMAcap);
		marker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
		marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
		plot.addRangeMarker(marker);
		
		Double vEMA = stat.EMA(values);
		lblEMA.setText(lblEMAcap + df.format(vEMA));
		
		marker = new ValueMarker(vEMA);
		marker.setPaint(Color.blue);
		marker.setLabel(lblEMAcap);
		marker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
		marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
		plot.addRangeMarker(marker);
		
		TimeSeriesCollection tsc = (TimeSeriesCollection)plot.getDataset();
		
		TimeSeries series2 = new TimeSeries("Trend", Minute.class);
		
		List<JSONStockIndex> sortedlist = stat.sortStockIndexes(indexes);
		
		int numvals = sortedlist.size()/7;
		int counter = 0;
		double[] vals = new double[numvals];
		int num = sortedlist.size();
		for(int i = 0; i < num; i++)
		{
			if(counter == numvals)
			{
				double vema = stat.EMA(vals);
				series2.add(new Minute(sortedlist.get(i-1-numvals/2).getDateOfAdd()), vema);
				counter = 0;
				
				if(num - i <= numvals )
				{
					vals = new double[num - i ];
					for(int a = 0; a < num - i ; a++)
					{
						vals[a] = sortedlist.get(i+a).getPrice();
					}
					
					vema = stat.EMA(vals);
					series2.add(new Minute(sortedlist.get(sortedlist.size()-1).getDateOfAdd()), vema);
					counter = 0;
					break;
				}
			}
			
			
			vals[counter] = sortedlist.get(i).getPrice();
			counter++;
		}
		
		tsc.addSeries(series2);
		
	}

	@Override
	public void updateStockIndexes(ListOfStockIndex indexes) {
		
		
		chart.setTitle(list.getSelectedValue().toString());
			
		TimeSeries series1 = new TimeSeries("Cena", Minute.class);

		for(JSONStockIndex si : indexes.getStockIndexes() ) {
			series1.add(new Minute(si.getDateOfAdd()), si.getPrice());
			
		}
		
		plot.setDataset(new TimeSeriesCollection(series1));
		
		updateStockStatistics(indexes);
	}

	@Override
	public void updateDerivativeList(final ListOfDerivative derivatives) {
			
		list.setModel(new AbstractListModel() {
			
			List<JSONDerivative> values = derivatives.getDerivatives();
			
			public int getSize() {
				return values.size();
			}
			public Object getElementAt(int index) {
				return values.get(index);
			}
		});		
	}

	@Override
	public void updateExpiredDateList(ListOfDate expiredDates) {
		
		comboBox.setModel(new DefaultComboBoxModel(expiredDates.getDates().toArray()));
		
	}

	@Override
	public void updateDerivativeValues(ListOfDerivativeValue derivativeValues) {
		chart.setTitle(list.getSelectedValue().toString());
		
		TimeSeries series1 = new TimeSeries("Cena", Minute.class);
		
		for(JSONDerivativeValue si : derivativeValues.getDerivativeValues() ) {
			series1.add(new Minute(si.getDateOfAdd()), si.getPrice());
		}
		
		plot.setDataset(new TimeSeriesCollection(series1));
		
		updateStockStatistics(Statistics.fromDerivativeValue(derivativeValues));
		
	}
}
