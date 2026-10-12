
import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DigitalClockAlarm extends JFrame {

    private final JLabel clockLabel = new JLabel("", SwingConstants.CENTER);
    private final JTextField alarmField = new JTextField("07:00", 5);
    private final JLabel statusLabel = new JLabel("No alarm set", SwingConstants.CENTER);
    private LocalTime alarmTime;
    private boolean alarmRang = false;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public DigitalClockAlarm() {
        setTitle("Digital Clock with Alarm");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        clockLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(clockLabel);

        JPanel alarmPanel = new JPanel();
        alarmPanel.add(new JLabel("Alarm (HH:mm):"));
        alarmPanel.add(alarmField);

        JButton setButton = new JButton("Set Alarm");
        JButton stopButton = new JButton("Stop Alarm");

        alarmPanel.add(setButton);
        alarmPanel.add(stopButton);
        add(alarmPanel);

        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(statusLabel);

        JLabel infoLabel = new JLabel(
                "Use 24-hour format, e.g. 18:30",
                SwingConstants.CENTER);
        add(infoLabel);

        setButton.addActionListener(e -> setAlarm());
        stopButton.addActionListener(e -> {
            alarmTime = null;
            alarmRang = false;
            statusLabel.setText("Alarm stopped");
        });

        Timer timer = new Timer(1000, e -> updateClock());
        timer.start();

        updateClock();
    }

    private void setAlarm() {
        try {
            alarmTime = LocalTime.parse(
                    alarmField.getText().trim(),
                    DateTimeFormatter.ofPattern("HH:mm"));

            alarmRang = false;
            statusLabel.setText("Alarm set for " + alarmTime);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid time in HH:mm format, e.g. 18:30");
        }
    }

    private void updateClock() {
        LocalTime now = LocalTime.now();
        clockLabel.setText(now.format(formatter));

        if (alarmTime != null
                && !alarmRang
                && now.getHour() == alarmTime.getHour()
                && now.getMinute() == alarmTime.getMinute()) {

            alarmRang = true;
            statusLabel.setText("ALARM RINGING!");

            Toolkit.getDefaultToolkit().beep();

            JOptionPane.showMessageDialog(
                    this,
                    "Alarm! The time is " + now.format(formatter),
                    "Alarm",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DigitalClockAlarm app = new DigitalClockAlarm();
            app.setVisible(true);
        });
    }
}
