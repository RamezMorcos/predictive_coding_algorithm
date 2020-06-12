package predictive;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.util.ArrayList;



import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
public class gui extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("predictive  ");
        TextField levels=new TextField();
        predictive_huffman n1=new predictive_huffman();
        Button button = new Button("  compress ");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Double> pixels=new ArrayList<>();
                int[][] p=new int[125][125];
                        p=ImageClass.readImage("D:\\cameraMan.jpg");
                int []r=new int[125*125];
                int counter=0;
                for(int i=0;i<125;i++){
                    for(int j=0;j<125;j++){
                        r[counter]=p[i][j];
                        System.out.println(r[counter]);
                        counter++;
                    }
                }
                String x=levels.getText();

                try {
                    n1.compress(r,Integer.parseInt(x));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button2 = new Button(" decompress ");
        ImageView iv1 = new ImageView();
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    n1.decompress();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }

        });
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(levels);

        HBox layout1 = new HBox(10);
        layout1.setPadding(new Insets(20, 20, 20, 20));
        layout1.getChildren().addAll(button, button2);





        BorderPane borderPane = new BorderPane();
        borderPane.setTop(layout);
        borderPane.setLeft(layout1);

        Scene scene = new Scene(borderPane, 300, 250);
        window.setScene(scene);


        window.show();


    }

}