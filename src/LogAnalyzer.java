
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.*;
 

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer()  {
         // complete constructor
         records = new ArrayList<LogEntry>();
         
     }
        
     public void readFile(String filename) throws IOException   {
         try {
         // FileResource resource = new FileResource(filename);
         BufferedReader br = new BufferedReader(new FileReader(filename));

         for (String line = br.readLine(); line != null; line = br.readLine()) {
             ArrayList<String> parse1 = parseString(line);
             ArrayList<Integer> parse2 = parseInt(line);
 
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss");
             Date date = formatter.parse(parse1.get(1));
     
             LogEntry tmp = new LogEntry(
                parse1.get(0),
                date,
                parse1.get(2),
                parse2.get(0),
                parse2.get(1)
             );
             records.add(tmp);  
         }
         
        } catch (ParseException e) {
            System.out.println(e);
        }
     }
     public void readFileNew(String filename) throws IOException   {
          
         BufferedReader br = new BufferedReader(new FileReader(filename));
         for (String line = br.readLine(); line != null; line = br.readLine()) {
             LogEntry tmp = WebLogParser.parseEntry(line);
             records.add(tmp);
         }
         
     }
     public ArrayList<String> parseString (String line) {
         ArrayList<String> parsed = new ArrayList<String>();
         //substring(inclusive, exclusive)
         String ip = line.substring(0,line.indexOf(" "));
         parsed.add(ip);
         String access = line.substring(line.indexOf("[")+1,line.indexOf("]")-6);
         parsed.add(access);
         String request = line.substring(line.indexOf("\""),line.indexOf("\"", line.indexOf("\"")+1)+1);
         parsed.add(request);
         return parsed;
     }
     public ArrayList<Integer> parseInt (String line) {
         ArrayList<Integer> parsed = new ArrayList<Integer>();
          
         int index = line.indexOf("\"", line.indexOf("\"")+1);

         String statusCode = line.substring(index+2, line.indexOf(" ", index+2));
         int start = line.indexOf(statusCode)+statusCode.length()+1;

         String bytes = line.substring(start);

         parsed.add(Integer.parseInt(statusCode));
         parsed.add(Integer.parseInt(bytes));
         return parsed;
     }
     public void printAll() {
  
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     public int countUniqueIPs(){
         int count = 0;
         ArrayList<String> tmp = new ArrayList<String>();
 
         for (LogEntry le : records) {
              tmp.add(le.getIpAddress());
         }
         
         for (int i = 0; i < tmp.size(); i++) {
             for (int j = i + 1; j < tmp.size(); j++){
                 if (tmp.get(i).equals(tmp.get(j))) {
                     tmp.remove(j--);
                      
                    }
            }
        }
         
         count = tmp.size();
         return count;
     }
     public void printAllHigherThanNum(int num) {
         for (LogEntry le : records) {
             if (le.getStatusCode() > num){
                 System.out.println(le);
                }
         }
     }
     public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
        
        ArrayList<String> tmp = new ArrayList<String>();
        for (LogEntry le : records) {
            String date = new SimpleDateFormat("MMM dd").format(le.getAccessTime());
            if(date.equals(someday)){
                tmp.add(le.getIpAddress());
            }
        }
        for (int i = 0; i < tmp.size(); i++) {
             for (int j = i + 1; j < tmp.size(); j++){
                 if (tmp.get(i).equals(tmp.get(j))) {
                     tmp.remove(j--);
                      
                    }
            }
        }
        ArrayList<String> uniqueIPVisits = new ArrayList<String>(tmp);
 
        return uniqueIPVisits;
     }
     public int countUniqueIPsInRange(int low, int high) {
         int count = 0;
        ArrayList<String> tmp = new ArrayList<String>();
        for (LogEntry le : records) {
             
            if(le.getStatusCode() >= low && le.getStatusCode() <= high){
                tmp.add(le.getIpAddress());
            }
        }
         for (int i = 0; i < tmp.size(); i++) {
             for (int j = i + 1; j < tmp.size(); j++){
                 if (tmp.get(i).equals(tmp.get(j))) {
                     tmp.remove(j--);
                      
                    }
            }
        }
         count = tmp.size();
         return count;
     }
     public HashMap<String,Integer> countVisistsPerIP() {
         HashMap<String,Integer> counts = new HashMap<String,Integer>();
         for(LogEntry le : records) {
             String ip = le.getIpAddress();
             if (!counts.containsKey(ip)){
                 counts.put(ip,1);
             } else {
                 counts.put(ip,counts.get(ip)+1);
             }
         }
         return counts;
     }
     public int mostNumberVisistsByIP(HashMap<String,Integer> map){
         int count = 0;
         for (Map.Entry<String,Integer> set : map.entrySet()){
             if(set.getValue() > count){
                 count = set.getValue();
             }
         }
         return count;
     }
     public ArrayList<String> iPsMostVisits(HashMap<String,Integer> map){
         int mostCount = 0;
         ArrayList<String> IPs = new ArrayList<String>();
         for (Map.Entry<String,Integer> set : map.entrySet()){
             if(set.getValue() > mostCount){
                 mostCount = set.getValue();
             }
         }
         for (Map.Entry<String,Integer> set: map.entrySet()) {
             if (set.getValue() == mostCount) {
             IPs.add(set.getKey());
            }
         }
         return IPs;
     }
     public HashMap<String,ArrayList<String>> iPsForDays() {
         HashMap<String,ArrayList<String>> counts = new HashMap<String,ArrayList<String>>();
         for(LogEntry le : records) {
             String day = new SimpleDateFormat("MMM dd").format(le.getAccessTime());
             if (!counts.containsKey(day)){
                 ArrayList<String> IPs = new ArrayList<String>();
                 IPs.add(le.getIpAddress());
                 counts.put(day,IPs);
             } else {
                 counts.get(day).add(le.getIpAddress());
             }
         }
         return counts;
     }
     public String dayWithMostIPVisits(HashMap<String,ArrayList<String>> map) {
         String mostDay = "";
         int tmpMostVisits = 0;
         for(Map.Entry<String,ArrayList<String>> set : map.entrySet()) {
             if (set.getValue().size() > tmpMostVisits){
                 tmpMostVisits = set.getValue().size();
                 mostDay = set.getKey();
             }
         }
         return mostDay;
     }
     public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String,ArrayList<String>> map, String day){
         ArrayList<String> tmp = map.get(day);
         HashMap<String,Integer> tmpMap = new HashMap<String,Integer>();
          for(String str : tmp) {
             if (!tmpMap.containsKey(str)){
                 tmpMap.put(str,1);
             } else {
                 tmpMap.put(str,tmpMap.get(str)+1);
             }
         }
         
         ArrayList<String> mostVisits = iPsMostVisits(tmpMap);
         
         return mostVisits;
     }
}
