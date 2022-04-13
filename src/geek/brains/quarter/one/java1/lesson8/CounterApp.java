package geek.brains.quarter.one.java1.lesson8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CounterApp extends JFrame {
    private int counter = 0;
    private Map<JButton, Supplier<Integer>> buttonActions = new HashMap<>();

    public CounterApp() {

        setTitle("CounterApp window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 500, 250, 120);

        Font font = new Font("Arial", Font.BOLD, 30);

        JLabel counterView = new JLabel(String.valueOf(counter));
        counterView.setHorizontalAlignment(SwingConstants.CENTER);
        counterView.setFont(font);
        add(counterView, BorderLayout.CENTER);

        JButton incrementButton = new JButton(">");
        JButton decrementButton = new JButton("<");
        JButton resetButton = new JButton("reset");
        JButton tenIncButton = new JButton("+10");
        JButton tenDecButton = new JButton("-10");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(tenDecButton);
        panel.add(resetButton);
        panel.add(tenIncButton);

        add(incrementButton, BorderLayout.LINE_END);
        add(decrementButton, BorderLayout.LINE_START);
        add(panel, BorderLayout.SOUTH);

        buttonActions.put(incrementButton, ()-> counter++);
        buttonActions.put(decrementButton, () -> counter--);
        buttonActions.put(tenIncButton, () -> counter += 10);
        buttonActions.put(tenDecButton, () -> counter -= 10);
        buttonActions.put(resetButton, () -> counter = 0);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Supplier<Integer> action = buttonActions.get(e.getSource());
                if(action != null){
                    counterView.setText(String.valueOf(action.get()));
                }
            }
        };

        decrementButton.addActionListener(actionListener);
        incrementButton.addActionListener(actionListener);
        tenIncButton.addActionListener(actionListener);
        tenDecButton.addActionListener(actionListener);
        resetButton.addActionListener(actionListener);

        setVisible(true);
    }

    public static void main(String[] args) {
        new CounterApp();
    }
}
