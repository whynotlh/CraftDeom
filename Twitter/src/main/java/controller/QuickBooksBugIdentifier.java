package controller;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 4/28/16.
 */
public class QuickBooksBugIdentifier {

    private List<String> keywordFilter;

    public QuickBooksBugIdentifier(){
        keywordFilter = new ArrayList<String>();
        keywordFilter.add("bug");
        keywordFilter.add("Bug");
        keywordFilter.add("Exception");
        keywordFilter.add("Exceptions");
    }

    public JsonArray potentialBugFilter(List<JsonArray> inputLists){
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for(JsonArray jsonArray: inputLists){
            if(isPotentialBug(jsonArray)){
                builder.add(jsonArray);
            }
        }

        return builder.build();
    }

    /*

     */
    public boolean isPotentialBug(JsonArray inputString){
        String body_content = inputString.getString(0);
        if(containBugKeyWords(body_content, keywordFilter)){
            return true;
        }

        if(isComplain(body_content)){
            return true;
        }

        if(isSeriousUser(inputString.getString(2), inputString)){
            return true;
        }

        if(isSeriousQuestion(body_content)){
            return true;
        }

        return false;

    }

    public boolean containBugKeyWords(String content, List<String> keywordFilter){
        for(String s: keywordFilter){
            if(content.contains(s))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isComplain(String content){
        //To do: to be implemented
        return false;
    }

    public boolean isSeriousUser(String content, JsonArray userInfo){
        String userReputation = userInfo.getString(2);
        if(Integer.parseInt(userReputation) > 3000){
            return true;
        }
        return false;
    }

    public boolean isSeriousQuestion(String content){
        //To be implemented
        return true;
    }


}
