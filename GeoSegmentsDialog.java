package homework1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A JDailog GUI for choosing a GeoSegemnt and adding it to the route shown
 * by RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// the RouteDirectionsGUI that this JDialog was opened from
	private RouteFormatterGUI parent;
	
	// a control contained in this 
	private JList<GeoSegment> lstSegments;
	
	/**
	 * Creates a new GeoSegmentsDialog JDialog.
	 * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame
	 * 			owner and parent pnlParent
	 */
	public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
		// create a modal JDialog with an owner Frame (a modal window
		// in one that doesn't allow other windows to be active at the
		// same time).
		super(owner, "Please choose a GeoSegment", true);
		
		this.parent = pnlParent;

		// Layout manager
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Label
		JLabel lblSelect = new JLabel("Select a GeoSegment:");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10, 10, 5, 10);
		this.add(lblSelect, c);

		// Segment list
		lstSegments = new JList<>(ExampleGeoSegments.segments);
		lstSegments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Scroll pane for list
		JScrollPane scrollPane = new JScrollPane(lstSegments);
		scrollPane.setPreferredSize(new Dimension(300, 150));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0, 10, 10, 10);
		this.add(scrollPane, c);

		// Add Button
		JButton btnAdd = getJButton();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.insets = new Insets(0, 10, 10, 5);
		c.anchor = GridBagConstraints.EAST;
		this.add(btnAdd, c);

		// Cancel Button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> setVisible(false));
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 5, 10, 10);
		c.anchor = GridBagConstraints.WEST;
		this.add(btnCancel, c);
	}

	private JButton getJButton() {
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeoSegment selected = lstSegments.getSelectedValue();
				if (selected == null) {
					JOptionPane.showMessageDialog(
							GeoSegmentsDialog.this,
							"Please select a segment first.",
							"No segment selected",
							JOptionPane.WARNING_MESSAGE
					);
					return;
				}

				try {
					parent.addSegment(selected);
					setVisible(false); // hide if success
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(
							GeoSegmentsDialog.this,
							"Selected segment must start at the current route end.",
							"Invalid segment",
							JOptionPane.ERROR_MESSAGE
					);
				}
			}
		});
		return btnAdd;
	}
}
