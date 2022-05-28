import javax.swing.*;
import java.awt.*;
import java.sql.Driver;
import java.util.Objects;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenWindow extends JPanel {
    private int x ;
    private int y ;
    private int width ;
    private int height ;
    private ImageIcon openPic;
    private TextField phoneNumber;
    private TextField textField;
    private JButton button;


    public OpenWindow(int x, int y, int width, int height) {

        this.setBounds(x, y, width, height);

        this.setFocusable(true);
        this.requestFocus();
        this.setLayout(null);
        // this.setDoubleBuffered(true);
        this.setVisible(true);
        this.openPic = new ImageIcon("whatsapp.png");
        // drawButton();
        button = new JButton("WhatsApp Web");
        button.setBounds(20, 20, 150, 50);
        this.add(button);
        button.setBackground(Color.white);
        JLabel myTitle = new JLabel("הכנס מספר טלפון:");
        myTitle.setBounds(30, 60, 180, 50);
        Font myFont = new Font("Ariel", Font.BOLD, 18);
        this.add(myTitle);
        myTitle.setFont(myFont);
        this.phoneNumber = new TextField("");
        phoneNumber.setBounds(20, 110, 150, 30);
        this.add(phoneNumber);
        JLabel myTitle2 = new JLabel("הכנס הודעת טקסט:");
        myTitle2.setBounds(30, 125, 180, 50);
        Font myFont2 = new Font("Ariel", Font.BOLD, 18);
        this.add(myTitle2);
        myTitle2.setFont(myFont2);
        this.textField = new TextField("");
        textField.setBounds(20, 180, 150, 50);
        this.add(textField);
        button.addActionListener((event) -> {
            this.remove(button);
            checkNumberAndText();
        });


    }
//    public void drawButton() {
//        JLabel myTitel = new JLabel("הכנס מספר טלפון:");
//        myTitel.setBounds(30, 60, 180, 50);
//        Font myFont = new Font("Ariel", Font.BOLD, 18);
//        this.add(myTitel);
//        myTitel.setFont(myFont);
//        this.textField = new TextField("");
//        textField.setBounds(20, 110, 150, 30);
//        this.add(textField);
//        JLabel myTitel2 = new JLabel("הכנס הודעה טקסט:");
//        myTitel2.setBounds(30, 125, 180, 50);
//        Font myFont2 = new Font("Ariel", Font.BOLD, 18);
//        this.add(myTitel2);
//        myTitel2.setFont(myFont2);
//        this.textField = new TextField("");
//        textField.setBounds(20, 180, 150, 50);
//        this.add(textField);
//
//    }


    protected void paintComponent(Graphics graphics) {
        if (openPic != null) {
            this.openPic.paintIcon(this, graphics, 0, 0);
        }

    }


    public void checkNumberAndText () {
        //   MessageWindow messageWindow = new MessageWindow(0, 0, 600, 400);
        String phoneNumber = this.phoneNumber.getText();
        String textMassage = this.textField.getText();
        if (phoneNumber(phoneNumber) && textMassage(textMassage) ) {
            loginToWhatsApp(phoneNumber , textMassage);
        }


    } //ניסיון

    public void loginToWhatsApp(String phone, String massage) {

        // System.setProperty("webdriver.chrome.driver", "C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\מדמח סימסטר א\\chromedriver.exe");
        // System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lenovo\\Downloads\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "" +
                "C:\\Users\\noymi\\Downloads\\chromedriver_win32\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://api.whatsapp.com/send?phone=972" + phone);
        driver.manage().window().maximize();
        System.out.println("האם עידן צודק? ");
        new Thread(() -> {

            try {
                WebElement elementList = driver.findElement(By.className("_2vbn4"));
                WebElement element = driver.findElement(By.className("_3J6w8"));
                System.out.println("עידן די צדקת הוא לא מדפיס ");
                //  if (elementList != null) {
                postMassage(massage);
                System.out.println("true");
                //  }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }).start();



    }

    public boolean phoneNumber(String number) {
        boolean goodNumber = false;
        if (number.length() == 10) {
            for (int i = 0; i < number.length() - 1; i++) {
                if (number.charAt(i) >= '0' && number.charAt(i) <= '9') {
                    if (number.charAt(0) == '0' && number.charAt(1) == '5') {
                        goodNumber = true;
                    }
                } else {
                    goodNumber = false;
                    break;
                }
            }
        }
        return goodNumber;
    }

    public boolean textMassage (String massage) {
        if (Objects.equals(massage, "")){
            // הקפצת הודעה עם כפתור OK
            return false;
        } else {
            return true;
        }
    }



    public boolean postMassage (String massage) {
        ChromeDriver driver = new ChromeDriver();
        WebElement newMassage = driver.findElement(By.className("p3_M1"));
        newMassage.click();
        newMassage.sendKeys(massage);
        newMassage.sendKeys(Keys.ENTER);
        return true;
    }
}
