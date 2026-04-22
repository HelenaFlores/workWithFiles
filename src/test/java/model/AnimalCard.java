package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimalCard {
    private Animal animal;
    private Marking marking;
    private Owner owner;
    private Passport passport;
    private List<String> permissions;
    private Region region;
    private VetStation vetStation;
    private HelppetMatch helppetMatch;
}
