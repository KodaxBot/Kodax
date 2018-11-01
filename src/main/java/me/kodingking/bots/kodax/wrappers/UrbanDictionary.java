package me.kodingking.bots.kodax.wrappers;

import me.kodingking.bots.kodax.util.WebUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UrbanDictionary {
    
    private static final JsonParser JSON_PARSER = new JsonParser();
    
    public static RequestResult define(String word) throws Exception {
        return new RequestResult(JSON_PARSER.parse(WebUtil.httpGet(String.format("http://api.urbandictionary.com/v0/define?term=%s", word))).getAsJsonObject());
    }
    
    public static class RequestResult {
        private String word, author, permalink, definition, example;
        private boolean errored;
        
        private RequestResult(JsonObject jsonObject) {
            if (jsonObject.has("list") && jsonObject.getAsJsonArray("list").size() > 0) {
                JsonObject obj = jsonObject.getAsJsonArray("list").get(0).getAsJsonObject();
                word = obj.has("word") ? obj.get("word").getAsString() : "Invalid word";
                author = obj.has("author") ? obj.get("author").getAsString() : "No author";
                permalink = obj.has("permalink") ? obj.get("permalink").getAsString() : "No link";
                definition = obj.has("definition") ? obj.get("definition").getAsString() : "No definition";
                example = obj.has("example") ? obj.get("example").getAsString() : "No example";
            } else {
                errored = true;
            }
        }
        
        public String getWord() {
            return word;
        }
        
        public String getAuthor() {
            return author;
        }
        
        public String getPermalink() {
            return permalink;
        }
        
        public String getDefinition() {
            return definition;
        }
        
        public String getExample() {
            return example;
        }
        
        public boolean isErrored() {
            return errored;
        }
    } 
    
}
