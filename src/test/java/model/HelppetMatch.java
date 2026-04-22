package model;

import lombok.Data;

@Data
public class HelppetMatch {
    private Integer id;
    private Integer animalId;
    private String helppetMatchUrl;
    private Boolean userCanDeleteMatch;
}
