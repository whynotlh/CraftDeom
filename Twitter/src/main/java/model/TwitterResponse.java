package model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterResponse {

    @XmlElement(name = "statuses")
    private List<Status> statuses;

    public List<Status> getStatues(){
        return this.statuses;
    }
}
