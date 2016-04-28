package model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "link")
    private String link;

    @XmlElement(name = "is_answered")
    private String isAnswered;

    @XmlElement(name = "score")
    private String score;

    @XmlElement(name = "creation_date")
    private String createdAt;

    public String getCreatedAt(){
        return createdAt;
    }

    public String getScore(){
        return score;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getIsAnswered(){
        return isAnswered;
    }
}
