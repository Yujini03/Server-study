package Mulsev.clientSwing2;

import javax.swing.SwingUtilities;

public class Client {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            new ConnectionGUI();
        });

	}

}
