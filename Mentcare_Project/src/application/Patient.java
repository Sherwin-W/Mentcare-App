package application;

public class Patient {
    private String firstName;
    private String lastName;
    private String DOB;
    private String address;
    private String accountPass;
    private String phoneNum;
    private String healthNum;
    private String height;
    private String weight;
    private String bodyTemp;
    private String bloodPress;

    //below is needed for staff message inbox
    private String email;

    private String password;

    public Patient(){

    }
    public Patient(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Patient(String firstname, String lastName, String DOB, String address, String accountPass, String phoneNum, String healthNum){
        this.firstName = firstname;
        this.lastName = lastName;
        this.DOB = DOB;
        this.address = address;
        this.accountPass = accountPass;
        this.phoneNum = phoneNum;
        this.healthNum = healthNum;
    }

    //getters and setters for each element of the Patient object
    public String getFirstName(){
        return this.firstName;
    }
    public void updateFirstName(String temp){
        this.firstName = temp;
    }

    public String getLastName(){
        return this.lastName;
    }
    public void updateLastName(String temp){
        this.lastName = temp;
    }

    public String getDOB(){
        return this.DOB;
    }
    public void updateDOB(String temp){
        this.DOB = temp;
    }

    public String getAddress(){
        return this.address;
    }
    public void updateAddress(String temp){
        this.address = temp;
    }

    public String getEmail(){
        return this.email;
    }
    public void updateEmail(String temp){
        this.email = temp;
    }

    public String getPassword(){
        return this.password;
    }
    public void updatePassword(String temp){
        this.password = temp;
    }

    public String getPhoneNum(){
        return this.phoneNum;
    }
    public void updatePhoneNum(String temp){
        this.phoneNum = temp;
    }

    public String getHealthNum(){
        return this.healthNum;
    }
    public void updateHealthNum(String temp){
        this.healthNum = temp;
    }

    public void updateHeight(String height) {
        this.height = height;
    }

    public void updateWeight(String weight) {
        this.weight = weight;
    }

    public void updateBodyTemp(String bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public void updateBloodPress(String bloodPress) {
        this.bloodPress = bloodPress;
    }

    // Getters
    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getBodyTemp() {
        return bodyTemp;
    }

    public String getBloodPress() {
        return bloodPress;
    }
}
