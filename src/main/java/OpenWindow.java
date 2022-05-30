import javax.crypto.KeyAgreementSpi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.KeyPair;
import java.sql.Driver;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;


import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenWindow extends JPanel {
    private ImageIcon openPic;
    private TextField phoneNumber;
    private TextField textField;
    private JButton button;
    private ChromeDriver driver;


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

        openWindowItem();

        button.addActionListener((event) -> {
//            this.remove(button);
            checkNumberAndText();

        });


    }

    public void openWindowItem(){
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
    }


    protected void paintComponent(Graphics graphics) {
        if (openPic != null) {
            this.openPic.paintIcon(this, graphics, 0, 0);
        }

    }


    public void checkNumberAndText() {
        String phoneNumber = this.phoneNumber.getText();
        String textMassage = this.textField.getText();
        if (phoneNumber(phoneNumber) && textMassage(textMassage)) {
            loginToWhatsApp(phoneNumber, textMassage);

        }
////            JFrame frame = new JFrame(); // מציג בחלון חדש לגמרי
////            frame.setLayout(new GridLayout());
////            JLabel label = new JLabel("<מספר טלפון אינו תקין");
////            label.setBounds(200,200,30,30);
////            this.add(label);
////            this.setVisible(true);
    }



    public void loginToWhatsApp(String phone, String massage) {

        // System.setProperty("webdriver.chrome.driver", "C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\מדמח סימסטר א\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\מדמח סימסטר א\\chromedriver_win32 (1)\\chromedriver.exe");//שוהם
//        System.setProperty("webdriver.chrome.driver", "" +
//                "C:\\Users\\noymi\\Downloads\\chromedriver_win32\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://api.whatsapp.com/send?phone=972" + phone);
        driver.manage().window().maximize();

////*[@id="main"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2] // תוכן הודעה
        Thread thread = new Thread(() -> {
//            System.out.println("start : thread");
//            WebDriverWait = new WebDriverWait(driver,30);
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'COMPOSE')]")));

            try {
                WebElement continueChatting = driver.findElement(By.id("action-button"));
                continueChatting.click();
                Thread.sleep(30000);
                System.out.println("it works! ");
                List<WebElement> elementList = driver.findElements(By.className("_3K4-L"));
                if (elementList != null) {
                    WebElement newMassage = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]"));
                    newMassage.sendKeys(massage);
                    WebElement send = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]/button/span"));
                    System.out.println(send );
                    send.click();
//                    Thread.sleep(1500);
                    int v = messageStatus(driver);
                    System.out.println(v);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("false" + e);

            }
        });thread.start();


    }
    public int messageStatus(ChromeDriver driver){
        int blueV = 0;
        WebElement status = driver.findElement(By.className("_3l4_3"));
//        WebElement status1 = driver.findElement(By.className("_2WtuC"));
//        WebElement status2 = driver.findElement(By.className("YYcY9"));
        System.out.println(status + "list ");
            if(status.equals("נמסרה")){
                blueV = 1;
            }else if(status.equals("נקרא")) {
                blueV = 2;
            }

        return blueV;
    }

    public boolean phoneNumber(String number) {
        boolean goodNumber = false;
        JLabel myTitle = new JLabel("");
        myTitle.setBounds(230, 35, 200, 50);
        Font myFont = new Font("Ariel", Font.BOLD, 14);
        myTitle.setForeground(Color.red);
        this.add(myTitle);
        myTitle.setFont(myFont);
        if(number.length() == 0){
            myTitle.setText("נא הכנס מספר טלפון");
            this.add(myTitle);
        }
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
        }if(!goodNumber && number.length() > 0) {

            myTitle.setText("מספר טלפון לא תקין!");
            myTitle.setBounds(230,50,200,50);
            this.add(myTitle);
        }
        return goodNumber;
    }

    public boolean textMassage(String massage) {
        if (Objects.equals(massage, "")) {
            JLabel myTitle = new JLabel("הודעת טקסט לא תקינה! ");
            myTitle.setBounds(230, 75, 200, 50);
            Font myFont = new Font("Ariel", Font.BOLD, 14);
            myTitle.setForeground(Color.red);
            this.add(myTitle);
            myTitle.setFont(myFont);
            return false;
        } else {
            return true;
        }
    }


    public boolean postMassage(String massage) {
        System.out.println("post massage: start");
        ChromeDriver driver = new ChromeDriver();
        try {
            WebElement newMassage = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]"));
//        WebElement newMassage = driver.findElement(By.className("_2vbn4"));
//        WebElement newMassage = driver.findElement(By.className("_3J6w8"));
//            newMassage.click();
            newMassage.sendKeys(massage);
//            newMassage.sendKeys(Keys.ENTER);

            System.out.println("hala");
        }catch (Exception e){
            System.out.println("no works "+e);
        }
        return true;
    }
}
