package ee.mathiaskivi.dond;

import java.text.DecimalFormat;
import java.util.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private AnchorPane scene_main_layout_0, scene_main_layout_1, scene_main_layout_2, scene_main_layout_3, scene_main_layout_4;

    @FXML
    private Label scene_main_amount_1, scene_main_amount_2, scene_main_amount_3, scene_main_amount_4, scene_main_amount_5, scene_main_amount_6, scene_main_amount_7, scene_main_amount_8, scene_main_amount_9, scene_main_amount_10, scene_main_amount_11, scene_main_amount_12, scene_main_amount_13, scene_main_amount_14, scene_main_amount_15, scene_main_amount_16, scene_main_amount_17, scene_main_amount_18, scene_main_amount_19, scene_main_amount_20, scene_main_amount_21, scene_main_amount_22, scene_main_amount_23, scene_main_amount_24, scene_main_amount_25, scene_main_amount_26, scene_main_instruction_1, scene_main_offer_1, scene_main_result_1, scene_main_result_2;

    @FXML
    private Button scene_main_briefcase_1, scene_main_briefcase_2, scene_main_briefcase_3, scene_main_briefcase_4, scene_main_briefcase_5, scene_main_briefcase_6, scene_main_briefcase_7, scene_main_briefcase_8, scene_main_briefcase_9, scene_main_briefcase_10, scene_main_briefcase_11, scene_main_briefcase_12, scene_main_briefcase_13, scene_main_briefcase_14, scene_main_briefcase_15, scene_main_briefcase_16, scene_main_briefcase_17, scene_main_briefcase_18, scene_main_briefcase_19, scene_main_briefcase_20, scene_main_briefcase_21, scene_main_briefcase_22, scene_main_briefcase_23, scene_main_briefcase_24, scene_main_briefcase_25, scene_main_briefcase_26, scene_main_player_1, scene_main_content_1, scene_main_offer_2, scene_main_offer_3, scene_main_result_3, scene_main_result_4;

    private final List<Double> amounts = new ArrayList<>();
    private final SortedMap<Integer, Briefcase> briefcases = new TreeMap<>();

    private int round;
    private int remaining;
    private double offer;

    private Briefcase briefcase;

    @FXML
    public void initialize() {
        start();
    }

    @FXML
    public void onBriefcaseClicked(MouseEvent mouseEvent) {
        int number = Integer.parseInt(((Button) mouseEvent.getSource()).getText());
        Briefcase briefcase = briefcases.remove(number);

        scene_main_layout_1.lookup("#scene_main_briefcase_" + number).setVisible(false);
        scene_main_layout_1.requestFocus();
        scene_main_layout_1.setMouseTransparent(true);

        if (this.briefcase == null) {
            this.briefcase = briefcase;

            scene_main_instruction_1.setText(remaining > 1 ? "Please select " + remaining + " briefcases." : "Please select " + remaining + " briefcase.");
            scene_main_player_1.setText(Integer.toString(briefcase.getNumber()));

            scene_main_layout_1.setMouseTransparent(false);
            return;
        }

        scene_main_content_1.setText(getDecimalFormat(briefcase.getAmount()) + "€");
        scene_main_layout_2.setVisible(true);

        for (int amount = 1; amount < 27; amount++) {
            Label label = (Label) scene_main_layout_0.lookup("#scene_main_amount_" + amount);

            if (Double.parseDouble(label.getText().replace("€", "")) == briefcase.getAmount()) {
                label.setVisible(false);
                break;
            }
        }

        remaining--;
        if (remaining <= 0) {
            scene_main_instruction_1.setText("");

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    scene_main_layout_1.requestFocus();

                    scene_main_layout_2.setVisible(false);
                    scene_main_layout_3.setMouseTransparent(true);
                    scene_main_layout_3.setVisible(true);

                    Platform.runLater(() -> scene_main_offer_1.setText(""));

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> scene_main_offer_1.setText(getDecimalFormat(getOffer()) + "€"));

                            scene_main_layout_3.setMouseTransparent(false);
                        }
                    }, 1500);
                }
            }, 1500);
        } else {
            scene_main_instruction_1.setText(remaining > 1 ? "Please select " + remaining + " briefcases." : "Please select " + remaining + " briefcase.");

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    scene_main_layout_1.requestFocus();
                    scene_main_layout_1.setMouseTransparent(false);

                    scene_main_layout_2.setVisible(false);
                }
            }, 1500);
        }
    }

    @FXML
    public void onDealClicked() {
        scene_main_layout_3.setVisible(false);
        scene_main_layout_4.setVisible(true);

        scene_main_result_1.setText("You accepted the banker's offer:");
        scene_main_result_2.setText(getDecimalFormat(offer) + "€");
        scene_main_result_3.setText(getDecimalFormat(briefcase.getAmount()) + "€");
    }

    @FXML
    public void onNoDealClicked() {
        if (briefcases.size() <= 1) {
            scene_main_layout_3.setVisible(false);
            scene_main_layout_4.setVisible(true);

            scene_main_result_1.setText("You declined the banker's offer:");
            scene_main_result_2.setText(getDecimalFormat(offer) + "€");
            scene_main_result_3.setText(getDecimalFormat(briefcase.getAmount()) + "€");
            return;
        }

        scene_main_layout_1.setMouseTransparent(false);
        scene_main_layout_3.setVisible(false);

        round++;
        switch (round) {
            case 2 -> remaining = 5;
            case 3 -> remaining = 4;
            case 4 -> remaining = 3;
            case 5 -> remaining = 2;
            default -> remaining = 1;
        }

        scene_main_instruction_1.setText(remaining > 1 ? "Please select " + remaining + " briefcases." : "Please select " + remaining + " briefcase.");
    }

    @FXML
    public void onPlayAgainClicked() {
        start();
    }

    private void start() {
        for (int layout_id = 1; layout_id < 5; layout_id++) {
            if (layout_id == 1) {
                scene_main_layout_1.setMouseTransparent(false);
                scene_main_layout_1.setVisible(true);
                continue;
            }

            scene_main_layout_0.lookup("#scene_main_layout_" + layout_id).setMouseTransparent(false);
            scene_main_layout_0.lookup("#scene_main_layout_" + layout_id).setVisible(false);
        }

        for (int summa_id = 1; summa_id < 27; summa_id++) {
            scene_main_layout_1.lookup("#scene_main_amount_" + summa_id).setVisible(true);
        }

        for (int kohver_id = 1; kohver_id < 27; kohver_id++) {
            scene_main_layout_1.lookup("#scene_main_briefcase_" + kohver_id).setVisible(true);
        }

        scene_main_instruction_1.setText("Please select a briefcase.");
        scene_main_player_1.setText("?");

        amounts.addAll(Arrays.asList(0.01, 1.00, 5.00, 10.00, 25.00, 50.00, 75.00, 100.00, 200.00, 300.00, 400.00, 500.00, 750.00, 1000.00, 5000.00, 10000.00, 25000.00, 50000.00, 75000.00, 100000.00, 200000.00, 300000.00, 400000.00, 500000.00, 750000.00, 1000000.00));
        briefcases.clear();

        for (int number = 1; number < 27; number++) {
            double amount = amounts.remove(new Random().nextInt(amounts.size()));

            briefcases.put(number, new Briefcase(amount, number));
        }

        round = 1;
        remaining = 6;
        offer = 0;

        briefcase = null;

        scene_main_layout_1.requestFocus();
    }

    private String getDecimalFormat(double value) {
        if (value < 1) {
            return new DecimalFormat("0.00").format(value);
        }

        return new DecimalFormat("0").format(value);
    }

    private double getOffer() {
        // Formula: https://commcognition.blogspot.com/2007/06/deal-or-no-deal-bankers-formula.html

        double average = 0;
        double maximum = 0;
        double size = briefcases.size();

        for (Briefcase briefcase : briefcases.values()) {
            average = average + briefcase.getAmount();

            if (maximum <= briefcase.getAmount()) {
                maximum = briefcase.getAmount();
            }
        }
        average = average / size;

        offer = (int) (12275.3 + (0.748 * average) + (-2714.74 * size) + (-0.040 * maximum) + (0.0000006986 * Math.pow(average, 2)) + (32.623 * Math.pow(size, 2)));

        return offer;
    }

}