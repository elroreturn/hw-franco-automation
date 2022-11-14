package models;

import java.util.Date;

public class Group {
    
    private String _id;
    private Date date;
    private String user;

    public Group() {
    }

    public Group(String _id, Date date, String user) {
        this._id = _id;
        this.date = date;
        this.user = user;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
