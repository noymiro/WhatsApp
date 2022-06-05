import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        new Main();
    }

    public Main (){

        OpenWindow openWindow = new OpenWindow(0 , 0 , 600 , 600);
        this.add(openWindow);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        //  this.setLayout(null);
        this.setVisible(true);



    }
}