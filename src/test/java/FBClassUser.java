
public class FBClassUser {
    public String first_name;
    public String last_name;
    public String reg_email;
    public String password;
    public String birth_day;
    public String birth_month;
    public String birth_year;
    public int sex;

    public FBClassUser (String first_name, String last_name, String reg_email,
                        String password, String birth_day, String birth_month, String birth_year, int sex){
        this.first_name = first_name;
        this.last_name = last_name;
        this.reg_email = reg_email;
        this.password = password;
        this.birth_day = birth_day;
        this.birth_month = birth_month;
        this.birth_year = birth_year;
        this.sex = sex;
    }
}
