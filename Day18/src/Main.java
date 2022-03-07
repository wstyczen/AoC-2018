public class Main {
    public static void main(String[] args) {
        Area area = new Area("Day18.txt", 100 );
//        area.getLengthOfCycle(); // !!! elapses time within
        // cycle length = 28
        int minuteReq = 1000000000;
        while (minuteReq > 1000) {
            minuteReq -= 28;
        }
        area.elapseTime(minuteReq);
        System.out.println(area.getNumberOf('#')*area.getNumberOf('|'));
    }
}
