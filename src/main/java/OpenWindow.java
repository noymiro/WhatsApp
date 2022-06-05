import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenWindow extends JPanel {
    private ImageIcon openPic;
    private TextField phoneNumber;
    private TextField textField;
    private Message messageToSend;
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

    public void openWindowItem() {
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
        if (validPhoneNumber(phoneNumber) && validTextMessage(textMassage)) {
            this.messageToSend = new Message(textMassage, phoneNumber);
            startDriver();

        }
////            JFrame frame = new JFrame(); // מציג בחלון חדש לגמרי
////            frame.setLayout(new GridLayout());
////            JLabel label = new JLabel("<מספר טלפון אינו תקין");
////            label.setBounds(200,200,30,30);
////            this.add(label);
////            this.setVisible(true);
    }

    public void startDriver() {
        // System.setProperty("webdriver.chrome.driver", "C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\מדמח סימסטר א\\chromedriver.exe");
        //    System.setProperty("webdriver.chrome.driver", "C:\\Users\\sofer\\OneDrive\\שולחן העבודה\\מדמח סימסטר א\\chromedriver_win32 (1)\\chromedriver.exe");//שוהם
        System.setProperty("webdriver.chrome.driver", "" +
                "C:\\Users\\noymi\\Downloads\\chromedriver_win32\\chromedriver.exe");
        this.driver = new ChromeDriver();
        driver.get("https://api.whatsapp.com/send?phone=972" + this.messageToSend.getPhoneNumber());
        driver.manage().window().maximize();
        loginToWhatsApp();
    }

    public void loginToWhatsApp() {
////*[@id="main"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2] // תוכן הודעה
        try {
            boolean sent = false;
            WebElement continueChatting = driver.findElement(By.id("action-button"));//המשך לצאט
            continueChatting.click();
            Thread.sleep(1000);
            WebElement whatAppWeb = driver.findElement(By.xpath("//*[@id=\"fallback_block\"]/div/div/h4[2]/a"));// השתמש בווצאפ ווב
            whatAppWeb.click();
            waitToLogIn();
            System.out.println("it works! ");
            WebElement newMassage = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]"));
            newMassage.sendKeys(messageToSend.getMessage());
            WebElement send = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]/button/span"));
            send.click();
            JLabel myTitle = new JLabel("");
            myTitle.setText("ההודעה נשלחה בהצלחה");
            sent = true;
            myTitle.setBounds(230, 50, 200, 50);
            this.add(myTitle);

        } catch (Exception e) {
            loginToWhatsApp();
        }
        statusMessage(messageToSend);
    }


    public void statusMessage(Message message) {

     new Thread(() -> {
            if (waitToMessage()) {
                WebElement read = this.driver.findElement(By.cssSelector("div[class='do8e0lj9 l7jjieqr k6y3xtnu']"));
                WebElement read2 = read.findElement(By.tagName("span"));
                String find = read2.getAttribute("data-testid");
                if (find.equals("msg-check")) {
                    message.setTypeStatus(1);
                    }
                     else if (find.equals("msg-dblcheck")) {
                        message.setTypeStatus(2);
                        String className = read2.getAttribute("class");
                        if (className.equals("_3l4_3")) {
                            message.setTypeStatus(3);
                        }
                    }
                    System.out.println(message.getTypeStatus());
                }
        }).start();
     repaint();
    }

    public boolean waitToMessage() {
        new Thread(() -> {
            List<WebElement> webElements = driver.findElements(By.className("_1PzAL"));//מחכה שישלח הודעה
            if (webElements != null && !webElements.isEmpty() ) {
                System.out.println("WebElement:Exist");
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }

    public boolean validPhoneNumber(String number) {
        boolean goodNumber = false;
        JLabel myTitle = new JLabel("");
        myTitle.setBounds(230, 35, 200, 50);
        Font myFont = new Font("Ariel", Font.BOLD, 14);
        myTitle.setForeground(Color.black);
        this.add(myTitle);
        myTitle.setFont(myFont);
        if (number.length() == 0) {
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
        }
        if (!goodNumber && number.length() > 0) {
            myTitle.setText("מספר טלפון לא תקין!");
            myTitle.setBounds(30, 250, 200, 50);
            this.add(myTitle);
        } else {
            this.remove(myTitle);
        }
        return goodNumber;
    }

    public boolean validTextMessage(String massage) {
        JLabel myTitle = new JLabel("הודעת טקסט לא תקינה! ");
        if (Objects.equals(massage, "")) {
            myTitle.setText("הודעת טקסט לא תקינה! ");
            myTitle.setBounds(30, 280, 200, 50);
            Font myFont = new Font("Ariel", Font.BOLD, 14);
            myTitle.setForeground(Color.black);
            this.add(myTitle);
            myTitle.setFont(myFont);
            return false;
        } else {
            return true;
        }
    }

    public void waitToLogIn() {
        while (true) {
            List<WebElement> elementList = this.driver.findElements(By.className("_3K4-L"));
            if (elementList != null && !elementList.isEmpty()) {
                break;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


//                            read = driver.findElement(By.cssSelector("span[data-testid='msg-check']"));
//                            if (read != null) {
//                                messageToSend.setTypeStatus(1);
//                            }


//                    if (sent) {
//                        JLabel myTitle1 = new JLabel("");
//                        myTitle1.setText("הודעה נקראה");
//                        myTitle1.setBounds(230, 50, 200, 50);
//                        this.add(myTitle);
//                        break;
//                    } else {
//                        Thread.sleep(10000);
//                    }


//
//            while (true) {
//                list = driver.findElements(By.cssSelector("span[data-testid='msg-dblcheck']"));
//                if (list != null && !list.isEmpty()) {
//                    System.out.println(" status: " + list.get(list.size()-1).getAccessibleName());
//                    break;
//                } else {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            while (true) {
////                System.out.println("start to check status: " + list.size());
////                for (int i = 0; i < list.size(); i++) {
////                    System.out.println(list.get(i).getAccessibleName());
////                }
//                try {
//                    Thread.sleep(6000);
//                    if (Objects.equals(list.get(list.size()-1).getAccessibleName(), "נמסרה")) {
//                        System.out.println("נמסרה");
//                        //+ list.get(list.size()-1).getAccessibleName());
//                        break;
//
//                    } else if (Objects.equals(list.get(list.size()-1).getAccessibleName(), "נקראה")) {
//                        System.out.println(": נקרא " + list.get(list.size()-1).getAccessibleName());
//                        break;
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        threadTwo.start();
//        return true;
//    }


    //*[@id="main"]/div[3]/div/div[2]/div[3]/div[25]/div/div[1]/div[1]/div[2]/div/div/span //נקרא
    // *[@id="main"]/div[3]/div/div[2]/div[3]/div[29]/div/div[1]/div[1]/div[2]/div/div/span // נמסרה
    //*[@id="main"]/div[3]/div/div[2]/div[3]/div[19]/div/div[1]/div[1]/div[2]/div/div/span // נמסרה
    //_1qB8f // נקרא
    //_1qB8f // נמסרה
    //_1PzAL // חלון הודעה
    // _3l4_3
    //_2WtuC נמסרה



//    public boolean postMassage(String massage) {
//        System.out.println("post massage: start");
//        ChromeDriver driver = new ChromeDriver();
//        try {
//            WebElement newMassage = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[2]"));
////        WebElement newMassage = driver.findElement(By.className("_2vbn4"));
////        WebElement newMassage = driver.findElement(By.className("_3J6w8"));
////            newMassage.click();
//            newMassage.sendKeys(massage);
////            newMassage.sendKeys(Keys.ENTER);
//
//            System.out.println("hala");
//        }catch (Exception e){
//            System.out.println("no works "+e);
//        }
//        return true;
//    }

//קומבינה להאט עד שיקבל אלמנט
//                boolean noExists = true;
//                while (noExists){
//                    try {
//                        noExists = driver.findElement(By.xpath("//*[@id=\"fallback_block\"]/div/div/h4[2]/a")).getSize().equals(0);
//                    }catch (Exception e){
//                        noExists = true;
//                        System.out.println(e);
//                    }
//                }
