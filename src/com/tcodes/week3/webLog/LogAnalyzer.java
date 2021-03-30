package com.tcodes.week3.webLog;
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.lang.reflect.Array;
import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<>();
     }
        
     public void readFile(String filename) {
         records.clear();

         FileResource fr = new FileResource(filename);
         for (String line: fr.lines()) {
             LogEntry logEntry = WebLogParser.parseEntry(line);
             records.add(logEntry);
         }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public int countUniqueIPs()
     {
         ArrayList<String> ipAddrs = new ArrayList<>();
         for (LogEntry logEntry : records) {
             String ipaddr = logEntry.getIpAddress();
             if(!ipAddrs.contains(ipaddr)){
                 ipAddrs.add(ipaddr);
             }

         }

         return ipAddrs.size();
     }

     public void printAllHigherThanNum(int num)
     {
         for(LogEntry logEntry : records){
             int statusCode = logEntry.getStatusCode();
             if(statusCode > num)
                 System.out.println(statusCode);
         }
     }

     public ArrayList<String> uniqueIPVisitsOnDay(String someday)
     {
         ArrayList<String> IPVisits = new ArrayList<>();
         for (LogEntry logEntry :
                 records) {
             String date = logEntry.getAccessTime().toString();
             int dayId = date.indexOf("/");
             String day = date.substring(0, dayId);
             int monthId = date.indexOf("/", dayId+1);
             String month = date.substring(dayId, monthId);
             String ipaddr = logEntry.getIpAddress();

             if(month.equals(someday.substring(0, 3)) && day.equals(someday.substring(3))){
                 if(!IPVisits.contains(ipaddr))
                     IPVisits.add(ipaddr);
             }
         }

         return IPVisits;
     }

     public ArrayList<String> UniqueIPAddressesInRange(int low, int high)
     {
         ArrayList<String> uniqueIPs = new ArrayList<>();
         for(LogEntry logEntry : records){
             int statusCode = logEntry.getStatusCode();
             String ipaddr = logEntry.getIpAddress();
             if(statusCode >= low && statusCode <= high) {
                 if(!uniqueIPs.contains(ipaddr))
                     uniqueIPs.add(ipaddr);
             }
         }

         return uniqueIPs;
     }

     public HashMap<String, Integer> CountVisitsPerIP()
     {
         HashMap<String, Integer> ipToVisits = new HashMap<>();
         for(LogEntry logEntry : records){
             String ipaddr = logEntry.getIpAddress();

             if(ipToVisits.containsKey(ipaddr)){
                 int cnt = ipToVisits.get(ipaddr);
                 ipToVisits.put(ipaddr, cnt+1);
             }
             else {
                 ipToVisits.put(ipaddr, 1);
             }
         }

         return ipToVisits;
     }

     public int mostNumberVisitsByIp(HashMap<String, Integer> ipToVisits)
     {
         int maxVisits = 0;
         for(String key : ipToVisits.keySet()){
             if(maxVisits < ipToVisits.get(key))
                 maxVisits = ipToVisits.get(key);
         }

         return maxVisits;
     }

     public ArrayList<String> iPsMostVisits(HashMap<String, Integer> ipToVisits)
     {
         ArrayList<String> mostVisitedIPs = new ArrayList<>();

         int maxVisits = mostNumberVisitsByIp(ipToVisits);
         for (String ip :
                 ipToVisits.keySet()) {
             if (maxVisits == ipToVisits.get(ip)){
                 if(!mostVisitedIPs.contains(ip)){
                     mostVisitedIPs.add(ip);
                 }
             }
         }

         return mostVisitedIPs;
     }

     public HashMap<String, ArrayList<String>> iPsForDays()
     {
         HashMap<String, ArrayList<String>> dayToIP = new HashMap<>();

         for (LogEntry logEntry : records) {
             String ip = logEntry.getIpAddress();
             String date = logEntry.getAccessTime().toString();

             if(dayToIP.containsKey(date)){
                 ArrayList<String> ipList = dayToIP.get(date);
                 ipList.add(ip);
             }
         }

         return dayToIP;
     }

     public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> dayToIP)
     {
         int maxVisits = 0;
         String maxVisitsDay = new String();

         for (String day : dayToIP.keySet()) {
             int visits = dayToIP.get(day).size();
             if(visits > maxVisits) {
                 maxVisitsDay = day;
                 maxVisits = visits;
             }
         }

         return maxVisitsDay;
     }

     public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> dayToIP, String date)
     {
         ArrayList<String> ipsOnDay = dayToIP.get(date);
         HashMap<String, Integer> ipToCount = new HashMap<>();

         for (String ip :
                 ipsOnDay) {
             if (!ipToCount.containsKey(ip)) {
                 ipToCount.put(ip, 1);
             } else {
                 int cnt = ipToCount.get(ip);
                 ipToCount.put(ip, cnt + 1);
             }
         }

         return iPsMostVisits(ipToCount);
     }
}
