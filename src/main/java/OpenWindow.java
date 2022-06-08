import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenWindow extends JPanel {
    private ImageIcon openPic;
    private TextField phoneNumber;
    private TextField textField;
    private JButton button;


    public OpenWindow(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setFocusable(true);
        this.requestFocus();
        this.setLayout(null);
        this.setVisible(true);
        this.openPic = new ImageIcon("whatsapp.png");
        button = new JButton("WhatsApp Web");
        button.setBounds(Final.BUTTON_X, Final.BUTTON_Y, Final.BUTTON_WIDTH, Final.BUTTON_HRIGT);
        this.add(button);
        button.setBackground(Color.white);
        openWindowItem();
        button.addActionListener((event) -> {
            checkNumberAndText();
        });
    }

    public void openWindowItem() {
        JLabel myTitle = new JLabel("הכנס מספר טלפון:");
        myTitle.setBounds(Final.X_TITEL, Final.Y_TITEL, Final.TITEL_BOUNDS_WIDTH, Final.TITEL_BOUNDS_HIGHT);
        Font myFont = new Font("Ariel", Font.BOLD, Final.FONT_SIZE);
        this.add(myTitle);
        myTitle.setFont(myFont);
        this.phoneNumber = new TextField("");
        phoneNumber.setBounds(Final.X_BOUNDS, Final.Y_PHONE, Final.WIDTH_PHONE, Final.TITEL_BOUNDS_HIGHT);
        this.add(phoneNumber);

        JLabel myTitle2 = new JLabel("הכנס הודעת טקסט:");
        myTitle2.setBounds(Final.X_TITEL, Final.TITLE2_Y + 20, Final.TITEL_BOUNDS_WIDTH, Final.TITEL_BOUNDS_HIGHT);
        Font myFont2 = new Font("Ariel", Font.BOLD, Final.FONT_SIZE);
        this.add(myTitle2);
        myTitle2.setFont(myFont2);
        this.textField = new TextField("");
        textField.setBounds(Final.X_BOUNDS, Final.TITEL_BOUNDS_WIDTH + 10, Final.WIDTH_PHONE, Final.TITEL_BOUNDS_HIGHT);
        this.add(textField);
    }


    protected void paintComponent(Graphics graphics) {
        if (openPic != null) {
            this.openPic.paintIcon(this, graphics, Final.X, Final.Y);
        }
    }

    public void checkNumberAndText() {
        String phoneNumber = this.phoneNumber.getText();
        String textMassage = this.textField.getText();
        if (phoneNumber(phoneNumber) && textMassage(textMassage)) {
            loginToWhatsApp(phoneNumber, textMassage);
        }
    }

    public void loginToWhatsApp(String phone, String massage) {
        //      System.setProperty("webdriver.chrome.driver", "C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\מדמח סימסטר א\\chromedriver_win32 (1)\\chromedriver.exe");//שוהם
        System.setProperty("webdriver.chrome.driver", "" + "C:\\Users\\noymi\\Downloads\\chromedriver_win32\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://api.whatsapp.com/send?phone=972" + phone);
        driver.manage().window().maximize();
        Thread thread = new Thread(() -> {
            try {
                WebElement continueChatting = driver.findElement(By.id("action-button"));//המשך לצאט
                continueChatting.click();
                Thread.sleep(Final.THREAD_SLEEP);
                WebElement whatAppWeb = driver.findElement(By.xpath("//*[@id=\"fallback_block\"]/div/div/h4[2]/a"));// השתמש בווצאפ ווב
                whatAppWeb.click();
                waitToLogIn(driver);
                System.out.println("it works! ");
                WebElement newMassage = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]"));
                newMassage.sendKeys(massage);
                WebElement send = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]/button/span"));
                System.out.println(send);
                send.click();
                if (deliveredMassage(driver)) {
                    messageWesRead(driver);
                }
                response(driver);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("false" + e);
            }
        });
        thread.start();
    }

    public boolean deliveredMassage(ChromeDriver driver) {
        boolean massage;
        JLabel messageStatusSend = new JLabel("");
        while (true) {
            List<WebElement> list = driver.findElements(By.cssSelector("span[data-icon='msg-dblcheck']"));
            try {
                if (Objects.equals(list.get(list.size() - 1).getAccessibleName(), "נמסרה")) {
                    System.out.println(": נמסרה " + list.get(list.size() - 1).getAccessibleName());
                    messageStatusSend.setText("ההודעה נמסרה");
                    messageStatusSend.setBounds(Final.X_TITEL, Final.MESSAGE_Y, Final.MESSAGE_WIDTH, Final.MESSAGE_HEIGHT);
                    this.add(messageStatusSend);
                    Font myFont = new Font("Ariel", Font.BOLD, Final.FONT_SIZE);
                    messageStatusSend.setFont(myFont);


                    massage = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return massage;
    }

    //0526931323
    public void messageWesRead(ChromeDriver driver) {
        JLabel messageStatusSend = new JLabel("");
        while (true) {
            List<WebElement> list = driver.findElements(By.cssSelector("span[data-icon='msg-dblcheck']"));
            try {
                if (Objects.equals(list.get(list.size() - 1).getAccessibleName(), "נקראה")) {
                    System.out.println(" נקראה:  " + list.get(list.size() - 1).getAccessibleName());
                    messageStatusSend.setText("ההודעה נקראה");
                    messageStatusSend.setBounds(Final.X_TITEL, Final.MESSAGE_Y2, Final.MESSAGE_WIDTH, Final.MESSAGE_HEIGHT);
                    this.add(messageStatusSend);
                    Font myFont = new Font("Ariel", Font.BOLD, Final.FONT_SIZE);
                    messageStatusSend.setFont(myFont);

                    break;
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public boolean phoneNumber(String number) {
        boolean goodNumber = false;
        JLabel myTitle = new JLabel("");
        myTitle.setBounds(Final.X_TITEL, Final.TITEL_BOUNDS_Y, Final.TITEL_WIDTH, Final.TITEL_BOUNDS_HIGHT);
        Font myFont = new Font("Ariel", Font.BOLD, Final.FONT_SIZE3);
        myTitle.setForeground(Color.black);
        this.add(myTitle);
        myTitle.setFont(myFont);
        if (number.length() == 0) {
            myTitle.setText("נא הכנס מספר טלפון:");
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
        }
        if (!goodNumber && number.length() > 0) {

            myTitle.setText("מספר טלפון לא תקין!");
            myTitle.setBounds(Final.X_TITEL, Final.TITEL_Y, Final.TITEL_WIDTH, Final.TITEL_BOUNDS_HIGHT);
            this.add(myTitle);
        }

        return goodNumber;
    }

    public boolean textMassage(String massage) {
        if (Objects.equals(massage, "")) {
            JLabel myTitle = new JLabel("הודעת טקסט לא תקינה! ");
            myTitle.setBounds(Final.X_TITEL, Final.Y_TITEL2, Final.TITEL_WIDTH,  Final.TITEL_HEIGHT2);
            Font myFont = new Font("Ariel", Font.BOLD, Final.FONT_SIZE2);
            myTitle.setForeground(Color.black);
            this.add(myTitle);
            myTitle.setFont(myFont);
            return false;
        } else {
            return true;
        }
    }

    public void waitToLogIn(ChromeDriver driver) {
        while (true) {
            List<WebElement> elementList = driver.findElements(By.className("_3K4-L"));
            if (elementList != null && !elementList.isEmpty()) {
                break;
            } else {
                try {
                    Thread.sleep(Final.THREAD_SLEEP2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void response(ChromeDriver driver) {

        JLabel message = new JLabel("");
        System.out.println("response: start");
        List<WebElement> listOfRecipient = driver.findElements(By.className("_2wUmf"));
        int size = listOfRecipient.size();
        while (true) {
            listOfRecipient = driver.findElements(By.className("_2wUmf"));
            if (listOfRecipient != null && !listOfRecipient.isEmpty()) {
                System.out.println("no new message");
            } else {
                try {
                    Thread.sleep(Final.THREAD_SLEEP2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(Final.THREAD_SLEEP3);
            } catch (Exception e) {
                System.out.println(e);
            }
            if (size != listOfRecipient.size()) {
                message.setText("ההודעה שנשלחה:");
                message.setBounds(Final.X_END, Final.Y_END, Final.WIDTH_END_ME, Final.HEIGHT_END_ME);
                this.add(message);
                Font myFont = new Font("Ariel", Font.BOLD, Final.FONT_SIZE2);
                message.setFont(myFont);
                System.out.println("listOfRecipient: " + listOfRecipient.get(listOfRecipient.size() - 1).getText());
                String str = listOfRecipient.get(listOfRecipient.size() - 1).getText() + " ";
                JTextField textField = new JTextField(str, 40);
                textField.setBounds(Final.X_END_ME, Final.Y_END_ME, Final.WIDTH_END_ME, Final.HEIGHT_END_ME);
                this.add(textField);
                Font font = new Font("Ariel", Font.BOLD, Final.FONT_SIZE2);
                textField.setFont(font);
                break;
            }
        }
    }
}

