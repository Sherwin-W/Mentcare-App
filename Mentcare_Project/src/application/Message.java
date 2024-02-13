package application;

public class Message {

    private int id;
    private String subject;
    private String recipient;
    private String content;

    public Message(int id, String subject, String recipient, String content){
        this.id = id;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
    }

    public Message(String subject, String recipient, String content){
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
