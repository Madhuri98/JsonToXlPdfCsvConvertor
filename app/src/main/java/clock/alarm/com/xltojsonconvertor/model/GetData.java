package clock.alarm.com.xltojsonconvertor.model;

import java.util.Date;
/**
 * Created by Madhuri on 26/09/18.
 */
public class GetData {
    private String id;
    private String name;
    private String note;
    private String by;
    private int price;
    private Date dueDate;
    private Date cAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getcAt() {
        return cAt;
    }

    public void setcAt(Date cAt) {
        this.cAt = cAt;
    }
}
