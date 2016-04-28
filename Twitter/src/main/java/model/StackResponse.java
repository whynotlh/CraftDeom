package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class StackResponse {

    @XmlElement(name = "quota_max")
    private int quota_max;

    @XmlElement(name = "items")
    private List<Question> items;

    public int getQuota_max() {
        return quota_max;
    }


    public List<Question> getItems(){
        return this.items;
    }

}
