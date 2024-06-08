
import java.io.IOException;
import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        // complete method
        //LogAnalyzer la = new LogAnalyzer();
        //la.readFile("short-test_log");
        //la.printAll();
        LogAnalyzer la2 = new LogAnalyzer();
        try {
			la2.readFileNew("weblog2_log");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        la2.printAll();
        int unique = la2.countUniqueIPs();
        System.out.println("Unique IPs: " + unique);
        System.out.println("-------------");
        System.out.println("Print Higher than 400:");
        la2.printAllHigherThanNum(400);
        System.out.println("-------------");
        System.out.println("Unique on Day:");
        ArrayList<String> newList = la2.uniqueIPVisitsOnDay("Sep 24");
        for (String s: newList) {
            System.out.println(s);
        }
        System.out.println("Size of previous Array: " + newList.size());
        System.out.println("-------------");
        System.out.println("Unique in Range:" + la2.countUniqueIPsInRange(200, 299));
    }
        public void testLogAnalyzer2() {
        
        LogAnalyzer la = new LogAnalyzer();
        try {
			la.readFileNew("weblog2_log");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        la.printAll();
        System.out.println("-------------");
        int unique = la.countUniqueIPs();
        System.out.println("Unique IPs: " + unique);
        System.out.println("-------------");
        System.out.println("Count visits per IP: " );
        HashMap<String,Integer> map = la.countVisistsPerIP();
        for (Map.Entry<String,Integer> set : map.entrySet()){
            System.out.println(set.getKey() + " , " + set.getValue());
        }
        System.out.println("-------------");
        System.out.println("Most number of visits by IP: " + la.mostNumberVisistsByIP(map));
        System.out.println("-------------");
        System.out.println("IPs Most Visits: ");
        ArrayList<String> al = la.iPsMostVisits(map);
        for(String s: al) {
            System.out.println(s);
        }
        System.out.println("-------------"); 
        System.out.println("IPs for Days: ");
        HashMap<String, ArrayList<String>> mapList = la.iPsForDays();
        mapList.toString();
        for (Map.Entry<String,ArrayList<String>> set2 : mapList.entrySet()){
            System.out.println("Key: " + set2.getKey());
            System.out.println("Value: ");
            for(String s : set2.getValue()) {
                System.out.println(s);
            }
        }
        System.out.println("-------------");
        System.out.println("Day with most visits: " + la.dayWithMostIPVisits(mapList));
        System.out.println("-------------");
        System.out.println("IPs With Most Visits On Day: ");
        ArrayList<String> mostList = la.iPsWithMostVisitsOnDay(mapList, "Sep 30");
        for(String s: mostList) {
            System.out.println(s);
        }
        System.out.println("-------------");    
    }
}
