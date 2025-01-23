package test1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class Category {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;
}
