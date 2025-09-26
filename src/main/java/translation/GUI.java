package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();

            JPanel countryPanel = new JPanel(new BorderLayout());

            String[] countryCodes = translator.getCountryCodes().toArray(new String[0]);
            String[] countries = new String[countryCodes.length];

            for(int i = 0; i < countryCodes.length; i++) {
                countries[i] = countryConverter.fromCountryCode(countryCodes[i]);
            }

            JList<String> list = new JList<>(countries);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new Dimension(150, 100));
            countryPanel.add(scrollPane, BorderLayout.CENTER);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            JComboBox<String> languageComboBox = new JComboBox<>();

            String[] languageCodes = translator.getLanguageCodes().toArray(new String[0]);
            String[] languages = new String[languageCodes.length];

            for(int i = 0; i < languageCodes.length; i++) {
                languages[i] = languageConverter.fromLanguageCode(languageCodes[i]);
            }

            for(int i = 0; i < languages.length; i++) {
                languageComboBox.addItem(languages[i]);
            }


            languageComboBox.setPreferredSize(new Dimension(100,18));
            languagePanel.add(languageComboBox);

            JPanel resultPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            resultPanel.add(resultLabelText);
            resultPanel.add(resultLabel);

            // adding listener for when the user clicks the submit button

            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        String selectedCountryName = list.getSelectedValue();
                        String selectedCountryCode = countryConverter.fromCountry(selectedCountryName);

                        String selectedLanguage = (String) languageComboBox.getSelectedItem();
                        String selectedLanguageCode = languageConverter.fromLanguage(selectedLanguage);

                        if (selectedCountryCode != null && selectedLanguage != null) {
                            JSONTranslation translator = new JSONTranslation();
                            String translation = translator.getCountryNameTranslation(selectedCountryCode, selectedLanguageCode);

                            //JLabel translationLabel = new JLabel(translation);
                            resultLabel.setText(translation);
                        };
                    }

            languageComboBox.addActionListener(l -> {
                        String selectedCountryCode = list.getSelectedValue();
                        String selectedLanguage = (String) languageComboBox.getSelectedItem();

                        if (selectedLanguage != null && selectedCountryCode != null) {
                            JSONTranslation translator = new JSONTranslation();
                            String translation = translator.getCountryNameTranslation(selectedCountryCode, selectedLanguage);

                            resultLabel.setText(translation);
                        }
                    }
                    );

                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
