package test1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Pet {
    private int id;
    private String name;
    private String status;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;

    private Integer code;
    private String type;
    private String message="error";

    public Pet() {
    }

    @JsonCreator
    public Pet(@JsonProperty("id") int id,
               @JsonProperty("name") String name,
               @JsonProperty("status") String status,
               @JsonProperty("code") Integer code,
               @JsonProperty("type") String type,
               @JsonProperty("message") String message) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.code = code;
        this.type = type;
        this.message = message;
    }

    public static class Builder {
        private final int id;
        private final String name;
        private final String status;
        private Category category;
        private List<String> photoUrls;
        private List<Tag> tags;

        public Builder(int id, String name, String status) {
            this.id = id;
            this.name = name;
            this.status = status;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder photoUrls(List<String> photoUrls) {
            this.photoUrls = photoUrls;
            return this;
        }

        public Builder tags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Pet build() {
            Pet pet = new Pet();
            pet.id = this.id;
            pet.name = this.name;
            pet.status = this.status;
            pet.category = this.category;
            pet.photoUrls = this.photoUrls;
            pet.tags = this.tags;
            return pet;
        }
    }
}
