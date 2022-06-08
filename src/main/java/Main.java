import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        new Main();
    }

    public Main (){

        OpenWindow openWindow = new OpenWindow(Final.X , Final.Y , Final.WINDOW_WIDTH , Final.WINDOW_HEIGT);
        this.add(openWindow);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Final.WINDOW_WIDTH2,Final.WINDOW_HEIGT);
        //  this.setLayout(null);
        this.setVisible(true);



    }
}