package test1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder(toBuilder = true)
class Category extends Pet{
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public boolean equals(Object o) {
        Category category = (Category) o;
        return category.id == ((Category) o).id && category.name == ((Category) o).name;
    }

    public int hashCode() {
        return Objects.hash(id, name);
    }
}
