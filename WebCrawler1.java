import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class WebCrawler1 {
    //Queue for BFS
    static Queue<String> q = new LinkedList<>();
    
    //URLs already visited
    static Set<String> marked = new HashSet<>();
    
   /*          /about_us/contact_information/     */
  
    // static String regex = "https://cs.txstate.edu/*(//w+\\/)*(\\w+/*)";           //Crawling only two sites for our website
    // static String regex = "https://www.cs.utah.edu/*(//w+\\/)*(\\w+/*)";          //Working correctly for this site

   static String regex = "http[s]*://(\\w+\\.)*(\\w+)";                              //Crawling all sites for overall Texas state

   //static String root = "https://cs.txstate.edu/";                                 //Crawling only two sites
   //static String root = "https://www.cs.utah.edu/";                                //Working correctly
   static String root = "https://txstate.edu/";                                      //Crawling all sites for overall Texas state
   //BFS Routine
    public static void bfs() throws IOException{
        q.add(root);
            
        while(!q.isEmpty()){  
           
        	String s = q.poll();
            
            //Find only almost 100 websites.
            if(marked.size()>100)return; 
            
            boolean ok = false;
            URL url = null;
            BufferedReader br = null;
           
            
            while(!ok){ 
                try
                {
                    url = new URL(s);
                    System.out.println("URL: "+s);
                    if(s.contains("calender")) {
                    	System.out.println("calender found");
                    	break;
                    }
                    	
                    br = new BufferedReader(new InputStreamReader(url.openStream()));
                    ok = true;
                    //throw new Exception();
                }catch(MalformedURLException e)
                {
                    System.out.println("\nMalformedURL : "+e+"\n");
                    //Get next URL from queue
                    s = q.poll();
                    ok = false;
                    //continue;
                }catch(IOException e){
                   System.out.println("\nIOException for URL : "+e+"\n");
                    //Get next URL from queue     
                    s = q.poll();
                    ok = false;
                }
            }         
            
            StringBuilder sb = new StringBuilder();
            
           
        	   while((s = br.readLine())!=null){
                sb.append(s);
            }
         
            s = sb.toString();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            System.out.println("printing "+matcher);
            
            while(matcher.find()){
                String w = matcher.group(); 
               // System.out.println("printing "+w);
                if(!marked.contains(w)){
                    marked.add(w);
                    System.out.println("Site : "+w);
                    q.add(w);
                }
            } 
        }
    }
    
    //Display results from SET marked
    public static void displayResults(){
        System.out.println("\n\nResults: ");
        System.out.println("\nWeb sites crawled : "+marked.size()+"\n");
        
        for(String s:marked){
            System.out.println(s);
        }
    }
    
    //Run
    public static void main(String[] args){
        try{
           bfs();
            displayResults(); 
        	  
        }catch(IOException e){
            System.out.println("IOException caught : "+e);
        }
    }
}