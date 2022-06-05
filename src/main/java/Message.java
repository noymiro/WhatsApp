public class Message {
    private String message;
    private String phoneNumber;
    private Integer typeStatus;
    private String messageBake;

    public Message(String message,String phoneNumber){
        this.message=message;
        this.phoneNumber=phoneNumber;
        this.typeStatus=null;
        messageBake=null;
    }

    public String getMessage() {
        return message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getTypeStatus() {
        return typeStatus;
    }

    public String getMessageBake() {
        return messageBake;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTypeStatus(Integer typeStatus) {
        this.typeStatus = typeStatus;
    }

    public void setMessageBake(String messageBake) {
        this.messageBake = messageBake;
    }
}
