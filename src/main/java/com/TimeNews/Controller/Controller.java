package com.TimeNews.Controller;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;


@RestController
@RequestMapping(value="/")
public class Controller {
	static class TimeLatest{
		String title,link;
		TimeLatest(String t, String l){
			title = t;
			link =l;
		}
	}
    @GetMapping(value="/getTimeStories", produces="application/json")
    @ResponseBody
    public String getTimeStories() {
    	ArrayList<TimeLatest> lt= new ArrayList<>();
		try {
		      Document doc = Jsoup.connect("https://www.time.com/").get();
		      Elements repositories = doc.select("ol.swipe-h>li");
		      for (Element repository : repositories) {
		        String repositoryTitle = repository.getElementsByClass("title").text();
		        String repositoryLink = repository.getElementsByTag("a").attr("href");
		        lt.add(new TimeLatest(repositoryTitle,repositoryLink));
		      }
		    // In case of any IO errors, we want the messages written to the console
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
    	return new Gson().toJson(lt);
    }    
}

