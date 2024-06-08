

import java.io.IOException;
import java.util.*;

public class Tester2
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
 
        LogAnalyzer la = new LogAnalyzer();
        try {
			la.readFileNew("weblog3-short_log");
		} catch (IOException e) {
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
