package com.example.taschenrechner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class CalculatorApp extends Application {
    private final int windowWidth = 550;
    private final int windowHeight = 800;
    public Text text = new Text("");
    public String operator1, operator2, operator3;


    @Override
    public void start(Stage stage) throws IOException {
        operator1 = operator2 = operator3 = "";
        Scene scene = new Scene(createMenu(), windowWidth, windowHeight);
        stage.setTitle("Taschenrechner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public StackPane createMenu() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);
        text.setFont(Font.font("arial", 35));
        HBox hbox = new HBox(text);
        hbox.setPadding(new Insets(75, 10, 10, 10));
        hbox.setAlignment(Pos.BASELINE_CENTER);
        grid.addRow(1, buttonCreator("C"), buttonCreator(" "), buttonCreator("%"), buttonCreator("/"));
        grid.addRow(2, buttonCreator("7"), buttonCreator("8"), buttonCreator("9"), buttonCreator("*"));
        grid.addRow(3, buttonCreator("4"), buttonCreator("5"), buttonCreator("6"), buttonCreator("-"));
        grid.addRow(4, buttonCreator("1"), buttonCreator("2"), buttonCreator("3"), buttonCreator("+"));
        grid.addRow(5, buttonCreator("0"), buttonCreator("Sqr"), buttonCreator("."), buttonCreator("="));
        StackPane screen = new StackPane(hbox, grid);
        return screen;
    }

    public Button buttonCreator(String text) {
        Button btnNumber = new Button();
        btnNumber.setMinSize(50, 50);
        btnNumber.setText(text);
        btnNumber.setOnAction(a -> display(btnNumber));
        return btnNumber;
    }

    public void display(Button button) {
        String buttonText = button.getText();

        if ((buttonText.charAt(0) >= '0' && buttonText.charAt(0) <= '9') || buttonText.charAt(0) == '.') {
            if (buttonText.charAt(0) == '.' && operator3.contains(".")) {
                System.err.println("Zu viele komma");
            } else if (operator2.contains(".")) {
                System.err.println("Zu viele komma");
            }
            else if (!operator2.equals("")) {
                operator3 = operator3 + buttonText;
            }
            else {
                if (buttonText.charAt(0) == '.' && operator1.contains(".")) {
                    System.err.println("Zu viele komma");
                } else {
                    operator1 = operator1 + buttonText;
                }
            }

            text.setText(operator1 + operator2 + operator3);
        }
        else if (buttonText.charAt(0) == 'C') {
            operator1 = operator2 = operator3 = "";
            text.setText(operator1 + operator2 + operator3);
        }

        else if (buttonText.charAt(0) == '%' && operator3.equals("")) {
            double summe;

            summe = (Double.parseDouble(operator1) * 0.01);

            text.setText(String.valueOf(summe));

            operator1 = Double.toString(summe);

            operator2 = operator3 = "";
        }
        else if (buttonText.equals("Sqr")) {
            double wurzel;

            wurzel = (Math.sqrt(Double.parseDouble(operator1)));

            text.setText(String.valueOf(wurzel));

            operator1 = Double.toString(wurzel);

            operator2 = operator3 = "";
        }
        else if (buttonText.charAt(0) == '=') {

            double summe;

            if (!operator1.equals("") && !operator2.equals("") && !operator3.equals("")) {
                // store the value in 1st
                if (operator2.equals("+")) {
                    summe = (Double.parseDouble(operator1) + Double.parseDouble(operator3));
                }
                else if (operator2.equals("-")) {
                    summe = (Double.parseDouble(operator1) - Double.parseDouble(operator3));
                }
                else if (operator2.equals("/")) {
                    if (!operator3.equals("0")) {
                        summe = (Double.parseDouble(operator1) / Double.parseDouble(operator3));
                    } else {
                        System.err.println("Durch null is nicht teilbar");
                        summe = Double.parseDouble(operator1);
                    }
                }
                else {
                    summe = (Double.parseDouble(operator1) * Double.parseDouble(operator3));
                }
            } else {
                System.err.println("Nicht genug operatoren");
                summe = Double.parseDouble(operator1);
            }

            text.setText(operator1 + operator2 + operator3 + "=" + summe);

            operator1 = Double.toString(summe);

            operator2 = operator3 = "";
        }
        else {
            if (operator2.equals("") || operator3.equals("")) {
                operator2 = buttonText;
            }
            else {
                double summe;

                if (operator2.equals("+")) {
                    summe = (Double.parseDouble(operator1) + Double.parseDouble(operator3));
                }
                else if (operator2.equals("-")) {
                    summe = (Double.parseDouble(operator1) - Double.parseDouble(operator3));
                }
                else if (operator2.equals("/")) {
                    if (!operator3.equals("0")) {
                        summe = (Double.parseDouble(operator1) / Double.parseDouble(operator3));
                    } else {
                        System.err.println("Durch null is nicht teilbar");
                        summe = Double.parseDouble(operator1);
                    }
                }
                else {
                    summe = (Double.parseDouble(operator1) * Double.parseDouble(operator3));
                }

                operator1 = Double.toString(summe);

                operator2 = buttonText;

                operator3 = "";
            }

            text.setText(operator1 + operator2 + operator3);
        }
//        switch (button.getText()) {
//            case "+":
//                operator = "+";
//                break;
//            case "-":
//                operator = "-";
//                break;
//            case "/":
//                operator = "/";
//                break;
//            case "*":
//                operator = "*";
//                break;
//            case "%":
//                break;
//            case "=":
//                break;
//        }
//        if (button.getText() == "=") {
//            calc.turnTextInNumbers(numbers1, numbers2, operator);
//            text.setText(String.valueOf(calc.ergebnis));
//        }
//        if (operator != null && !operator1) {
//            operator1 = true;
//            numbers1 = text.getText();
//            System.out.println("Nummer1:" + numbers1 + " Operator:"+ operator + " Nummer2:" + numbers2);
//        } else if (operator1) {
//            numbers2 = numbers2 + button.getText();
//            System.out.println("Nummer1:" + numbers1 + " Operator:"+ operator + " Nummer2:" + numbers2);
//        }
//        System.out.println(button.getText());
//        String saveText = text.getText();
//        if (button.getText() != "=") {
//            text.setText(saveText + button.getText());
    }
}