import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public Date(String line) {
        this.intializeFields(line);
    }

    public void intializeFields(String line) {
        Matcher m = Pattern.compile("(\\d+)").matcher(line);
        int [] input = new int [5];
        int index = 0;
        while (m.find()) {
            input[index] = Integer.parseInt(m.group(1));
            index++;
        }
        this.year = input[0];
        this.month = input[1];
        this.day = input[2];
        this.hour = input[3];
        this.minute = input[4];
    }

    public int compareInts(int i1, int i2) {
        if (i1>i2)
            return 1;
        else if (i1<i2)
            return -1;
        else
            return 0;
    }

    @Override
    public int compareTo(Date date) {
        if (this.getYear() > date.getYear()) {
            return 1;
        } else if (this.getYear() < date.getYear()) {
            return -1;
        } else {
            if (this.getMonth() > date.getMonth()) {
                return 1;
            } else if (this.getMonth() < date.getMonth()) {
                return -1;
            } else {
                if (this.getDay() > date.getDay()) {
                    return 1;
                } else if (this.getDay() < date.getDay()) {
                    return -1;
                } else {
                    if (this.getHour() > date.getHour()) {
                        return 1;
                    } else if (this.getHour() < date.getHour()) {
                        return -1;
                    } else {
                        if (this.getMinute() > date.getMinute()) {
                            return 1;
                        } else if (this.getMinute() < date.getMinute()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
