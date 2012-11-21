package zpi.asystent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AboutFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AboutFrame dialog = new AboutFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutFrame() {
		setTitle("O programie");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblAsystentInwestoraGiedowego = new JLabel("Asystent Inwestora Giełdowego v1.0");
			lblAsystentInwestoraGiedowego.setHorizontalAlignment(SwingConstants.CENTER);
			lblAsystentInwestoraGiedowego.setFont(new Font("Dialog", Font.BOLD, 16));
			contentPanel.add(lblAsystentInwestoraGiedowego, BorderLayout.NORTH);
		}
		
		JTextPane desc = new JTextPane();
		desc.setFont(new Font("Dialog", Font.PLAIN, 12));
		desc.setContentType("text/html");
		desc.setEditable(false);
		desc.setText("<p align=\"justify\">Celem aplikacji jest pobieranie w czasie rzeczywistym danych z największych giełd światowych. Dane " +
				"te są prezenetowane w przystępnej dla użytkownika formie za pomocą niniejszego GUI.</p><p>Autorzy:</p>" +
				"<ul><li><strong>Serwer</strong> - R. Dziadusz, M. Gruszczyński, D. Kijowski</li>" +
				"<li><strong>GUI</strong> - P. Cisek, M. Gruszczyński, M. Kowal</li><" +
				"li><strong>Statystyka</strong> - K. Filiński, T. Ibrom, Ł. Legenc</li></ul>");
		desc.setBackground(new Color(238,238,238));
		contentPanel.add(desc, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setHorizontalAlignment(SwingConstants.TRAILING);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
