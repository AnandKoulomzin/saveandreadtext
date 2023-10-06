import java.net.*;
import java.io.*;

public class HtmlRead {
    public static void main(String[] args) {
        new HtmlRead();
    }

    public void pull(String url2, int depth) {
        int counter = 0;
        try{
            System.out.println();
            URL url = new URL(url2);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
//                line.indexOf("href=\"h") >= 0 || line.indexOf("href=\'h") >= 0
                if (line.contains("href")) {
                    int firstIndex = line.indexOf("href=") + 6;
                    int lastIndex = 0;
                    boolean isWhite = false;
                    if (line.contains("/wiki/")) {
                        isWhite = true;
                    }
                    if (line.charAt(firstIndex - 1) == '\'') {
                        lastIndex = line.indexOf("\'", firstIndex);
                    } else if (line.charAt(firstIndex - 1) == '\"'){
                        lastIndex = line.indexOf("\"", firstIndex);
                    }

                    if (firstIndex >= 0 && lastIndex >= 0) {
                        counter++;
                        if (isWhite==true) {
                            System.out.println("https://en.wikipedia.org" + line.substring(firstIndex,lastIndex));
                            System.out.println(counter);
                            System.out.println("depth " + depth);
                            if (!url2.equals(line.substring(firstIndex,lastIndex)) && depth < 2) {
                                pull("https://en.wikipedia.org" + line.substring(firstIndex,lastIndex), depth+1);
                            }


                        } else if (line.substring(firstIndex,lastIndex).contains("http")){
                            System.out.println(line.substring(firstIndex,lastIndex));
                            System.out.println(counter);
                            System.out.println("depth " + depth);
                            if (!url2.equals(line.substring(firstIndex,lastIndex)) && depth < 2) {
                                pull(line.substring(firstIndex,lastIndex),depth+1);
                            }

                        }
//                        System.out.println("og  " + line);
                    }

                }

            }
            reader.close();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    public HtmlRead(){
        pull("https://en.wikipedia.org/wiki/Tom_Cruise",0);
    }



}
